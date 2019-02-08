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
                    <span>{{ props.row.name }}</span>
                  </el-form-item>
                  <el-form-item label="商店类型">
                    <span>{{ props.row.type }}</span>
                  </el-form-item>
                  <el-form-item label="联系电话">
                    <span>{{ props.row.phone }}</span>
                  </el-form-item>
                  <el-form-item label="商店地址">
                    <span>{{ props.row.address }}</span>
                  </el-form-item>
                  <el-form-item label="营业时间">
                    <span>{{ props.row.startHour }} - {{ props.row.endHour }}</span>
                  </el-form-item>
                </el-form>

                <el-button type="danger" style="width: 45%; margin-top: 20px" @click="reject(props.row.sellerId)">不通过</el-button>
                <el-button type="primary" style="width: 45%; margin-top: 20px" @click="pass(props.row.sellerId)">通过</el-button>

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
          prop="name">
        </el-table-column>
        <el-table-column
          label="类型"
          prop="type">
        </el-table-column>
      </el-table>
    </el-col>
  </div>
</template>

<script>
  import AdminNavigation from "../../components/AdminNavigation"

  export default {
    components: {AdminNavigation},
    name: "verify-seller",
    data() {
      return {
        sellerList: [
          {
            sellerId: '123444',
            name: '食其家',
            type: '快餐便当',
            address: '南京市鼓楼区',
            phone: '13700000001',
            startHour: "8:00",
            endHour: "20:00",
            icon: '../../static/uploadImages/347454.jpg',
          }
        ]
      }
    },
    methods: {
      reject: function (sellerId) {
        // TODO 拒绝
        console.log(sellerId)

      },
      pass: function (sellerId) {
        // TODO 通过
        console.log(sellerId)
      }
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
