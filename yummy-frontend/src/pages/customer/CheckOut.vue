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
              {{address.detail}} <span style="color: blue; font-size: 16px" @click="modifyAddress">[修改]</span>
            </el-col>
          </el-row>

          <el-row>
            <el-col
              style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;"
              :span="12">
              收货人
            </el-col>
            <el-col :span="10" style="text-align: right; margin-right: 30px; padding-top: 13px; font-size: 18px">
              {{address.receiver}} <span style="color: blue; font-size: 16px" @click="modifyAddress">[修改]</span>
            </el-col>
          </el-row>

          <el-row>
            <el-col
              style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;"
              :span="12">
              联系电话
            </el-col>
            <el-col :span="10" style="text-align: right; margin-right: 30px; padding-top: 13px; font-size: 18px">
              {{address.phone}} <span style="color: blue; font-size: 16px" @click="modifyAddress">[修改]</span>
            </el-col>
          </el-row>

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
            <el-col :span="12" style="text-align: right; margin-right: 30px; padding-top: 13px; font-size: 18px">
              {{deliverTime}}
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
        deliverTime: global.formatTime(new Date()),

        orderId: '',
        dialogVisible: false
      }
    },
    methods: {
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

        // TODO 用户ID设置
        this.$axios({
          method: 'post',
          url: '/order/pre/place',
          data: {
            sellerId: this.$route.params.sellerId,
            email: '222',
            foodIds: foodId,
            amount: num
          }
        }).then(response => {
          let data_ = response.data

          this.total = data_.totalMoney
          this.discount = data_.totalMoney - data_.payMoney
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
      modifyAddress: function () {
        // TODO
        console.log("modify")
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
            email: '222',
            foodIds: foodId,
            amount: num,
            reachTime: new Date(),
            addressId: this.address.addressId
          }
        }).then(response => {
          let data_ = response.data

          this.total = data_.totalMoney
          this.discount = data_.totalMoney - data_.payMoney
          this.orderId = data_.orderId
        }).catch(function (err) {
          console.log(err)
        })
      },
      payMoney: function () {
        // TODO 付款
        this.dialogVisible = false

        // TODO 用户ID设置
        this.$axios({
          method: 'get',
          url: '/order/pay',
          params: {
            orderId: this.orderId
          }
        }).then(response => {
          let data_ = response.data

          if(data_.isSuccess){
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
        // TODO 取消付款，此时已经下单，但是需要去我的订单那边付款
        this.dialogVisible = false

        this.$message({
          message: '您已取消付款，如需进行付款，请至「我的订单」处进行付款',
          type: 'warning'
        })

        this.$router.push('/order')
      }
    },
    mounted() {
      console.log(this.$route.params)
      this.foodList = this.$route.params.foodList

      // 计算优惠
      this.calculateTotalMoney()


    }
  }
</script>

<style scoped>

</style>
