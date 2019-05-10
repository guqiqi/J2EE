<template>
  <div>
    <navigation default_active="/courier/info"/>
    <el-col :span="24" :offset="2" style="margin-top: 20px">
      <el-col :span="20" style="margin-left: 10px; margin-right: 10px; ">
        <el-row
          style="font-size: 20px; font-weight: bold; text-align: left; padding-left: 20px; padding-bottom: 5px;border-bottom: #cecece 1px solid">
          个人中心
        </el-row>
        <el-row>
          <el-col :span="10" class="title">
            昵称
          </el-col>
          <el-col :span="14" class="content">
            {{username}}
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" class="title">
            联系电话
          </el-col>
          <el-col :span="14" class="content">
            {{phone}}
          </el-col>
        </el-row>
        <el-row>
          <el-button type="primary" style="width: 70%; margin-top: 30px" @click="modifyInfo">编辑信息</el-button>
        </el-row>
      </el-col>
    </el-col>

    <el-dialog title="个人信息" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="昵称" label-width="80px">
          <el-input v-model="tempUsername" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="联系电话" label-width="80px">
          <el-input v-model="tempPhone" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="密码" label-width="80px">
          <el-input type="password" v-model="tempPassword" auto-complete="off"/>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelModify">取 消</el-button>
        <el-button type="primary" @click="confirmModify">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import ElRow from "element-ui/packages/row/src/row"
  import global from "../../../static/Global"

  const navigation = () => import('../../components/CourierNavigation.vue')
  export default {
    name: "personal-info",
    data() {
      return {
        username: '',
        phone: '',
        password: '',
        dialogFormVisible: false,
        tempUsername: this.username,
        tempPhone: this.phone,
        tempPassword: this.password,
      }
    },
    components: {
      ElRow,
      navigation
    },
    methods: {
      modifyInfo: function () {
        this.dialogFormVisible = true
        this.tempUsername = this.username
        this.tempPhone = this.phone
        this.tempPassword = this.password
      },
      cancelModify: function () {
        this.dialogFormVisible = false
        this.tempUsername = this.username
        this.tempPhone = this.phone
        this.tempPassword = this.password
      },
      confirmModify: function () {
        this.dialogFormVisible = false

        this.$axios({
          method: 'post',
          url: '/courier/modify',
          data:{
            courierId: global.userId,
            username: this.tempUsername,
            phone: this.tempPhone,
            password: this.tempPassword
          }
        }).then(response=>{
          let data_ = response.data

          this.username = this.tempUsername
          this.phone = this.tempPhone
          this.password = this.tempPassword

          if(data_.isSuccess){
            this.$alert('个人信息修改成功', '成功', {
              confirmButtonText: '确定',
              type: 'success'
            });
          }
        }).catch(function(err){
          console.log(err)
        })
      },
    },
    mounted() {
      console.log(global.userId)

      this.$axios({
        method: 'get',
        url: '/courier/info',
        params:{
          courierId: global.userId
        }
      }).then(response=>{
        let data_ = response.data
        this.username = data_.username
        this.phone = data_.phone
        this.password = data_.password
      }).catch(function(err){
        console.log(err)
      })
    }
  }
</script>

<style scoped>
  .title {
    font-size: 20px;
    font-weight: bold;
    margin-top: 20px;
  }

  .content {
    font-size: 20px;
    margin-top: 20px;
  }
</style>
