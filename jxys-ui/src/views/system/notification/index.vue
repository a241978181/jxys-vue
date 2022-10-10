<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="通知级别" prop="level">
        <el-select v-model="queryParams.level" clearable placeholder="请选择通知级别">
          <el-option
            v-for="dict in dict.type.sys_notification_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          clearable
          placeholder="请输入标题"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通知状态" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="请选择通知状态">
          <el-option
            v-for="dict in dict.type.sys_notice_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:notification:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:notification:edit']"
          :disabled="single"
          icon="el-icon-edit"
          plain
          size="mini"
          type="success"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:notification:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          plain
          size="mini"
          type="danger"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:notification:export']"
          icon="el-icon-download"
          plain
          size="mini"
          type="warning"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="notificationList" border @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="创建时间" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="通知级别" prop="level">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_notification_level" :value="scope.row.level"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="URL" min-width="200" prop="url" show-overflow-tooltip/>
      <el-table-column align="center" label="标题" min-width="200" prop="title" show-overflow-tooltip/>
      <el-table-column align="center" label="内容" min-width="200" prop="message" show-overflow-tooltip/>
      <el-table-column align="center" label="通知状态" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_notice_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作" width="150">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['system:notification:list']"
            icon="el-icon-view"
            size="mini"
            type="text"
            @click="handleList(scope.row)"
          >查看
          </el-button>
          <el-button
            v-hasPermi="['system:notification:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-hasPermi="['system:notification:remove']"
            icon="el-icon-delete"
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改通知管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="1000px">
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">
        <el-form-item label="通知级别" prop="level">
          <el-select v-model="form.level" placeholder="请选择通知级别" style="width: 100%;">
            <el-option
              v-for="dict in dict.type.sys_notification_level"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="URL" prop="url">
          <el-input v-model="form.url" placeholder="请输入URL"/>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="内容" prop="message">
          <el-input v-model="form.message" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="通知状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择通知状态" style="width: 100%;">
            <el-option
              v-for="dict in dict.type.sys_notice_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="通知用户" prop="usernameArr">
          <el-transfer v-model="form.usernameArr" :data="userListAll" :filter-method="filterMethod" :props="{
              key: 'userName',
              label: 'nickName'
            }"
                       filterable>
          </el-transfer>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addNotification,
  delNotification,
  getNotification,
  listNotification,
  updateNotification
} from "@/api/system/notification";
import {listAllUser} from "@/api/system/user";

export default {
  name: "Notification",
  dicts: ['sys_notice_status', 'sys_notification_level'],
  data() {
    return {
      //用户组
      userListAll: [],
      //查询用户组
      filterMethod(query, item) {
        return item.nickName.indexOf(query) > -1;
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 通知管理表格数据
      notificationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        level: null,
        title: null,
        status: null,
        usernameArr: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        level: [
          {required: true, message: "级别不能为空", trigger: "blur"}
        ],
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        message: [
          {required: true, message: "内容不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getUserListAll();
  },
  methods: {
    /**
     * 查询全部用户
     */
    getUserListAll() {
      listAllUser().then(response => {
        this.userListAll = response.rows;
      });
    },
    /** 查询通知管理列表 */
    getList() {
      this.loading = true;
      listNotification(this.queryParams).then(response => {
        this.notificationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        deleted: null,
        level: 1,
        url: null,
        title: null,
        message: null,
        status: '0'
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加通知管理";
    },
    /**
     * 查看按钮
     */
    handleList(row) {
      this.$router.push({name: 'NotificationUser', query: {id: row.id, title: row.title}});
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getNotification(id).then(response => {
        let item = response.data;
        if (item.usernameList != null) {
          //赋值标准组
          item.usernameArr = [];
          for (let i = 0; i < item.usernameList.length; i++) {
            item.usernameArr.push(item.usernameList[i].sysUserName)
          }
        }
        this.form = item
        this.open = true;
        this.title = "修改通知管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let item = {...this.form}
          //编辑用户组
          let usernameArrStr = '';
          if (!(item.usernameArr == null)) {
            for (let i = 0; i < item.usernameArr.length; i++) {
              usernameArrStr += item.usernameArr[i] + ',';
            }
          }
          if (usernameArrStr !== '') {
            usernameArrStr = usernameArrStr.substring(0, usernameArrStr.lastIndexOf(','));
          }
          item.usernameArr = usernameArrStr;
          if (item.id != null) {
            updateNotification(item).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNotification(item).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除通知管理编号为"' + ids + '"的数据项？').then(function () {
        return delNotification(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/notification/export', {
        ...this.queryParams
      }, `notification_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

