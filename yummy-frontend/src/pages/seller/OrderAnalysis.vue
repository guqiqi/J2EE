<template>
  <div>
    <el-row class="chart_title" style="margin-top: 40px">
      订单分析
      <el-row style="margin-top: 40px; margin-bottom: 20px">
        <el-button size="small" :type="type[0]" @click="getHourCost">时间段</el-button>
        <el-button size="small" :type="type[1]" @click="getWeekCost">周</el-button>
        <el-button size="small" :type="type[2]" @click="getMonthCost">月</el-button>
        <el-button size="small" :type="type[3]" @click="getQuarterCost">季度</el-button>
      </el-row>
    </el-row>
    <el-row>
      <el-col :span="6">
        <div id="repurchase" :style="{width: '300px', height: '400px', marginLeft: '10px', marginTop: '40px'}"></div>
      </el-col>
      <el-col :span="18">
        <div id="order" :style="{width: '750px', height: '400px', marginLeft: '5%'}"></div>
      </el-col>
    </el-row>

    <el-row class="chart_title" style="margin-top: 20px">
      具体订单情况
    </el-row>
    <el-table
      :data="orderData"
      border
      show-summary
      style="width: 90%; margin-left: 5%; margin-top: 20px">
      <el-table-column
        prop="orderId"
        label="订单编号"
        align="center">
      </el-table-column>
      <el-table-column
        sortable
        prop="total"
        label="总额"
        align="right">
      </el-table-column>
      <el-table-column
        sortable
        prop="pay"
        label="用户实付"
        align="right">
      </el-table-column>
      <el-table-column
        sortable
        prop="receive"
        label="商家实收"
        align="right">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    name: "order-analysis",

    data() {
      return {
        hourData: [[0, 0, 0, 10.3, 15.9, 0, 25.6, 0, 24.8, 9.4, 0, 20.4], [0, 0, 0, 8.3, 12.9, 0, 23.6, 0, 21.8, 8.4, 0, 19.4], [0, 0, 0, 8.0, 12.4, 0, 23.2, 0, 21.1, 7.9, 0, 18.4]],
        weekData: [[22.9, 16.3, 0, 22.3, 13.8, 0, 17.8], [20.9, 14.3, 0, 20.3, 10.8, 0, 16.8], [19.9, 13.3, 0, 19.5, 8.8, 0, 14.8]],
        monthData: [[0, 15.9, 10.3, 14, 23.3, 30.8, 0, 0, 0, 0, 0, 0], [0, 10.9, 8.3, 11.5, 20.3, 27.8, 0, 0, 0, 0, 0, 0], [0, 10.4, 7.3, 10.5, 19.7, 25.8, 0, 0, 0, 0, 0, 0]],
        quarterData: [[19.3, 24, 0, 0], [17.5, 20, 0, 0], [16.8, 18, 0, 0]],

        xaxis: [["0~1", "2~3", "4~5", "6~7", "8~9", "10~11", "12~13", "14~15", "16~17", "18~19", "20~21", "22~23"],
          ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
          ["1月", "2月", "2月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
          ["第一季度", "第二季度", "第三季度", "第四季度"],
        ],
        type: ['primary', '', '', ''],
        typeIndex: 0,

        orderData: [],

        repurchaseData: [{value: 7, name: '复购订单数'}, {value: 4, name: '新订单数'}]
      }

    },

    methods: {
      getHourCost: function () {
        this.type = ['primary', '', '', '']
        this.typeIndex = 0

        this.drawOrder()
      },

      getWeekCost: function () {
        this.type = ['', 'primary', '', '']
        this.typeIndex = 1

        this.drawOrder()
      },

      getMonthCost: function () {
        this.type = ['', '', 'primary', '']
        this.typeIndex = 2

        this.drawOrder()
      },

      getQuarterCost: function () {
        this.type = ['', '', '', 'primary']
        this.typeIndex = 3

        this.drawOrder()
      },

      drawOrder: function () {
        let order = this.$echarts.init(document.getElementById('order'))

        let data
        if (this.typeIndex === 0)
          data = this.hourData
        else if (this.typeIndex === 1)
          data = this.weekData
        else if (this.typeIndex === 2)
          data = this.monthData
        else
          data = this.quarterData

        order.setOption({
          xAxis: {
            data: this.xaxis[this.typeIndex]
          },
          tooltip: {},
          yAxis: {
            name: '价格'
          },
          legend: {
            x: 'center',
            data: ['平均客单价', '平均用户实付客单价', '平均商家实收客单价'],
          },
          series: [
            {
              type: 'line',
              name: '平均客单价',
              data: data[0]
            },
            {
              type: 'line',
              name: '平均用户实付客单价',
              data: data[1]
            },
            {
              type: 'line',
              name: '平均商家实收客单价',
              data: data[2]
            }
          ]
        })
      },

      drawRepurchase: function () {
        let repurchase = this.$echarts.init(document.getElementById('repurchase'))

        repurchase.setOption({
          legend: {
            orient: 'vertical',
            x: 'left',
            data: ['复购订单数', '新订单数']
          },
          tooltip: {},
          series: [
            {
              name: '复购订单比率',
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
              data: this.repurchaseData
            }
          ]
        })
      },
    },

    mounted() {
      this.drawOrder()
      this.drawRepurchase()

      // 得到具体订单情况
      this.$axios({
        method: 'get',
        url: '/statistic/seller/order',
      }).then(response => {
        let data_ = response.data

        this.orderData = data_.orderData
      }).catch(function (err) {
        console.log(err)
      })
    }

  }
</script>

<style scoped>

</style>
