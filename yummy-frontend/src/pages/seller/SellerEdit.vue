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
  import global from '../../../static/Global'

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
      this.$axios({
        method: 'get',
        url: '/seller/info',
        params: {
          sellerId: global.userId
        }
      }).then(response => {
        let data_ = response.data
        this.name = data_.name
        this.type = data_.type
        this.address = data_.address
        this.phone = data_.phone
        this.startHour = global.formatTime(new Date(data_.startHour))
        this.endHour = global.formatTime(new Date(data_.endHour))
        this.password = data_.password
        this.icon = data_.icon
      }).catch(function (err) {
        console.log(err)
      })
    }
  }
</script>

<style scoped>

</style>
