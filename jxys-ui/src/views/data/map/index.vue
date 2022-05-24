<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item  label="名称" prop="mc">
        <el-input
          v-model="queryParams.mc"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item  label="行政区划代码" prop="adcode">
        <el-input
          v-model="queryParams.adcode"
          placeholder="请输入行政区划代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item  label="级别" prop="jb">
        <el-select v-model="queryParams.jb" clearable placeholder="请选择">
          <el-option
            v-for="item in jbList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border v-loading="loading" :data="mapList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip label="级别" align="center" prop="jb" width="200" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.jb=='0'" type="danger">国级</el-tag>
          <el-tag v-if="scope.row.jb=='1'" >省级</el-tag>
          <el-tag v-if="scope.row.jb=='2'" type="success">市级</el-tag>
          <el-tag v-if="scope.row.jb=='3'" type="info">区县级</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="名称" align="center" prop="mc" width="200" />
      <el-table-column show-overflow-tooltip label="名称-英文" align="center" prop="mcYw" width="200" />
      <el-table-column show-overflow-tooltip label="所属颜色" align="center" prop="color" width="200" />
      <el-table-column show-overflow-tooltip label="行政区划代码" align="center" prop="adcode" width="200" />
      <el-table-column show-overflow-tooltip label="缩放级别" align="center" prop="zoom" width="200" />
      <el-table-column show-overflow-tooltip label="中心点x" align="center" prop="zxdX" width="200" />
      <el-table-column show-overflow-tooltip label="中心点y" align="center" prop="zxdY" width="200" />
      <el-table-column show-overflow-tooltip label="全称" align="center" prop="qc" width="200" />
      <el-table-column show-overflow-tooltip label="简称" align="center" prop="jc" width="200" />
      <el-table-column show-overflow-tooltip label="邮编" align="center" prop="yb" width="200" />
      <el-table-column show-overflow-tooltip label="长途区号" align="center" prop="ctqh" width="200" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="showMap(scope.row)"
          >查看地图</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改字典-地图对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">
        <el-form-item label="级别" prop="jb">
          <el-input v-model="form.jb" placeholder="请输入级别:0：国家级 1：省级 2：市级 3：区级" />
        </el-form-item>
        <el-form-item label="名称" prop="mc">
          <el-input v-model="form.mc" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="名称-英文" prop="mcYw">
          <el-input v-model="form.mcYw" placeholder="请输入名称-英文" />
        </el-form-item>
        <el-form-item label="所属颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入所属颜色" />
        </el-form-item>
        <el-form-item label="行政区划代码" prop="adcode">
          <el-input v-model="form.adcode" placeholder="请输入行政区划代码" />
        </el-form-item>
        <el-form-item label="缩放级别" prop="zoom">
          <el-input v-model="form.zoom" placeholder="请输入缩放级别" />
        </el-form-item>
        <el-form-item label="中心点x" prop="zxdX">
          <el-input v-model="form.zxdX" placeholder="请输入中心点x" />
        </el-form-item>
        <el-form-item label="中心点y" prop="zxdY">
          <el-input v-model="form.zxdY" placeholder="请输入中心点y" />
        </el-form-item>
        <el-form-item label="全称" prop="qc">
          <el-input v-model="form.qc" placeholder="请输入全称" />
        </el-form-item>
        <el-form-item label="简称" prop="jc">
          <el-input v-model="form.jc" placeholder="请输入简称" />
        </el-form-item>
        <el-form-item label="邮编" prop="yb">
          <el-input v-model="form.yb" placeholder="请输入邮编" />
        </el-form-item>
        <el-form-item label="长途区号" prop="ctqh">
          <el-input v-model="form.ctqh" placeholder="请输入长途区号" />
        </el-form-item>
        <el-form-item label="父id" prop="pid">
          <el-input v-model="form.pid" placeholder="请输入父id" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--  地图展示弹出框-->
    <el-dialog :title="map.mapName+'地图展示'" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
      <div style="width: 100%;height: 400px;">
        <Olmap v-if="map.adcode" :x="map.zxdX" :y="map.zxdY" :adcode="map.adcode" ref="olmap" style="height: 369px;"/>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false;map={}">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { listMap, getMap, delMap, addMap, updateMap } from "@/api/data/map";
import Olmap from '@/components/Map/Olmap.vue'
export default {
  name: "Map",
  components:{
    Olmap,
  },
  data() {
    return {
      //省份地图信息
      map:{
        //当前查询地图名称
        mapName:'',
        //行政区划代码
        adcode:'',
        //中心点X轴坐标
        zxdX:0,
        //中心点Y轴坐标
        zxdY:0,
        //缩放级别
        zoom:0,
      },
      //地图展示弹出框键
      dialogVisible: false,
      //地区级别
      jbList:[{
        value:'0',
        label:'国级'
      },{
        value:'1',
        label:'省级'
      },{
        value:'2',
        label:'市级'
      },{
        value:'3',
        label:'区县级'
      },],
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
      // 字典-地图表格数据
      mapList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mc: null,
        adcode: null,
        jb:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //查看地图
    showMap(row){
      this.map={
          mapName:row.mc,
          adcode:row.adcode,
          zxdX:row.zxdX,
          zxdY:row.zxdY,
          zoom:row.zoom,
      }
      this.dialogVisible=true;
    },
    //弹出框关闭提醒
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    /** 查询字典-地图列表 */
    getList() {
      this.loading = true;
      listMap(this.queryParams).then(response => {
        this.mapList = response.rows;
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
        jb: null,
        mc: null,
        mcYw: null,
        color: null,
        adcode: null,
        zoom: null,
        zxdX: null,
        zxdY: null,
        qc: null,
        jc: null,
        yb: null,
        ctqh: null,
        pid: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加字典-地图";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMap(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改字典-地图";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMap(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMap(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除字典-地图编号为"' + ids + '"的数据项？').then(function() {
        return delMap(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zd/map/export', {
        ...this.queryParams
      }, `map_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
