<template>
  <div>
    <navigation default_active="/home"/>
    <!--<img src="./../../static/images/banner.png" style="width: 100%; "/>-->
    <el-row style="">
      <el-col :span="20" :offset="2">
        <el-col :span="4">
          <img :src="photo" width="100%" height="100%"/>
        </el-col>
        <el-col :span="20">
          <el-row style="text-align: right; margin-right: 30px; margin-top: 40px; font-weight: bold; font-size: 25px">
            {{sellerName}}
          </el-row>
          <el-row style="text-align: right; margin-right: 30px; margin-top: 60px;"> 销量: {{orderCount}}</el-row>
        </el-col>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="20" :offset="2" style="margin-top: 10px">
        <el-tabs v-model="type">
          <el-tab-pane label="所有商品" name="all">
            <!--<el-row>-->
            <!--<el-radio-group v-model="subTitle" @change="modifyType" size="small">-->
            <!--<el-radio-button :label="subList.type" v-for="subList in foodList"/>-->
            <!--</el-radio-group>-->
            <!--</el-row>-->

            <div v-for="subList in foodList">
              <el-row style="font-size: 24px; font-weight: bold; text-align: left; margin-top: 15px; margin-left: 10px">
                {{subList.type}}
              </el-row>
              <el-row>
                <el-col :span="8" v-for="food in subList.foods">
                  <el-card shadow="always"
                           style="margin-top: 10px; padding: 0; margin-left: 5px; margin-right: 5px">
                    <el-col :span="8">
                      <img width="80%" height="80%" style="margin-left: -30%" :src="food.photo"/>
                    </el-col>
                    <el-col :span="16">
                      <el-row style="text-align: left; color: #272727; font-weight: bold; font-size: 18px">{{food.name}}
                      </el-row>

                      <el-row style="text-align: left; color: #626262; font-size: 10px">{{food.description}}
                      </el-row>
                      <el-row
                        style="text-align: left; color: #f72d20; font-weight: bold; font-size: 15px; margin-top: 15px">
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
                        <el-col v-if="food.num === 0" :span="10">
                          <el-button size="mini" type="primary" @click="firstAddShoppingCart(food.foodId)">加入购物车
                          </el-button>
                        </el-col>
                        <el-col v-else :span="10">
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
        sellerId: '1234566',
        sellerName: '食其家',
        photo: './../../static/images/logo.png',
        address: '南京鼓楼广场',
        phone: '1377777777777',
        orderCount: 10,
        startHour: 8,
        endHour: 19,

        shoppingCart: [],
        totalMoney: 0.0,
        totalCount: 0,
        foodList: [
          {
            type: '热销',
            foods: [
              {
                name: '鱼香肉丝',
                foodId: 1,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 2,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 3,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 4,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              }
            ]
          },
          {
            type: '套餐',
            foods: [
              {
                name: '鱼香肉末',
                foodId: 5,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 6,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 7,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 23.4,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 8,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 23.4,
                description: '本店特色，欢迎品尝',
                num: 0
              }
            ]
          },
          {
            type: '小吃',
            foods: [
              {
                name: '鱼香肉丝',
                foodId: 9,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 10,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 11,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 12,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              }
            ]
          },
          {
            type: '饮料',
            foods: [
              {
                name: '鱼香肉丝',
                foodId: 13,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 14,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 15,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              },
              {
                name: '鱼香肉丝',
                foodId: 16,
                photo: './../static/images/logo.png',
                money: 23.4,
                discountMoney: 13,
                description: '本店特色，欢迎品尝',
                num: 0
              }
            ]
          },
        ]
      }
    },
    methods: {
      modifyType: function () {
        console.log(this.type)
      },
      firstAddShoppingCart: function (foodId) {
        console.log(foodId)

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
        console.log(id)
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
        this.$router.push({name: 'checkout', params: {foodList: this.shoppingCart}})
      }
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
