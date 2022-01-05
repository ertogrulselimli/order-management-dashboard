<template>
    <div class="app-container">
        <alov-datatable
                ref="dataTable"
                :columns="columns"
                :list-url="remoteUrl"
                :entity-name="'Product'"
        >
        </alov-datatable>
    </div>
</template>
<script>
    export default {
        name: "ListProduct",
        data() {
            return {
                remoteUrl: '/product-list',
                columns: [
                    {
                        label: "id",
                        field: "id",
                        sortable: true
                    },
                    {
                        label: "name",
                        field: "name",
                    },
                    {
                        label: "description",
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
