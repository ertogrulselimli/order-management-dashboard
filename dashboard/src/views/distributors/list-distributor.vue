<template>
    <div class="app-container">
        <alov-datatable
                ref="alovTable"
                :columns="columns"
                :list-url="remoteUrl"
                :entity-name="'Distributor'"
        >
        </alov-datatable>
    </div>
</template>
<script>
    import httpClient from '@/utils/request'
    export default {
        name: "ListDistributor",
        data() {
            return {
                remoteUrl: '/dst-list',
                columns: [
                    {
                        label: "id",
                        field: "id",
                        sortable: true
                    },
                    {
                        label:'region',
                        field:'region',
                    },
                    {
                        label: "Ad",
                        field: "name",
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

        }
    }
</script>

<style scoped>

</style>
