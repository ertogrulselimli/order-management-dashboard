<template>
    <el-dialog :visible.sync="visible">
          <vue-good-table
             mode="remote"
             ref="dialogDataTable"
             :columns="cols"
             :rows="rows"
             :total-rows="totalRecords"
             :is-loading.sync="isLoading"
             :search-options="{enabled:true,trigger:'enter'}"
             :select-options="{enabled:true}"
             @on-search="onSearch"
             @on-selected-rows-change="selectionChanged"
          />
        <span slot="footer" class="dialog-footer">
            <el-button @click="closeDialog">Cancel</el-button>
            <el-button type="primary" :disabled="selectedRows.length<1" @click="handleAddRows">Add</el-button>
         </span>
    </el-dialog>
</template>

<script>
    import  httpCient from '@/utils/request.js'
    import _ from 'lodash';

    export default {
        name:'DialogDataTable',
        props:{
          showDialog:{
              type:Boolean,
              default:false
          },
          searchUrl:{
              type:String,
              require:true
          },
            columns:{
                type:Array,
                required:true
            }
        },
        data(){
            return {
                selectedRows:[],
                rows:[],
                cols:this.columns,
                isLoading:false,
                totalRecords: 0,
                serverParams: {
                    page: 0,
                    perPage: 10,
                    searchTerm: ''
                },
            }
        },
        created(){
          console.log('Dialog Datatable created');
        },
        mounted() {
            this.loadItems();
        },
        computed:{
            visible: {
                get: function () {
                    return this.showDialog
                },
                set: function (newValue) {
                    this.$emit('update:showDialog',false);
                }
            }
        },
        watch:{
            visible:async function (newVisible,oldVisible) {
                console.log('In watcher of visible computed property ')
                console.log(newVisible);
                console.log(oldVisible);
            }
        },
        methods:{

            closeDialog(){
                this.$emit('update:showDialog',false);
            },

            selectionChanged(raw){
                this.selectedRows=raw.selectedRows;
            },

            loadItems() {
                this.isLoading = true;
                httpCient.post(this.searchUrl, this.serverParams).then(response => {
                    this.isLoading = false;
                    this.totalRecords = response.data.totalElements;
                    this.rows = response.data.content;
                });
            },


            onPageChange(params) {
                this.updateParams({page: params.currentPage - 1});
                this.loadItems();
            },

            onPerPageChange(params) {
                this.updateParams({perPage: params.currentPerPage});
                this.loadItems();
            },

            handleAddRows(){
                this.$emit('update:showDialog',false);
                this.$nextTick(()=>{
                    this.$emit('on-add-click',this.selectedRows);
                })

            },
            updateParams(newProps) {
                this.serverParams = Object.assign({}, this.serverParams, newProps);
            },

            onSearch: _.debounce(function (params) {
                this.updateParams(params);
                this.updateParams({page: 0});
                this.isLoading = true;
                this.loadItems();
            }, 500)
        }
    }
</script>

<style scoped>

</style>
