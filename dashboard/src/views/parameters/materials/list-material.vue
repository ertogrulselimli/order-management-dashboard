<template>
    <div class="app-container">
        <alov-datatable
                ref="alovTable"
                :columns="columns"
                :list-url="remoteUrl"
                :entity-name="'RawMaterial'"
                @on-add-click="onAdd"
                @on-row-view="onView"
                @on-row-edit="onEdit"
                :on-row-delete="onDelete"
        >
        </alov-datatable>
        <dialog-form  ref="dialogForm"
                      :show-dialog.sync="showDialog"
                      :on-submit="onSubmit"
                      :form="form"
                      :rules="formRules"
                      :is-edit="isEdit"
                      :is-view="isView"
                      :loader-func="loaderFunc"
        >
            <template v-slot:form-items="{form}">
                <el-row :gutter="100">
                    <el-col :span="12">
                        <el-input type="hidden" v-if="isEdit" v-model="form.id"/>
                        <el-form-item label="Ad" prop="name">
                            <el-input v-model="form.name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="Palet üçün">
                            <el-checkbox v-model="form.forPalet"></el-checkbox>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="100">
                    <el-col :span="12">
                        <el-form-item label="Açıqlama" prop="description">
                            <el-input v-model="form.description" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </template>
        </dialog-form>
    </div>
</template>

<script>
    import httpClient from '@/utils/request'
    import DialogForm from '@/components/DialogForm/DialogForm';
    const form={
        id:null,
        name:'',
        description:'',
        forPalet:false
    };
    export default {
        name: "ListMaterial",
        components:{DialogForm},
        data() {
            return {
                actionedRow:null,
                remoteUrl: '/material-list',
                showDialog:false,
                isEdit:false,
                isView:false,
                formRules:{
                    name:[{required: true,message:'Ad girməlisiniz',trigger: 'blur'}],
                },
                form: Object.assign(form,{}),
                columns: [
                    {
                        label: "id",
                        field: "id",
                        sortable: true
                    },
                    {
                        label: "Ad",
                        field: "name",
                        sortable: false
                    },
                    {
                        label: "Açıqlama",
                        field: "description",
                        sortable: false
                    }
                ]
            }
        },
        mounted() {

        },

        methods: {
            async loaderFunc(){
                const response =  await httpClient.get(`/material/${this.actionedRow.id}`);
                this.form=Object.assign(this.form,response.data);
            },
            onAdd(){
                this.isView=false;
                this.isEdit=false;
                this.showDialog=true;
            },
            onView(row){
                this.actionedRow=row;
                this.showDialog=true;
                this.isEdit=false;
                this.isView=true;
            },
            onEdit(row){
                this.actionedRow=row;
                this.showDialog=true;
                this.isView=false;
                this.isEdit=true;
            },

            async onDelete(row){
                await httpClient.post(`/material/delete/${row.id}`,null);
                this.$refs['alovTable'].refresh();
            },
            async onSubmit(){
                const url = this.isEdit ? '/material/edit' : '/material';
                await httpClient.post(url, this.form);
                this.$refs['alovTable'].refresh();
            }
        }
    }
</script>
<style scoped>

</style>