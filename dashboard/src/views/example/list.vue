<template>
  <div class="app-container">
    <alov-datatable
             :columns="columns"
             :list-url="remoteUrl"
             :add-search="false"
             :on-row-delete="rowDelete"
           >
       <template v-slot:table-actions>
       </template>
    </alov-datatable>
  </div>
</template>
<script>

  import service from '@/utils/request';
  import {Message} from 'element-ui'

export default {
  name: 'ArticleList',
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      fullscreenLoading:false,
      remoteUrl:'example-list',
      columns:[
          {
            label: "id",
            field: "id",
            sortable: true
          },
          {
            label: "Kod",
            field: "code",
          },
          {
            label: "Description",
            field: "description",
          },
          {
            label:'Type',
            field:'type',
            columnStyles:{
              align:'right',
              width:'20px'
            },
            filterOptions: {
              enabled: true, // enable filter for this column
              placeholder: 'Hamısı', // placeholder for filter input
              filterDropdownItems: [{value: true, text: 'Approved'},
                {value: false, text: 'Not Approved'}
              ],
            },
          },
          {
           label:'Category',
           field:'category'
          }
      ]
      }
    },

  created() {
  },
  methods: {
       async rowDelete(row){
         await service.post(`/mock-data/${row.id}`);
      }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
