<template>
  <div>
    <navigation default_active="/info"/>
    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-col :span="3">
        <user-navigation default_active="/customer/statistic"/>
      </el-col>
      <el-col :span="20" style="margin-left: 10px; margin-right: 10px; ">
        <el-row
          style="font-size: 20px; font-weight: bold; text-align: left; padding-left: 20px; padding-bottom: 5px;border-bottom: #cecece 1px solid">
          消费统计
        </el-row>
      </el-col>
    </el-col>
  </div>

</template>

<script>
  import UserNavigation from "../components/UserNavigation"

  const navigation = () => import('../components/Navigation.vue')

  export default {
    name: "customer-statistic",
    components: {
      UserNavigation,
      navigation
    }
  }
</script>

<style scoped>

</style>
