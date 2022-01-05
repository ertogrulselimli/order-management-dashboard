<template>
    <div v-loading.fullscreen.lock="fullscreenLoading"
         element-loading-text="Loading..."
         element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(0, 0, 0, 0.8)">
        <div class="filter-container">
            <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit"
                       @click.prevent="handleAddClick">
                Əlavə et
            </el-button>
            <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download"
                       @click.prevent="handleExcelExport">
                Excel
            </el-button>
            <el-button :loading="isLoading" class="filter-item" type="primary" icon="el-icon-refresh"
                       @click.prevent="refresh">
                Yenilə
            </el-button>
        </div>
        <vue-good-table
                mode="remote"
                @on-page-change="onPageChange"
                @on-sort-change="onSortChange"
                @on-column-filter="onColumnFilter"
                @on-per-page-change="onPerPageChange"
                @on-search="onSearch"
                :is-loading.sync="isLoading"
                :total-rows="totalRecords"
                :pagination-options="{enabled: true,}"
                :search-options="searchOptions"
                :rows="rows"
                :columns="cols"
                :rtl="false"
        >
            <template v-slot:table-actions>
                <slot name="table-actions"></slot>
                <el-dropdown>
                    <el-button type="primary"><i class="el-icon-setting" aria-hidden="true"></i></el-button>
                    <el-dropdown-menu>
                        <el-dropdown-item v-for="(column, index) in cols" :key="index">
                            <a href="#" class="small" @click.prevent="toggleColumn(index, $event)" tabIndex="-1">
                                <input :checked="!column.hidden" type="checkbox"/>&nbsp;{{column.label}}</a>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </template>
            <template v-slot:table-row="props">
                <div align="right" v-if="(props.column.field=='actions')&&(actions)">
                    <slot name="actions-field" v-bind="props">

                    </slot>
                    <slot v-if="viewButton" name="view-button" v-bind="props">
                        <el-button v-permission="viewPermission || `Edit${entityName}` || `View${entityName}`" icon="el-icon-view" type="info"
                                   size="mini" @click="viewItemClick(props.row)">
                            Bax
                        </el-button>
                    </slot>
                    <slot v-if="editButton" name="edit-button" v-bind="props">
                        <el-button v-permission="editPermission || `Edit${entityName}`" icon="el-icon-edit"
                                   type="primary" size="mini" @click="editItemClick(props.row)">
                            Dəyiş
                        </el-button>
                    </slot>
                    <slot v-if="deleteButton" name="delete-button" v-bind="props">
                        <el-button icon="el-icon-delete" v-permission="deletePermission||`Delete${entityName}`"
                                   size="mini" type="danger" @click="deleteItemClick(props.row)">
                            Sil
                        </el-button>
                    </slot>
                </div>
                <slot v-else :name="props.column.field" v-bind="props">
                    <div v-bind="props.column.columnStyles"> {{props.formattedRow[props.column.field]}}</div>
                </slot>
            </template>
            <template v-slot:pagination-bottom="props">
                <pagination v-show="props.total>0" :total="props.total" :per-page-changed="props.perPageChanged"
                            :page-changed="props.pageChanged" :page-sizes="pageSizes"/>
            </template>
        </vue-good-table>
    </div>
</template>

