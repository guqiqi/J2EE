<template>
  <el-menu :default-active="default_active" class="el-menu-demo" mode="horizontal" router>
    <img style="width: 45px; height: 45px; float: left; margin-top: 10px; margin-left: 20px; margin-right: 20px"
         src="../assets/logo.png">
    <el-menu-item index="/home">首页</el-menu-item>
    <el-menu-item index="/order">我的订单</el-menu-item>
    <el-menu-item index="/address">地址管理</el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: "navigation",
    props: {default_active: String},
    mounted() {

    }
  }
</script>

<style scoped>

</style>
