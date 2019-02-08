<template>
  <div class="sign-up">
    <el-row>
      <img src="../../assets/logo.png">
    </el-row>
    <el-row>
      <el-input
        placeholder="邮箱"
        v-model="email"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row style="margin-top: 40px">
      <el-button type="primary" style="width: 300px">发送邮件</el-button>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        email: ''
      }
    }
  }
</script>

<style scoped>
  .sign-up {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 100px;
  }

  .text-input {
    width: 300px;
    margin-top: 50px;
  }
</style>
