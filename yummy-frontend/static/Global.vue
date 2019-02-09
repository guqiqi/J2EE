<script>
  let userId = ''

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
    userId, getLevel, setUserId
  }
</script>

<style scoped>

</style>
