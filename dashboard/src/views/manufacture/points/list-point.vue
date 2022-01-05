<template>
    <div class="app-container">
        <alov-datatable
                ref="alovTable"
                :columns="columns"
                :list-url="remoteUrl"
                :entity-name="'ManufacturePoint'"
        >
        </alov-datatable>
    </div>
</template>
<script>
    import httpClient from '@/utils/request'
    export default {
        name: "ListManufacturePoint",
        data() {
            return {
                remoteUrl: '/mnfp-list',
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
