<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎使用教学反馈后台系统</div>
  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import { getToken} from '@/utils/auth'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data() {
    return{
   }
  },
  created() {
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
