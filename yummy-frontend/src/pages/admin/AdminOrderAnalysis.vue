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
      <el-col :span="5" :offset="1">
        <div id="repurchase" :style="{width: '300px', height: '400px', marginLeft: '10px', marginTop: '40px'}"></div>
      </el-col>
      <el-col :span="18">
        <div id="order" :style="{width: '850px', height: '400px', marginLeft: '50px'}"></div>
      </el-col>
    </el-row>

    <el-row class="chart_title" style="margin-top: 20px; margin-bottom: 40px">
      订单地域分析
    </el-row>
    <div class="map-container">
      <div id="orderContainer" style="width:90%;height:600px;margin-left:5%; margin-bottom: 40px"></div>
    </div>

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
    name: "admin-order-analysis",

    data() {
      return {
        hourData: [[0, 0, 0, 13, 20.9, 20.3, 13, 30.3, 24.8, 35.6, 29.8, 30.4], [0, 0, 0, 12, 18.9, 16.3, 12, 27.3, 20.8, 33.6, 25.8, 20.4], [0, 0, 0, 11.8, 17.9, 15.3, 11.5, 25.9, 19.6, 32.6, 24.8, 19.4]],
        weekData: [[24.9, 26.3, 0, 32.3, 33.8, 0, 27.8], [20.9, 24.3, 0, 30.3, 30.8, 0, 26.8], [19.9, 23.3, 0, 29.5, 28.8, 0, 24.8]],
        monthData: [[0, 25.9, 20.3, 24, 33.3, 40.8, 0, 0, 0, 0, 0, 0], [0, 20.9, 18.3, 21.5, 30.3, 37.8, 0, 0, 0, 0, 0, 0], [0, 18.9, 17.3, 20.5, 29.7, 35.8, 0, 0, 0, 0, 0, 0]],
        quarterData: [[29.3, 34, 0, 0], [27.5, 30, 0, 0], [26.8, 28, 0, 0]],

        xaxis: [["0~1", "2~3", "4~5", "6~7", "8~9", "10~11", "12~13", "14~15", "16~17", "18~19", "20~21", "22~23"],
          ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
          ["1月", "2月", "2月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
          ["第一季度", "第二季度", "第三季度", "第四季度"],
        ],
        type: ['primary', '', '', ''],
        typeIndex: 0,

        orderData: [],

        repurchaseData: [{value: 10, name: '复购订单数'}, {value: 13, name: '新订单数'}],
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
            width: 900
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

      // 创建地图
      initMap() {
        let map = new AMap.Map('orderContainer', {
          features: ['bg', 'road', 'building', 'point'],
          resizeEnable: true,
          zoom: 5,
          mapStyle: "amap://styles/4261c8dec313b784c0933324313428a8666",
          center: [118.77948, 32.05489],
          pitch: 1,
          scrollWheel: true,
          viewMode: '2D',
        })
        if (!this.isSupportCanvas()) {
          this.$Message.info('热力图仅对支持canvas的浏览器适用,您所使用的浏览器不能使用热力图功能,请换个浏览器试试~')
        }

        let heatmap
        let heatmapData = [];
        //从接口获取数据
        //官网示例数据结构 http://a.amap.com/jsapi_demos/static/resource/heatmapData.js
        [{
          "lng": 118.7777209282,
          "lat": 32.0583000608,
          "count": 8
        }, {
          "lng": 118.7818837166,
          "lat": 32.0524259369,
          "count": 5
        }, {
          "lng": 118.7915182114,
          "lat": 32.0515529699,
          "count": 2
        }, {
          "lng": 118.7858104706,
          "lat": 32.0572634781,
          "count": 2
        }, {
          "lng": 118.783,
          "lat": 32.0512983529,
          "count": 3
        }, {
          "lng": 116.4071700000,
          "lat": 39.9046900000,
          "count": 2
        }, {
          "lng": 104.0647600000,
          "lat": 30.5702000000,
          "count": 1
        }].forEach(item => {
          let obj = {
            lng: item.lng,
            lat: item.lat,
            count: item.count,
          }
          heatmapData.push(obj)
        })
        map.plugin(["AMap.Heatmap"], function () {
          //初始化heatmap对象
          heatmap = new AMap.Heatmap(map, {
            radius: 25, //给定半径
            opacity: [0, 0.8],
            gradient: {
              0.5: 'blue',
              0.65: 'rgb(117,211,248)',
              0.7: 'rgb(0, 255, 0)',
              0.9: '#ffea00',
              1.0: 'red'
            }
          })
          //设置数据集
          heatmap.setDataSet({
            data: heatmapData,
            max: 8
          })
        })
      },

      isSupportCanvas() {//判断浏览区是否支持canvas
        let elem = document.createElement('canvas')
        return !!(elem.getContext && elem.getContext('2d'))
      },
    },

    mounted() {
      this.drawOrder()
      this.drawRepurchase()
      this.initMap()

      // TODO 订单分析表格数据
    }

  }
</script>

<style scoped>

</style>
