<template>
  <div style="margin-top: 100px">
    <el-row class="text">
      您已提交申请，申请进展可登陆系统查看
    </el-row>
    <el-row class="text">
      您的商户号为 <span style="color: #f72d20; font-weight: bold">{{sellerId}}</span>
    </el-row>
    <el-row style="font-size: 24px">
      <span style="color: #f72d20;">请记住您的商户号，以后需使用商户号<router-link to="/seller/signIn">登陆系统</router-link></span>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "seller-sign-prompt",
    data() {
      return {
        sellerId: this.$route.params.sellerId,
      }
    },
    mounted(){
      console.log(this.$route)
      console.log(this.$route.params)
    }
  }
</script>

<style scoped>
  .text {
    font-size: 30px;
    margin: 20px;
  }
</style>
