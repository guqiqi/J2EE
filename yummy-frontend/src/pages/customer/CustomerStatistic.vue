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
                prop="name"
                label="商家名称"
                align="center">
              </el-table-column>
              <el-table-column
                prop="times"
                label="消费次数"
                align="right">
              </el-table-column>
              <el-table-column
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
                prop="time"
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
        </el-tabs>
      </el-col>
    </el-col>
  </div>

</template>

<script>
  import UserNavigation from "../../components/UserNavigation"
  import global from '../../../static/Global'

  const navigation = () => import('../../components/Navigation.vue')

  export default {
    name: "customer-statistic",
    components: {
      UserNavigation,
      navigation
    },
    data() {
      return {
        activeName: 'first',

        recentVolume: [],
        costByHourData: [],
        costByTypeData: [],
        timesByTypeData: [],

        costData: [],

        cancelData: [
          {
            id: '12987122',
            name: '王小虎',
            payMoney: '234',
            returnMoney: '3.2',
            time: '2019-10-10 29:23:23'
          }, {
            name: '王小虎',
            payMoney: '234',
            returnMoney: '3.2',
            time: '2019-10-10 29:23:23'
          }, {
            name: '王小虎',
            payMoney: '234',
            returnMoney: '3.2',
            time: '2019-10-10 29:23:23'
          }, {
            name: '王小虎',
            payMoney: '234',
            returnMoney: '3.2',
            time: '2019-10-10 29:23:23'
          }, {
            name: '王小虎',
            payMoney: '234',
            returnMoney: '3.2',
            time: '2019-10-10 29:23:23'
          }
        ]
      }
    },
    methods: {
      drawCostByHour: function () {
        let costByHour = this.$echarts.init(document.getElementById('costByHour'))

        costByHour.setOption({
          xAxis: {
            data: ["0~1", "2~3", "4~5", "6~7", "8~9", "10~11", "12~13", "14~15", "16~17", "18~19", "20~21", "22~23"]
          },
          tooltip: {},
          yAxis: {},
          series: [{
            type: 'line',
            data: this.costByHourData
          }]
        })
      },
      drawCostByType: function () {
        let costByType = this.$echarts.init(document.getElementById('costByType'))

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

        this.drawCostByHour()
        this.drawCostByType()

      }).catch(function (err) {
        console.log(err)
      })

      // TODO 退订
    }
  }
</script>

<style scoped>
  .chart_title {
    font-size: 20px;
    font-weight: bold;
  }
</style>
