<template>
  <el-menu :default-active="default_active" class="el-menu-vertical-demo" router>
    <el-menu-item index="/order">
      <i class="el-icon-menu"></i>
      <span slot="title">我的订单</span>
    </el-menu-item>
    <el-menu-item index="/address">
      <i class="el-icon-location-outline"></i>
      <span slot="title">地址管理</span>
    </el-menu-item>
    <el-menu-item index="/customer/statistic" >
      <i class="el-icon-document"></i>
      <span slot="title">统计信息</span>
    </el-menu-item>
    <el-menu-item index="/info" >
      <i class="el-icon-setting"></i>
      <span slot="title">个人中心</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: "user-navigation",
    props: {
      default_active: String
    }
  }
</script>

<style scoped>

</style>
