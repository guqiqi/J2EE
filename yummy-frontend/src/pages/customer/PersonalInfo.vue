<template>
  <div>
    <navigation default_active="/info"/>
    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-col :span="3">
        <user-navigation default_active="/info"/>
      </el-col>
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
          <el-col :span="10" class="title">
            积分
          </el-col>
          <el-col :span="14" class="content">
            {{point}}
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" class="title">
            等级
          </el-col>
          <el-col :span="14" class="content">
            {{level}}
          </el-col>
        </el-row>
        <el-row>
          <el-button type="primary" style="width: 70%; margin-top: 30px" @click="modifyInfo">编辑信息</el-button>
        </el-row>
        <el-row>
          <el-button type="danger" style="width: 70%; margin-top: 20px" @click="writeOff">注销用户</el-button>
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
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelModify">取 消</el-button>
        <el-button type="primary" @click="confirmModify">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import UserNavigation from "../../components/UserNavigation"
  import ElRow from "element-ui/packages/row/src/row"

  const navigation = () => import('../../components/Navigation.vue')
  // import {navigation} from '../components/Navigation'
  export default {
    name: "personal-info",
    data() {
      return {
        username: 'kiki',
        phone: '137000000000',
        point: 2300,
        level: '黄金会员',
        dialogFormVisible: false,
        tempUsername: this.username,
        tempPhone: this.phone
      }
    },
    components: {
      ElRow,
      navigation, UserNavigation
    },
    methods: {
      modifyInfo: function () {
        this.dialogFormVisible = true
      },
      cancelModify: function () {
        this.dialogFormVisible = false
        this.tempUsername = this.username
        this.tempPhone = this.phone
      },
      confirmModify: function () {
        // TODO save
        this.username = this.tempUsername
        this.phone = this.tempPhone
        this.dialogFormVisible = false
      },
      writeOff: function () {
        // TODO save
      }
    },
    mounted() {
      this.tempUsername = this.username
      this.tempPhone = this.phone
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
    margin-top: 30px;
  }
</style>
