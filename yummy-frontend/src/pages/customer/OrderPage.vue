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
            :data="orderList"
            style="width: 100%">
            <el-table-column
              width="260px"
              prop="orderId"
              label="订单编号"/>
            <el-table-column
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
              label="订单状态">
              <template slot-scope="scope">
                {{getStatus(scope.row.status)}}
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button v-if="scope.row.status === 0" type="text" @click="pay(scope.row.orderId)">付款</el-button>
                <el-button v-if="scope.row.status === 2" type="text" @click="confirm(scope.row.orderId)">确认收货</el-button>
                <el-button v-if="scope.row.status !== 3 && scope.row.status !== -1" type="text" @click="cancel(scope.row.orderId)">取消订单</el-button>
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
  // import {navigation} from '../components/Navigation'
  export default {
    name: "order-page",
    data(){
      return{
        orderList: []
      }
    },
    components:{navigation, UserNavigation},
    methods: {
      getStatus: function (status) {
        console.log()
        if(status === -1)
          return '已取消'
        else if(status === 0)
          return '待付款'
        else if(status === 1)
          return '待配货'
        else if(status === 2)
          return '待收货'
        else if(status === 3)
          return '已完成'
      },
      pay: function (id) {
        // TODO 付款
        for(let i = 0; i < this.orderList.length; i++){
          if(this.orderList[i].orderId === id){
            this.orderList[i].status = 1
            break
          }
        }
      },
      confirm: function (id) {
        // TODO 确认收货
        for(let i = 0; i < this.orderList.length; i++){
          if(this.orderList[i].orderId === id){
            this.orderList[i].status = 3
            break
          }
        }


      },
      cancel: function (id) {
        // TODO 取消订单
        for(let i = 0; i < this.orderList.length; i++){
          if(this.orderList[i].orderId === id){
            this.orderList[i].status = -1
            break
          }
        }
      },
      getAllOrder: function () {
        this.$axios({
          method: 'get',
          url: '/order/customer/order',
          params: {
            email: "222",
          }
        }).then(response => {
          this.orderList = response.data.orders

          for(let i = 0; i < this.orderList.length; i++){
            this.orderList[i].placeTime = global.formatDate(new Date(this.orderList[i].placeTime))
          }
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
