<template>
    <base-form :form="form"
               :on-submit="handleSubmit"
               :is-view="isView"
               :is-edit="isEdit"
               :rules="formRules"
               :loader-func="loaderFunc"
    >
        <template v-slot:form-items="{form}">
            <el-row :gutter="100">
                <el-col :span="12">

                    <el-form-item label="Ad" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">

                    <el-form-item label="Region" prop="region">
                        <el-input v-model="form.region"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="100">
                <el-col :span="12">
                    <el-form-item label="Açıqlama">
                        <el-input v-model="form.description" type="textarea"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-input type="hidden" v-if="isEdit" v-model="form.id"/>
        </template>
    </base-form>
</template>

<script>
    import BaseForm from '@/components/BaseForm'
    import httpClient from '@/utils/request';

    export default {
        name: "point-form",
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
                loading: false,
                formRules:{
                    name:[
                        {required:true,message:'name is required',trigger:'blur'},
                        {min:3,message:'Length must be greater than 3',trigger:'blur'},
                    ],
                    description:[{required:true,message:'Description is required',trigger:'blur'},],
                },
                form: {
                    id:null,
                    name:'',
                    description:'',
                    region:''
                }
            }
        },

        mounted() {
        },
        methods: {

           async loaderFunc(){
               const response =  await httpClient.get(`/manufacturePoint/${this.$route.params.id}`);
               this.form=Object.assign(this.form,response.data);
            },

            async handleSubmit() {
                const url = this.isEdit ? '/manufacturePoint/edit' : '/manufacturePoint';
                await httpClient.post(url, this.form);
            }
        }
    }
</script>

<style lang="scss" scoped>
</style>
