<template>
  <div id="map" ref="rootmap">

  </div>
</template>

<script>
import "ol/ol.css"
import {Map, View} from "ol"
import {defaults as defaultControls, FullScreen} from "ol/control";
import {defaults} from 'ol/interaction'
import DragPan from 'ol/interaction/DragPan'//先在项目中引用此包
import {Text, Fill, Circle, Stroke, Style} from 'ol/style';
import mapconfig from './mapconfig'

export default {
  data() {
    return {
      map: null,
      selectFeature: null,
    };
  },
  props:{
    //地图类型0：单独设置点位1：普通地图
    mapType: {
      type: Number,
      default: 1
    },
    //缩放比例
    zoom: {
      type: Number,
      default: 6
    },
    //地区数据
    adcode: {
      type: Number,
      default: 100000
    },
    //是否允许拖拽
    isDrag: {
      type: Boolean,
      default: true
    },
    //是否允许滚动缩放
    isZoom: {
      type: Boolean,
      default: true
    },
    //中心点x
    x: {
      type: Number,
      default: 104.06
    },
    //中心点y
    y: {
      type: Number,
      default: 35.67
    },
  },
  mounted() {
    this.showMap();
  },
  methods: {
    //生成地图
    showMap(){
      var mapcontainer = this.$refs.rootmap;
      this.map = new Map({
        target: mapcontainer,
        layers: mapconfig.streetmap(this.mapType,this.adcode),
        controls: defaultControls({
          attributionOptions: ({
            collapsible: true
          })
        }),
        view: new View({
          projection: "EPSG:4326",    //使用这个坐标系
          center: [this.x, this.y],  //中国中心
          zoom: this.zoom,
          minZoom: this.isZoom?1:this.zoom,
          maxZoom: this.isZoom?8:this.zoom
        }),
        interactions: defaults({mouseWheelZoom: this.isZoom})
      });
      //设置点击事件
      this.map.on('singleclick', this.mapClick);
      this.map.on('pointermove', this.pointermove);
      let pan = this.getPan()
      pan.setActive(this.isDrag)//false禁止拖拽，true允许拖拽

      if (this.mapType == 0) {
        this.loadPointMap();
      }
    },
    //地图点击时间
    mapClick(e) {
      if (this.selectFeature) {
        this.selectFeature.setStyle(null)
      }
      var pixel = this.map.getEventPixel(e.originalEvent);
      var featureInfo = this.map.forEachFeatureAtPixel(pixel,function (feature, layer) {
            return {feature: feature, layer: layer};
      });
      if (featureInfo !== undefined && featureInfo !== null && featureInfo.layer !== null) {
        this.selectFeature = featureInfo.feature;
        let name = featureInfo.feature.values_.name
        featureInfo.feature.setStyle(new Style({
          stroke: new Stroke({color: '#fff400', width: 5}),
          fill: new Fill({color: '#ffc863'}),
          text: new Text({text: name, stroke: new Stroke({color: '#0f1714'})}),
        }))
        this.$emit('mapClick', featureInfo.feature.values_);
      }
    },
    clearSelectMap() {
      if (this.selectFeature) {
        this.selectFeature.setStyle(null)
      }
      this.selectFeature = null
    },
    pointermove(e) {
      //鼠标经过箭头变手势，，可以在if的条件中加入指定的图层layerId即可
      let pixel = this.map.getEventPixel(e.originalEvent);
      let hit = this.map.hasFeatureAtPixel(pixel, {
        layerFilter: layer => {
          return true;
        },
      });
      this.map.getTargetElement().style.cursor = hit ? 'pointer' : '';
    },
    getMap() {
      return this.map;
    },
    clearMap(adcode) {
      let allLayers = this.map.getAllLayers();
      for (let i = 0; i < allLayers.length; i++) {
        this.map.removeLayer(allLayers[i]);
      }
      let newLayers = mapconfig.streetmap(this.mapType,adcode?adcode:this.adcode)
      this.map.addLayer(newLayers[0])
      if (this.mapType == 0) {
        this.loadPointMap(val)
      }
    },
    //设置点位数据
    loadPointMap() {
      let pointVector = mapconfig.loadPointMap()
      this.map.addLayer(pointVector)
    },
    getPan() {
      let pan = null
      this.map.getInteractions().forEach(element => {
        if (element instanceof DragPan) {
          pan = element
        }
      })
      return pan
    },
  }
};
</script>

<style>
#map {
  /*height: calc(100vh - 136px);*/
  height: 100%;
  width: 100%;
}

/*隐藏ol的一些自带元素*/
.ol-attribution, .ol-zoom {
  display: none;
}


</style>
