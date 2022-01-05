<template>
    <div class="app-container">
        <alov-datatable
                :entity-name="'Role'"
                :list-url="listUrl"
                :columns="columns"
                :on-row-delete="deleteRole"
        >
        </alov-datatable>
    </div>
</template>

<script>
    import httpClient from '@/utils/request'

    export default {
        name: "ListRole",
        data() {
            return {
                listUrl: '/manager-roles',
                columns: [{
                    label: 'Id',
                    field: 'id',
                    sortable: true
                }, {
                    label: 'Name',
                    field: 'name'
                }, {
                    label: 'Description',
                    field: 'description'
                },
                ]
            }
        },
        methods: {
            async deleteRole(row) {
                try {
                    await httpClient.post(`/manager-role/delete/${row.id}`);
                } catch (e) {
                    //This is also sample to handle non standard errors
                    if (e.response.data && e.response.data.errorCode == 1002) {
                        let htmlString = '<ul>';
                        e.response.data.addInfo.forEach(c => {
                            htmlString += `<li>${c}</li>`
                        })
                        htmlString += '</ul>'
                        await this.$alert(htmlString, e.response.data.errorMessage, {
                            dangerouslyUseHTMLString: true
                        });
                        return Promise.reject(e);
                    } else {
                        return Promise.reject(e);
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
