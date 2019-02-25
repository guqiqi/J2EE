<template>
  <div>
    <admin-navigation default_active="/admin/cost"/>

    <el-col :span="18" :offset="3" style="margin-top: 40px">
      <el-row style="font-size: 25px; font-weight: bold; text-align: right; margin-right: 30px">
        <el-col :span="6">
          <el-button size="small" :type="type[0]" @click="getDailyProfit">本日</el-button>
          <el-button size="small" :type="type[1]" @click="getMonthlyProfit">本月</el-button>
          <el-button size="small" :type="type[2]" @click="getTotalProfit">总额</el-button>
        </el-col>
        截至<span style="color: red"> {{formatDate(today)}} </span>, {{prompt}}系统收益<span
        style="color: red"> {{profit.toFixed(2)}} </span>元
      </el-row>
      <el-table
        :data="sellerTableData"
        style="width: 100%">
        <el-table-column
          prop="sellerId"
          label="商户号">
        </el-table-column>
        <el-table-column
          prop="name"
          label="商户名称">
        </el-table-column>
        <el-table-column
          prop="amount"
          label="营业额">
        </el-table-column>
        <el-table-column
          prop="profit"
          label="网站收益">
        </el-table-column>
      </el-table>
    </el-col>
  </div>
</template>

<script>
  import AdminNavigation from "../../components/AdminNavigation"
  import global from '../../../static/Global'

  export default {
    components: {AdminNavigation},
    name: "admin-cost",
    data() {
      return {
        type: ['primary', '', ''],
        prompt: '今日',
        sellerTableData: [
          {
            sellerId: 'skdjgnx',
            name: '食其家',
            amount: 23403,
            profit: 234
          }
        ],
        today: new Date(),
        profit: 0.98
      }
    },
    methods: {
      formatDate: function (date) {
        return global.formatDate(date)
      },
      getDailyProfit: function () {
        this.type = ['primary', '', '']
        this.prompt = '今日'
        this.today = new Date()

        this.$axios({
          method: 'get',
          url: '/statistic/profit/daily',
        }).then(response => {
          let data_ = response.data

          this.sellerTableData = data_.sellerTableData
          this.profit = data_.profit
        }).catch(function (err) {
          console.log(err)
        })
      },
      getMonthlyProfit: function () {
        this.type = ['', 'primary', '']
        this.prompt = '本月'
        this.today = new Date()

        this.$axios({
          method: 'get',
          url: '/statistic/profit/monthly',
        }).then(response => {
          let data_ = response.data

          this.sellerTableData = data_.sellerTableData
          this.profit = data_.profit
        }).catch(function (err) {
          console.log(err)
        })
      },
      getTotalProfit: function () {
        this.type = ['', '', 'primary']
        this.prompt = ''
        this.today = new Date()

        this.$axios({
          method: 'get',
          url: '/statistic/profit/total',
        }).then(response => {
          let data_ = response.data

          this.sellerTableData = data_.sellerTableData
          this.profit = data_.profit
        }).catch(function (err) {
          console.log(err)
        })
      }
    },
    mounted(){
      this.getDailyProfit()
    }
  }
</script>

<style scoped>

</style>
