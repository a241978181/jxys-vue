<template>
  <div class="home">
    <span class="time"><img style="width: 180px;height: 90px;" src="//www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"></span>
    <div class="search_box">
      <div class="search_x">
        <input type="text" v-model="value" class="search_ipt" @keydown.enter="openlinks(value)" @blur="narrow"
          :style="{ width: iptwidth + 'px' }"
        />
        <span class="el-icon-search iconbut" @mousemove="x" @click="openlinks(value)"></span>
      </div>
    </div>
    <div class="drop-box">
      <div class="list-box" :style="{ width: listwidth + 'px' }">
        <ul class="search_list">
          <li v-for="(item, index) in data" :key="index" class="search_tags" @click="openlinks(item.q)">
            {{ item.q }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  data() {
    return {
      value: "",
      iptwidth: "0",
      listwidth: "0",
      data: {},
      timer: "",//定义一个定时器的变量
    };
  },
  methods: {
    x() {
      this.iptwidth = "450";
    },
    openlinks(val) {
      if(val){
        window.open(`https://www.baidu.com/s?wd=${val}`);
      }
    },
    narrow(){
      if(!this.value){
        this.iptwidth="0"
      }
    },
    check(i){
      const num = (i<10)?("0"+i) : i;
      return num;
    }
  },
  created() {
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
    }
  },
  watch: {
    async value(val) {
      if (val != "") {
        const data = await this.$jsonp(
          `https://www.baidu.com/sugrec?prod=pc&cb=getData&wd=${val}`,
          {
            callbackQuery: "cb",
            callbackName: "jsonp_func",
          }
        );

        this.data = data.g;
        this.listwidth = "490";
      } else this.listwidth = "0";
    },
  },
};
</script>

<style>
.home {
  width: 100%;
  height: 100vh;
  background-image: url("../../../../assets/images/bg.jpg");
  overflow: hidden;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.time {
  margin-top: 50px;
  font-size: 30px;
  cursor: pointer;
  color: #fff;
  transition: 0.3s;
}
.time:hover {
  transform: scale(1.1);
}
.search_box {
  position: absolute;
  top: 120px;
  left: 50%;
  margin-top: 50px;
  transform: translate(-50%, -50%);
  border-radius: 40px;
  height: 40px;
  background: white;
}
.search_ipt {
  border: none;
  background: none;
  outline: none;
  float: left;
  padding: 0;
  color: black;
  font-size: 16px;
  transition: 0.4s;
  line-height: 40px;
  width: 450px;
  margin-top: -1px;
  text-align: center;
  font-size: small;
  font-weight: 400;
}
.iconbut {
  color: #e84118;
  float: right;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: white;
  display: flex;
  justify-content: center;
  align-items: center;
}
.iconbut:hover {
  color: #40a9ff;
  cursor: pointer;
}
.drop-box {
  height: 360px;
  position: absolute;
  top: 380px;
  left: 50%;
  transform: translate(-50%, -50%);
}
.search_list {
  width: 100%;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.4);
  margin: 0;
  padding: 0;
  overflow: hidden;
  font-size: small;
}
.search_tags {
  list-style: none;
  width: 100%;
  height: 35px;
  color: rgba(255, 255, 255, 0.8);
  line-height: 35px;
  text-indent: 30px;
  cursor: pointer;
}
.search_tags:hover {
  text-indent: 40px;
  background: rgba(255, 255, 255, 0.4);
}
</style>
