<template>
  <div>
    <seller-navigation default_active="/seller/statistic"/>
    <el-tabs v-model="activeName" type="border-card" style="width: 90%; margin-left: 5%; margin-top: 20px">
      <el-tab-pane label="消费统计" name="first">
        <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
          本周销售<span style="color: red"> {{recentVolume[0].toFixed(2)}} </span>元，本月销售<span
          style="color: red"> {{recentVolume[1].toFixed(2)}} </span>元，本年度销售<span style="color: red"> {{recentVolume[2].toFixed(2)}} </span>元
        </el-row>
        <el-row class="chart_title" style="margin-top: 40px">
          销售活跃时间表
        </el-row>
        <div id="sellByHour" :style="{width: '90%', height: '400px', marginLeft: '5%'}"></div>

        <el-row class="chart_title" style="margin-top: 20px">
          具体销售情况
        </el-row>
        <el-table
          :data="sellData"
          border
          show-summary
          style="width: 90%; margin-left: 5%; margin-top: 20px">
          <el-table-column
            prop="name"
            label="用户昵称"
            align="center">
          </el-table-column>
          <el-table-column
            sortable
            prop="times"
            label="消费次数"
            align="right">
          </el-table-column>
          <el-table-column
            sortable
            prop="cost"
            label="消费金额"
            align="right">
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="退订统计" name="second">
        <el-table
          :data="cancelData"
          border
          show-summary
          style="width: 90%; margin-left: 5%; margin-top: 20px">
          <el-table-column
            prop="name"
            label="商家名称"
            align="center">
          </el-table-column>
          <el-table-column
            sortable
            prop="placeTime"
            width="250px"
            label="下单时间"
            align="center">
          </el-table-column>
          <el-table-column
            prop="cancelTime"
            width="250px"
            label="退款时间"
            align="center">
          </el-table-column>
          <el-table-column
            sortable
            prop="payMoney"
            label="支付费用"
            align="right">
          </el-table-column>
          <el-table-column
            sortable
            prop="returnMoney"
            label="退款费用"
            align="right">
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import SellerNavigation from "../../components/SellerNavigation"
  import global from '../../../static/Global'

  export default {
    components: {SellerNavigation},
    name: "seller-statistic",
    data() {
      return {
        activeName: 'first',

        recentVolume: [0, 2, 1],
        sellByHourData: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1],

        sellData: [],

        cancelData: []
      }
    },
    methods: {
      drawSellByHour: function () {
        let sellByHour = this.$echarts.init(document.getElementById('sellByHour'))

        sellByHour.setOption({
          xAxis: {
            data: ["0~1", "2~3", "4~5", "6~7", "8~9", "10~11", "12~13", "14~15", "16~17", "18~19", "20~21", "22~23"]
          },
          tooltip: {},
          yAxis: {},
          series: [{
            type: 'line',
            data: this.sellByHourData
          }]
        })
      },
    },
    mounted() {
      this.$axios({
        method: 'get',
        url: '/seller/statistic/cost',
        params: {
          sellerId: global.userId
        }
      }).then(response => {
        let data_ = response.data

        this.recentVolume = data_.recentVolume
        this.sellByHourData = data_.sellByHour
        this.sellData = data_.sellTable

        this.drawSellByHour()

      }).catch(function (err) {
        console.log(err)
      })

      // 退订
      this.$axios({
        method: 'get',
        url: '/seller/statistic/cancel',
        params: {
          sellerId: global.userId
        }
      }).then(response => {
        let data_ = response.data

        this.cancelData = data_.cancelList
      }).catch(function (err) {
        console.log(err)
      })
    }
  }
</script>

<style scoped>

</style>