<script>

    /* if mode is client you should provide rows  */
    import httpCient from '@/utils/request.js'
    import _ from 'lodash';
    import Pagination from '@/components/Pagination';
    import {MessageBox, Message} from 'element-ui'

    export default {
        name: 'AlovDatatable',
        props: {
            entityName: {
                type: String,
                required: true
            },
            deletePermission: {
                type: String,
                required: false //if not delete button by itself is constructed this is not requireemnt
            },
            viewPermission: {
                type: String,
                required: false
            },
            editPermission: {
                type: String,
                required: false
            },
            onRowDelete: {
                type: Function,
                required: false
            },
            editButton: {
                type: Boolean,
                required: false,
                default: true
            },
            deleteButton: {
                type: Boolean,
                required: false,
                default: true
            },
            viewButton: {
                type: Boolean,
                required: false,
                default: true
            },
            actions: {
                type: Boolean,
                required: false,
                default: true,
            },
            mode: {
                type: String,
                required: false,
                default: 'server'/* if mode set to server remoteUrl must be specified  */
            },
            listUrl: {
                required: false
            },
            columns: {
                type: Array,
                required: true
            },
            pageSizes: {
                type: Array,
                default() {
                    return [10, 20, 30, 50, 100]
                }
            },
            addSearch: {
                type: Boolean,
                default: true
            },
            searchText: {
                type: String,
                default: 'Search'
            }
        },
        components: {Pagination, MessageBox, Message},
        data() {
            const searchOptions = {enabled: this.addSearch, placeholder: this.searchText}
            let cols = this.columns.map(c => {
                if (!c.sortable) {
                    c['sortable'] = false;
                }
                return c;
            });
            if (this.actions) {
                cols = cols.concat({
                    label: 'Actions',
                    field: 'actions',
                    sortable: false,
                    width: '25%',
                    tdClass: 'text-center'
                })
            }
            return {
                searchOptions,
                fullscreenLoading: false,
                downloadLoading: false,
                total: 0,
                cols,
                rows: this.rows || [],
                totalRecords: 0,
                isLoading: false,
                serverParams: {
                    columnFilters: {},
                    sortOptions: [],
                    page: 0,
                    perPage: 10,
                    searchTerm: ''
                },
            }
        },
        created() {
        },

        mounted() {
            console.log('On mount of Datatable')
            console.log(this.rows);
            this.loadItems();
        },

        methods: {
            refresh(){
                this.serverParams.page=0;
                this.serverParams.searchTerm='';
                this.serverParams.columnFilters={}
                this.loadItems();
            },
            deleteItemClick(row) {
                this.$confirm('Bu obyekti birdəfəlik siləcək. Davam edirsiniz?', 'Warning', {
                    confirmButtonText: 'Bəli',
                    cancelButtonText: 'Ləğv et',
                    type: 'warning'
                }).then(async () => {
                    this.fullscreenLoading = true;
                    await this.onRowDelete(row);
                    this.fullscreenLoading = false;
                    Message.success({duration: 1500, showClose: true, center: true, message: 'Uğurla silindi'});
                    this.refresh();
                }).catch((error) => {
                    this.fullscreenLoading = false;
                    const errorResp=error.response && error.response.data;
                    if(errorResp && errorResp.errorCode ==1001) {
                        this.$message({
                            type: 'error',
                            message: errorResp.errorMessage,
                            duration:2000
                        });
                    }else{
                        this.$message({
                            type:'info',
                            message:'Silinmə ləğv olundu'
                        });
                    }
                });
            },

            editItemClick(row) {
                if (this.$listeners && this.$listeners['on-row-edit']) {
                    this.$emit('on-row-edit', row);
                } else {
                    this.$router.push({name: `Edit${this.entityName}`, params: {id: row.id}});
                }
            },

            viewItemClick(row) {
                if (this.$listeners && this.$listeners['on-row-view']) {
                    this.$emit('on-row-view', row);
                } else {
                    this.$router.push({name: `View${this.entityName}`, params: {id: row.id}});
                }
            },

            handleAddClick() {
                if (this.$listeners && this.$listeners['on-add-click']) {
                    this.$emit('on-add-click');
                } else {
                    this.$router.push({name: `New${this.entityName}`})
                }
            },

            toggleColumn(index, event) {
                // Set hidden to inverse of what it currently is
                this.$set(this.cols[index], 'hidden', !this.cols[index].hidden);
            },

            handleExcelExport() {
                this.downloadLoading = true
                const tHeader = this.cols.filter(c => c.field != 'actions').map(t => t.field);
                const data = this.formatJson(tHeader, this.rows);
                import('@/vendor/Export2Excel').then(excel => {
                    excel.export_json_to_excel({
                        header: tHeader,
                        data,
                        filename: 'table-list'
                    });
                    this.downloadLoading = false
                });
            },

            formatJson(filterVal, data) {
                return data.map(v => filterVal.map(j => {
                    return v[j];
                }))
            },

            loadItems() {
                this.isLoading = true;
                httpCient.post(this.listUrl, this.serverParams).then(response => {
                    this.isLoading = false;
                    this.totalRecords = response.data.totalElements;
                    this.rows = response.data.content;
                }).catch(error => {
                    const errorResp = error.response;
                    this.isLoading = false;
                    Message({
                        message: errorResp.data.errorMessage,
                        type: 'error',
                        duration: 5 * 1000
                    });
                });
            },

            updateParams(newProps) {
                this.serverParams = Object.assign({}, this.serverParams, newProps);
            },

            onPageChange(params) {
                this.updateParams({page: params.currentPage - 1});
                this.loadItems();
            },
            onPerPageChange(params) {
                this.updateParams({perPage: params.currentPerPage});
                this.loadItems();
            },
            onSortChange(params) {
                this.updateParams({
                    sortOptions: params,
                });
                this.loadItems();
            },
            onColumnFilter(params) {
                this.updateParams(params);
                this.loadItems();
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
