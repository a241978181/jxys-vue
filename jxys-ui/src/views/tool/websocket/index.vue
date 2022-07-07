<template>
  <div class="shuYes" >
    <h1 style="font-family: 楷体;font-size: 40px;">测试WebSocket</h1>
    <div style="width: 100%;display: flex;">
      <el-form label-width="180px" :model="formLabelAlign">
        <el-form-item label="向后端发送数据">
          <el-input v-model="formLabelAlign.sendStr">
            <el-button @click="createSocket" slot="append" icon="el-icon-search" type="info" plain>发送</el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="接收到的数据">
          <el-input v-model="formLabelAlign.receptionStr"></el-input>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {createWebSocket} from "@/utils/webSocket";

export default {
  name: "Index",
  components: {
  },
  data() {
    return{
      formLabelAlign: {
        sendStr:'',
        receptionStr:'',
      },
      //webSocket操作对象
      socket: undefined,
    }
  },
  mounted() {
    this.createSocket();
  },
  /**
   * 离开当前页面 关闭socket
   * */
  destroyed() {
    this.socket.close();
  },
  methods: {
    /**
     * 创建socket连接
     */
    createSocket() {
      this.socket = createWebSocket("ws://localhost:8080/jxys/webSocket?type=1", this.socketReceive, (evt) => {
        console.log("创建socket参数"+evt)
        this.socket.send(this.formLabelAlign.sendStr);
      }, function (evt) {
        console.log("Connection closed.");
      })
    },
    /**
     * 处理接受的socket 消息
     * @param event
     */
    socketReceive(event) {
      console.log(event)
      // var msgs = JSON.parse(event.data);
      this.formLabelAlign.receptionStr=event.data
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
