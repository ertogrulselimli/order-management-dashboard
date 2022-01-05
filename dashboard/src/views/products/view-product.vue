<template>
    <product-form :is-view="true"/>
</template>

<script>
    import ProductForm from './components/product-form'
    export default {
        name: "ViewProduct",
        components:{ProductForm}
    }
</script>

<style scoped>

</style>
