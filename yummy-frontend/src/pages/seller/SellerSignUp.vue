<template>
  <div class="sign-up">
    <el-row>
      <img src="../../assets/logo.png">
    </el-row>
    <seller-edit-info :isSignUp="isSignUp"/>
  </div>
</template>

<script>
  import SellerEditInfo from "../../components/SellerEditInfo"

  export default {
    components: {
      SellerEditInfo,
    },
    data() {
      return {
        isSignUp: true
      }
    },
    methods: {}
  }
</script>

<style scoped>
  .sign-up {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 20px;
  }
</style>
