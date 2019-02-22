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
        name: '',
        type: '',
        address: '',
        phone: '',
        startHour: '',
        endHour: '',
        icon: '',
        password: ''
      }
    },
    mounted(){
    }
  }
</script>

<style scoped>

</style>
