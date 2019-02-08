<template>
  <el-menu :default-active="default_active" class="el-menu-demo" mode="horizontal" router>
    <img style="width: 45px; height: 45px; float: left; margin-top: 10px; margin-left: 20px; margin-right: 20px"
         src="../../static/images/logo.png" @click="toHome">
    <el-menu-item index="/admin/verify">餐厅审核</el-menu-item>
    <el-menu-item index="/admin/cost">费用结算</el-menu-item>
    <el-menu-item index="/admin/statistic">统计数据</el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: "admin-navigation",
    props: {default_active: String},
    methods: {
      toHome: function () {
        this.$router.push('/admin/verify')
      }
    }
  }
</script>

<style scoped>

</style>
