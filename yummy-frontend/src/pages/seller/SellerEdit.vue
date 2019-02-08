<template>
  <div>
    <seller-navigation default_active="/seller/info"/>
    <seller-edit-info :name="this.name" :type="this.type" :address="this.address"
                 :phone="this.phone" :password="this.password"
                 :startHour="this.startHour" :endHour="this.endHour" :icon="this.icon"/>
  </div>
</template>

<script>
  import SellerNavigation from "../../components/SellerNavigation"
  import SellerEditInfo from "../../components/SellerEditInfo"


  export default {
    components: {SellerNavigation, SellerEditInfo},
    name: "seller-edit",
    data(){
      return {
        name: '食其家',
        type: '快餐便当',
        address: '南京市鼓楼广场',
        phone: '12888883894',
        startHour: '9:00 : 00',
        endHour: '20:00 : 00',
        icon: '../../static/uploadImages/347454.jpg',
        password: '123'
      }
    },
    mounted(){
      // TODO 初始化信息
    }
  }
</script>

<style scoped>

</style>
