<template>
  <div class="dashboard-editor-container">
    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row :gutter="8"  style="background:#fff;padding:16px 16px 0;margin-bottom:32px;" v-permission="'ROLE_SUPERADMIN'">
      <el-table :data="activities" style="width: 100%;padding-top: 15px;">
        <el-table-column label="Session ID" >
          <template slot-scope="scope">
            {{ scope.row.sessionId  }}
          </template>
        </el-table-column>
        <el-table-column label="Username"  align="center">
          <template slot-scope="scope">
            {{ scope.row.userLogin }}
          </template>
        </el-table-column>
        <el-table-column label="Page">
          <template slot-scope="scope">
            {{scope.row.page}}
          </template>
        </el-table-column>
        <el-table-column label="Ip address" align="center">
          <template slot-scope="{row}">
            {{row.ipAddress}}
          </template>
        </el-table-column>
        <el-table-column label="Time">
          <template slot-scope="{row}">
            {{row.time}}
          </template>
        </el-table-column>
      </el-table>
      <!--  &lt;!&ndash;  <el-col :xs="{span: 24}" :sm="{span: 24}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}" style="padding-right:8px;margin-bottom:30px;">
            <transaction-table />
          </el-col>&ndash;&gt;
          <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;">
            <todo-list />
          </el-col>
          <el-col :xs="{span: 24}" :sm="{span: 12}" :md="{span: 12}" :lg="{span: 6}" :xl="{span: 6}" style="margin-bottom:30px;">
            <box-card />
          </el-col>-->
    </el-row>
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import RaddarChart from './components/RaddarChart'
import PieChart from './components/PieChart'
import BarChart from './components/BarChart'
import TransactionTable from './components/TransactionTable'
import TodoList from './components/TodoList'
import BoxCard from './components/BoxCard'


const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'DashboardAdmin',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart,
    TransactionTable,
    TodoList,
    BoxCard
  },

  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      activities:[]
    }
  },


  mounted(){
      let interval;
      interval = setInterval(() => {
        if (this.$store.state.user.isSuperAdmin) {
          this.$stompClient.subscribe('/topic/tracker', message => {
            this.showActivity(JSON.parse(message.body))
          })
        } else {
          this.$router.afterEach(() => {
            if (this.$stompClient !== null && this.$stompClient.connected) {//cheking stomClient connected
              this.$stompClient.send(
                      '/wss/activity', // destination
                      JSON.stringify({page: this.$router.currentRoute.fullPath}), // body
                      {} // header
              );
            }
          });
        }
        clearInterval(interval);
      }, 1000);
    },

  destroyed(){
      this.$router.afterEach(()=>{});
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    showActivity(activity) {
      let existingActivity = false;
      for (let index = 0; index < this.activities.length; index++) {
        if (this.activities[index].sessionId === activity.sessionId) {
          existingActivity = true;
          if (activity.page === 'logout') {
            this.activities.splice(index, 1);
          } else {
            this.activities.splice(index, 1);
            this.activities.push(activity);
          }
        }
      }
      if (!existingActivity && activity.page !== 'logout') {
        this.activities.push(activity);
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
