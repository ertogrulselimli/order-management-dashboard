<template>
<el-dialog ref="formDialog" :visible.sync="visible" v-loading="screenLoading"
           element-loading-text="Loading..."
           element-loading-spinner="el-icon-loading">
        <el-form ref="baseForm"
                 :model="form"
                 :rules="rules"
                 class="form-container"
                 :label-position="labelPosition"
                 :disabled="isView">
                <slot name="form-items" :form="form">

                </slot>
        </el-form>
     <span slot="footer" class="dialog-footer">
            <el-button @click="closeDialog">Bağla</el-button>
            <el-button  v-if="!isView"  type="primary"  @click.prevent="handleSubmit"> Yadda Saxla </el-button>
     </span>
</el-dialog>
</template>

<script>
    import {Message} from 'element-ui'

    export default {
        name: "DialogForm",
        props: {
            showDialog:{
                type:Boolean,
                default:false
            },
            labelPosition:{
                type:String,
                required:false,
                default:"top"

            },
            rules: {
                type: Object,
                required: false
            },
            loaderFunc:{
                type:Function,
                required:false
            },
            viewUrl:{
                type:String,
                required:false
            },
            form: {
                type: Object,
                required: true
            },
            isEdit: {
                type: Boolean,
                required: false,
                default: false
            },
            isView: {
                type: Boolean,
                required: false,
                default: false
            },
            onSubmit: {
                type: Function,
                required: true
            }
        },
        components: {
        },
        data() {
            return {
                screenLoading: false,
            }
        },
        computed:{
            visible: {
                // getter
                get: function () {
                    return this.showDialog
                },
                // setter
                set: function (newValue) {
                    this.$emit('update:showDialog',false);
                }
            }
        },
        watch:{
            visible:async function (newVisible,oldVisible) {
                if(newVisible==true){
                    if (this.isEdit ||this.isView) {
                        this.screenLoading = true;
                        try{
                            await this.loaderFunc();
                            this.screenLoading=false;
                        }catch (e) {
                            const errorResp = e.response;
                            if(errorResp.status==400 && errorResp.data.errorCode==1001){
                                //This means it is standard error you can catch these here
                                Message({
                                    message: errorResp.data.errorMessage,
                                    type: 'error',
                                    duration: 5 * 1000
                                });
                            }
                            this.screenLoading=false;
                        }
                    }else{
                        //this is add operation so call //resetFields
                        this.resetForm();
                    }
                }
            }
        },
        mounted() {
        },
        methods: {
            closeDialog(){
                this.visible=false;
            },

            resetForm(){
               this.$refs['baseForm'].resetFields();
            },

            async handleSubmit() {
                this.$refs['baseForm'].validate(async (valid)=>{
                    try{
                        if(valid) {
                            this.screenLoading = true;
                            await this.onSubmit();
                            this.screenLoading = false;
                            const message = this.isEdit ? 'Successfully updated' : 'Successfully Added';
                            this.closeDialog();
                            this.resetForm();
                            this.$notify({
                                title: 'Success',
                                message: message,
                                type: 'success',
                                duration: 2000
                            });
                        }else{
                            return false;
                        }
                    }catch (error) {
                        this.screenLoading=false;
                        const errorResp = error.response;
                        Message({
                            message: errorResp.data.errorMessage,
                            type: 'error',
                            duration: 5 * 1000
                        });
                    }
                })
            }
        },
    }
</script>

<style lang="scss" scoped>
    @import "~@/styles/mixin.scss";

    .createPost-container {
        position: relative;

        .createPost-main-container {
            padding: 40px 45px 20px 50px;
        }

        .word-counter {
            width: 40px;
            position: absolute;
            right: 10px;
            top: 0px;
        }
    }
</style>
