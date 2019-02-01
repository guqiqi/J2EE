<template>
  <div>
    <navigation default_active="/home"/>

    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-col :span="10">
        <el-card body-style="padding: 0" style="margin-right: 10px">
          <el-row
            style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px; border-bottom: #cbcbcb 1px solid">
            订单详情
          </el-row>
          <el-table
            :data="foodList"
            style="width: 90%; margin-left: 5%">
            <el-table-column
              prop="name"
              label="商品名称"
              width="120"/>
            <el-table-column
              label="数量">
              <template slot-scope="scope">
                <el-input-number @change="addInCart(scope.row.foodId, scope.row.num)" v-model="scope.row.num"/>
              </template>
            </el-table-column>
            <el-table-column
              prop="sellerName"
              label="小计(元)"
              width="100">
              <template slot-scope="scope">
                ¥{{(scope.row.num * scope.row.discountMoney).toFixed(2)}}
              </template>
            </el-table-column>
          </el-table>

          <el-row style="padding-top: 10px; padding-bottom: 10px; border-top: #cbcbcb 1px solid">
            <el-col :span="16" style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px;">
              合计
            </el-col>
            <el-col :span="8" style="text-align: right; padding-right: 50px; font-size: 16px">
              ¥ {{total.toFixed(2)}}
            </el-col>
            <el-col :span="16" style="text-align: left; padding-left: 10px; margin-top: 4px; font-size: 15px;">
              店铺优惠
            </el-col>
            <el-col :span="8" style="text-align: right; padding-right: 50px; margin-top: 4px; font-size: 15px">
              - ¥ {{sellerDiscount.toFixed(2)}}
            </el-col>
            <el-col :span="16" style="text-align: left; padding-left: 10px; margin-top: 4px; font-size: 15px;">
              Yummy优惠
            </el-col>
            <el-col :span="8" style="text-align: right; padding-right: 50px; margin-top: 4px; font-size: 15px">
              - ¥ {{yummyDiscount.toFixed(2)}}
            </el-col>
            <el-col :span="24" style="text-align: right; padding-right: 50px; font-size: 30px; font-weight: bold; color: red; margin-top: 30px; margin-bottom: 10px">
              ¥ {{(total - sellerDiscount - yummyDiscount).toFixed(2)}}
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card body-style="padding: 0" style="margin-left: 10px">
          <el-row
            style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;">
            收货地址
          </el-row>
          <el-col :span="16" style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px;">

          </el-col>

          <el-row
            style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;">
            支付方式
          </el-row>

          <el-row
            style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;">
            送达时间
          </el-row>

          <el-row>
          <el-button type="danger" style="width: 90%; margin-bottom: 10px" @click="placeOrder">确认下单</el-button>
          </el-row>
        </el-card>
      </el-col>
    </el-col>
  </div>
</template>

<script>
  const navigation = () => import('../components/Navigation.vue')

  export default {
    name: "check-out",
    components: {navigation},
    data() {
      return {
        foodList: [],
        total: 34.4,
        sellerDiscount : 0.94,
        yummyDiscount: 0.34
      }
    },
    methods: {
      calculateTotalMoney: function () {
        let total = 0.0

        for (let i = 0; i < this.foodList.length; i++) {
          total += this.foodList[i].discountMoney * this.foodList[i].num
        }

        this.total = total
      },
      addInCart: function (id, num) {
        if(num === 0) {
          for (let i = 0; i < this.foodList.length; i++) {
            if (this.foodList[i].foodId === id) {
              this.foodList.splice(i, 1)
              break
            }
          }
        }

        this.calculateTotalMoney()
      },
      placeOrder: function () {
        // TODO
      }
    },
    mounted() {
      console.log(this.$route.params)
      this.foodList = this.$route.params.foodList

      // TODO 计算优惠
    }
  }
</script>

<style scoped>

</style>
