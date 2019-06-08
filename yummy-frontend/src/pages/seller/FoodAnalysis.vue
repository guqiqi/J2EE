<template>
  <div>
    <el-row class="chart_title" style="margin-top: 40px;margin-bottom: 20px">
      商品销售额分析
      <el-row style="margin-top: 40px">
        <el-button size="small" :type="type[0]" @click="getHourCost">时间段</el-button>
        <el-button size="small" :type="type[1]" @click="getWeekCost">周</el-button>
        <el-button size="small" :type="type[2]" @click="getMonthCost">月</el-button>
        <el-button size="small" :type="type[3]" @click="getQuarterCost">季度</el-button>
      </el-row>
    </el-row>
    <div id="sell" :style="{width: '1200px', height: '500px', marginLeft: '40px'}"></div>

    <el-row class="chart_title" style="margin-top: 40px;margin-bottom: 20px">
      商品销售量分析
      <el-row style="margin-top: 40px">
        <el-button size="small" :type="countType[0]" @click="getHour">时间段</el-button>
        <el-button size="small" :type="countType[1]" @click="getWeek">周</el-button>
        <el-button size="small" :type="countType[2]" @click="getMonth">月</el-button>
        <el-button size="small" :type="countType[3]" @click="getQuarter">季度</el-button>
      </el-row>
    </el-row>
    <div id="sellCount" :style="{width: '1200px', height: '500px', marginLeft: '40px'}"></div>

    <el-row class="chart_title" style="margin-top: 40px;margin-bottom: 20px">
      套餐与商品拉新分析
    </el-row>
    <el-row>
      <el-col :span="6">
        <div id="package" :style="{width: '400px', height: '400px'}"></div>
      </el-col>
      <el-col :span="18">
        <div id="newCustomer" :style="{width: '850px', height: '400px', marginLeft: '40px'}"></div>
      </el-col>
    </el-row>

    <el-row class="chart_title" style="margin-top: 40px; margin-bottom: 20px">
      波士顿矩阵分析
    </el-row>
    <div id="boston" :style="{width: '1200px', height: '500px', marginLeft: '40px'}"></div>

    <el-row class="chart_title" style="margin-top: 40px; margin-bottom: 20px">
      存流失贡献度
    </el-row>
    <div id="retain" :style="{width: '1200px', height: '500px', marginLeft: '40px'}"></div>
  </div>
</template>

