<template>
  <div>
    <navigation default_active="/order"/>
    订单页
  </div>
</template>

<script>
  const navigation = () => import('../components/Navigation.vue')
  // import {navigation} from '../components/Navigation'
  export default {
    name: "order-page",
    data(){
      return{

      }
    },
    components:{navigation}
  }
</script>

<style scoped>

</style>
