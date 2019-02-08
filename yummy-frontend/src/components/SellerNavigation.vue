<template>
  <el-menu :default-active="default_active" class="el-menu-demo" mode="horizontal" router>
    <img style="width: 45px; height: 45px; float: left; margin-top: 10px; margin-left: 20px; margin-right: 20px"
         src="../../static/images/logo.png" @click="toHome">
    <el-menu-item index="/seller/home">首页</el-menu-item>
    <el-menu-item index="/seller/order">我的订单</el-menu-item>
    <el-menu-item index="/seller/statistic">经营统计</el-menu-item>
    <el-menu-item index="/seller/info">个人中心</el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: "seller-navigation",
    props: {default_active: String},
    mounted() {

    },
    methods: {
      toHome: function () {
        this.$router.push('/seller/home')
      }
    },
  }
</script>

<style scoped>

</style>
