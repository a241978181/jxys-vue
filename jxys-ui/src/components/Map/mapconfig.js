import TileLayer from "ol/layer/Tile"
import TileArcGISRest from 'ol/source/TileArcGISRest'
import GeoJSON from "ol/format/GeoJSON"
import VectorLayer from "ol/layer/Vector";
import {Text,Fill,Circle, Stroke, Style} from 'ol/style';
import VectorSource from "ol/source/Vector";


//显示层
var streetmap=function(maptype,adcode){
    var mapLayerVector=null
    switch(maptype){
        case 0:
            mapLayerVector=new VectorLayer({
                title: 'Vector',
                source: new VectorSource({
                    projection: 'EPSG:4326',
                    url: "https://geo.datav.aliyun.com/areas_v3/bound/"+adcode+"_full.json", //GeoJSON的文件路径，用户可以根据需求而改变
                    format: new GeoJSON()
                }),
                style: function (feature) {
                    return new Style({
                        stroke:new Stroke({
                            color:'#FFFFFF'
                        }),
                        fill:new Fill({
                            color:'#e2e3e7'
                        }),
                    })
                }
            })
            break;
        case 1:
            mapLayerVector=new VectorLayer({
                title: 'Vector',
                source: new VectorSource({
                    projection: 'EPSG:4326',
                    url: "https://geo.datav.aliyun.com/areas_v3/bound/"+adcode+"_full.json", //GeoJSON的文件路径，用户可以根据需求而改变
                    format: new GeoJSON()
                }),
                style: function (feature) {
                    var name=feature.values_.name
                    return new Style({
                        stroke:new Stroke({
                            color:'#FFFFFF'
                        }),
                        fill:new Fill({
                            color:'#e2e3e7'
                        }),
                        text: new Text({
                            text: name,
                            font: '20',
                            stroke:new Stroke({
                                color:'rgba(135,133,133,0.89)'
                            }),
                        })
                    })
                }
            })
            break;
    }
    return [mapLayerVector];
}


//点位数据
var loadPointMap=function(){

    var mapLayerVector=new VectorLayer({
        title: 'Vector',
        source: new VectorSource({
            projection: 'EPSG:4326',
            url: "/static/json/chinadata.json", //GeoJSON的文件路径，用户可以根据需求而改变
            format: new GeoJSON()
        }),
        style: function (feature) {
            var data=feature.values_.data
            var name=feature.values_.name
            return new Style({
                image: new Circle({
                    // 点的颜色
                    fill: new Fill({
                        color: '#a6cbe4'
                    }),
                    // 圆形半径
                    radius: (data/10)+5
                }),
                text: new Text({
                    text: data+"\n"+name,
                    font: '7',
                    stroke:new Stroke({
                        color:'#ced4ce'
                    }),
                })
            })
        }
    })

    return mapLayerVector;
}

var mapconfig={
    streetmap:streetmap,
    loadPointMap:loadPointMap,
};

export default mapconfig
