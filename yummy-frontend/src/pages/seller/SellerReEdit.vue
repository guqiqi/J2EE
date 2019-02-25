<template>
  <div>
    <seller-navigation default_active="/seller/info"/>
    <seller-edit-info :name="this.name" :type="this.type" :address="this.address"
                      :phone="this.phone" :password="this.password"
                      :startHour="this.startHour" :endHour="this.endHour" :icon="this.icon" :isFirstEdit="isFirstEdit"
                      :sellerId="this.$route.params.sellerId"/>
  </div>
</template>

<script>
  import global from '../../../static/Global'
  import SellerNavigation from "../../components/SellerNavigation"
  import SellerEditInfo from "../../components/SellerEditInfo"

  export default {
    name: "seller-re-edit",
    components: {SellerEditInfo, SellerNavigation},
    data(){
      return {
        name: '',
        type: '',
        address: '',
        phone: '',
        startHour: '',
        endHour: '',
        icon: '',
        password: '',
        isFirstEdit: true
      }
    },
    mounted(){
      this.$axios({
        method: 'get',
        url: '/seller/info',
        params: {
          sellerId: this.$route.params.sellerId
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
