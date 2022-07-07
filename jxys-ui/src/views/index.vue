<template>
  <div class="hengYes" >
    <h1 style="font-family: 楷体;font-size: 40px;">建行一世改良版脚手架</h1>
  </div>
</template>
<script>

export default {
  name: "Index",
  components: {
  },
  data() {
    return{
    }
  },
  mounted() {
  },
  /**
   * 离开当前页面
   * */
  destroyed() {
  },
  methods: {
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
