<template>
  <div class="app-container">
    <!--form-->
    <user-form></user-form>
    <!--/form-->
    <!--filters-->
    <el-form ref="filterForm" :model="filterForm" label-width="120px" label-position="right">
      <el-row :gutter="10">
        <el-col :span="7">
          <el-form-item label="Name">
            <el-input v-model="filterForm.name"/>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="Login">
            <el-input v-model="filterForm.login"/>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="Status">
            <el-select v-model="filterForm.status" multiple placeholder="Select">
              <el-option
                v-for="status in optionsStatus"
                :key="status.value"
                :label="status.label"
                :value="status.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="3" align="right">
          <el-button type="primary" icon="el-icon-search" @click="getAllData()">Search</el-button>
        </el-col>
      </el-row>
    </el-form>
    <!--/filters-->
    <!--grid-->
    <el-row :gutter="10">
      <el-col :span="4">
        <el-dropdown size="mini" split-button type="info">
          Action
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>Export to PDF</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-row>
    <el-table :data="dataTblUsers" height="450" style="width: 100%" highlight-current-row @current-change="handleCurrentChange">
      <el-table-column prop="nameComplete" label="Name" sortable></el-table-column>
      <el-table-column prop="login" label="Login" sortable width="180"></el-table-column>
      <el-table-column prop="email" label="Email"></el-table-column>
      <el-table-column prop="status" label="Status" width="180"
       :filters="[{ text: 'Active', value: '1' }, { text: 'Inactive', value: '0' }]"
       :filter-method="filterStatus"
      >
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === false ? 'danger' : 'success'"
            disable-transitions>{{scope.row.status === false ? 'Inactive' : 'Active'}}</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <!--/grid-->
  </div>
</template>

<script>
import {AXIOS} from '@/components/http-common'
import userForm from '@/views/user/user-form'
import jsUserSummary from '@/views/user/js/user-form'

export default {
  components: {
    "user-form": userForm
  },
  data: function () {
    return {
      optionsStatus: [{
        value: '1',
        label: 'Active'
      }, {
        value: '0',
        label: 'Inactive'
      }],

      filterForm: {
        name: '',
        login: '',
        status: []
      },

      dataTblUsers: [{
        nameComplete: '',
        login: '',
        email: '',
        status: '1',
        statusName: 'Active'
      }],

      errors: {
        getAllUser: null
      }
    }
  },
  created: function () {
    this.getAllData();
  },
  methods: {
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    clearFilter() {
      this.$refs.filterTable.clearFilter();
    },
    filterStatus(value, row) {
      return row.status === value;
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    getAllData () {
      AXIOS.get(`/user/getAllUser`)
        .then(response => {
          // JSON responses are automatically parsed.
          this.dataTblUsers = response.data
        })
        .catch(e => {
          this.errors.getAllUser.push(e)
        })
    }
  }
}
</script>
