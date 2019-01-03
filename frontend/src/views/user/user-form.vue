<template>
  <div id="user-form">
    <el-row :gutter="10">
      <el-col :span="4">
        <h3>User Summary</h3>
      </el-col>
      <el-col :span="3" :offset="17" align="right">
        <el-button type="success" icon="el-icon-plus" @click="dialogFormVisible = true">Add User</el-button>
      </el-col>
    </el-row>
    <!--form-->
    <el-dialog title="Create User" :visible.sync="dialogFormVisible">
      <el-form :model="formUser">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-row :gutter="10">
              <el-col :span="24">
                <el-form-item label="Name">
                  <el-input v-model="formUser.name"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="10">
              <el-col :span="24">
                <el-form-item label="Last Name">
                  <el-input v-model="formUser.lastName"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="12" align="center">
            <el-upload class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="formUser.imageUrl" :src="formUser.imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="Email">
              <el-input v-model="formUser.email"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Login">
              <el-input v-model="formUser.login"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="Password" prop="pass">
              <el-input type="password" v-model="formUser.password" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Confirm" prop="checkPass">
              <el-input type="password" v-model="formUser.checkPass" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-switch style="display: block" v-model="formUser.status" active-color="#13ce66" inactive-color="#ff4949"
                       active-text="Active" inactive-text="Inactive">
            </el-switch>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">Cancel</el-button>
      <el-button type="success" @click="saveUser()" icon="el-icon-disk">Save</el-button>
    </span>
    </el-dialog>
    <!--/form-->
  </div>
</template>

<script>
import jsUserform from '@/views/user/js/user-form'
import {AXIOS} from '@/components/http-common'
export default {
  data () {
    return {
      dialogTableVisible: false,
      dialogFormVisible: false,
      formUser: {
        name: '',
        lastName: '',
        email: '',
        login: '',
        password: '',
        checkPass: '',
        status: true,
        imageUrl: '',
        errors: []
      }
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.formUser.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPGOrPNG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPGOrPNG) {
        this.$message.error('Avatar picture must be JPG or PNG format!');
      }
      if (!isLt2M) {
        this.$message.error('Avatar picture size can not exceed 2MB!');
      }
      return isJPGOrPNG && isLt2M;
    },
    saveUser () {
      AXIOS.get(`/user/hello`)
        .then(response => {
          // JSON responses are automatically parsed.
          this.formUser.name = response.data
        })
        .catch(e => {
          this.formUser.errors.push(e)
        })
   }
  }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
