<template>
  <div>
    <navigation/>
    首页
  </div>
</template>

<script>
  const navigation = () => import('../components/Navigation.vue')
  // import {navigation} from '../components/Navigation'
  export default {
    data(){
      return{

      }
    },
    components:{navigation}
  }
</script>

<style scoped>

</style>
