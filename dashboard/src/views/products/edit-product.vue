<template>
    <product-form :is-edit=true></product-form>
</template>

<script>
    import ProductForm from './components/product-form'
    export default {
        name: 'EditProduct',
        components: {ProductForm}
    }
</script>

<style scoped>

</style>
