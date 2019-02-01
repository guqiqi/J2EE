<template>
  <div>
    <navigation default_active="/home"/>
    <el-row>
      <el-col :span="20" :offset="2" style="margin-top: 10px">
        <el-radio-group v-model="foodType" @change="modifyType" size="small">
          <el-radio-button label="全部商家"/>
          <el-radio-button label="快餐便当"/>
          <el-radio-button label="甜品饮品"/>
          <el-radio-button label="果蔬生鲜"/>
          <el-radio-button label="商店超市"/>
          <el-radio-button label="鲜花绿植"/>
        </el-radio-group>
      </el-col>
    </el-row>
    <el-col :span="20" :offset="2">
      <el-col :span="8" v-for="seller in sellerList">
        <el-card shadow="never"
                 style="margin-top: 10px; padding: 0; border-left-color: white; border-right-color: white">
          <div @click="goSellerDetail(seller.sellerId)">
            <el-col :span="8">
              <img width="80%" height="80%" style="margin-left: -30%" :src="seller.icon"/>
            </el-col>
            <el-col :span="16">
              <el-row style="text-align: left; color: #272727; font-weight: bold; font-size: 18px">{{seller.name}}
              </el-row>
              <el-row v-if="seller.status" style="text-align: left; margin-top: 15%; color: #d61524; font-size: 15px">
                正在营业
              </el-row>
              <el-row v-else="seller.status" style="text-align: left; margin-top: 15%; color: #272727; font-size: 15px">
                休息中
              </el-row>
            </el-col>
          </div>
        </el-card>
      </el-col>
    </el-col>
  </div>
</template>

<script>
  const navigation = () => import('../components/Navigation.vue')
  // import {navigation} from '../components/Navigation'
  export default {
    data() {
      return {
        sellerList: [
          {
            sellerId: 1,
            name: '食其家',
            status: true,
            icon: './../static/images/logo.png'
          },
          {
            sellerId: 2,
            name: '食其家',
            status: true,
            icon: './../static/images/logo.png'
          },
          {
            sellerId: 3,
            name: '食其家',
            status: true,
            icon: './../static/images/logo.png'
          },
          {
            sellerId: 4,
            name: '食其家',
            status: false,
            icon: './../static/images/logo.png'
          }
        ],
        foodType: '全部商家'
      }
    },
    components: {navigation},
    methods: {
      modifyType: function () {
        console.log(this.foodType)
        // TODO 修改商品列表
      },
      goSellerDetail: function (sellerId) {
        console.log(sellerId)
        this.$router.push('/sellerDetail')
      }
    }
  }
</script>

<style scoped>

</style>
