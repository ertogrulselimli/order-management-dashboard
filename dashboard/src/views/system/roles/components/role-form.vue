<template>
    <base-form :is-view="isView"
               :is-edit="isEdit"
               :form="roleForm"
               :on-submit="handleSubmit"
               :loader-func="loaderFunc"
               :rules="rules"
    >
        <template v-slot:form-items="{form}">
            <el-input v-if="isEdit" type="hidden" v-model="form.id"/>
            <el-row :gutter="100">
                <el-col :span="12">
                    <el-form-item label="Role name" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Is Menu Role">
                        <el-checkbox v-model="form.isMenuRole"></el-checkbox>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="100">
                <el-col :span="12">
                    <el-form-item label="Role Description">
                        <el-input
                                type="textarea"
                                :rows="2"
                                placeholder="Please input"
                                v-model="form.description">
                        </el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row v-if="form.isMenuRole">
                <el-col :span="24">
                    <el-card align="center">
                        <el-button @click="openMenuDialog">add/Revoke menus</el-button>
                    </el-card>
                    <el-table :data="form.menus" border fit highlight-current-row style="width: 100%">
                        <el-table-column label="Menu Name">
                            <template slot-scope="{row}">
                                <span>{{ row.name }}</span>
                            </template>
                        </el-table-column>
                        <template slot="empty">
                            No menus
                        </template>
                    </el-table>
                </el-col>
            </el-row>
            <calculate-menus v-if="showDialog"
                             :accessed-menus="form.menus"
                             :show-dialog.sync="showDialog"
                             @on-apply-menus="applyMenus"
            />
        </template>
    </base-form>
</template>

<script>
    import CalculateMenus from '@/views/system/calculate-menus.vue';
    import httpClient from '@/utils/request';
    import BaseForm from '@/components/BaseForm'

    export default {
        name: "role-form",
        props: {
            isEdit: {
                type: Boolean,
                required: false,
                default: false
            },
            isView: {
                type: Boolean,
                required: false,
                default: false
            }
        },

        components: {CalculateMenus, BaseForm},
        data() {
            return {
                showDialog: false,
                rules: {
                    name: [{required: true, message: 'name is required'},{min:5,message:'must be more than 5 character'}],
                },
                roleForm: {
                    name: '',
                    description: '',
                    isMenuRole: true,
                    menus: []
                }
            }
        },
        methods: {

            applyMenus(menus) {
                this.roleForm.menus = menus;
            },

            openMenuDialog() {
                this.showDialog = true;
            },

            async loaderFunc() {
                const id = this.$route.params && this.$route.params.id
                const response = await httpClient.get(`/manager-roles/${id}`);
                this.roleForm = Object.assign(this.roleForm, response.data);
            },
            async handleSubmit() {
                const url = this.isEdit ? '/manager-role/update' : '/manager-role';
                await httpClient.post(url, this.roleForm);
            }
        }
    }
</script>
<style scoped>
</style>
