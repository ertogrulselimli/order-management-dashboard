<template>
    <div class="createPost-container" v-loading="screenLoading"
         element-loading-text="Loading..."
         element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(0, 0, 0, 0.8)">
        <el-form ref="baseForm"
                 :model="form"
                 :rules="rules"
                 class="form-container"
                 :label-position="labelPosition"
                 :disabled="isView">
            <sticky v-if="!isView" :z-index="10" :class-name="'sub-navbar published'">
                <el-button style="margin-left: 10px;" type="success" @click.prevent="handleSubmit">
                    Saxla
                </el-button>
                <el-button type="warning" @click="$router.go(-1)">
                    Ləğv et
                </el-button>
            </sticky>
            <div class="createPost-main-container">
                <slot name="form-items" :form="form">

                </slot>
            </div>
        </el-form>
    </div>
</template>

<script>
    import Sticky from '@/components/Sticky'
    import {Message} from 'element-ui'

    export default {
        name: "index",
        props: {
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
            Sticky
        },
        data() {
            return {
                screenLoading: false,
            }
        },
     async  created(){
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
            }
        },
        mounted() {
        },
        methods: {
            async handleSubmit() {
                    this.$refs['baseForm'].validate(async (valid)=>{
                       try{
                        if(valid) {
                            this.screenLoading = true;
                            await this.onSubmit();
                            this.screenLoading = false;
                            const message = this.isEdit ? 'Successfully updated' : 'Successfully Added';
                            this.$notify({
                                title: 'Success',
                                message: message,
                                type: 'success',
                                duration: 2000
                            });
                            const currentRoute=this.$route;
                            const previousRoute=this.$previousRoute;
                            await this.$router.replace({name: previousRoute.name});
                            await this.$store.dispatch('tagsView/delView', currentRoute);
                        }else{
                            return false;
                        }}catch (error) {
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
