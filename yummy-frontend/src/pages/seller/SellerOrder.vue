<template>
  <div>
    <seller-navigation default_active="/seller/order"/>
    <el-col :span="20" :offset="2" style="margin-top: 30px">
      <el-row>
        <el-table
          :data="orderList"
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <div>
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-row>
                    <el-form-item label="收货地址">
                      <span>{{ props.row.detail }}</span>
                    </el-form-item>
                  </el-row>
                  <el-col :span="12">
                    <el-form-item label="收货人">
                      <span>{{ props.row.receiver }}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="联系电话">
                      <span>{{ props.row.phone }}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="下单时间">
                      <span>{{ props.row.placeTime }}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="预计送达时间">
                      <span>{{ props.row.receiveTime }}</span>
                    </el-form-item>
                  </el-col>
                </el-form>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            width="230px"
            prop="orderId"
            label="订单编号"/>
          <el-table-column
            prop="placeTime"
            label="下单时间"/>
          <el-table-column
            prop="receiveTime"
            label="预计送达时间"/>
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
              <el-button v-if="scope.row.status === 1" type="text" @click="deliver(scope.row.orderId)">开始配送</el-button>
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
  import ElRow from "element-ui/packages/row/src/row"

  export default {
    components: {
      ElRow,
      SellerNavigation
    },
    name: "seller-order",
    data() {
      return {
        orderList: []
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
        //开始配送
        for (let i = 0; i < this.orderList.length; i++) {
          if (this.orderList[i].orderId === id) {
            this.orderList[i].status = 2
            break
          }
        }

        this.$axios({
          method: 'patch',
          url: '/order/deliver',
          params: {
            orderId: id,
          }
        }).then(response => {
          if (response.data.isSuccess) {
            this.$message.success("开始配送")
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
        // 确认送达
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
      getAllOrder: function () {
        this.$axios({
          method: 'get',
          url: '/order/seller/order',
          params: {
            sellerId: global.userId
          }
        }).then(response => {
          this.orderList = response.data.orders
          for(let i = 0; i < this.orderList.length; i++){
            this.orderList[i].placeTime = global.formatDate(new Date(this.orderList[i].placeTime))
            this.orderList[i].receiveTime = global.formatDate(new Date(this.orderList[i].receiveTime))
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
