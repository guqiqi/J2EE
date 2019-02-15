<template>
  <div>
    <seller-navigation default_active="/seller/order"/>
    <el-col :span="18" :offset="3" style="margin-top: 30px">
      <el-row>
        <el-table
          :data="orderList"
          style="width: 100%">
          <el-table-column
            prop="orderId"
            label="订单编号"/>
          <el-table-column
            prop="placeTime"
            label="下单时间"/>
          <el-table-column
            prop="sellerName"
            label="商家名称"/>
          <el-table-column
            prop="payMoney"
            label="实付金额"/>
          <el-table-column
            label="订单状态">
            <template slot-scope="scope">
              {{getStatus(scope.row.status)}}
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button v-if="scope.row.status === 1" type="text" @click="deliver(scope.row.orderId)">配送中</el-button>
              <el-button v-if="scope.row.status === 2" type="text" @click="confirm(scope.row.orderId)">已送达</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-row>
    </el-col>
  </div>
</template>

<script>
  import SellerNavigation from "../../components/SellerNavigation"
  import global from '../../../static/Global'

  export default {
    components: {SellerNavigation},
    name: "seller-order",
    data() {
      return {
        orderList: [
          {
            orderId: '203948586596',
            sellerName: '食其家',
            placeTime: '20190102',
            status: 2,
            payMoney: 200.10
          },
          {
            orderId: '203948586595',
            sellerName: '食其家',
            placeTime: '20190102',
            status: 1,
            payMoney: 200.10
          },
          {
            orderId: '203948586593',
            sellerName: '食其家',
            placeTime: '20190102',
            status: 3,
            payMoney: 200.10
          },
          {
            orderId: '203948586597',
            sellerName: '食其家',
            placeTime: '20190102',
            status: 2,
            payMoney: 200.10
          },
          {
            orderId: '203948586598',
            sellerName: '食其家',
            placeTime: '20190102',
            status: -1,
            payMoney: 200.10
          }
        ]
      }
    },
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
      deliver: function (id) {
        // TODO 开始配送
        for (let i = 0; i < this.orderList.length; i++) {
          if (this.orderList[i].orderId === id) {
            this.orderList[i].status = 2
            break
          }
        }
      },
      confirm: function (id) {
        // TODO 确认送达
        for (let i = 0; i < this.orderList.length; i++) {
          if (this.orderList[i].orderId === id) {
            this.orderList[i].status = 3
            break
          }
        }
      },
      getAllOrder: function () {
        this.$axios({
          method: 'get',
          url: '/order/seller/order',
          params: {
            sellerId: global.userId
          }
        }).then(response => {
          console.log(response.data.orders)
          this.orderList = response.data.orders
        }).catch(function (err) {
          console.log(err)
        })
      }
    },
    mounted(){
      this.getAllOrder()
    }
  }
</script>

<style scoped>

</style>
