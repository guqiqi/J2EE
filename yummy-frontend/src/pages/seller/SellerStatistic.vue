<template>
  <div>
    <seller-navigation default_active="/seller/statistic"/>
    <el-tabs v-model="activeName" type="border-card" style="width: 90%; margin-left: 5%; margin-top: 20px">
      <el-tab-pane label="销量分析" name="first">
        <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
          本周销售<span style="color: red"> {{recentVolume[0].toFixed(2)}} </span>元，本月销售<span
          style="color: red"> {{recentVolume[1].toFixed(2)}} </span>元，本年度销售<span style="color: red"> {{recentVolume[2].toFixed(2)}} </span>元
        </el-row>

        <el-row class="chart_title" style="margin-top: 40px">
          销售活跃时间表
          <el-row style="margin-top: 40px">
            <el-button size="small" :type="type[0]" @click="getHourCost">时间段</el-button>
            <el-button size="small" :type="type[1]" @click="getWeekCost">周</el-button>
            <el-button size="small" :type="type[2]" @click="getMonthCost">月</el-button>
            <el-button size="small" :type="type[3]" @click="getQuarterCost">季度</el-button>
          </el-row>
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

      <el-tab-pane label="订单分析" name="second">
        <order-analysis/>
      </el-tab-pane>

      <el-tab-pane label="商品分析" name="third">
        <food-analysis/>
      </el-tab-pane>

      <el-tab-pane label="用户分析" name="forth">
        <user-analysis/>
      </el-tab-pane>

      <el-tab-pane label="退订分析" name="fifth">
        <el-row class="chart_title" style="margin-top: 20px; margin-bottom: 20px">
          退订统计
        </el-row>
        <el-row>
          <el-col :span="10" :offset="2">
            <div id="unsubscribeTimes" :style="{width: '400px', height: '400px', marginLeft: '5%'}"></div>
          </el-col>
          <el-col :span="10" :offset="2">
            <div id="unsubscribeMoney" :style="{width: '400px', height: '400px', marginLeft: '5%'}"></div>
          </el-col>
        </el-row>

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
  import OrderAnalysis from "./OrderAnalysis"
  import UserAnalysis from "./UserAnalysis"
  import FoodAnalysis from "./FoodAnalysis"

  export default {
    components: {
      FoodAnalysis,
      UserAnalysis,
      OrderAnalysis,
      SellerNavigation
    },
    name: "seller-statistic",
    data() {
      return {
        activeName: 'first',

        recentVolume: [0, 2, 1],
        sellByHourData: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1],

        sellData: [],

        cancelData: [],

        type: ['primary', '', '', ''],

        unsubscribeTimes: [{value: 2, name: '退订单数'}, {value: 10, name: '实际订单数'}],
        unsubscribeMoney: [{value: 2, name: '违约金'}, {value: 10, name: '退款额'}, {value: 10, name: '实际订单额'}],

      }
    },
    methods: {
      getHourCost: function () {
        this.type = ['primary', '', '', '']

        //TODO
      },

      getWeekCost: function () {
        this.type = ['', 'primary', '', '']

        //TODO
      },

      getMonthCost: function () {
        this.type = ['', '', 'primary', '']

        // TODO
      },

      getQuarterCost: function () {
        this.type = ['', '', '', 'primary']

        // TODO
      },

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

      drawUnsubscribeTimes: function () {
        let unsubscribeTimes = this.$echarts.init(document.getElementById("unsubscribeTimes"))

        unsubscribeTimes.setOption({
          legend: {
            orient: 'vertical',
            x: 'left',
            data: ['退订单数', '实际订单数']
          },
          tooltip: {},
          series: [
            {
              name: '实际订单比率',
              type: 'pie',
              selectedMode: 'single',
              radius: ['40%', '60%'],

              label: {
                normal: {
                  position: 'inner'
                }
              },
              labelLine: {
                normal: {
                  show: false
                }
              },
              data: this.unsubscribeTimes
            }
          ]
        })
      },
      drawUnsubscribeMoney: function () {
        let unsubscribeMoney = this.$echarts.init(document.getElementById("unsubscribeMoney"))

        unsubscribeMoney.setOption({
          legend: {
            orient: 'vertical',
            x: 'left',
            data: ['违约金', '退款额', '实际订单额']
          },
          tooltip: {},
          series: [
            {
              type: 'pie',
              selectedMode: 'single',
              radius: ['40%', '60%'],

              label: {
                normal: {
                  position: 'inner'
                }
              },
              labelLine: {
                normal: {
                  show: false
                }
              },
              data: this.unsubscribeMoney
            }
          ]
        })
      }
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
        this.drawUnsubscribeTimes()
        this.drawUnsubscribeMoney()

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
