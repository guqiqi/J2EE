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
      <el-col :span="8" v-for="seller in tempSellerList">
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
  const navigation = () => import('../../components/Navigation.vue')
  // import {navigation} from '../components/Navigation'
  export default {
    data() {
      return {
        sellerList: [],
        tempSellerList: [],
        foodType: '全部商家'
      }
    },
    components: {navigation},
    methods: {
      modifyType: function () {
        // 修改商品列表
        if(this.foodType !== '全部商家') {
          let newSellers = []
          for (let i = 0; i < this.sellerList.length; i++) {
            if (this.foodType === this.sellerList[i].type){
              newSellers.push(this.sellerList[i])
            }
          }
          this.tempSellerList = newSellers
        }
        else {
          this.tempSellerList = this.sellerList
        }
      },
      goSellerDetail: function (sellerId) {
        for (let i = 0; i < this.sellerList.length; i++) {
          if (sellerId === this.sellerList[i].sellerId){
            if(this.sellerList[i].status)
              this.$router.push({name: 'sellerDetail', params: {sellerId: sellerId}})
            else
              this.$confirm('商店不在营业时间内，请选择其他商店', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              })
          }
        }
      },
      getAllSeller: function () {
        this.$axios({
          method: 'get',
          url: '/seller/all',
        }).then(response => {
          let sellerList = response.data.sellerList

          for (let i = 0; i < sellerList.length; i++) {
            if (sellerList[i].status === 2) {
              if (new Date(sellerList[i].startHour).getHours() < new Date().getHours() &&
                new Date(sellerList[i].endHour).getHours() > new Date().getHours()){
                sellerList[i].status = true
              }
              else
                sellerList[i].status = false
              this.sellerList.push(sellerList[i])
            }
          }

          this.sellerList = sellerList
          this.tempSellerList = sellerList
        }).catch(function (err) {
          console.log(err)
        })
      }
    },
    mounted(){
      this.getAllSeller()
    }
  }
</script>

<style scoped>

</style>
