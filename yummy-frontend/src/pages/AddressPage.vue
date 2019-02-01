<template>
  <div>
    <navigation default_active="/address"/>
    地址页
  </div>
</template>

<script>
  const navigation = () => import('../components/Navigation.vue')
  // import {navigation} from '../components/Navigation'
  export default {
    name: "address-page",
    data(){
      return{

      }
    },
    components:{navigation}
  }
</script>

<style scoped>

</style>
