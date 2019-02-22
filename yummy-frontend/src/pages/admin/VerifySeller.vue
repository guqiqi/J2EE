<template>
  <div>
    <admin-navigation default_active="/admin/verify"/>
    <el-col :span="20" :offset="2" style="margin-top: 20px">
      <el-table
        :data="sellerList"
        style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <div>
              <el-col :span="6">
                <img width="180px" height="180px" style="text-align: right" :src="props.row.icon"/>
              </el-col>
              <el-col :span="18">
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-form-item label="商店编号">
                    <span>{{ props.row.sellerId }}</span>
                  </el-form-item>
                  <el-form-item label="商店名称">
                    <span>{{ props.row.tempName }}</span>
                  </el-form-item>
                  <el-form-item label="商店类型">
                    <span>{{ props.row.tempType }}</span>
                  </el-form-item>
                  <el-form-item label="联系电话">
                    <span>{{ props.row.tempPhone }}</span>
                  </el-form-item>
                  <el-form-item label="商店地址">
                    <span>{{ props.row.tempAddress }}</span>
                  </el-form-item>
                  <el-form-item label="营业时间">
                    <span>{{ props.row.tempStartHour }} - {{ props.row.tempEndHour }}</span>
                  </el-form-item>
                </el-form>

                <el-button type="danger" style="width: 45%; margin-top: 20px" @click="reject(props.row.sellerId)">不通过
                </el-button>
                <el-button type="primary" style="width: 45%; margin-top: 20px" @click="pass(props.row.sellerId)">通过
                </el-button>
              </el-col>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="商店编号"
          prop="sellerId">
        </el-table-column>
        <el-table-column
          label="商品名称"
          prop="tempName">
        </el-table-column>
        <el-table-column
          label="类型"
          prop="tempType">
        </el-table-column>
      </el-table>
    </el-col>
  </div>
</template>

<script>
  import global from '../../../static/Global'
  import AdminNavigation from "../../components/AdminNavigation"

  export default {
    components: {AdminNavigation},
    name: "verify-seller",
    data() {
      return {
        sellerList: []
      }
    },
    methods: {
      reject: function (sellerId) {
        // 拒绝
        this.$axios({
          method: 'patch',
          url: '/seller/verify',
          params: {
            sellerId: sellerId,
            isPass: false
          }
        }).then(response => {
          if(response.data.isSuccess){
            this.$message.success('审核成功')
            this.getAllSeller()
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      pass: function (sellerId) {
        // 通过
        this.$axios({
          method: 'patch',
          url: '/seller/verify',
          params: {
            sellerId: sellerId,
            isPass: true
          }
        }).then(response => {
          if(response.data.isSuccess){
            this.$message.success('审核成功')
            this.getAllSeller()
          }
        }).catch(function (err) {
          console.log(err)
        })
      },
      getAllSeller: function () {
        this.$axios({
          method: 'get',
          url: '/seller/all',
        }).then(response => {
          let sellerList = response.data.sellerList

          for(let i = 0 ; i < sellerList.length; i++){
            if(!(sellerList[i].status === 1 || sellerList[i].status === 3)){
              sellerList.splice(i, 1)
              i--
            }
            else {
              sellerList[i].tempStartHour = global.formatTime(new Date(sellerList[i].tempStartHour))
              sellerList[i].tempEndHour = global.formatTime(new Date(sellerList[i].tempEndHour))
            }
          }

          this.sellerList = sellerList
        }).catch(function (err) {
          console.log(err)
        })
      }
    },
    mounted() {
      this.getAllSeller()
    }
  }
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
