<template>
    <manager-form :is-edit=true></manager-form>
</template>

<script>
    import ManagerForm from './components/manager-form'
    export default {
        name: 'EditManager',
        components: {ManagerForm}
    }
</script>

<style scoped>

</style>
