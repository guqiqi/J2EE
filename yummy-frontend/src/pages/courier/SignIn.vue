<template>
  <div class="sign-in">
    <el-row>
      <img src="../../assets/logo.png">
    </el-row>
    <el-row>
      <el-input
        placeholder="电话"
        v-model="phone"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row>
      <el-input
        placeholder="密码"
        v-model="password"
        type="password"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row style="margin-top: 40px; margin-bottom: 10px">
      <el-button type="primary" style="width: 300px" @click="signIn">登陆</el-button>
    </el-row>
    <el-row>
      <span style="color: gray">
        没有账号？立即
      </span>
      <router-link to="/courier/signUp">注册</router-link>
    </el-row>
  </div>
</template>

<script>
  import global from '../../../static/Global'

  export default {
    data() {
      return {
        phone: '',
        password: ''
      }
    },
    methods:{
      signIn: function(){
        // 验证登陆
        this.$axios({
          method: 'post',
          url: '/courier/login',
          data:{
            phone: this.phone,
            password: this.password
          }
        }).then(response=>{
          if(response.data.isValid) {
            global.setUserId(response.data.mes)
            this.$router.push('/courier/home')
          }
          else {
            this.$alert(response.data.mes, '错误', {
              confirmButtonText: '确定',
              type: 'error'
            });
          }
        }).catch(function(err){
          console.log(err)
        })
      },
    }
  }
</script>

<style scoped>
  .sign-in {
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
