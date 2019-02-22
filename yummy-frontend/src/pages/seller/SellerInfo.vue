<template>
  <div>
    <seller-navigation default_active="/seller/info"/>
    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-row>
        <el-col :span="10" class="title">
          商店名称
        </el-col>
        <el-col :span="14" class="content">
          {{name}}
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10" class="title">
          店铺头像
        </el-col>
        <el-col :span="14" class="content">
          <img :src="icon" width="180px" height="180px"/>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10" class="title">
          商店类型
        </el-col>
        <el-col :span="14" class="content">
          {{type}}
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10" class="title">
          商店地址
        </el-col>
        <el-col :span="14" class="content">
          {{address}}
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
          营业时间
        </el-col>
        <el-col :span="14" class="content">
          {{startHour}} - {{endHour}}
        </el-col>
      </el-row>
      <el-row>
        <el-button type="primary" style="width: 70%; margin-top: 30px" @click="modifyInfo">编辑信息</el-button>
      </el-row>
      <!--<el-row>-->
      <!--<el-button type="danger" style="width: 70%; margin-top: 20px" @click="writeOff">注销用户</el-button>-->
      <!--</el-row>-->
    </el-col>
  </div>
</template>

<script>
  import global from '../../../static/Global'
  import SellerNavigation from "../../components/SellerNavigation"

  export default {
    components: {SellerNavigation},
    name: "seller-info",
    data() {
      return {
        name: '',
        type: '',
        address: '',
        phone: '',
        startHour: '',
        endHour: '',
        icon: '',
        password: ''
      }
    },
    methods: {
      modifyInfo: function () {
        this.$router.push('/seller/edit')
      }
    },
    mounted() {
      this.$axios({
        method: 'get',
        url: '/seller/info',
        params: {
          sellerId: global.userId
        }
      }).then(response => {
        let data_ = response.data
        this.name = data_.name
        this.type = data_.type
        this.address = data_.address
        this.phone = data_.phone
        this.startHour = global.formatTime(new Date(data_.startHour))
        this.endHour = global.formatTime(new Date(data_.endHour))
        this.password = data_.password
        this.icon = data_.icon
      }).catch(function (err) {
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
    margin-top: 30px;
  }
</style>
