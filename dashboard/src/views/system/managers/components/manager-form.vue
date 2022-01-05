<template>
    <base-form :form="form"
               :on-submit="handleSubmit"
               :is-view="isView"
               :is-edit="isEdit"
               :rules="formRules"
               :loader-func="loaderFunc"
    >
        <template v-slot:form-items="{form}">
            <el-input type="hidden" v-if="isEdit" v-model="form.id"/>
            <el-row :gutter="100">
                <el-col :span="12">
                    <el-form-item label="Ad" prop="firstname">
                        <el-input v-model="form.firstname"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Soyad" prop="lastname">
                        <el-input v-model="form.lastname"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="100" v-if="!(isEdit||isView)">
                <el-col :span="12">
                    <el-form-item label="İstifadəçi adı" prop="username" v-if="!(isEdit||isView)">
                        <el-input v-model="form.username"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item v-if="!(isEdit||isView)" label="parol" prop="password">
                        <el-input  ref="password" :type="passwordType" v-model="form.password"/>
                        <span class="show-pwd" @click="showPwd">
                          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
                        </span>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="100">
                <el-col :span="12">
                    <el-form-item label="Email" prop="email">
                        <el-input v-model="form.email"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="rol" prop="roleId">
                        <el-select v-model="form.roleId" placeholder="Rol seçin" @change="onChangeRole" clearable>
                            <el-option v-for="(item) in roles" :key="item.id" :label="item.description"
                                       :value="item.id"/>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="100">
                <el-col :span="12">
                    <el-form-item v-if="selectedRole && selectedRole.name==='ROLE_MANUFACTURER'"
                                  label="İstehsal nöqtəsi" prop="manufacturePointId">
                        <el-select v-model="form.manufacturePointId" clearable placeholder="Istehsal Nöqtəsini  seçin">
                            <el-option v-for="(item) in manufacturePoints" :key="item.id" :label="item.name"
                                       :value="item.id"/>
                        </el-select>
                    </el-form-item>
                    <el-form-item v-else-if="selectedRole && selectedRole.name==='ROLE_DISTRIBUTOR'"
                                  label="Distributor" prop="distributorId">
                        <el-select v-model="form.distributorId" clearable placeholder="Distributoru seçin">
                            <el-option v-for="(item) in distributors" :key="item.id" :label="item.name"
                                       :value="item.id"/>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
        </template>
    </base-form>
</template>

<script>
    import BaseForm from '@/components/BaseForm'
    import httpClient from '@/utils/request';
    import FR,{RuleMessages} from '@/utils/form-rule-constants'

    export default {
        name: "manager-form",
        components: {BaseForm},
        props: {
            isEdit: {
                type: Boolean,
                default: false,
                required: false
            },
            isView: {
                type: Boolean,
                default: false,
                required: false
            }
        },
        data() {
            return {
                passwordType:'password',
                formProperties: {
                    labelPosition: "'top'"
                },
                selectedRole: null,
                roles: [],
                manufacturePoints: [],
                distributors: [],
                loading: false,
                rolesUrl: '/manager-roles',
                rolesCols: [
                    {field: 'id', label: 'Id'},
                    {field: 'name', label: 'name'},
                    {field: 'description', label: 'Description'}
                ],
                formRules: {
                    username: [
                        {required: true, message: RuleMessages.required_field, trigger: 'blur'},
                        {min: 3, message: 'Length must be greater than 3', trigger: 'blur'},
                    ],
                    password: [FR.required],
                    firstname: [FR.required],
                    lastname: [FR.required],
                    email: [FR.required]
                },
                showDialog: false,
                form: {
                    username: '',
                    email: '',
                    firstname: '',
                    lastname: '',
                    password: '',
                    roleId: null,
                    manufacturePointId: null,
                    distributorId: null
                }
            }
        },


        async beforeMount() {
            const rsp = await httpClient.get('/mnfp-select')
            this.manufacturePoints = rsp.data;
            const response = await httpClient.get(`/roles-select`);
            this.roles = response.data;
            const rs = await httpClient.get('/dstb-select');
            this.distributors = rs.data;
        },

        mounted() {

        },

        methods: {

            showPwd() {
                if (this.passwordType === 'password') {
                    this.passwordType = ''
                } else {
                    this.passwordType = 'password'
                }
                this.$nextTick(() => {
                    this.$refs.password.focus()
                })
            },
            async loaderFunc() {
                const response = await httpClient.get(`/manager/${this.$route.params.id}`);
                const rsp= response.data;
                this.form = Object.assign(this.form,rsp);
                this.selectedRole={id:rsp.roleId,name:rsp.roleName};
            },

            onChangeRole(val) {
                const filtredRoles = this.roles.filter(v => v.id === val);
                this.selectedRole = filtredRoles[0];
            },

            handleClose(r) {
                this.form.roles.splice(this.form.roles.indexOf(r), 1);
            },

            onRolesAdd(roles) {
                const combinedArray = [...this.form.roles, ...roles];
                this.form.roles = Array.from(new Set(combinedArray.map(a => a.id)))
                    .map(id => {
                        return combinedArray.find(a => a.id === id)
                    })
            },
            async handleSubmit() {
                const url = this.isEdit ? '/manager/update' : '/manager';
                await httpClient.post(url, this.form);
            }
        }
    }
</script>

<style lang="scss" scoped>
    .el-select {
        display: block;
    }
    .show-pwd {
        position: absolute;
        right: 10px;
        top: 7px;
        font-size: 16px;
        color: #3A71A8;
        cursor: pointer;
        user-select: none;
    }
</style>
