<template>
  <div>
    <el-row>
      商店名称:
      <el-input
        placeholder="商店名称"
        v-model="name"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row>
      <el-col :span="10" style="text-align: right; margin-top: 20px; padding-right: 27px">
        店铺头像:
      </el-col>
      <el-col :span="12" style="margin-left: 9px; margin-top: 20px">
        <el-upload
          class="avatar-uploader"
          action="/api/upload/imgUpload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="this.icon" :src="icon" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-col>
    </el-row>
    <el-row>
      商店类型:
      <el-select v-model="type" placeholder="请选择" class="text-input">
        <el-option
          v-for="item in this.typeOption"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </el-row>
    <el-row>
      商店地址:
      <el-input
        placeholder="商店地址"
        v-model="address"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row>
      联系电话:
      <el-input
        placeholder="联系电话"
        v-model="phone"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row>
      登陆密码:
      <el-input
        placeholder="密码"
        type="password"
        v-model="password"
        clearable
        class="text-input">
      </el-input>
    </el-row>
    <el-row>
      营业时间:
      <el-time-picker
        style="width: 150px; margin-top: 20px; margin-left: 30px"
        v-model="startHour"
        :picker-options="{selectableRange: '0:0:0 - 23:59:59'}"
        placeholder="任意时间点">
      </el-time-picker>
      <el-time-picker
        style="width: 150px; margin-top: 20px; margin-left: 15px"
        arrow-control
        v-model="endHour"
        :picker-options="{selectableRange: '0:0:0 - 23:59:59'}"
        placeholder="任意时间点">
      </el-time-picker>
    </el-row>
    <el-row>
      <el-button v-if="isSignUp" type="primary" style="width: 440px; margin-top: 20px" @click="register">确认注册
      </el-button>
      <el-button v-else type="primary" style="width: 440px; margin-top: 20px" @click="modify">确认修改</el-button>
    </el-row>
  </div>
</template>

<script>
  import Global from '../../static/Global'
  export default {
    name: "seller-edit-info",
    data() {
      return {
        typeOption: [
          {
            value: '快餐便当',
            label: '快餐便当'
          },
          {
            value: '甜品饮品',
            label: '甜品饮品'
          },
          {
            value: '果蔬生鲜',
            label: '果蔬生鲜'
          },
          {
            value: '商店超市',
            label: '商店超市'
          },
          {
            value: '鲜花绿植',
            label: '鲜花绿植'
          }
        ]
      }
    },
    props: {
      name: {
        type: String,
        default: ''
      },
      type: {
        type: String,
        default: '快餐便当'
      },
      address: {
        type: String,
        default: ''
      },
      phone: {
        type: String,
        default: ''
      },
      password: {
        type: String,
        default: ''
      },
      startHour: {
        type: String,
        default: Global.startHour
      },
      endHour: {
        type: String,
        default: Global.endHour
      },
      icon: {
        type: String,
        default: ''
      },
      isSignUp: {
        type: Boolean,
        default: false
      }
    },
    methods: {
      register: function () {
        let sellerId = ''
        this.isLoading = false
        // 注册
        this.$axios({
          method: 'post',
          url: '/seller/register',
          data:{
            name: this.name,
            type: this.type,
            address: this.address,
            phone: this.phone,
            startHour: this.startHour,
            endHour: this.endHour,
            icon: this.icon,
            password: this.password,
          }
        }).then(response=>{
          console.log(response)
          this.isLoading = false
          if(response.data.isSuccess){
            console.log(response.data)
            sellerId = response.data.sellerId
            this.$router.push({name: 'sellerPrompt', params: {sellerId: sellerId}})
          }
          else {
            this.$alert('系统繁忙，请稍后再试', '提示', {
              confirmButtonText: '确定',
            });
          }
        }).catch(function(err){
          console.log(err)
        })
      },
      modify: function () {
        // TODO 修改
        this.$axios({
          method: 'post',
          url: '/seller/modify',
          data:{
            name: this.name,
            type: this.type,
            address: this.address,
            phone: this.phone,
            startHour: this.startHour,
            endHour: this.endHour,
            icon: this.icon,
            password: this.password,
            sellerId: Global.userId
          }
        }).then(response=>{
          this.isLoading = false
          if(response.data.isSuccess){
            this.$alert('信息已提交，请耐心等待系统审核', '提示', {
              confirmButtonText: '确定',
            });
            this.$router.push("/seller/home")
          }
          else {
            this.$alert('系统繁忙，请稍后再试', '提示', {
              confirmButtonText: '确定',
            });
          }
        }).catch(function(err){
          console.log(err)
        })
      },
      handleAvatarSuccess(res, file) {
        let now = new Date()
        let exitTime = now.getTime() + 1000

        let isSleep = true
        while (isSleep) {
          now = new Date()
          if (now.getTime() > exitTime)
            isSleep = false
        }

        this.icon = res.url
      },
      beforeAvatarUpload(file) {
        // const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        // if (!isJPG) {
        //   this.$message.error('上传头像图片只能是 JPG 格式!')
        // }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isLt2M
      },
    },
    mounted() {
      console.log(this.isSignUp)
      if(!this.isSignUp){
        this.$axios({
          method: 'get',
          url: '/seller/info',
          params: {
            sellerId: Global.userId
          }
        }).then(response => {
          let data_ = response.data
          this.name = data_.name
          this.type = data_.type
          this.address = data_.address
          this.phone = data_.phone
          this.startHour = data_.startHour
          this.endHour = data_.endHour
          this.icon = data_.icon
          this.password = data_.password
        }).catch(function (err) {
          console.log(err)
        })
      }
    }
  }
</script>

<style scoped>
  .text-input {
    width: 325px;
    margin-left: 30px;
    margin-top: 20px
  }

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
</style>
