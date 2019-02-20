<template>
  <div>
    <navigation default_active="/home"/>

    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-col :span="10">
        <el-card body-style="padding: 0" style="margin-right: 10px">
          <el-row
            style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px; border-bottom: #cbcbcb 1px solid">
            订单详情
          </el-row>
          <el-table
            :data="foodList"
            style="width: 90%; margin-left: 5%">
            <el-table-column
              prop="name"
              label="商品名称"
              width="120"/>
            <el-table-column
              label="数量">
              <template slot-scope="scope">
                <el-input-number @change="addInCart(scope.row.foodId, scope.row.num)" v-model="scope.row.num"/>
              </template>
            </el-table-column>
            <el-table-column
              prop="sellerName"
              label="小计(元)"
              width="100">
              <template slot-scope="scope">
                ¥{{(scope.row.num * scope.row.discountMoney).toFixed(2)}}
              </template>
            </el-table-column>
          </el-table>

          <el-row style="padding-top: 10px; padding-bottom: 10px; border-top: #cbcbcb 1px solid">
            <el-col :span="16" style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px;">
              合计
            </el-col>
            <el-col :span="8" style="text-align: right; padding-right: 50px; font-size: 16px">
              ¥ {{total.toFixed(2)}}
            </el-col>
            <el-col :span="16" style="text-align: left; padding-left: 10px; margin-top: 4px; font-size: 15px;">
              优惠
            </el-col>
            <el-col :span="8" style="text-align: right; padding-right: 50px; margin-top: 4px; font-size: 15px">
              - ¥ {{discount.toFixed(2)}}
            </el-col>
            <el-col :span="24"
                    style="text-align: right; padding-right: 50px; font-size: 30px; font-weight: bold; color: red; margin-top: 30px; margin-bottom: 10px">
              ¥ {{(total - discount).toFixed(2)}}
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card body-style="padding: 0" style="margin-left: 10px">
          <el-row>
            <el-col
              style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;"
              :span="12">
              收货地址
            </el-col>
            <el-col :span="10" style="text-align: right; margin-right: 30px; padding-top: 13px; font-size: 18px">
              <span style="color: blue; font-size: 16px" @click="startAddAddress">[新增]</span>
            </el-col>
          </el-row>

          <div v-if="!allAddress">
            <el-card shadow="never" style="margin: 10px 20px;" body-style="paddingTop: 10px; paddingBottom: 10px">
              <el-row style="text-align: left">
                <span style="font-weight: bold">{{address.receiver}}</span> {{address.phone}}
              </el-row>
              <el-row style="text-align: left">
                {{address.detail}}
              </el-row>
            </el-card>

            <el-row style="color: #9e9e9e; text-align: left; margin: 10px 20px">
              <span @click="showAllAddress">其他地址</span>
              <i class="el-icon-arrow-down"/>
            </el-row>
          </div>

          <div v-else>
            <span v-for="item in addressList" @click="modifyAddress(item.addressId)">
              <el-card :shadow="item.addressId === address.addressId ? 'always' : 'never'"
                       style="margin: 10px 20px;" body-style="paddingTop: 10px; paddingBottom: 10px">
              <el-row style="text-align: left">
                <span style="font-weight: bold">{{item.receiver}}</span>  {{item.phone}}
              </el-row>
              <el-row style="text-align: left">
                {{item.detail}}
              </el-row>
            </el-card>
            </span>

          </div>
        </el-card>
        <el-card body-style="padding: 0" style="margin-left: 10px; margin-top: 20px">
          <el-row>
            <el-col
              style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;"
              :span="10">支付方式
            </el-col>
            <el-col :span="12" style="text-align: right; margin-right: 30px; padding-top: 13px; font-size: 18px">
              账户余额
            </el-col>
          </el-row>

          <el-row>
            <el-col
              style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;"
              :span="10">送达时间
            </el-col>
            <el-col :span="12" style="text-align: right; margin-right: 30px; padding-top: 5px; font-size: 18px">
              <el-time-select
                align="right"
                v-model="deliverTime"
                :picker-options="{start: this.deliverTime ,step: '00:15', end: '20:00'}"
                placeholder="选择时间">
              </el-time-select>
            </el-col>
          </el-row>

          <el-row>
            <el-button type="danger" style="width: 90%; margin-bottom: 10px; margin-top: 30px" @click="placeOrder">
              确认下单
            </el-button>
          </el-row>
        </el-card>
      </el-col>
    </el-col>

    <el-dialog
      title="支付提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span
        style="font-size: 20px; text-align: left">您需要支付 ¥{{(total - discount).toFixed(2)}}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelPay">取 消</el-button>
        <el-button type="primary" @click="payMoney">立即付款</el-button>
      </span>
    </el-dialog>

    <el-dialog title="地址信息" :visible.sync="addressDialogVisible">
      <el-form>
        <el-form-item label="收货人" label-width="80px">
          <el-input v-model="tempAddress.receiver" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="联系电话" label-width="80px">
          <el-input v-model="tempAddress.phone" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="收货地址" label-width="80px">
          <el-input v-model="tempAddress.detail" auto-complete="off"/>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="addAddress">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  const navigation = () => import('../../components/Navigation.vue')
  import global from '../../../static/Global'

  export default {
    name: "check-out",
    components: {navigation},
    data() {
      return {
        foodList: [],
        total: 0,
        discount: 0,

        address: {
          addressId: 1,
          detail: "南京大学",
          receiver: 'kiki',
          phone: '13770758178'
        },
        deliverTime: this.getFirstTime(),

        addressList: [],

        orderId: '',
        dialogVisible: false,

        allAddress: false,
        tempAddress: {
          detail: "",
          receiver: '',
          phone: ''
        },
        addressDialogVisible: false
      }
    },
    methods: {
      startAddAddress: function () {
        this.addressDialogVisible = true
        this.tempAddress = {
          detail: "",
          receiver: '',
          phone: ''
        }
      },
      addAddress: function () {
        this.addressDialogVisible = false

        this.$axios({
          method: 'post',
          url: '/user/address/add',
          data: {
            email: global.userId,
            receiver: this.tempAddress.receiver,
            phone: this.tempAddress.phone,
            detail: this.tempAddress.detail,
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
        this.addressDialogVisible = false
      },
      getFirstTime: function () {
        let t = new Date()
        t.setMinutes(t.getMinutes() + 30)
        return global.formatTime(t)
      },
      calculateTotalMoney: function () {
        let total = 0.0

        for (let i = 0; i < this.foodList.length; i++) {
          total += this.foodList[i].discountMoney * this.foodList[i].num
        }

        this.total = total

        let foodId = []
        let num = []

        for (let i = 0; i < this.foodList.length; i++) {
          foodId.push(this.foodList[i].foodId)
          num.push(this.foodList[i].num)
        }

        this.$axios({
          method: 'post',
          url: '/order/pre/place',
          data: {
            sellerId: this.$route.params.sellerId,
            email: global.userId,
            foodIds: foodId,
            amount: num
          }
        }).then(response => {
          let data_ = response.data

          if (data_.totalMoney === undefined) {
            this.$message({
              message: '抱歉，库存不足，请重新选择商品',
              type: 'warning'
            })
            this.$router.go(-1)
          }
          else {
            this.total = data_.totalMoney
            this.discount = data_.totalMoney - data_.payMoney
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      addInCart: function (id, num) {
        if (num === 0) {
          for (let i = 0; i < this.foodList.length; i++) {
            if (this.foodList[i].foodId === id) {
              this.foodList.splice(i, 1)
              break
            }
          }
        }

        this.calculateTotalMoney()
      },
      showAllAddress: function () {
        this.allAddress = true
      },
      modifyAddress: function (addressId) {
        for (let i = 0; i < this.addressList.length; i++) {
          if (this.addressList[i].addressId === addressId) {
            this.address = this.addressList[i]
            break
          }
        }
      },
      placeOrder: function () {
        this.dialogVisible = true
        // TODO 收货时间
        let foodId = []
        let num = []

        for (let i = 0; i < this.foodList.length; i++) {
          foodId.push(this.foodList[i].foodId)
          num.push(this.foodList[i].num)
        }

        this.$axios({
          method: 'post',
          url: '/order/place',
          data: {
            sellerId: this.$route.params.sellerId,
            email: global.userId,
            foodIds: foodId,
            amount: num,
            reachTime: this.deliverTime,
            addressId: this.address.addressId
          }
        }).then(response => {
          let data_ = response.data

          if (data_.totalMoney === undefined) {
            this.$router.go(-1)
          }
          else {
            this.total = data_.totalMoney
            this.discount = data_.totalMoney - data_.payMoney
            this.orderId = data_.orderId
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      payMoney: function () {
        // 付款
        this.dialogVisible = false

        this.$axios({
          method: 'get',
          url: '/order/pay',
          params: {
            orderId: this.orderId
          }
        }).then(response => {
          let data_ = response.data

          if (data_.isSuccess) {
            this.$message({
              message: '您已成功付款，请等待商家准备商品并配送',
              type: 'success'
            })

            this.$router.push('/home')
          }
          else {
            this.$message({
              message: '抱歉，您的账户余额不足，请充钱后，请至「我的订单」处进行付款',
              type: 'warning'
            })
            this.$router.push('/order')
          }
        }).catch(function (err) {
          console.log(err)
        })

      },
      cancelPay: function () {
        // 取消付款，此时已经下单，但是需要去我的订单那边付款
        this.dialogVisible = false

        this.$message({
          message: '您已取消付款，如需进行付款，请至「我的订单」处进行付款',
          type: 'warning'
        })

        this.$router.push('/order')
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
          this.address = this.addressList[0]

        }).catch(function (err) {
          console.log(err)
        })
      }
    },
    mounted() {
      console.log(this.$route.params)
      this.foodList = this.$route.params.foodList

      // 计算优惠
      this.calculateTotalMoney()

      this.getAllAddress()
    }
  }
</script>

<style scoped>

</style>
