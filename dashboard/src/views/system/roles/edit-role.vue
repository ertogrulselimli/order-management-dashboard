<template>
    <role-form :is-edit="true"></role-form>
</template>

<script>
    import RoleForm from './components/role-form'

    export default {
        name: "EditRole",
        components:{RoleForm}
    }
</script>

<style scoped>

</style>