<script>
  export default {
    name: "food-analysis",

    data() {
      return {
        foodNames: ['❤招牌皮蛋瘦肉粥', '农家鸡蛋肉松饼', '十三香冒油咸鸭蛋',
          '香香奶黄包', '❤安心油条', '❤民国美龄粥'],

        retainData: [4, 5, 2, 4, 5, 5],
        leaveData: [-0.34, -1, -0.68, -1.23, -0.53, -3.33],

        bostonData: [
          [[30, 40, '❤招牌皮蛋瘦肉粥', 4]],
          [[40, 20.4, '农家鸡蛋肉松饼', 9]],
          [[10, 10, '十三香冒油咸鸭蛋', 20]],
          [[20, 20, '香香奶黄包', 30]],
          [[29, 48, '❤安心油条', 10]],
          [[10, 11, '❤民国美龄粥', 10]]],

        xaxis: [["0~1", "2~3", "4~5", "6~7", "8~9", "10~11", "12~13", "14~15", "16~17", "18~19", "20~21", "22~23"],
          ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
          ["1月", "2月", "2月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
          ["第一季度", "第二季度", "第三季度", "第四季度"],
        ],
        type: ['primary', '', '', ''],
        typeIndex: 0,

        hourData: [[3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]],
        weekData: [],
        monthData: [],
        quarterData: [],

        countType: ['primary', '', '', ''],
        countTypeIndex: 0,

        countHourData: [[3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]],
        countWeekData: [],
        countMonthData: [],
        countQuarterData: [],

        packageData: [{value: 20, name: '套餐销量'}, {value: 10, name: '单品销量'}],
        newCustomerData: [0, 2, 2, 2, 22, 1]

      }
    },

    methods: {
      getHourCost: function () {
        this.type = ['primary', '', '', '']
        this.typeIndex = 0

        this.drawSell()
      },

      getWeekCost: function () {
        this.type = ['', 'primary', '', '']
        this.typeIndex = 1

        this.drawSell()
      },

      getMonthCost: function () {
        this.type = ['', '', 'primary', '']
        this.typeIndex = 2

        this.drawSell()
      },

      getQuarterCost: function () {
        this.type = ['', '', '', 'primary']
        this.typeIndex = 3

        this.drawSell()
      },

      drawSell: function () {
        let sell = this.$echarts.init(document.getElementById('sell'))

        let data
        if (this.typeIndex === 0)
          data = this.hourData
        else if (this.typeIndex === 1)
          data = this.weekData
        else if (this.typeIndex === 2)
          data = this.monthData
        else
          data = this.quarterData

        let series = []
        for (let i = 1; i < this.foodNames.length; i++) {
          series.push({
            name: this.foodNames[i - 1],
            type: 'bar',
            barWidth: 40,
            data: data[i],
            stack: '销售总额',
          })
        }

        sell.setOption({
          xAxis: {
            data: this.xaxis[this.typeIndex]
          },
          tooltip: {},
          yAxis: [
            {
              type: 'value',
              name: '销售金额',
              position: 'left',

            }],
          series: series
        })
      },

      getHour: function () {
        this.countType = ['primary', '', '', '']
        this.countTypeIndex = 0

        this.drawSellCount()
      },

      getWeek: function () {
        this.countType = ['', 'primary', '', '']
        this.countTypeIndex = 1

        this.drawSellCount()
      },

      getMonth: function () {
        this.countType = ['', '', 'primary', '']
        this.countTypeIndex = 2

        this.drawSellCount()
      },

      getQuarter: function () {
        this.countType = ['', '', '', 'primary']
        this.countTypeIndex = 3

        this.drawSellCount()
      },

      drawSellCount: function () {
        let sellCount = this.$echarts.init(document.getElementById('sellCount'))

        let data
        if (this.typeIndex === 0)
          data = this.countHourData
        else if (this.typeIndex === 1)
          data = this.countWeekData
        else if (this.typeIndex === 2)
          data = this.countMonthData
        else
          data = this.countQuarterData

        let series = []
        for (let i = 1; i < this.foodNames.length; i++) {
          series.push({
            name: this.foodNames[i - 1],
            type: 'bar',
            barWidth: 40,
            data: data[i],
            stack: '销售量',
          })
        }

        sellCount.setOption({
          xAxis: {
            data: this.xaxis[this.typeIndex]
          },
          tooltip: {},
          yAxis: [
            {
              type: 'value',
              name: '销售量',
              position: 'left',

            }],
          series: series
        })
      },

      drawBoston: function () {
        let boston = this.$echarts.init(document.getElementById('boston'))

        let data = []
        for (let i = 0; i < this.bostonData.length; i++) {
          data.push({
            name: this.bostonData[i][0][2],
            data: this.bostonData[i],
            type: 'scatter',
            symbolSize: function (data) {
              return data[3] * 3
            },
            label: {
              emphasis: {
                show: true,
                formatter: function (param) {
                  return param.data[2]
                },
                position: 'top'
              }
            },
          })
        }

        boston.setOption({
          legend: {
            x: 'center',
            data: ['留存指数', '流失指数', '综合指数'],
          },
          xAxis: {
            type: 'value',
            name: '销量环比增长率',
            splitLine: {
              lineStyle: {
                type: 'dashed'
              }
            }
          },
          yAxis: {
            type: 'value',
            name: '商家实收',
            splitLine: {
              lineStyle: {
                type: 'dashed'
              }
            }
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross'
            }
          },
          series: data
        })
      },

      drawRetain: function () {
        let retain = this.$echarts.init(document.getElementById('retain'))

        let itemStyle = {
          normal: {},
          emphasis: {
            barBorderWidth: 1,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0,0,0,0.5)'
          }
        }

        let comprehensiveData = []
        for (let i = 0; i < this.retainData.length; i++) {
          comprehensiveData.push(this.retainData[i] + this.leaveData[i])
        }

        retain.setOption({
          legend: {
            x: 'center',
            data: ['留存指数', '流失指数', '综合指数'],
          },
          xAxis: {
            data: this.foodNames,
            name: '菜品',
          },
          yAxis: {
            inverse: true,
            splitArea: {show: false}
          },
          tooltip: {},
          series: [
            {
              name: '留存指数',
              type: 'bar',
              stack: 'one',
              itemStyle: itemStyle,
              data: this.retainData
            },
            {
              name: '流失指数',
              type: 'bar',
              stack: 'one',
              itemStyle: itemStyle,
              data: this.leaveData
            },
            {
              name: '综合指数',
              type: 'bar',
              stack: 'two',
              itemStyle: itemStyle,
              data: comprehensiveData
            }
          ]
        })
      },

      drawPackage: function () {
        let packageDom = this.$echarts.init(document.getElementById('package'))

        packageDom.setOption({
          title: {
            text: '套餐、单品销量比例',
            left: 'center'
          },
          legend: {
            orient: 'vertical',
            x: 'right',
            data: ['套餐销量', '单品销量']
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
              data: this.packageData
            }
          ]
        })
      },

      drawNewCustomer: function () {
        let newCustomer = this.$echarts.init(document.getElementById('newCustomer'))

        console.log(newCustomer)
        newCustomer.setOption({
          title: {
            text: '商品拉新排名',
            left: 'center'
          },
          legend: {
            orient: 'vertical',
            x: 'right',
            data: ['拉新数量']
          },
          tooltip: {},
          xAxis: {
            data: this.foodNames,
            name: '菜品',
          },
          yAxis: {
            type: 'value',
            name: '数量'
          },
          series: [
            {
              type: 'bar',
              data: this.newCustomerData,
              name: '拉新数量',
            }
          ]
        })
      },
    },

    mounted() {
      this.drawRetain()
      this.drawBoston()
      this.drawSell()
      this.drawSellCount()
      this.drawPackage()
      this.drawNewCustomer()
    }
  }
</script>

<style scoped>

</style>
