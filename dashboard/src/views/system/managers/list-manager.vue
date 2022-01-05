<template>
    <div class="app-container">
        <alov-datatable
                ref="dataTable"
                :columns="columns"
                :list-url="remoteUrl"
                :entity-name="'Manager'"
                :on-row-delete="deleteManager"
        >
            <template v-slot:actions-field="props">
                <el-button v-if="props.row.active" v-permission="'ROLE_SUPERADMIN'" icon="el-icon-lock"
                           type="danger" size="mini" @click="blockManager(props.row)">
                    block
                </el-button>
                <el-button v-else type="success" v-permission="'ROLE_SUPERADMIN'" icon="el-icon-unlock"
                           size="mini" @click="blockManager(props.row,'unblock')">
                    unblock
                </el-button>
            </template>
            <template v-slot:active="props">
                <div>
                    <i v-if="props.row.active" class="el-icon-success"/>
                    <i v-else class="el-icon-error"/>
                </div>
            </template>
        </alov-datatable>
    </div>
</template>
<script>
    import httpClient from '@/utils/request'
    import {dataTableMixin} from "@/mixins/datatable-mixin";

    export default {
        name: "ListManager",
        mixins:[dataTableMixin],
        data() {
            return {
                remoteUrl: '/manager-list',
                columns: [
                    {
                        label: "id",
                        field: "id",
                        sortable: true
                    },
                    {
                        label: "İstifadəçi adı",
                        field: "username",
                    },
                    {
                        label: "Email",
                        field: "email",
                        sortable: false
                    },
                    {
                        label: 'Ad',
                        field: 'firstname'
                    },
                    {
                        label: 'Soyad',
                        field: 'lastname'
                    },
                    {
                        label: 'aktiv',
                        field: 'active',
                        columnStyles: {width: "6px"}
                    }
                ]
            }
        },
        mounted() {

        },
        methods: {
            async blockManager(row, flag) {
                const confirMessage = `Are you sure to ${flag ? 'unblock' : 'block'} manager ${row.username} `;
                this.$confirm(confirMessage, 'Warning', {
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(async () => {
                    if (!flag) {
                        await httpClient.post(`/manager/${row.id}/block`);
                    } else {
                        await httpClient.post(`/manager/${row.id}/unblock`)
                    }
                    this.$refs['alovTable'].refresh();
                });
            },

            async deleteManager(row) {
                await httpClient.post(`/manager/delete/${row.id}`);
            }
        }
    }
</script>

<style scoped>

</style>
