<template>
  <div>
    <navigation default_active="/order"/>
    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-col :span="3">
        <user-navigation default_active="/order"/>
      </el-col>
      <el-col :span="20" style="margin-left: 10px; margin-right: 10px; ">
        <el-row
          style="font-size: 20px; font-weight: bold; text-align: left; padding-left: 20px; padding-bottom: 5px;border-bottom: #cecece 1px solid">
          我的订单
        </el-row>
        <el-row>
          <el-table
            :data="orderList.filter(data => !search || data.sellerName.toLowerCase().includes(search.toLowerCase()))"
            style="width: 100%">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-table
                  :data="props.row.foods"
                  style="width: 90%; margin-left: 5%; margin-top: 20px">
                  <el-table-column
                    prop="foodName"
                    label="商品名称"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="foodNumber"
                    label="购买数量"
                    align="right">
                  </el-table-column>
                  <el-table-column
                    prop="money"
                    label="单价"
                    align="right">
                  </el-table-column>
                  <el-table-column
                    label="总价"
                    align="right">
                    <template slot-scope="scope">
                      {{(scope.row.money * scope.row.foodNumber).toFixed(2)}}
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-table-column>
            <el-table-column
              sortable
              width="260px"
              prop="orderId"
              label="订单编号"/>
            <el-table-column
              sortable
              width="200px"
              prop="placeTime"
              label="下单时间"/>
            <el-table-column
              prop="sellerName"
              label="商家名称"/>
            <el-table-column
              prop="payMoney"
              label="实付金额"/>
            <el-table-column
              sortable
              label="订单状态">
              <template slot-scope="scope">
                {{getStatus(scope.row.status)}}
              </template>
            </el-table-column>
            <el-table-column align="right">
              <template slot="header" slot-scope="scope">
                <el-input
                  v-model="search"
                  size="mini"
                  placeholder="输入关键字搜索"/>
              </template>

              <template slot-scope="scope">
                <el-button v-if="scope.row.status === 0" type="text" @click="pay(scope.row.orderId)">付款</el-button>
                <el-button v-if="scope.row.status === 2" type="text" @click="confirm(scope.row.orderId)">确认收货
                </el-button>
                <el-button v-if="scope.row.status !== 3 && scope.row.status !== -1" type="text"
                           @click="cancel(scope.row.orderId)">取消订单
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-row>
      </el-col>
    </el-col>
  </div>
</template>

<script>
  import UserNavigation from "../../components/UserNavigation"

  const navigation = () => import('../../components/Navigation.vue')
  import global from '../../../static/Global'

  export default {
    name: "order-page",
    data() {
      return {
        orderList: [],
        search: ''
      }
    },
    components: {navigation, UserNavigation},
    methods: {
      getStatus: function (status) {
        console.log()
        if (status === -1)
          return '已取消'
        else if (status === 0)
          return '待付款'
        else if (status === 1)
          return '待配货'
        else if (status === 2)
          return '待收货'
        else if (status === 3)
          return '已完成'
      },
      pay: function (id) {
        // 付款
        for (let i = 0; i < this.orderList.length; i++) {
          if (this.orderList[i].orderId === id) {
            this.orderList[i].status = 1
            break
          }
        }

        this.$axios({
          method: 'patch',
          url: '/order/pay',
          params: {
            orderId: id,
          }
        }).then(response => {
          if (response.data.isSuccess) {
            this.$message.success("支付成功")
            this.getAllOrder()
          }
          else {
            this.$message.warning("系统繁忙，请稍后再试")
          }
        }).catch(function (err) {
          console.log(err)
        })


      },
      confirm: function (id) {
        // 确认收货
        for (let i = 0; i < this.orderList.length; i++) {
          if (this.orderList[i].orderId === id) {
            this.orderList[i].status = 3
            break
          }
        }

        this.$axios({
          method: 'patch',
          url: '/order/finish',
          params: {
            orderId: id,
          }
        }).then(response => {
          if (response.data.isSuccess) {
            this.$message.success("确认收货成功")
            this.getAllOrder()
          }
          else {
            this.$message.warning("系统繁忙，请稍后再试")
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      cancel: function (id) {
        // 取消订单
        for (let i = 0; i < this.orderList.length; i++) {
          if (this.orderList[i].orderId === id) {
            this.orderList[i].status = -1
            break
          }
        }

        this.$axios({
          method: 'patch',
          url: '/order/cancel',
          params: {
            orderId: id,
          }
        }).then(response => {
          if (response.data.isSuccess) {
            this.$message.success("取消订单成功")
            this.getAllOrder()
          }
          else {
            this.$message.warning("系统繁忙，请稍后再试")
          }
        }).catch(function (err) {
          console.log(err)
        })

      },
      getAllOrder: function () {
        this.$axios({
          method: 'get',
          url: '/order/customer/order',
          params: {
            email: global.userId,
          }
        }).then(response => {
          this.orderList = response.data.orders

          for (let i = 0; i < this.orderList.length; i++) {
            this.orderList[i].placeTime = global.formatDate(new Date(this.orderList[i].placeTime))
          }
        }).catch(function (err) {
          console.log(err)
        })
      }
    },
    mounted() {
      this.getAllOrder()
    }
  }
</script>

<style scoped>

</style>
