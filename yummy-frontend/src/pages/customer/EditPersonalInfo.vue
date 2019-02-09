<template>
  <div style="margin-top: 80px">
    <el-row style="font-weight: bold; font-size: 25px">
      欢迎注册Yummy平台,请完善个人信息
    </el-row>
    <el-col :span="10" :offset="7">
      <el-form label-position="left">
        <el-form-item label="昵称" label-width="80px" style="margin-top: 30px">
          <el-input v-model="username" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="联系电话" label-width="80px" style="margin-top: 30px">
          <el-input v-model="phone" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="登陆密码" label-width="80px" style="margin-top: 30px">
          <el-input v-model="password" type="password" auto-complete="off"/>
        </el-form-item>
      </el-form>

      <el-button type="primary" style="width: 100%; margin-top: 30px" @click="commit">提交</el-button>
    </el-col>

  </div>
</template>

<script>
  import global from '../../../static/Global'
  export default {
    name: "edit-personal-info",

    data() {
      return {
        username: '',
        phone: '',
        password: ''
      }
    },
    methods: {
      commit: function () {
        this.$axios({
          method: 'post',
          url: '/user/add',
          data:{
            email: this.$route.query.token,
            username: this.username,
            password: this.password,
            phone: this.phone
          }
        }).then(response=>{
          console.log(response)
          if(response.data.isSuccess){
            global.setUserId(response.data.email)
            this.$router.push('/home')
          }
          else {
            alert('添加失败')
          }
        }).catch(function(err){
          console.log(err)
        })
      }
    },

    mounted() {
      console.log(this.$route.query)
    }
  }
</script>

<style scoped>

</style>
