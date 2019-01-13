<template>
  <!--form-->
  <el-dialog title="Change Password" :visible.sync="dialogFormVisible" width="30%" :before-close="handleClose">
    <el-form :model="formPass" status-icon :rules="rulesForm" ref="formPass">
      <el-form-item label="Password" prop="password">
        <el-input type="password" v-model="formPass.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Confirm" prop="checkPass">
        <el-input type="password" v-model="formPass.checkPass" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false; resetForm('formPass')">Cancel</el-button>
      <el-button type="success" @click="changePass('formPass')" icon="el-icon-disk">Change</el-button>
    </span>
  </el-dialog>
  <!--/form-->
</template>

<script>
import {AXIOS} from '@/components/http-common'
import { EventBus } from '@/utils/event-bus'

export default {
  data () {
    const validatePass = (rule, value, callback) => {
      if (this.formPass.password === '') {
        callback(new Error('Please input the password'));
      } else {
        if (this.formPass.checkPass !== '') {
          this.$refs.formPass.validateField('checkPass');
        }
        callback();
      }
    };
    const validatePassConfirm = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the password again'));
      } else if (value !== this.formPass.password) {
        callback(new Error('Two inputs don\'t match!'));
      } else {
        callback();
      }
    };
    return {
      dialogFormVisible: false,
      rulesForm: {
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePassConfirm, trigger: 'blur' }
        ]
      },
      formPass: {
        id: 0,
        password: '',
        checkPass: ''
      },
      errors: {
        changePass: []
      }
    }
  },
  mounted() {
    EventBus.$on('open-change-pass', (userId) => {
      this.openForm();
      this.formPass.id = userId;
    });
  },
  methods: {
    handleClose(done) {
      this.resetForm('formPass');
      done();
    },
    openForm() {
      this.dialogFormVisible = true;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.formPass = {
        id: 0,
        password: '',
        checkPass: ''
      };
    },
    changePass (formPassValid) {
      this.$refs[formPassValid].validate((valid) => {
        if (!valid) {
          this.$message({
            message: 'Invalid Form.',
            type: 'warning'
          });
          return false;
        } else {
          this.$message({
            message: 'Form valid.',
            type: 'success'
          });
        }
      });
    }
  }
}
</script>
