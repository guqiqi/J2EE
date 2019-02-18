<template>
  <div>
    <admin-navigation default_active="/admin/statistic"/>
    <el-tabs v-model="activeName" type="border-card" style="width: 90%; margin-left: 5%; margin-top: 20px">
      <el-tab-pane label="用户统计" name="first">
        <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
          截至<span style="color: red"> {{formatDate(today)}} </span>, 系统共有<span
          style="color: red"> {{customerNumber}} </span>名注册用户
        </el-row>

        <el-row class="chart_title" style="margin-top: 20px">
          用户数量统计表
        </el-row>
        <div id="customerIncrease" :style="{width: '90%', height: '400px', marginLeft: '5%'}"></div>

      </el-tab-pane>
      <el-tab-pane label="商户统计" name="second">
        <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
          截至<span style="color: red"> {{formatDate(today)}} </span>, 系统共有<span
          style="color: red"> {{customerNumber}} </span>名注册商户
        </el-row>
        <el-row class="chart_title" style="margin-top: 20px">
          商户数量统计表
        </el-row>
        <div id="sellerIncrease" :style="{width: '90%', height: '400px', marginLeft: '5%'}"></div>

        <el-row class="chart_title" style="margin-top: 20px">
          商户分类统计表
        </el-row>
        <div id="sellerNumber" :style="{width: '90%', height: '400px', marginLeft: '5%'}"></div>
      </el-tab-pane>
      <el-tab-pane label="经营统计" name="third">
        <el-row>
          <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
            本周销售额<span style="color: red"> {{recentVolume[0]}} </span>元，本月销售额<span
            style="color: red"> {{recentVolume[1]}} </span>元，本年度销售额<span style="color: red"> {{recentVolume[2]}} </span>元
          </el-row>

          <el-row class="chart_title" style="margin-top: 20px">
            消费活跃时间情况表
          </el-row>
        </el-row>
        <div id="volumeByHour" :style="{width: '100%', height: '400px'}"></div>
        
        <el-row class="chart_title">
          分类消费统计表 不同类型的餐厅（用户数，点餐次数，消费总金额，平均消费金额）
        </el-row>
        <div id="volumeByType" :style="{width: '90%', height: '400px', marginLeft: '5%'}"></div>

      </el-tab-pane>
    </el-tabs>


  </div>
</template>

<script>
  import AdminNavigation from "../../components/AdminNavigation"
  import global from '../../../static/Global'

  export default {
    components: {AdminNavigation},
    name: "admin-statistic",
    data() {
      return {
        activeName: 'first',

        today: new Date(),
        customerNumber: 0,
        sellerNumber: 0,

        recentVolume: [0, 0, 1],

        customerIncreaseDate: [2, 2, 2, 2, 2],
        sellerIncreaseData: [2, 6, 2, 2, 78],
        sellerNumberData: [1, 1, 1, 1, 1],
        volumeByHourData: [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2],
        volumeByTypeData: [1, 1, 1, 1, 1]
      }
    },
    mounted() {
      // TODO 获得数据

      this.drawCustomerIncrease()
      this.drawSellerIncrease()
      this.drawSellerNumber()
      this.drawVolumeByHour()
      this.drawVolumeByType()
    },
    methods: {
      formatDate: function (date) {
        return global.formatDate(date)
      },
      drawCustomerIncrease: function () {
        let customerIncrease = this.$echarts.init(document.getElementById('customerIncrease'))
        // TODO
        customerIncrease.setOption({
          xAxis: {
            data: ["快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"]
          },
          yAxis: {},
          series: [{
            // name: '销量',
            type: 'line',
            data: this.customerIncreaseDate
          }]
        })
      },
      drawSellerIncrease: function () {
        let sellerIncrease = this.$echarts.init(document.getElementById('sellerIncrease'))
        // TODO
        sellerIncrease.setOption({
          xAxis: {
            data: ["快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"]
          },
          yAxis: {},
          series: [{
            // name: '销量',
            type: 'line',
            data: this.sellerIncreaseData
          }]
        })

        console.log(sellerIncrease)
      },
      drawSellerNumber: function () {
        let sellerNumber = this.$echarts.init(document.getElementById('sellerNumber'))
        // TODO
        sellerNumber.setOption({
          xAxis: {
            data: ["快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"]
          },
          yAxis: {},
          series: [{
            // name: '销量',
            type: 'bar',
            data: this.sellerNumberData
          }]
        })
      },
      drawVolumeByHour: function () {
        let volumeByHour = this.$echarts.init(document.getElementById('volumeByHour'))
        // TODO
        volumeByHour.setOption({
          xAxis: {
            data: ["0~1", "2~3", "4~5", "6~7", "8~9", "10~11", "12~13", "14~15", "16~17", "18~19", "20~21", "22~23"]
          },
          yAxis: {},
          series: [{
            type: 'line',
            data: this.volumeByHourData
          }]
        })
      },
      drawVolumeByType: function () {
        let volumeByType = this.$echarts.init(document.getElementById('volumeByType'))
        // TODO
        volumeByType.setOption({
          xAxis: {
            data: ["快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"]
          },
          yAxis: {},
          series: [{
            // name: '销量',
            type: 'bar',
            data: this.volumeByTypeData
          }]
        })
      },
    }
  }
</script>

<style scoped>
  .chart_title {
    font-size: 20px;
    font-weight: bold;
  }
</style>
