<template>
    <distributor-form :is-edit=true></distributor-form>
</template>

<script>
    import DistributorForm from './components/distributor-form'
    export default {
        name: 'EditDistributor',
        components: {DistributorForm}
    }
</script>

<style scoped>

</style>
