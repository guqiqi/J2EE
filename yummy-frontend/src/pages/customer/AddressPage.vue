<template>
  <div>
    <navigation default_active="/address"/>
    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-col :span="3">
        <user-navigation default_active="/address"/>
      </el-col>
      <el-col :span="20" style="margin-left: 10px; margin-right: 10px; ">
        <el-row
          style="font-size: 20px; font-weight: bold; text-align: left; padding-left: 20px; padding-bottom: 5px;border-bottom: #cecece 1px solid">
          地址管理
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="8" v-for="address in addressList">
            <el-card shadow="never" body-style="padding: 10px" style="margin: 5px">
              <el-row style="text-align: left; margin-left: 5px; margin-top: 5px">
                <el-col :span="14">{{address.receiver}}</el-col>
                <el-col :span="5">
                  <el-button @click="modifyAddress(address.addressId)" size="mini" type="primary">修改</el-button>
                </el-col>
                <el-col :span="5">
                  <el-button @click="deleteAddress(address.addressId)" size="mini" type="danger">删除</el-button>
                </el-col>
              </el-row>
              <el-row style="text-align: left; margin-left: 5px; margin-top: 5px">
                {{address.detail}}
              </el-row>
              <el-row style="text-align: left; margin-left: 5px; margin-top: 5px">
                {{address.phone}}
              </el-row>
            </el-card>
          </el-col>
        </el-row>
        <el-row>
          <el-card shadow="never" body-style="padding: 10px"
                   style="margin: 5px; padding-top: 17px; padding-bottom: 17px">
            <span style="font-weight: bold; font-size: 35px;" @click="addAddress">新增</span>
          </el-card>
        </el-row>
      </el-col>
    </el-col>

    <el-dialog title="地址信息" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="收货人" label-width="80px">
          <el-input v-model="tempReceiver" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="联系电话" label-width="80px">
          <el-input v-model="tempPhone" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="收货地址" label-width="80px">
          <el-input v-model="tempDetail" auto-complete="off"/>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button v-if="isModify" type="primary" @click="confirmModify">确 定</el-button>
        <el-button v-else type="primary" @click="confirmAdd">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import UserNavigation from "../../components/UserNavigation"
  import global from '../../../static/Global'

  const navigation = () => import('../../components/Navigation.vue')
  // import {navigation} from '../components/Navigation'
  export default {
    name: "address-page",
    data() {
      return {
        isModify: true,
        dialogFormVisible: false,
        tempId: 1,
        tempDetail: '',
        tempPhone: '',
        tempReceiver: '',
        addressList: []
      }
    },
    components: {
      UserNavigation,
      navigation
    },
    methods: {
      modifyAddress: function (id) {
        this.isModify = true
        this.tempId = id

        for (let i = 0; i < this.addressList.length; i++) {
          if (this.addressList[i].addressId === id) {
            this.tempDetail = this.addressList[i].detail
            this.tempPhone = this.addressList[i].phone
            this.tempReceiver = this.addressList[i].receiver
          }
        }

        this.dialogFormVisible = true
      },
      deleteAddress: function (id) {
        this.$axios({
          method: 'delete',
          url: '/user/address/delete',
          params: {
            addressId: id
          }
        }).then(response => {
          let data_ = response.data

          if (data_.isSuccess) {
            this.getAllAddress()
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      cancel: function () {
        this.dialogFormVisible = false
      },
      confirmModify: function () {
        this.$axios({
          method: 'post',
          url: '/user/address/modify',
          data: {
            email: global.userId,
            receiver: this.tempReceiver,
            phone: this.tempPhone,
            detail: this.tempDetail,
            addressId: this.tempId
          }
        }).then(response => {
          let data_ = response.data

          if (data_.isSuccess) {
            this.getAllAddress()
          }
        }).catch(function (err) {
          console.log(err)
        })
        this.dialogFormVisible = false
      },
      addAddress: function () {
        this.isModify = false

        this.tempDetail = ''
        this.tempPhone = ''
        this.tempReceiver = ''

        this.dialogFormVisible = true
      },
      confirmAdd: function () {
        // add
        this.dialogFormVisible = false

        this.$axios({
          method: 'post',
          url: '/user/address/add',
          data: {
            email: global.userId,
            receiver: this.tempReceiver,
            phone: this.tempPhone,
            detail: this.tempDetail,
          }
        }).then(response => {
          let data_ = response.data

          if (data_.isSuccess) {
            this.getAllAddress()
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      getAllAddress: function () {
        this.$axios({
          method: 'get',
          url: '/user/address/all',
          params: {
            email: global.userId
          }
        }).then(response => {
          let data_ = response.data

          this.addressList = data_.addressList

        }).catch(function (err) {
          console.log(err)
        })
      }
    },
    mounted() {
      this.getAllAddress()
    }
  }
</script>

<style scoped>

</style>
