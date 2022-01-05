<template>
    <role-form :is-view="true"></role-form>
</template>

<script>
   import RoleForm from './components/role-form'
    export default {
        name: "ViewRole",
        components:{RoleForm}
    }
</script>

<style scoped>

</style>
