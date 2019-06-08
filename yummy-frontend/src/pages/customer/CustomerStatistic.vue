<template>
  <div>
    <navigation default_active="/info"/>
    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-col :span="3">
        <user-navigation default_active="/customer/statistic"/>
      </el-col>
      <el-col :span="20" style="margin-left: 10px; margin-right: 10px; ">
        <el-row
          style="font-size: 20px; font-weight: bold; text-align: left; padding-left: 20px; padding-bottom: 5px;border-bottom: #cecece 1px solid">
          统计信息
        </el-row>
        <el-tabs v-model="activeName" type="border-card" style="width: 90%; margin-left: 5%; margin-top: 20px">
          <el-tab-pane label="消费统计" name="first">
            <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
              本周消费额<span style="color: red"> {{recentVolume[0].toFixed(2)}} </span>元，本月消费额<span
              style="color: red"> {{recentVolume[1].toFixed(2)}} </span>元，本年度消费额<span style="color: red"> {{recentVolume[2].toFixed(2)}} </span>元
            </el-row>
            <el-row class="chart_title" style="margin-top: 40px">
              消费活跃时间表
              <el-row style="margin-top: 10px">
                <el-button size="small" :type="type[0]" @click="getHourCost">时间段</el-button>
                <el-button size="small" :type="type[1]" @click="getWeekCost">周</el-button>
                <el-button size="small" :type="type[2]" @click="getMonthCost">月</el-button>
              </el-row>
            </el-row>
            <div id="costByHour" :style="{width: '90%', height: '400px', marginLeft: '5%'}"></div>

            <el-row class="chart_title" style="margin-top: 20px">
              分类消费统计表
            </el-row>
            <div id="costByType" :style="{width: '90%', height: '400px', marginLeft: '5%'}"></div>

            <el-row class="chart_title" style="margin-top: 20px">
              具体消费情况
            </el-row>
            <el-table
              :data="costData"
              border
              show-summary
              style="width: 90%; margin-left: 5%; margin-top: 20px">
              <el-table-column
                sortable
                prop="name"
                label="商家名称"
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
            <el-row class="chart_title" style="margin-top: 20px">
              退订统计
            </el-row>
            <el-row>
              <el-col :span="12">
                <div id="unsubscribeTimes" :style="{width: '400px', height: '400px', marginLeft: '5%'}"></div>
              </el-col>
              <el-col :span="12">
                <div id="unsubscribeMoney" :style="{width: '400px', height: '400px', marginLeft: '5%'}"></div>
              </el-col>
            </el-row>


            <el-table
              :data="cancelData"
              border
              show-summary
              style="width: 90%; margin-left: 5%; margin-top: 20px">
              <el-table-column
                sortable
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
                prop="payMoney"
                label="支付费用"
                align="right">
              </el-table-column>
              <el-table-column
                prop="returnMoney"
                label="退款费用"
                align="right">
              </el-table-column>
            </el-table>
          </el-tab-pane>

          <el-tab-pane label="商家配送时间统计" name="third">
            <el-row class="chart_title" style="margin-top: 40px">
              商家配送时间表
            </el-row>
            <div id="deliverTime" :style="{width: '850px', height: '400px', marginLeft: '20px'}"></div>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-col>
  </div>

</template>

