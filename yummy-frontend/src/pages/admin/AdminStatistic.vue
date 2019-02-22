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
        <div id="customerIncrease" :style="{width: '1090px', height: '400px', marginLeft: '100px'}"></div>

      </el-tab-pane>
      <el-tab-pane label="商户统计" name="second">
        <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
          截至<span style="color: red"> {{formatDate(today)}} </span>, 系统共有<span
          style="color: red"> {{sellerNumber}} </span>名注册商户
        </el-row>
        <el-row class="chart_title" style="margin-top: 20px">
          商户数量统计表
        </el-row>
        <div id="sellerIncrease" :style="{width: '1090px', height: '400px', marginLeft: '100px'}"></div>

        <el-row class="chart_title" style="margin-top: 20px">
          商户分类统计表
        </el-row>
        <div id="sellerNumber" :style="{width: '1090px', height: '400px', marginLeft: '100px'}"></div>
        <div id="sellerNumberByTypePie" :style="{width: '1090px', height: '400px', marginLeft: '100px'}"></div>

      </el-tab-pane>
      <el-tab-pane label="经营统计" name="third">
        <el-row>
          <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
            本周销售额<span style="color: red"> {{recentVolume[0].toFixed(2)}} </span>元，本月销售额<span
            style="color: red"> {{recentVolume[1].toFixed(2)}} </span>元，本年度销售额<span style="color: red"> {{recentVolume[2].toFixed(2)}} </span>元
          </el-row>

          <el-row class="chart_title" style="margin-top: 20px">
            消费活跃时间表
          </el-row>
        </el-row>
        <div id="volumeByHour" :style="{width: '1090px', height: '400px', marginLeft: '100px'}"></div>

        <el-row class="chart_title">
          分类消费统计表 不同类型的餐厅（用户数，点餐次数，消费总金额，平均消费金额）
        </el-row>
        <div id="volumeByType" :style="{width: '1090px', height: '400px', marginLeft: '100px'}"></div>
        <div id="volumeByTypePie" :style="{width: '1090px', height: '400px', marginLeft: '100px'}"></div>

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

        recentVolume: [0, 0, 0],

        customerIncreaseDate: [0, 0, 0, 3, 5, 10, 12],
        sellerIncreaseData: [0, 0, 0, 3, 6, 10, 15],
        sellerNumberByTypeData: [2, 4, 5, 2, 2],
        sellerNumberByTypePieData: [
          {name: "快餐便当", value: 2},
          {name: "甜品饮品", value: 4},
          {name: "果蔬生鲜", value: 5},
          {name: "商店超市", value: 2},
          {name: "鲜花绿植", value: 2}],

        volumeByHourData: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        volumeByTypeData: [0, 0, 0, 0, 0],
        volumeByTypePieData: [
          {name: "快餐便当", value: 0},
          {name: "甜品饮品", value: 0},
          {name: "果蔬生鲜", value: 0},
          {name: "商店超市", value: 0},
          {name: "鲜花绿植", value: 0}],
      }
    },
    mounted() {
      // 获得数据
      this.$axios({
        method: 'get',
        url: '/statistic/customer',
      }).then(response => {
        let data_ = response.data

        this.customerNumber = data_.customerNumber
        this.customerIncreaseDate = data_.customerIncrease

        console.log(this.customerIncreaseDate)

        this.drawCustomerIncrease()
      }).catch(function (err) {
        console.log(err)
      })

      this.$axios({
        method: 'get',
        url: '/statistic/seller',
      }).then(response => {
        let data_ = response.data

        this.sellerNumber = data_.sellerNumber
        this.sellerNumberByTypeData = data_.sellerNumberByType
        this.sellerIncreaseData = data_.sellerIncrease

        console.log(this.sellerIncreaseData)

        this.sellerNumberByTypePieData = [
          {name: "快餐便当", value: this.sellerNumberByTypeData[0]},
          {name: "甜品饮品", value: this.sellerNumberByTypeData[1]},
          {name: "果蔬生鲜", value: this.sellerNumberByTypeData[2]},
          {name: "商店超市", value: this.sellerNumberByTypeData[3]},
          {name: "鲜花绿植", value: this.sellerNumberByTypeData[4]}]

        this.drawSellerNumber()
        this.drawSellerIncrease()
      }).catch(function (err) {
        console.log(err)
      })

      this.$axios({
        method: 'get',
        url: '/statistic/profit',
      }).then(response => {
        let data_ = response.data

        this.recentVolume = data_.recentVolume
        this.volumeByHourData = data_.volumeByHour
        this.volumeByTypeData = data_.volumeByType

        this.volumeByTypePieData = [
          {name: "快餐便当", value: this.volumeByTypeData[0]},
          {name: "甜品饮品", value: this.volumeByTypeData[1]},
          {name: "果蔬生鲜", value: this.volumeByTypeData[2]},
          {name: "商店超市", value: this.volumeByTypeData[3]},
          {name: "鲜花绿植", value: this.volumeByTypeData[4]}]

        this.drawVolumeByHour()
        this.drawVolumeByType()
      }).catch(function (err) {
        console.log(err)
      })
    },
    methods: {
      formatDate: function (date) {
        return global.formatDate(date)
      },
      drawCustomerIncrease: function () {
        let customerIncrease = this.$echarts.init(document.getElementById('customerIncrease'))

        customerIncrease.setOption({
          tooltip: {},
          xAxis: {
            data: ["2018.10以前", "2018.10", "2018.11", "2018.12", "2019.01", "2019.02", "2019.03"]
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
        sellerIncrease.setOption({
          tooltip: {},
          xAxis: {
            data: ["2018.10以前", "2018.10", "2018.11", "2018.12", "2019.01", "2019.02", "2019.03"]
          },
          yAxis: {},
          series: [{
            // name: '销量',
            type: 'line',
            data: this.sellerIncreaseData
          }]
        })
      },
      drawSellerNumber: function () {
        let sellerNumber = this.$echarts.init(document.getElementById('sellerNumber'))
        let sellerNumberByTypePie = this.$echarts.init(document.getElementById('sellerNumberByTypePie'))

        sellerNumber.setOption({
          tooltip: {},
          xAxis: {
            data: ["快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"]
          },
          yAxis: {},
          series: [{
            // name: '销量',
            type: 'bar',
            data: this.sellerNumberByTypeData
          }]
        })

        sellerNumberByTypePie.setOption({
          tooltip: {},
          series: [{
            // name: '销量',
            type: 'pie',
            data: this.sellerNumberByTypePieData
          }],
          tooltip: {
            trigger: 'item',
            formatter: "{b} : {c} ({d}%)"
          },
        })
      },
      drawVolumeByHour: function () {
        let volumeByHour = this.$echarts.init(document.getElementById('volumeByHour'))

        volumeByHour.setOption({
          tooltip: {},
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
        let volumeByTypePie = this.$echarts.init(document.getElementById('volumeByTypePie'))

        volumeByType.setOption({
          tooltip: {},
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

        volumeByTypePie.setOption({
          // xAxis: {
          //   data: ["快餐便当", "甜品饮品", "果蔬生鲜", "商店超市", "鲜花绿植"]
          // },
          // yAxis: {},
          series: [{
            // name: '销量',
            type: 'pie',
            data: this.volumeByTypePieData
          }],
          tooltip: {
            trigger: 'item',
            formatter: "{b} : {c} ({d}%)"
          },
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
