<template>
  <div>
    <el-badge :is-dot="isDot" style="position: relative;top: -7px;right: -22px;"></el-badge>
    <el-popover placement="bottom-start" trigger="click" width="400">
      <div style="width: 100%;display: flex;align-items: center;flex-direction: column;">
        <el-table :cell-style="{cursor:'pointer'}" :data="messageList" max-height="300px"
                  @row-click="onClickNotification">
          <el-table-column label="标题" property="title" width="100"></el-table-column>
          <el-table-column label="内容" property="message" show-overflow-tooltip></el-table-column>
        </el-table>
        <div style="width: 100%;display: flex;align-items: center;justify-content: flex-end;margin-top: 5px;">
          <el-button type="text" @click="readAll">全部已读</el-button>
        </div>
      </div>
      <svg-icon slot="reference" icon-class="message" style="height: 100%;" @click="clickOn"/>
    </el-popover>
  </div>
</template>
<script>
import {createWebSocket} from "@/utils/webSocket";

export default {
  name: "Index",
  components: {},
  data() {
    return {
      //临时消息堆
      messageList: [],
      //webSocket操作对象
      socket: undefined,
      //小红点键
      isDot: false,
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
    //全部已读
    readAll() {
      this.messageList = []
      this.socket.send("ALL");
    },
    //向后端反馈已经查看该条消息
    sendMessageRead(item) {
      this.socket.send(item.id);
    },
    //强提醒点击事件
    onClickNotification(item) {
      //弹窗显示内容
      if (item.url != null && item.url.length > 0) {
        window.open(item.url, "_blank", "height=600, width=600, top=50, left=350, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
      } else {
        let myWindow = window.open('', '', 'width=600,height=600, top=50, left=250');
        myWindow.document.write("<p>" + item.message + "</p>");
        myWindow.focus();
      }
      //删除数组中该元素
      for (let i = 0; i < this.messageList.length; i++) {
        if (this.messageList[i].message === item.message) {
          this.messageList.splice(i, 1);
          break;
        }
      }
      //向后端返回已读数据
      if (item.id != null && item.id.length > 0) {
        this.sendMessageRead(item);
      }
    },
    //点击按钮
    clickOn() {
      this.isDot = false;
    },
    /**
     * 创建socket连接
     */
    createSocket() {
      this.socket = createWebSocket("ws://localhost:2190/jxys/webSocket/notification?userName=" + this.$store.getters.name, this.socketReceive, (evt) => {
      }, function (evt) {
        console.log("Connection closed.");
      })
    },
    /**
     * 处理接受的socket 消息
     * @param event
     */
    socketReceive(event) {
      var NotificationItem = JSON.parse(event.data);
      this.messageList.push(NotificationItem)
      if (NotificationItem.level === 1) {
        this.isDot = true;
      } else if (NotificationItem.level === 2) {
        const h = this.$createElement;
        let mythis = this;
        this.$notify({
          title: NotificationItem.title,
          duration: 5000,
          message: h('i', {style: 'color: teal'}, NotificationItem.message),
          onClick() {
            mythis.onClickNotification(NotificationItem)
            this.close()
          }
        });
      } else if (NotificationItem.level === 3) {
        const h = this.$createElement;
        let mythis = this;
        this.$notify({
          title: NotificationItem.title,
          message: h('a', {style: 'color: teal'}, NotificationItem.message),
          duration: 0,
          onClick() {
            mythis.onClickNotification(NotificationItem)
            this.close()
          }
        });
      }
    },
  },
};
</script>
<style scoped>
</style>

<style lang="scss" scoped>
.box-card {
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