<script>
  import UserNavigation from "../../components/UserNavigation"
  import global from '../../../static/Global'
  import ElRow from "element-ui/packages/row/src/row"

  const navigation = () => import('../../components/Navigation.vue')

  export default {
    name: "customer-statistic",
    components: {
      ElRow,
      UserNavigation,
      navigation
    },
    data() {
      return {
        type: ['primary', '', ''],
        typeIndex: 0,
        xaxis: [["0~1", "2~3", "4~5", "6~7", "8~9", "10~11", "12~13", "14~15", "16~17", "18~19", "20~21", "22~23"],
          ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
          ["1月", "2月", "2月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
        ],

        activeName: 'first',

        recentVolume: [1, 1, 1],
        costByHourData: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2],
        costByWeekData: [50, 12.6, 20, 0, 23.6, 50, 160.8],
        costByMonthData: [0, 10.6, 28.4, 11.4, 20.3, 196.06, 0, 0, 0, 0, 0, 0],

        costByTypeData: [2, 2, 2, 2, 2],
        timesByTypeData: [3, 3, 3, 3, 3],

        deliverFastTimeData: [17, 20, 0, 0, 0],
        deliverAverageTimeData: [25, 37, 0, 0, 0],
        deliverSlowTimeData: [33, 45, 0, 0, 0],

        unsubscribeTimes: [{value: 2, name: '退订单数'}, {value: 10, name: '实际订单数'}],
        unsubscribeMoney: [{value: 2.65, name: '违约金'}, {value: 50.27, name: '退款额'}, {value: 226.76, name: '实际订单额'}],

        costData: [],

        cancelData: []
      }
    },
    methods: {
      getHourCost: function () {
        this.type = ['primary', '', '']
        this.typeIndex = 0
        this.drawCostByHour()
      },

      getWeekCost: function () {
        this.type = ['', 'primary', '']
        this.typeIndex = 1
        this.drawCostByHour()
      },

      getMonthCost: function () {
        this.type = ['', '', 'primary']
        this.typeIndex = 2
        this.drawCostByHour()
      },

      drawCostByHour: function () {
        let costByHour = this.$echarts.init(document.getElementById('costByHour'))

        let data
        if (this.typeIndex === 0)
          data = this.costByHourData
        else if (this.typeIndex === 1)
          data = this.costByWeekData
        else
          data = this.costByMonthData

        costByHour.setOption({
          xAxis: {
            data: this.xaxis[this.typeIndex]
          },
          tooltip: {},
          yAxis: {},
          series: [{
            type: 'line',
            data: data
          }]
        })
      },
      drawCostByType: function () {
        let costByType = this.$echarts.init(document.getElementById("costByType"))

        let colors = ['#4371ba', '#871717']

        costByType.setOption({
          color: colors,

          legend: {
            data: ['消费金额', '消费次数']
          },
          xAxis: {
            data: ["快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"]
          },
          tooltip: {},
          yAxis: [
            {
              type: 'value',
              name: '消费金额',
              position: 'left',
              axisLine: {
                lineStyle: {
                  color: colors[0]
                }
              },
            },
            {
              type: 'value',
              name: '消费次数',
              position: 'right',
              axisLine: {
                lineStyle: {
                  color: colors[1]
                }
              },
            }
          ],
          series: [
            {
              type: 'bar',
              name: '消费金额',
              data: this.costByTypeData
            },
            {
              type: 'line',
              name: '消费次数',
              data: this.timesByTypeData
            }
          ]
        })
      },
      drawDeliverTime: function () {
        let deliverTime = this.$echarts.init(document.getElementById("deliverTime"))

        deliverTime.setOption({
          legend: {
            data: ['最快配送时间', '平均配送时间', '最慢配送时间']
          },
          xAxis: {
            data: ["快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"]
          },
          tooltip: {},
          yAxis: {},
          series: [
            {
              type: 'bar',
              name: '平均配送时间',
              data: this.deliverAverageTimeData
            },
            {
              type: 'line',
              name: '最快配送时间',
              data: this.deliverFastTimeData
            },
            {
              type: 'line',
              name: '最慢配送时间',
              data: this.deliverSlowTimeData
            }
          ]
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
        url: '/user/statistic/cost',
        params: {
          email: global.userId
        }
      }).then(response => {

        let data_ = response.data

        this.recentVolume = data_.recentVolume
        this.costByHourData = data_.costByHour
        this.costByTypeData = data_.costByType
        this.timesByTypeData = data_.timesByType
        this.costData = data_.costTable

        this.drawCostByType()
        this.drawCostByHour()
        this.drawDeliverTime()
        this.drawUnsubscribeTimes()
        this.drawUnsubscribeMoney()
      }).catch(function (err) {
        console.log(err)
      })

      // 退订
      this.$axios({
        method: 'get',
        url: '/user/statistic/cancel',
        params: {
          email: global.userId
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
  .chart_title {
    font-size: 20px;
    font-weight: bold;
  }
</style>
