<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="通知对象" prop="sysUserName">
        <el-input
          v-model="queryParams.sysUserName"
          clearable
          placeholder="请输入通知对象"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通知状态" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="请选择通知状态">
          <el-option
            v-for="dict in dict.type.sys_notification_read"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否发送" prop="send">
        <el-select v-model="queryParams.send" clearable placeholder="请选择是否发送">
          <el-option
            v-for="dict in dict.type.sys_notification_send"
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
      <el-col>
        {{ this.$route.query.title }}
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="notificationUserList" border>
      <el-table-column align="center" label="通知对象" min-width="200" prop="sysNickName" show-overflow-tooltip/>
      <el-table-column align="center" label="通知状态" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_notification_read" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="是否发送" prop="send">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_notification_send" :value="scope.row.send"/>
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
  </div>
</template>

<script>

import {listNotificationUser} from "@/api/system/notification";

export default {
  name: "NotificationUser",
  dicts: ['sys_notification_send', 'sys_notification_read'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 通知公告用户表格数据
      notificationUserList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sysNotificationId: this.$route.query.id,
        sysUserName: null,
        status: null,
        send: null
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询通知公告用户列表 */
    getList() {
      this.loading = true;
      listNotificationUser(this.queryParams).then(response => {
        this.notificationUserList = response.rows;
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
        sysNotificationId: null,
        sysUserName: null,
        status: null,
        send: null
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
  }
};
</script>

