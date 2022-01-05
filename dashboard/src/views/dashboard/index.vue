<template>
  <div class="dashboard-container">
    <component :is="'adminDashboard'" />
  </div>
</template>

<script>
import adminDashboard from './admin'


export default {
  name: 'Dashboard',
  components: { adminDashboard },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
  }
}
</script>
