<template>
    <div class="app-container">
        <alov-datatable
                ref="alovTable"
                :columns="columns"
                :list-url="remoteUrl"
                :entity-name="'MeasurementUnit'"
                @on-add-click="onAdd"
                @on-row-view="onView"
                @on-row-edit="onEdit"
                :on-row-delete="onDelete"
        >
        </alov-datatable>
        <dialog-form  :show-dialog.sync="showDialog"
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
                        <el-form-item label="Ölçü tanımı" prop="description">
                            <el-input v-model="form.description"></el-input>
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
    export default {
        name: "ListUnit",
        components:{DialogForm},
        data() {
            return {
                actionedRow:null,
                remoteUrl: '/unit-list',
                showDialog:false,
                isEdit:false,
                isView:false,
                formRules:{
                    description:[{required:true,message:'Ölçü tanımı girməlisiz',trigger:'blur'},],
                },
                form: {
                    id:null,
                    description:'',
                },
                columns: [
                    {
                        label: "id",
                        field: "id",
                        sortable: true
                    },
                    {
                        label: "Ölçü adı",
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
                const response =  await httpClient.get(`/unit/${this.actionedRow.id}`);
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
                 await httpClient.post(`/unit/delete/${row.id}`,null);
                 this.$refs['alovTable'].refresh();
            },
            async onSubmit(){
              const url = this.isEdit ? '/unit/edit' : '/unit';
              await httpClient.post(url, this.form);
              this.$refs['alovTable'].refresh();
            }
        }
    }
</script>
<style scoped>

</style>