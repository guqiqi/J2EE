<template>
  <div>
    <seller-navigation default_active="/seller/home"/>
  </div>
</template>

<script>
  import SellerNavigation from "../../components/SellerNavigation"

  export default {
    components: {SellerNavigation},
    name: "seller-home"
  }
</script>

<style scoped>

</style>
