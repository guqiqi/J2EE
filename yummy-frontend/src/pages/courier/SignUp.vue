<template>
  <div class="sign-up" :v-loading="isLoading">
    <el-row :v-loading="isLoading">
      <img src="../../assets/logo.png">
    </el-row>
    <el-row :v-loading="isLoading">
      <el-input
        placeholder="昵称"
        v-model="name"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row :v-loading="isLoading">
      <el-input
        placeholder="电话"
        v-model="phone"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row :v-loading="isLoading">
      <el-input
        placeholder="密码"
        v-model="password"
        type="password"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row style="margin-top: 40px" :v-loading="isLoading">
      <el-button type="primary" style="width: 300px" @click="signUp">注册</el-button>
    </el-row>
  </div>
</template>

<script>
  import global from '../../../static/Global'

  export default {
    data() {
      return {
        phone: '',
        password: '',
        name: '',
        isLoading: false
      }
    },
    methods: {
      signUp: function () {
        this.isLoading = true
        this.$axios({
          method: 'post',
          url: '/courier/signUp',
          data: {
            phone: this.phone,
            password: this.password,
            username: this.namew
          }
        }).then(response => {
          this.isLoading = false
          if (response.data.isSuccess !== "该账号不存在" && response.data.isSuccess !== '账号密码不匹配') {
            global.setUserId(response.data.isSuccess)
            this.$router.push('/courier/home')
          }
          else {
            alert('发送失败')
          }
        }).catch(function (err) {
          console.log(err)
        })
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
    margin-top: 15px;
  }
</style>
