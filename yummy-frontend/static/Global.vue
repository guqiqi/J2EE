<script>
  let userId = ''
  let startHour = new Date()
  startHour.setHours(8)
  startHour.setMinutes(0)
  startHour.setSeconds(0)

  let endHour = new Date()
  endHour.setHours(20)
  endHour.setMinutes(0)
  endHour.setSeconds(0)

  function setUserId(userId) {
    this.userId = userId
  }

  function getLevel(point) {
    if(point < 100)
      return '普通会员'
    if(point < 500)
      return '白银会员'

    return '黄金会员'
  }

  export default {
    userId, getLevel, setUserId, startHour, endHour
  }
</script>

<style scoped>

</style>
