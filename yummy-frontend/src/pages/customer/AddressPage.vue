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
                <el-col :span="16">{{address.receiver}}</el-col>
                <el-col :span="4">
                  <span @click="modifyAddress(address.addressId)" style="color: #9e9e9e">修改</span>
                </el-col>
                <el-col :span="4">
                  <span @click="deleteAddress(address.addressId)" style="color: #9e9e9e">删除</span>
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

          <el-col :span="8">
            <el-card shadow="never" body-style="padding: 10px" style="margin: 5px; padding-top: 17px; padding-bottom: 17px">
              <span style="font-weight: bold; font-size: 35px;" @click="addAddress">新增</span>
            </el-card>
          </el-col>
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
        addressList: [
          {
            addressId: 1,
            detail: '南京大学',
            phone: '1283949455',
            label: 1,
            receiver: 'kiki'
          },
          {
            addressId: 2,
            detail: '南京大学',
            phone: '1283949455',
            label: 1,
            receiver: 'kiki'
          },
          {
            addressId: 3,
            detail: '南京大学',
            phone: '1283949455',
            label: 1,
            receiver: 'kiki'
          },
          {
            addressId: 4,
            detail: '南京大学',
            phone: '1283949455',
            label: 1,
            receiver: 'kiki'
          }
        ]
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
        for (let i = 0; i < this.addressList.length; i++) {
          if (this.addressList[i].addressId === id) {
            this.addressList.splice(i, 1)
            console.log(this.addressList)
          }
        }
        console.log(id)
        // TODO delete
      },
      cancel: function () {
        this.dialogFormVisible = false
      },
      confirmModify: function () {
        for (let i = 0; i < this.addressList.length; i++) {
          if (this.addressList[i].addressId === this.tempId) {
            this.addressList[i].detail = this.tempDetail
            this.addressList[i].phone = this.tempPhone
            this.addressList[i].receiver = this.tempReceiver
          }
        }
        // TODO 修改
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
      //  TODO add
        this.dialogFormVisible = false

        this.addressList.push({
          addressId: 10,
          detail: this.tempDetail,
          phone: this.tempPhone,
          label: 1,
          receiver: this.tempReceiver
        })
      }
    }
  }
</script>

<style scoped>

</style>
