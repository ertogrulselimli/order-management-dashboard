<template>
    <manager-form :is-view="true"/>
</template>

<script>
    import ManagerForm from './components/manager-form'
    export default {
        name: "ViewManager",
        components:{ManagerForm}
    }
</script>

<style scoped>

</style>
