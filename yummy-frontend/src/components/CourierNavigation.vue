<template>
  <el-menu :default-active="default_active" class="el-menu-demo" mode="horizontal" router>
    <img style="width: 45px; height: 45px; float: left; margin-top: 10px; margin-left: 20px; margin-right: 20px"
         src="../../static/images/logo.png" @click="toHome">
    <el-menu-item index="/courier/home">我的订单</el-menu-item>
    <el-menu-item index="/courier/info">个人中心</el-menu-item>

    <el-menu-item style="position: fixed; right: 10px" index="/courier/signIn">
      <el-button type="primary" size="small">
        登出
      </el-button>
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: "navigation",
    props: {default_active: String},
    mounted() {

    },
    methods: {
      toHome: function () {
        this.$router.push('/courier/home')
      }
    }
  }
</script>

<style scoped>

</style>
