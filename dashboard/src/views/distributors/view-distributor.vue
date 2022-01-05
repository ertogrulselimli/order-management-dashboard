<template>
    <distributor-form :is-view="true"/>
</template>

<script>
    import DistributorForm from './components/distributor-form'
    export default {
        name: "ViewDistributor",
        components:{DistributorForm}
    }
</script>

<style scoped>

</style>
