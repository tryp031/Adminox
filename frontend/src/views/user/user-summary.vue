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
        <el-dropdown size="mini" split-button type="info" @command="handleCommand">
          Action
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="pdf">Export to PDF</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-row>
    <el-table :data="dataTblUsers" height="450" style="width: 100%" highlight-current-row @current-change="handleCurrentChange">
      <el-table-column prop="nameComplete" label="Name" sortable></el-table-column>
      <el-table-column prop="login" label="Login" sortable width="180"></el-table-column>
      <el-table-column prop="email" label="Email"></el-table-column>
      <el-table-column prop="status" label="Status" width="180"
       :filters="[{ text: 'Active', value: true }, { text: 'Inactive', value: false }]"
       :filter-method="filterStatus"
      >
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === false ? 'danger' : 'success'"
            disable-transitions>{{scope.row.status === false ? 'Inactive' : 'Active'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="Action" width="120">
        <template slot-scope="scope">
          <el-tooltip content="Edit" placement="top">
            <el-button icon="el-icon-edit" circle @click.native.prevent="editUser(scope.row)"></el-button>
          </el-tooltip>
          <el-tooltip content="Change status" placement="top">
            <el-button icon="el-icon-refresh" circle @click="updateUserStatus(scope.row)"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <!--/grid-->
  </div>
</template>

<script>
import {AXIOS} from '@/components/http-common'
import { EventBus } from '@/utils/event-bus'
import userForm from '@/views/user/user-form'
import jsPDF from 'jspdf'
import autoTable from 'jspdf-autotable'

export default {
  components: {
    "user-form": userForm
  },
  data: function () {
    return {
      optionsStatus: [{
        value: 1,
        label: 'Active'
      }, {
        value: 0,
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
        status: '1'
      }],

      errors: {
        getAllUser: [],
        updateStatus: []
      }
    }
  },
  created: function () {
    this.getAllData();
  },
  mounted() {
    EventBus.$on('get-users', () => {
      this.getAllData();
    });
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
    handleCommand(command) {
      if (command === 'pdf') {
        let pdfName = 'Export Users';
        const columns = [
          {title: "Name", dataKey: "nameComplete"},
          {title: "Login", dataKey: "login"},
          {title: "Email", dataKey: "email"},
          {title: "Status", dataKey: "status"}
        ];

        // Only pt supported (not mm or in)
        const doc = new jsPDF('p', 'pt');
        doc.autoTable(columns, this.dataTblUsers, {
          margin: {top: 60},
          addPageContent: function(data) {
            doc.text("Users", 40, 30);
          }
        });
        doc.save(pdfName);
      }
    },
    editUser(data) {
      EventBus.$emit('open-form', data);
    },
    updateUserStatus(data) {
      AXIOS.post(`/user/updateStatus/${data.id}`)
        .then(response => {
          data.status = !data.status;
          this.$message({
            message: 'Successfully updated status.',
            type: 'success'
          });
        })
        .catch(e => {
          this.errors.updateStatus.push(e);
          this.$message({
            message: 'Error updating status.',
            type: 'error'
          });
        });
    },
    getAllData () {
      AXIOS.post(`/user/getAllUser`, this.filterForm)
        .then(response => {
          // JSON responses are automatically parsed.
          this.dataTblUsers = response.data
        })
        .catch(e => {
          this.errors.getAllUser.push(e)
        });
    }
  }
}
</script>
