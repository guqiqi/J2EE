<template>
  <div>
    <el-row class="chart_title" style="margin-top: 40px;">
      用户平均收入
      <el-row style="margin-top: 40px">
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

        repurchaseData: [{value: 20, name: '复购用户数'}, {value: 10, name: '新用户数'}],
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
              data: [320, 332, 301, 334, 390]
            },
            {
              name: '女',
              type: 'bar',
              label: labelOption,
              data: [220, 182, 191, 234, 290]
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
          center: [118.77948, 32.05489],
          pitch: 1,
          jogEnable: false,
          dragEnable: false,
          scrollWheel: false,
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
          "lng": 118.76948,
          "lat": 32,
          "count": 100
        }, {
          "lng": 118.77848,
          "lat": 32.05479,
          "count": 90
        }, {
          "lng": 118.87948,
          "lat": 32.05589,
          "count": 20
        }, {
          "lng": 116.481707,
          "lat": 39.940089,
          "count": 13
        }, {
          "lng": 116.410588,
          "lat": 39.880172,
          "count": 14
        }, {
          "lng": 116.394816,
          "lat": 39.91181,
          "count": 15
        }, {
          "lng": 116.416002,
          "lat": 39.952917,
          "count": 16
        }, {
          "lng": 116.39671,
          "lat": 39.924903,
          "count": 17
        }, {
          "lng": 116.180816,
          "lat": 39.957553,
          "count": 18
        }, {
          "lng": 116.382035,
          "lat": 39.874114,
          "count": 19
        }, {
          "lng": 116.316648,
          "lat": 39.914529,
          "count": 20
        }, {
          "lng": 116.395803,
          "lat": 39.908556,
          "count": 21
        }, {
          "lng": 116.74553,
          "lat": 39.875916,
          "count": 22
        }, {
          "lng": 116.352289,
          "lat": 39.916475,
          "count": 23
        }, {
          "lng": 116.441548,
          "lat": 39.878262,
          "count": 24
        }, {
          "lng": 116.318947,
          "lat": 39.942735,
          "count": 25
        }, {
          "lng": 116.382585,
          "lat": 39.941949,
          "count": 26
        }, {
          "lng": 116.42042,
          "lat": 39.884017,
          "count": 27
        }, {
          "lng": 116.31744,
          "lat": 39.892561,
          "count": 28
        }, {
          "lng": 116.407059,
          "lat": 39.912438,
          "count": 29
        }, {
          "lng": 116.412351,
          "lat": 39.888082,
          "count": 30
        }, {
          "lng": 116.444341,
          "lat": 39.915891,
          "count": 31
        }, {
          "lng": 116.335385,
          "lat": 39.741756,
          "count": 32
        }, {
          "lng": 116.3926,
          "lat": 40.008733,
          "count": 33
        }, {
          "lng": 116.389731,
          "lat": 39.92292,
          "count": 34
        }, {
          "lng": 116.413371,
          "lat": 39.874483,
          "count": 35
        }, {
          "lng": 116.199752,
          "lat": 39.911717,
          "count": 36
        }, {
          "lng": 116.278472,
          "lat": 40.254994,
          "count": 37
        }, {
          "lng": 116.464252,
          "lat": 39.925828,
          "count": 38
        }, {
          "lng": 116.479475,
          "lat": 39.937945,
          "count": 39
        }, {
          "lng": 116.415599,
          "lat": 39.956902,
          "count": 40
        }, {
          "lng": 116.355675,
          "lat": 39.870089,
          "count": 41
        }, {
          "lng": 116.295267,
          "lat": 39.987171,
          "count": 42
        }, {
          "lng": 116.323634,
          "lat": 39.911692,
          "count": 43
        }, {
          "lng": 116.692769,
          "lat": 40.173307,
          "count": 44
        }, {
          "lng": 116.287888,
          "lat": 39.928531,
          "count": 45
        }, {
          "lng": 116.386502,
          "lat": 39.922747,
          "count": 46
        }, {
          "lng": 116.236773,
          "lat": 40.218341,
          "count": 47
        }, {
          "lng": 116.490636,
          "lat": 39.804253,
          "count": 48
        }, {
          "lng": 116.391095,
          "lat": 39.925791,
          "count": 49
        }, {
          "lng": 116.472402,
          "lat": 39.769178,
          "count": 50
        }, {
          "lng": 116.38657,
          "lat": 39.956731,
          "count": 51
        }, {
          "lng": 116.427536,
          "lat": 39.943671,
          "count": 52
        }, {
          "lng": 116.374547,
          "lat": 39.967588,
          "count": 53
        }, {
          "lng": 116.380383,
          "lat": 39.871634,
          "count": 54
        }, {
          "lng": 116.376092,
          "lat": 39.965485,
          "count": 55
        }, {
          "lng": 116.352424,
          "lat": 39.91811,
          "count": 56
        }, {
          "lng": 116.020157,
          "lat": 40.348526,
          "count": 57
        }, {
          "lng": 116.416201,
          "lat": 39.951736,
          "count": 58
        }, {
          "lng": 116.405392,
          "lat": 39.908738,
          "count": 59
        }, {
          "lng": 116.49238,
          "lat": 39.926248,
          "count": 60
        }, {
          "lng": 116.389282,
          "lat": 39.988391,
          "count": 61
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
        var elem = document.createElement('canvas')
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
