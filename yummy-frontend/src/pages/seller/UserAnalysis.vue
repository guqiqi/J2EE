<template>
  <div>
    <el-row class="chart_title" style="margin-top: 40px;">
      用户订单收益
      <el-row style="margin-top: 40px; margin-bottom: 20px">
        <el-button size="small" :type="type[0]" @click="getHourCost">时间段</el-button>
        <el-button size="small" :type="type[1]" @click="getWeekCost">周</el-button>
        <el-button size="small" :type="type[2]" @click="getMonthCost">月</el-button>
        <el-button size="small" :type="type[3]" @click="getQuarterCost">季度</el-button>
      </el-row>
    </el-row>
    <div id="arpu" :style="{width: '1200px', height: '500px', marginLeft: '40px'}"></div>

    <el-row class="chart_title" style="margin-top: 40px; margin-bottom: 40px">
      用户特征分析
    </el-row>
    <el-row>
      <el-col :span="6" :offset="1">
        <div id="repurchaseUser"
             :style="{width: '300px', height: '400px', marginLeft: '10px', marginTop: '40px'}"></div>
      </el-col>
      <el-col :span="17">
        <div id="other" :style="{width: '780px', height: '400px', marginLeft: '60px', marginTop: '40px'}"></div>
      </el-col>
    </el-row>

    <el-row class="chart_title" style="margin-top: 40px; margin-bottom: 40px">
      用户地域分析
    </el-row>
    <div class="map-container">
      <div id="container" style="width:90%;height:500px;margin-left:5%"></div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "user-analysis",

    data() {
      return {
        hourData: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]],
        weekData: [],
        monthData: [],
        quarterData: [],

        xaxis: [["0~1", "2~3", "4~5", "6~7", "8~9", "10~11", "12~13", "14~15", "16~17", "18~19", "20~21", "22~23"],
          ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
          ["1月", "2月", "2月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
          ["第一季度", "第二季度", "第三季度", "第四季度"],
        ],
        type: ['primary', '', '', ''],
        typeIndex: 0,

        repurchaseData: [{value: 3, name: '复购用户数'}, {value: 4, name: '新用户数'}],
      }
    },

    methods: {
      getHourCost: function () {
        this.type = ['primary', '', '', '']
        this.typeIndex = 0

        this.drawArpu()
      },

      getWeekCost: function () {
        this.type = ['', 'primary', '', '']
        this.typeIndex = 1

        this.drawArpu()
      },

      getMonthCost: function () {
        this.type = ['', '', 'primary', '']
        this.typeIndex = 2

        this.drawArpu()
      },

      getQuarterCost: function () {
        this.type = ['', '', '', 'primary']
        this.typeIndex = 3

        this.drawArpu()
      },

      drawArpu: function () {
        let arpu = this.$echarts.init(document.getElementById('arpu'))

        let data
        if (this.typeIndex === 0)
          data = this.hourData
        else if (this.typeIndex === 1)
          data = this.weekData
        else if (this.typeIndex === 2)
          data = this.monthData
        else
          data = this.quarterData

        arpu.setOption({
          xAxis: {
            data: this.xaxis[this.typeIndex]
          },
          tooltip: {},
          legend: {
            data: ['平均购买次数', '平均订单额', '平均实付额', '平均实收额']
          },
          yAxis: [
            {
              type: 'value',
              name: '消费金额',
              position: 'left',

            },
            {
              type: 'value',
              name: '消费次数',
              position: 'right',
            }],
          series: [
            {
              type: 'bar',
              name: '平均购买次数',
              data: data[0]
            },
            {
              type: 'line',
              name: '平均订单额',
              data: data[1]
            },
            {
              type: 'line',
              name: '平均实付额',
              data: data[2]
            },
            {
              type: 'line',
              name: '平均实收额',
              data: data[3]
            }
          ]
        })
      },

      drawRepurchase: function () {
        let posList = [
          'left', 'right', 'top', 'bottom',
          'inside',
          'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
          'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
        ]

        app.configParameters = {
          rotate: {
            min: -90,
            max: 90
          },
          align: {
            options: {
              left: 'left',
              center: 'center',
              right: 'right'
            }
          },
          verticalAlign: {
            options: {
              top: 'top',
              middle: 'middle',
              bottom: 'bottom'
            }
          },
          position: {
            options: this.$echarts.util.reduce(posList, function (map, pos) {
              map[pos] = pos
              return map
            }, {})
          },
          distance: {
            min: 0,
            max: 100
          }
        }

        app.config = {
          rotate: 90,
          align: 'left',
          verticalAlign: 'middle',
          position: 'insideBottom',
          distance: 15,
          onChange: function () {
            let labelOption = {
              normal: {
                rotate: app.config.rotate,
                align: app.config.align,
                verticalAlign: app.config.verticalAlign,
                position: app.config.position,
                distance: app.config.distance
              }
            }
            myChart.setOption({
              series: [{
                label: labelOption
              }, {
                label: labelOption
              }, {
                label: labelOption
              }, {
                label: labelOption
              }]
            })
          }
        }


        let labelOption = {
          normal: {
            show: true,
            position: app.config.position,
            distance: app.config.distance,
            align: app.config.align,
            verticalAlign: app.config.verticalAlign,
            rotate: app.config.rotate,
            formatter: '{c}  {name|{a}}',
            fontSize: 16,
            rich: {
              name: {
                textBorderColor: '#fff'
              }
            }
          }
        }

        let repurchase = this.$echarts.init(document.getElementById('other'))

        repurchase.setOption({
          legend: {
            orient: 'vertical',
            x: 'left',
            data: ['复购用户数', '新用户数']
          },
          tooltip: {},
          xAxis: {
            type: 'category',
            name: '配送距离',
            axisTick: {show: false},
            data: ['<500m', '500m-1km', '1km-2km', '2km-4km', '>4km']
          },
          yAxis: {
            type: 'value',
            name: '数量',
          },
          series: [
            {
              name: '男',
              type: 'bar',
              barGap: 0,
              label: labelOption,
              data: [0, 0, 1, 0, 0]
            },
            {
              name: '女',
              type: 'bar',
              label: labelOption,
              data: [1, 1, 0, 0, 1]
            },
          ]
        })

      },

      drawOther: function () {
        let repurchase = this.$echarts.init(document.getElementById('repurchaseUser'))

        repurchase.setOption({
          legend: {
            orient: 'vertical',
            x: 'left',
            data: ['复购用户数', '新用户数']
          },
          tooltip: {},
          series: [
            {
              name: '复购用户比率',
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
        let map = new AMap.Map('container', {
          features: ['bg', 'road', 'building', 'point'],
          resizeEnable: false,
          zoom: 15,
          center: [118.7831926346, 32.0608096335],
          pitch: 1,
          jogEnable: false,
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
          "lng": 118.7826991081,
          "lat": 32.0560450238,
          "count": 2
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
            max: 5
          })
        })
      },

      isSupportCanvas() {//判断浏览区是否支持canvas
        let elem = document.createElement('canvas')
        return !!(elem.getContext && elem.getContext('2d'))
      },
    },
    mounted() {
      this.drawArpu()
      this.drawRepurchase()
      this.drawOther()
      this.initMap()
    },

  }
</script>

<style scoped>

</style>
