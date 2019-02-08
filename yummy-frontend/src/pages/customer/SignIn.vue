<template>
  <div class="sign-in">
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
      <router-link to="/signUp">注册</router-link>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        email: '',
        password: ''
      }
    },
    methods:{
      signIn: function(){
        // TODO,验证登陆
        this.$axios({
          method: 'post',
          // url:'../static/test/getInfo.json', //<---本地地址
          url: '/user/login',
          data:{
            userId: this.email,
            password: this.password
          }
        }).then(response=>{
          console.log(response)
        }).catch(function(err){
          console.log(err)
        })

        // this.$router.push('/home')
      }
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
