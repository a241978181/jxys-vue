<template>
  <div class="shuYes">
    <el-card class="hengYes" style="width: 100%;height: 100px;">
      <span style="font-size: 40px;font-family: 楷体"><b>系统小组件大全</b></span>
    </el-card>
    <div class="hengYes">
      <el-card class="box-card" style="width: 100%;height: 120px;">
        <div slot="header" class="clearfix">
          <span>Buttons 样式</span>
        </div>
        <div style="margin-bottom:50px;">
          <el-col :span="4" class="text-center">
            <router-link class="pan-btn blue-btn" to="/index">
              Documentation
            </router-link>
          </el-col>
          <el-col :span="4" class="text-center">
            <router-link class="pan-btn light-blue-btn" to="/index">
              Icons
            </router-link>
          </el-col>
          <el-col :span="4" class="text-center">
            <router-link class="pan-btn pink-btn" to="/index">
              Excel
            </router-link>
          </el-col>
          <el-col :span="4" class="text-center">
            <el-button class="pan-btn green-btn">
              Table
            </el-button>
          </el-col>
          <el-col :span="4" class="text-center">
            <div class="pan-btn tiffany-btn">
              Form
            </div>
          </el-col>
          <el-col :span="4" class="text-center">
            <span class="pan-btn yellow-btn">
              Theme
            </span>
          </el-col>
        </div>
      </el-card>
    </div>
    <div class="hengYes">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>可移动弹出框</span>
        </div>
        <div class="component-item">
          <el-button type="primary"  @click="KCJdialogVisible = true">点击打开 可出界-移动弹出框</el-button>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>图片hover效果</span>
        </div>
        <div class="component-item">
          <pan-thumb width="130px" height="130px" image="https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191">
            建行一世
          </pan-thumb>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>水波纹 waves v-directive</span>
        </div>
        <div class="component-item">
          <el-button v-waves type="primary">
            水波纹效果
          </el-button>
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>hover text</span>
        </div>
        <div class="component-item">
          <mallki class-name="mallki-text" text="建行一世工作室" />
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>Material Design 的input</span>
        </div>
        <div style="height:100px;">
          <el-form :model="demo" :rules="demoRules">
            <el-form-item prop="title">
              <md-input v-model="demo.title" icon="el-icon-search" name="title" placeholder="输入标题">
                标题
              </md-input>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
    <div class="hengYes">
      <el-card class="box-card" style="width: 100%;height: 200px;">
        <div slot="header" class="clearfix">
          <span>可移动多选下拉</span>
        </div>
        <div class="component-item">
          <div class="components-container">
            <el-drag-select v-model="value" style="width:500px;" multiple placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-drag-select>
            <div style="margin-top:30px;">
              <el-tag v-for="item of value" :key="item" style="margin-right:15px;">
                {{ item }}
              </el-tag>
            </div>
          </div>
        </div>
      </el-card>
    </div>
    <div class="hengYes">
      <el-card class="box-card" style="width: 100%;height: 800px;">
        <div slot="header" class="clearfix">
          <span>富文本编辑器</span>
        </div>
        <div class="component-item">
          <aside>
            【Tinymce】更详细的富文本比较和介绍见
            <a target="_blank" class="link-type" href="https://panjiachen.github.io/vue-element-admin-site/feature/component/rich-editor.html">文档</a>
          </aside>
          <div>
            <tinymce v-model="content" :height="300" />
          </div>
          <div class="editor-content" v-html="content" />
        </div>
      </el-card>
    </div>

    <el-dialog title="这是可出界移动弹出框" v-dialogDrag :visible.sync="KCJdialogVisible" width="30%" :before-close="handleClose">
      <span>这是可出界移动弹出框！！</span>
    </el-dialog>
  </div>
</template>
<script>
import PanThumb from "@/components/PanThumb/index"
import Mallki from '@/components/TextHoverEffect/Mallki'
import MdInput from '@/components/MDinput'
import ElDragSelect from '@/components/DragSelect' // base on element-ui
import Tinymce from '@/components/Tinymce'
export default {
  name: "Index",
  components: {
    PanThumb,
    Mallki,
    MdInput,
    ElDragSelect,
    Tinymce
  },
  data() {
    const validate = (rule, value, callback) => {
      if (value.length !== 6) {
        callback(new Error('请输入六个字符'))
      } else {
        callback()
      }
    }
    return {
      content:``,
      KCJdialogVisible: false,
      demoRules: {
        title: [{ required: true, trigger: 'change', validator: validate }]
      },
      demo: {
        title: ''
      },
      value: ['Apple', 'Banana', 'Orange'],
      options: [{
        value: 'Apple',
        label: 'Apple'
      }, {
        value: 'Banana',
        label: 'Banana'
      }, {
        value: 'Orange',
        label: 'Orange'
      }, {
        value: 'Pear',
        label: 'Pear'
      }, {
        value: 'Strawberry',
        label: 'Strawberry'
      }]
    };
  },
  mounted() {
    //测试拼音转换功能
    console.log(this.pinyin('汉语拼音',{pattern:'pinyin',toneType:'none'}));
  },
  methods: {
    //弹出框关闭提醒
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
  },
};
</script>

<style scoped lang="scss">
.box-card{
  width: 300px;
  margin: 5px;
  height: 200px;
}
</style>
<style #scoped>

.shuNo {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.shuYes {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.shuYes100 {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 5px
}

.hengNo {
  width: 100%;
  display: flex;
  align-items: center;
}

.hengNo100 {
  display: flex;
  align-items: center;
}

.hengYes {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.hengLd {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.card {
  margin: 5px;
}

</style>
