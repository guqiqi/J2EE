<template>
  <div>
    <seller-navigation default_active="/seller/home"/>
    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-col :span="14">
        <el-card body-style="padding: 0" style="margin-right: 10px; padding-bottom: 20px">
          <el-row
            style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px; border-bottom: #cbcbcb 1px solid">
            <el-col :span="14">菜单</el-col>
            <el-col :span="10">
              <el-button size="mini" type="primary" @click="startModifyType">编辑分类</el-button>
              <el-button size="mini" type="primary" @click="startAddFood">新增商品</el-button>
            </el-col>
          </el-row>

          <el-radio-group v-model="foodType" @change="modifyChosenType" size="small"
                          style="margin-top: 20px; margin-bottom: 20px">
            <el-radio-button label="全部商品"/>
            <el-radio-button v-for="title in this.subType" :label="title"/>
          </el-radio-group>

          <el-col :span="12" v-for="food in tempFoodList">
            <el-card shadow="always"
                     style="margin-top: 10px; padding: 0 0 10px;margin-left: 10px; margin-right: 10px">
              <el-col :span="10">
                <img width="80%" height="80%" style="margin-left: -30%" :src="food.photo"/>
              </el-col>
              <el-col :span="14">
                <el-row style="text-align: left; color: #272727; font-weight: bold; font-size: 18px">{{food.name}}
                </el-row>

                <el-row style="text-align: left; color: #626262; font-size: 10px">{{food.description}}
                </el-row>
                <el-row style="text-align: left; font-weight: bold; font-size: 15px; margin-top: 15px">
                  <el-col :span="14" style="color: #f72d20; ">
                    ¥<span v-if="food.money === food.discountMoney">{{food.money.toFixed(2)}}</span>
                    <span v-else>
                      <s>{{food.money.toFixed(2)}}</s>
                      {{food.discountMoney.toFixed(2)}}
                    </span>
                  </el-col>
                  <el-col :span="10">
                    <span
                      style="background-color: red; color: white; border-radius: 5px; font-size: 14px; margin-left: 5px; padding-left: 1px; padding-right: 1px">余</span>
                    {{food.stock}}份
                  </el-col>
                </el-row>
                <el-row style="margin-top: 10px">
                  <el-col :span="10">
                    <el-button size="mini" type="danger" @click="deleteFood(food.foodId)">删除
                    </el-button>
                  </el-col>
                  <el-col :span="10">
                    <el-button size="mini" type="primary" @click="startModifyFood(food.foodId)">编辑
                    </el-button>
                  </el-col>
                </el-row>
              </el-col>
            </el-card>
          </el-col>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card body-style="padding: 0" style="margin-left: 10px">
          <el-row
            style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;">
            <el-col :span="18">会员优惠</el-col>
            <el-col :span="6">
              <el-button size="mini" type="primary" @click="startModifyCustomerDiscount">修改</el-button>
            </el-col>
          </el-row>

          <el-row class="smallTitle">
            <el-col :span="18">普通会员</el-col>
            <el-col :span="6">{{this.customerDiscount[0]}}</el-col>
          </el-row>

          <el-row class="smallTitle">
            <el-col :span="18">白银会员</el-col>
            <el-col :span="6">{{this.customerDiscount[1]}}</el-col>
          </el-row>

          <el-row class="smallTitle">
            <el-col :span="18">黄金会员</el-col>
            <el-col :span="6">{{this.customerDiscount[2]}}</el-col>
          </el-row>
        </el-card>

        <el-card body-style="padding: 0" style="margin-left: 10px; margin-top: 20px">
          <el-row
            style="text-align: left; padding-left: 10px; font-weight: bold; font-size: 20px; padding-top: 10px; padding-bottom: 10px;">
            <el-col :span="18">组合优惠</el-col>
            <el-col :span="6">
              <el-button size="mini" type="primary" @click="startAddComposedDiscount">增加</el-button>
            </el-col>
          </el-row>

          <el-card shadow="always" v-for="item in composedDiscounts"
                   style="margin-bottom: 10px; padding: 0 0 10px;margin-left: 10px; margin-right: 10px">
            <el-col :span="18">
              <el-row style="text-align: left; color: #272727; font-weight: bold; font-size: 18px">
                <span v-for="name in item.foodNames">{{name}} </span>
              </el-row>
              <el-row style="text-align: left; font-weight: bold; font-size: 15px; margin-top: 15px">
                <el-col :span="14" style="color: #f72d20; ">
                <span>
                      <s>{{item.money.toFixed(2)}}</s>
                      {{item.discountMoney.toFixed(2)}}
                    </span>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="6">
              <el-row>
                <el-button size="mini" type="danger" style="" @click="deleteComposedDiscount(item.discountId)">删除
                </el-button>
              </el-row>
              <el-row>
                <el-button size="mini" type="primary" style="margin-top: 10px"
                           @click="startModifyComposedDiscount(item.discountId)">编辑
                </el-button>
              </el-row>
            </el-col>
          </el-card>
        </el-card>
      </el-col>
    </el-col>

    <el-dialog title="商品信息" :visible.sync="foodFormVisible">
      <el-form>
        <el-form-item label="商品名称" label-width="80px">
          <el-input v-model="tempFoodDetail.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="商品描述" label-width="80px">
          <el-input v-model="tempFoodDetail.description" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="商品分类" label-width="80px">
          <el-select v-model="tempFoodDetail.foodType" placeholder="请选择" style="width: 100%">
            <el-option
              v-for="item in this.subType"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品图片" label-width="80px">
          <el-upload
            class="avatar-uploader"
            action="/api/upload/imgUpload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="this.tempFoodDetail.photo" :src="tempFoodDetail.photo" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="原价" label-width="80px">
          <el-input v-model="tempFoodDetail.money" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="折后价" label-width="80px">
          <el-input v-model="tempFoodDetail.discountMoney" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="库存" label-width="80px">
          <el-input-number v-model="tempFoodDetail.stock" :min="1" :max="999" label="描述文字" style="width: 100%"/>
        </el-form-item>
        <el-form-item label="销售时间" label-width="80px">
          <el-time-picker
            style="width: 200px;"
            v-model="tempFoodDetail.startTime"
            :picker-options="{selectableRange: '0:0:0 - 23:59:59'}"
            placeholder="任意时间点">
          </el-time-picker>
          <el-time-picker
            style="width: 200px; margin-left: 20px;"
            arrow-control
            v-model="tempFoodDetail.endTime"
            :picker-options="{selectableRange: '0:0:0 - 23:59:59'}"
            placeholder="任意时间点">
          </el-time-picker>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button v-if="this.isAdd" type="primary" @click="addFood()">确 定</el-button>
        <el-button v-else type="primary" @click="modifyFood(tempFoodDetail.foodId)">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="商品分类" :visible.sync="foodTypeFormVisible">
      <el-tag
        :key="tag"
        v-for="tag in tempFoodType"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)">
        {{tag}}
      </el-tag>
      <el-input
        class="input-new-tag"
        v-if="inputVisible"
        v-model="tempType"
        ref="saveTagInput"
        size="small"
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
      >
      </el-input>
      <el-button v-else class="button-new-tag" style="margin-top: 10px" size="small" @click="showInput">新增分类
      </el-button>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="modifyType">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="会员优惠" :visible.sync="customerDiscountFormVisible">
      <el-form>
        <el-form-item label="普通会员" label-width="80px">
          <el-input v-model="tempCustomerDiscount[0]" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="白银会员" label-width="80px">
          <el-input v-model="tempCustomerDiscount[1]" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="黄金会员" label-width="80px">
          <el-input v-model="tempCustomerDiscount[2]" auto-complete="off"/>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="modifyCustomerDiscount">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="组合优惠" :visible.sync="composedDiscountFormVisible">
      <el-form>
        <el-form-item label="商品" label-width="80px">
          <el-select v-model="tempComposedDiscount.foodIds" multiple placeholder="请选择" style="width: 100%"
                     @change="changeMoney">
            <el-option
              v-for="item in foodList"
              :key="item.foodId"
              :label="item.name"
              :value="item.foodId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-row style="margin-top: 10px; margin-bottom: 20px; text-align: left; margin-left: 38px">
          <el-col :span="5">
            总价
          </el-col>
          <el-col :span="19" style="text-align: right">
            {{this.tempComposedDiscount.money}}
          </el-col>
        </el-row>
        <el-form-item label="组合价" label-width="80px">
          <el-input v-model="tempComposedDiscount.discountMoney" auto-complete="off"/>
        </el-form-item>
        <el-form-item label="促销时间" label-width="80px">
          <el-date-picker
            style="width: 100%"
            v-model="tempComposedDiscount.date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button v-if="this.isAdd" type="primary" @click="addComposedDiscount">确 定</el-button>
        <el-button v-else type="primary" @click="modifyComposedDiscount">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import SellerNavigation from "../../components/SellerNavigation"
  import ElRow from "element-ui/packages/row/src/row"
  import global from '../../../static/Global'

  export default {
    components: {
      ElRow,
      SellerNavigation
    },
    name: "seller-home",
    data() {
      return {
        isAdd: false,
        foodFormVisible: false,
        foodTypeFormVisible: false,
        customerDiscountFormVisible: false,
        composedDiscountFormVisible: false,
        foodType: '全部商品',
        subType: [],
        tempFoodList: [],
        foodList: [],
        customerDiscount: [1, 0.9, 0.8], //店铺会员优惠价
        composedDiscounts: [ //店铺组合优惠列表
        ],
        inputVisible: false,
        tempFoodType: [], //原来的子分类
        tempType: '', //用户填写的子分类
        tempComposedDiscount: {//临时组合优惠
          discountId: 0,
          foodIds: [],
          foodNames: [],
          money: 0,
          discountMoney: 0,
          startTime: new Date(),
          endTime: new Date()
        },
        tempCustomerDiscount: [], //用户希望修改的会员优惠
        tempFoodDetail: {
          foodId: 0,
          name: '',
          description: '',
          photo: '',
          money: 0,
          foodType: '',
          discountMoney: 0,
          startTime: global.startHour,
          endTime: global.endHour,
          stock: 0
        }
      }
    },
    methods: {
      // 图片上传
      handleAvatarSuccess(res, file) {
        let now = new Date()
        let exitTime = now.getTime() + 1000

        let isSleep = true
        while (isSleep) {
          now = new Date()
          if (now.getTime() > exitTime)
            isSleep = false
        }

        this.tempFoodDetail.photo = res.url
      },
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isLt2M
      },

      // 标签编辑
      handleClose(tag) {
        this.subType.splice(this.subType.indexOf(tag), 1)
      },

      showInput() {
        this.inputVisible = true
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus()
        })
      },

      handleInputConfirm() {
        let inputValue = this.tempType
        if (inputValue) {
          this.subType.push(inputValue)
        }
        this.inputVisible = false
        this.tempType = ''
      },

      cancel: function () {
        this.foodFormVisible = false
        this.customerDiscountFormVisible = false
        this.composedDiscountFormVisible = false
      },
      modifyChosenType: function () {
        // 修改选择的类型
        this.getFoodsBySubType()
      },
      addFood: function () {
        // 增加商品
        this.$axios({
          method: 'post',
          url: '/seller/food/add',
          data: {
            sellerId: global.userId,
            name: this.tempFoodDetail.name,
            description: this.tempFoodDetail.description,
            photo: this.tempFoodDetail.photo,
            money: this.tempFoodDetail.money,
            discountMoney: this.tempFoodDetail.discountMoney,
            startTime: this.tempFoodDetail.startTime,
            endTime: this.tempFoodDetail.endTime,
            stock: this.tempFoodDetail.stock,
            foodType: this.tempFoodDetail.foodType,
          }
        }).then(response => {
          this.isLoading = false
          if (response.data.isSuccess) {
            this.getAllFoods()
            this.$message({
              message: '商品添加成功',
              type: 'success'
            })
          }
          else {
            this.$alert('系统繁忙，请稍后再试', '提示', {
              confirmButtonText: '确定',
            })
          }
        }).catch(function (err) {
          console.log(err)
        })

        this.foodFormVisible = false
      },
      modifyFood: function (foodId) {
        // 修改商品
        this.$axios({
          method: 'post',
          url: '/seller/food/modify',
          data: {
            sellerId: global.userId,
            foodId: this.tempFoodDetail.foodId,
            name: this.tempFoodDetail.name,
            description: this.tempFoodDetail.description,
            photo: this.tempFoodDetail.photo,
            money: this.tempFoodDetail.money,
            discountMoney: this.tempFoodDetail.discountMoney,
            startTime: this.tempFoodDetail.startTime,
            endTime: this.tempFoodDetail.endTime,
            stock: this.tempFoodDetail.stock,
            foodType: this.tempFoodDetail.foodType,
          }
        }).then(response => {
          this.isLoading = false
          if (response.data.isSuccess) {
            this.foodFormVisible = false
            this.getAllFoods()
            this.$message({
              message: '商品修改成功',
              type: 'success'
            })
          }
          else {
            this.$alert('系统繁忙，请稍后再试', '提示', {
              confirmButtonText: '确定',
            })
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      deleteFood: function (foodId) {
        // 删除商品
        this.$axios({
          method: 'delete',
          url: '/seller/food/delete',
          params: {
            foodId: foodId
          }
        }).then(response => {
          this.isLoading = false
          if (response.data.isSuccess) {
            this.getAllFoods()
            this.$message({
              message: '商品删除成功',
              type: 'success'
            })
          }
          else {
            this.$alert('系统繁忙，请稍后再试', '提示', {
              confirmButtonText: '确定',
            })
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      startModifyFood: function (foodId) {
        this.foodFormVisible = true
        this.isAdd = false

        for (let i = 0; i < this.foodList.length; i++) {
          if (this.foodList[i].foodId === foodId) {
            this.tempFoodDetail = this.foodList[i]
            return
          }
        }
      },
      startAddFood: function () {
        this.foodFormVisible = true
        this.isAdd = true

        this.tempFoodDetail = {
          name: '',
          description: '',
          photo: '',
          money: 0.0,
          discountMoney: 0.0,
          startTime: global.startHour,
          endTime: global.endHour,
          stock: 99,
          foodType: this.subType[0],
        }
      },
      startModifyType: function () {
        this.tempFoodType = this.subType
        this.foodTypeFormVisible = true
      },
      startModifyCustomerDiscount: function () {
        this.tempCustomerDiscount = [this.customerDiscount[0], this.customerDiscount[1], this.customerDiscount[2]]
        this.customerDiscountFormVisible = true
      },
      startAddComposedDiscount: function () {
        this.tempComposedDiscount = {
          foodIds: [],
          foodNames: [],
          money: 0,
          discountMoney: 0,
          date: []
        }
        this.composedDiscountFormVisible = true
        this.isAdd = true
      },
      startModifyComposedDiscount: function (discountId) {
        for (let i = 0; i < this.composedDiscounts.length; i++) {
          if (this.composedDiscounts[i].discountId === discountId) {
            let foods = []
            for(let j = 0; j < this.composedDiscounts[i].foodIds.length; j++){
              foods.push(parseInt(this.composedDiscounts[i].foodIds[j]))
            }
            this.tempComposedDiscount = {
              discountId: this.composedDiscounts[i].discountId,
              foodIds: foods,
              foodNames: this.composedDiscounts[i].foodNames,
              money: this.composedDiscounts[i].money,
              discountMoney: this.composedDiscounts[i].discountMoney,
              date: this.composedDiscounts[i].date
            }
            break
          }
        }

        this.composedDiscountFormVisible = true
        this.isAdd = false
      },
      modifyType: function () {
        // 修改子分类
        this.subType = this.tempFoodType

        this.$axios({
          method: 'post',
          url: '/seller/type/modify',
          data: {
            sellerId: global.userId,
            foodType: this.subType
          }
        }).then(response => {
          if (response.data.isSuccess) {
            this.foodTypeFormVisible = false
            this.getSubTypes()
            this.getAllFoods()
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      deleteComposedDiscount: function (discountId) {
        // 删除组合优惠
        this.$axios({
          method: 'delete',
          url: '/seller/discount/composition/delete',
          params: {
            discountId: discountId
          }
        }).then(response => {
          if (response.data.isSuccess) {
            this.getComposedDiscount()
            this.$message({
              message: '成功删除组合优惠',
              type: 'success'
            })
          }
          else {
            this.$alert('系统繁忙，请稍后再试', '提示', {
              confirmButtonText: '确定',
            })
          }
        }).catch(function (err) {
          console.log(err)
        })

      },
      addComposedDiscount: function () {
        // 增加组合优惠
        this.$axios({
          method: 'post',
          url: '/seller/discount/composition/add',
          data: {
            foodIds: this.tempComposedDiscount.foodIds,
            foodNames: this.tempComposedDiscount.foodNames,
            money: this.tempComposedDiscount.money,
            discountMoney: this.tempComposedDiscount.discountMoney,
            startTime: this.tempComposedDiscount.date[0],
            endTime: this.tempComposedDiscount.date[1],
            sellerId: global.userId
          }
        }).then(response => {
          this.getComposedDiscount()
          if (response.data.isSuccess) {
            this.getComposedDiscount()
            this.$message({
              message: '成功添加组合优惠',
              type: 'success'
            })
            this.composedDiscountFormVisible = false
          }
          else {
            this.$alert('系统繁忙，请稍后再试', '提示', {
              confirmButtonText: '确定',
            })
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      modifyComposedDiscount: function () {
        // 修改组合优惠
        this.$axios({
          method: 'post',
          url: '/seller/discount/composition/modify',
          data: {
            foodIds: this.tempComposedDiscount.foodIds,
            foodNames: this.tempComposedDiscount.foodNames,
            money: this.tempComposedDiscount.money,
            discountMoney: this.tempComposedDiscount.discountMoney,
            startTime: this.tempComposedDiscount.date[0],
            endTime: this.tempComposedDiscount.date[1],
            sellerId: global.userId,
            discountId: this.tempComposedDiscount.discountId
          }
        }).then(response => {
          if (response.data.isSuccess) {
            this.getComposedDiscount()
            this.$message({
              message: '成功修改组合优惠',
              type: 'success'
            })
            this.composedDiscountFormVisible = false
          }
          else {
            this.$alert('系统繁忙，请稍后再试', '提示', {
              confirmButtonText: '确定',
            })
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      changeMoney: function () {
        let money = 0.0

        for (let i = 0; i < this.tempComposedDiscount.foodIds.length; i++) {
          for (let j = 0; j < this.foodList.length; j++) {
            if (this.tempComposedDiscount.foodIds[i] === this.foodList[j].foodId) {
              money = money + this.foodList[j].money
            }
          }
        }

        this.tempComposedDiscount.money = money
      },
      modifyCustomerDiscount: function () {
        // 修改店铺会员优惠
        this.$axios({
          method: 'post',
          url: '/seller/discount/customer/modify',
          data: {
            sellerId: global.userId,
            discount: this.tempCustomerDiscount
          }
        }).then(response => {
          if (response.data.isSuccess) {
            this.customerDiscount = [this.tempCustomerDiscount[0], this.tempCustomerDiscount[1], this.tempCustomerDiscount[2]]
            this.customerDiscountFormVisible = false
            this.getCustomerDiscount()
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      getSubTypes: function () {
        // 加载子分类
        this.$axios({
          method: 'get',
          url: '/seller/info',
          params: {
            sellerId: global.userId
          }
        }).then(response => {
          let data_ = response.data
          this.subType = data_.foodType
        }).catch(function (err) {
          console.log(err)
        })
      },
      getFoodsBySubType: function () {
        if (this.foodType === '全部商品') {
          this.tempFoodList = this.foodList
        }
        else {
          this.tempFoodList = []

          for (let i = 0; i < this.foodList.length; i++) {
            if (this.foodList[i].foodType === this.foodType) {
              this.tempFoodList.push(this.foodList[i])
            }
          }
        }
      },
      getCustomerDiscount: function () {
        // 加载店铺会员优惠
        this.$axios({
          method: 'get',
          url: '/seller/info',
          params: {
            sellerId: global.userId
          }
        }).then(response => {
          let data_ = response.data
          this.customerDiscount = data_.discount
        }).catch(function (err) {
          console.log(err)
        })
      },
      getComposedDiscount: function () {
        // 加载店铺组合优惠
        this.$axios({
          method: 'get',
          url: '/seller/discount/composition/all',
          params: {
            sellerId: global.userId
          }
        }).then(response => {
          let data_ = response.data
          this.composedDiscounts = data_.composedDiscounts

          for (let i = 0; i < this.composedDiscounts.length; i++) {
            this.composedDiscounts[i].date = [new Date(this.composedDiscounts[i].date[0]),
              new Date(this.composedDiscounts[i].date[1])]
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      getAllFoods: function () {
        // 得到本店所有商品
        this.$axios({
          method: 'get',
          url: '/seller/food/all',
          params: {
            sellerId: global.userId
          }
        }).then(response => {
          let data_ = response.data
          this.foodList = data_.foodList
          this.getFoodsBySubType()
        }).catch(function (err) {
          console.log(err)
        })
      }
    },
    mounted() {
      this.getAllFoods()
      this.getCustomerDiscount()
      this.getComposedDiscount()
      this.getSubTypes()
    }
  }
</script>

<style scoped>
  .avatar-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    width: 178px;
    height: 178px;
  }

  .avatar-uploader:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

  .el-tag + .el-tag {
    margin-left: 10px;
  }

  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .input-new-tag {
    width: 90px;
    margin-top: 10px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  .smallTitle {
    text-align: left;
    padding-left: 10px;
    font-weight: bold;
    font-size: 18px;
    padding-top: 10px;
    padding-bottom: 10px;
    margin-left: 10px;
  }
</style>
