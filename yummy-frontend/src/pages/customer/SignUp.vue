<template>
  <div class="sign-up" :v-loading="isLoading">
    <el-row :v-loading="isLoading">
      <img src="../../assets/logo.png">
    </el-row>
    <el-row :v-loading="isLoading">
      <el-input
        placeholder="邮箱"
        v-model="email"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row style="margin-top: 40px" :v-loading="isLoading">
      <el-button type="primary" style="width: 300px" @click="sendEmail">发送邮件</el-button>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        email: '',
        isLoading: false
      }
    },
    methods: {
      sendEmail: function () {
        this.isLoading = true
        this.$axios({
          method: 'post',
          url: '/mail/send',
          data:{
            email: this.email
          }
        }).then(response=>{
          this.isLoading = false
          if(response.data.isSuccess){
            this.$alert('邮件已发送到您到邮箱，请点击链接进行验证', '提示', {
              confirmButtonText: '确定',
            });
          }
          else {
            alert('发送失败')
          }
        }).catch(function(err){
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
    margin-top: 50px;
  }
</style>
