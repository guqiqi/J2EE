<script>
  let userId = 'nnnnnnn'
  let startHour = new Date()
  startHour.setHours(8)
  startHour.setMinutes(0)
  startHour.setSeconds(0)

  let endHour = new Date()
  endHour.setHours(20)
  endHour.setMinutes(0)
  endHour.setSeconds(0)

  function formatDate(date) {
    let y = date.getFullYear()
    let m = date.getMonth() + 1
    m = m < 10 ? ('0' + m) : m
    let d = date.getDate()
    d = d < 10 ? ('0' + d) : d
    let h = date.getHours()
    let minute = date.getMinutes()
    minute = minute < 10 ? ('0' + minute) : minute
    let second = date.getSeconds()
    second = minute < 10 ? ('0' + second) : second
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
  }

  function setUserId(userId) {
    this.userId = userId
  }

  function getLevel(point) {
    if (point < 100)
      return '普通会员'
    if (point < 500)
      return '白银会员'

    return '黄金会员'
  }

  export default {
    userId, getLevel, setUserId, startHour, endHour, formatDate
  }
</script>

<style scoped>

</style>
