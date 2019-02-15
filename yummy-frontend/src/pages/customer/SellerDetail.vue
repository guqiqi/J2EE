<template>
  <div>
    <navigation default_active="/home"/>
    <!--<img src="./../../static/images/banner.png" style="width: 100%; "/>-->
    <el-row style="">
      <el-col :span="20" :offset="2">
        <el-col :span="4">
          <img :src="photo" width="100%" height="100%" style="margin-top: 20px"/>
        </el-col>
        <el-col :span="20">
          <el-row style="text-align: right; margin-right: 30px; margin-top: 40px; font-weight: bold; font-size: 25px">
            {{sellerName}}
          </el-row>
          <el-row v-if="this.discount !== [1, 1, 1]" style="text-align: right; margin-right: 30px; margin-top: 20px;">
            <span style="background-color: red; color: white; font-weight: bold; padding-right: 2px; padding-left: 2px; border-radius: 5px">折</span>
            <span v-if="this.discount[0] !== 1">店铺对普通会员{{(this.discount[0]*100).toFixed(0)}}折优惠,</span>
            <span v-if="this.discount[1] !== 1">对白银会员{{(this.discount[1]*100).toFixed(0)}}折优惠,</span>
            <span v-if="this.discount[2] !== 1">对黄金会员{{(this.discount[2]*100).toFixed(0)}}折优惠</span>
          </el-row>
          <el-row  v-for="item in groupDiscount" style="text-align: right; margin-right: 30px; margin-top: 10px;">
            <span style="background-color: red; color: white; font-weight: bold; padding-right: 2px; padding-left: 2px; border-radius: 5px">惠</span>
            <span>
               <span v-for="(name, index) in item.foodNames">
                 {{name}}
                 <span v-if="index !== item.foodNames.length - 1">➕</span>
               </span>仅需<span style="color: red; font-weight: bold"> {{item.discountMoney}}</span>
            </span>
          </el-row>
          <el-row style="text-align: right; margin-right: 30px; margin-top: 10px">销量: {{orderCount}}</el-row>
        </el-col>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="20" :offset="2" style="margin-top: 10px">
        <el-tabs v-model="type">
          <el-tab-pane label="所有商品" name="all">
            <div v-for="subList in foodList">
              <el-row style="font-size: 24px; font-weight: bold; text-align: left; margin-top: 15px; margin-left: 10px">
                {{subList.type}}
              </el-row>
              <el-row>
                <el-col :span="8" v-for="food in subList.foods">
                  <el-card shadow="always"
                           style="margin-top: 10px; margin-left: 5px; margin-right: 5px; padding: 0px">
                    <el-col :span="8">
                      <img width="80%" height="80%" style="margin-left: -30%" :src="food.photo"/>
                    </el-col>
                    <el-col :span="16">
                      <el-row style="text-align: left; color: #272727; font-weight: bold; font-size: 18px">{{food.name}}
                      </el-row>

                      <el-row style="text-align: left; color: #626262; font-size: 10px">{{food.description}}
                      </el-row>
                      <el-row
                        style="text-align: left; color: #f72d20; font-weight: bold; font-size: 15px; margin-top: 5px">
                        <el-col :span="14" style="margin-top: 4px">
                          ¥
                          <span v-if="food.money === food.discountMoney">
                            {{food.money.toFixed(2)}}
                          </span>
                          <span v-else>
                            <s>{{food.money.toFixed(2)}}</s>
                            {{food.discountMoney.toFixed(2)}}
                          </span>
                        </el-col>
                        <el-col v-if="food.num === 0" :span="10" style="margin-bottom: 10px">
                          <el-button size="mini" type="primary" @click="firstAddShoppingCart(food.foodId)">加入购物车
                          </el-button>
                        </el-col>
                        <el-col v-else :span="10" style="margin-bottom: 10px">
                          <el-input-number size="mini" v-model="food.num" @change="addInDetail(food.foodId, food.num)"
                                           style="width: 100%"/>
                        </el-col>
                      </el-row>
                    </el-col>
                  </el-card>
                </el-col>
              </el-row>
            </div>

          </el-tab-pane>
          <el-tab-pane label="商家介绍" name="info">
            <el-row>
              <el-col :span="10" class="title">
                商家名称
              </el-col>
              <el-col :span="14" class="content">
                {{sellerName}}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10" class="title">
                经营地址
              </el-col>
              <el-col :span="14" class="content">
                {{address}}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10" class="title">
                商家编号
              </el-col>
              <el-col :span="14" class="content">
                {{sellerId}}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10" class="title">
                联系电话
              </el-col>
              <el-col :span="14" class="content">
                {{phone}}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="10" class="title">
                营业时间
              </el-col>
              <el-col :span="14" class="content">
                {{startHour}} : 00 - {{endHour}} : 00
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>

    <div
      style="position: fixed; bottom: 0; right: 0; width: 25%; border-top: 2px #2e78cd solid;">
      <el-card body-style="padding: 0">
        <el-row style="padding: 0; margin: 0; border-bottom: 1px #adadad solid">
          <el-col :span="16" style="text-align: left; margin-left: 10px; padding-top: 4px; padding-bottom: 4px">
          <span>
            购物车
          </span>
            <span style="color: #2e78cd;" @click="clearShoppingCart">
            [清空]
          </span>
          </el-col>
        </el-row>

        <el-row v-for="food in shoppingCart" style="margin-top: 5px; margin-bottom: 5px">
          <el-col :span="11" style="text-align: left; margin-left: 8px; margin-top: 2px">
            {{food.name}}
          </el-col>
          <el-col :span="6">
            <el-input-number size="mini" v-model="food.num" @change="addInCart(food.foodId, food.num)"
                             style="width: 100%; "/>
          </el-col>
          <el-col :span="5" :offset="1" style="color: red; margin-top: 2px">
            {{(food.discountMoney * food.num).toFixed(2)}}
          </el-col>
        </el-row>

        <el-row style="padding: 0; margin: 0">
          <el-col :span="16"
                  style="background-color: #595959; color: white; font-weight: bold; font-size: 20px; text-align: left; padding-top: 15px; padding-left: 10px">
            <span v-if="totalCount !== 0">
              <el-badge :value="totalCount" class="item">
                <img src="../../../static/images/cart.png" width="30px" height="30px"/>
              </el-badge>
              <span style="margin-left: 10px">¥ {{this.totalMoney.toFixed(2)}}</span>
            </span>
            <span v-else>
              <img src="../../../static/images/cart.png" width="30px" height="30px"/>
              <span style="margin-left: 10px; padding-top: -10px">¥ {{this.totalMoney.toFixed(2)}}</span>
            </span>
          </el-col>
          <el-col :span="8"
                  style="background-color: rgba(68,169,66,0.75); color: white; font-weight: bold; font-size: 20px; padding-top: 15px; padding-bottom: 9px">
            <span @click="checkout">去结算</span>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
  const navigation = () => import('../../components/Navigation.vue')

  export default {
    components: {navigation},
    name: "seller-detail",
    data() {
      return {
        type: 'all', //tab的type
        sellerId: this.$route.params.sellerId,
        sellerName: '',
        photo: '',
        address: '',
        phone: '',
        orderCount: 0,
        startHour: 0,
        endHour: 0,

        discount: [],
        groupDiscount: [],

        shoppingCart: [],
        totalMoney: 0.0,
        totalCount: 0,
        foodList: []
      }
    },
    methods: {
      firstAddShoppingCart: function (foodId) {
        let name = ''
        let discountMoney = 0.0

        for (let i = 0; i < this.foodList.length; i++) {
          for (let j = 0; j < this.foodList[i].foods.length; j++) {
            if (this.foodList[i].foods[j].foodId === foodId) {
              this.foodList[i].foods[j].num = 1
              name = this.foodList[i].foods[j].name
              discountMoney = this.foodList[i].foods[j].discountMoney
            }
          }
        }

        this.shoppingCart.push({
          name: name,
          foodId: foodId,
          discountMoney: discountMoney,
          num: 1
        })

        this.calculateTotalMoney()
        this.calculateTotalCount()
      },
      addInDetail: function (id, num) {
        // 详情界面点击添加/删除
        if (num !== 0) {
          for (let i = 0; i < this.shoppingCart.length; i++) {
            if (this.shoppingCart[i].foodId === id) {
              this.shoppingCart[i].num = num
            }
          }
        }
        else { //减为0，需要从购物车中删除
          this.removeFromCart(id)
        }

        this.calculateTotalMoney()
        this.calculateTotalCount()
      },
      addInCart: function (id, num) {
        // 右下角购物车点击加/删除
        if (num !== 0) {
          for (let i = 0; i < this.foodList.length; i++) {
            for (let j = 0; j < this.foodList[i].foods.length; j++) {
              if (this.foodList[i].foods[j].foodId === id)
                this.foodList[i].foods[j].num = num
            }
          }
        }
        else { //减为0，需要从购物车中删除
          this.removeFromCart(id)
        }

        this.calculateTotalMoney()
        this.calculateTotalCount()
      },
      clearShoppingCart: function () {
        this.shoppingCart = []

        for (let i = 0; i < this.foodList.length; i++) {
          for (let j = 0; j < this.foodList[i].foods.length; j++) {
            this.foodList[i].foods[j].num = 0
          }
        }

        this.totalMoney = 0.0
        this.totalCount = 0
      },
      removeFromCart(id) {
        for (let i = 0; i < this.foodList.length; i++) {
          for (let j = 0; j < this.foodList[i].foods.length; j++) {
            if (this.foodList[i].foods[j].foodId === id) {
              this.foodList[i].foods[j].num = 0
              break
            }
          }
        }

        for (let i = 0; i < this.shoppingCart.length; i++) {
          if (this.shoppingCart[i].foodId === id) {
            this.shoppingCart.splice(i, 1)
            break
          }
        }
      },
      calculateTotalMoney: function () {
        let total = 0.0

        for (let i = 0; i < this.shoppingCart.length; i++) {
          total += this.shoppingCart[i].discountMoney * this.shoppingCart[i].num
        }

        this.totalMoney = total
      },
      calculateTotalCount: function () {
        let total = 0

        for (let i = 0; i < this.shoppingCart.length; i++) {
          total += this.shoppingCart[i].num
        }

        this.totalCount = total
      },
      checkout: function () {
        this.$router.push({name: 'checkout', params: {foodList: this.shoppingCart, sellerId: this.sellerId}})
      },
    },
    mounted() {
      console.log(this.$route.params.sellerId)

      this.$axios({
        method: 'get',
        url: '/seller/discount/composition/all',
        params: {
          sellerId: this.sellerId
        }
      }).then(response => {
        let data_ = response.data
        this.groupDiscount = data_.composedDiscounts

        for (let i = 0; i < this.groupDiscount.length; i++) {
          if(new Date().getDate() < new Date(this.groupDiscount[i].date[0]).getDate() || new Date().getDate() > new Date(this.groupDiscount[i].date[1]).getDate())
            this.groupDiscount.slice(i, 1)
        }
      }).catch(function (err) {
        console.log(err)
      })

      this.$axios({
        method: 'get',
        url: '/seller/info',
        params: {
          sellerId: this.sellerId
        }
      }).then(response => {
        this.sellerName = response.data.name
        this.phone = response.data.phone
        this.address = response.data.address
        this.orderCount = response.data.orderCount
        this.startHour = new Date(response.data.startHour).getHours()
        this.endHour = new Date(response.data.endHour).getHours()
        this.photo = response.data.icon
        this.discount = [parseFloat(response.data.discount[0]), parseFloat(response.data.discount[1]), parseFloat(response.data.discount[2])]
      }).catch(function (err) {
        console.log(err)
      })

      this.$axios({
        method: 'get',
        url: '/seller/food/all',
        params: {
          sellerId: this.sellerId
        }
      }).then(response => {
        let foodList = response.data.foodList
        let foodType = []

        for (let i = 0; i < foodList.length; i++) {
          if (foodType.indexOf(foodList[i].foodType) === -1 && foodList[i].stock > 0) {
            foodType.push(foodList[i].foodType)
          }
        }

        let classificationFood = []
        for (let i = 0; i < foodType.length; i++) {
          let foods = []
          for (let j = 0; j < foodList.length; j++) {
            if(foodList[j].foodType === foodType[i] && foodList[i].stock > 0){
              foodList[j].num = 0
              foods.push(foodList[j])
            }
          }
          classificationFood.push({
            type: foodType[i],
            foods: foods
          })
        }
        this.foodList = classificationFood
      }).catch(function (err) {
        console.log(err)
      })
    }

  }
</script>

<style scoped>
  .title {
    font-size: 30px;
    font-weight: bold;
    margin-top: 20px;
  }

  .content {
    font-size: 30px;
    margin-top: 30px;
  }
</style>
