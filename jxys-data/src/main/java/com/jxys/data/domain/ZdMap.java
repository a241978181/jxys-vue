package com.jxys.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jxys.common.annotation.Excel;
import com.jxys.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 字典-地图对象 zd_map
 *
 * @author 李建
 * @date 2022-05-13
 */
@TableName(value = "zd_map")
public class ZdMap extends BaseEntity {

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 级别:0：国家级 1：省级 2：市级 3：区级 */
    @Excel(name = "级别:0：国家级 1：省级 2：市级 3：区级")
    private String jb;

    /** 名称 */
    @Excel(name = "名称")
    private String mc;

    /** 名称-英文 */
    @Excel(name = "名称-英文")
    private String mcYw;

    /** 所属颜色 */
    @Excel(name = "所属颜色")
    private String color;

    /** 行政区划代码 */
    @Excel(name = "行政区划代码")
    private Long adcode;

    /** 缩放级别 */
    @Excel(name = "缩放级别")
    private BigDecimal zoom;

    /** 中心点x */
    @Excel(name = "中心点x")
    private BigDecimal zxdX;

    /** 中心点y */
    @Excel(name = "中心点y")
    private BigDecimal zxdY;

    /** 全称 */
    @Excel(name = "全称")
    private String qc;

    /** 简称 */
    @Excel(name = "简称")
    private String jc;

    /** 邮编 */
    @Excel(name = "邮编")
    private String yb;

    /** 长途区号 */
    @Excel(name = "长途区号")
    private String ctqh;

    /** 父id */
    @Excel(name = "父id")
    private Long pid;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setJb(String jb){
        this.jb = jb;
    }

    public String getJb(){
        return jb;
    }
    public void setMc(String mc){
        this.mc = mc;
    }

    public String getMc(){
        return mc;
    }
    public void setMcYw(String mcYw){
        this.mcYw = mcYw;
    }

    public String getMcYw(){
        return mcYw;
    }
    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }
    public void setAdcode(Long adcode){
        this.adcode = adcode;
    }

    public Long getAdcode(){
        return adcode;
    }

    public BigDecimal getZoom() {
        return zoom;
    }

    public void setZoom(BigDecimal zoom) {
        this.zoom = zoom;
    }

    public void setZxdX(BigDecimal zxdX){
        this.zxdX = zxdX;
    }

    public BigDecimal getZxdX(){
        return zxdX;
    }
    public void setZxdY(BigDecimal zxdY){
        this.zxdY = zxdY;
    }

    public BigDecimal getZxdY(){
        return zxdY;
    }
    public void setQc(String qc){
        this.qc = qc;
    }

    public String getQc(){
        return qc;
    }
    public void setJc(String jc){
        this.jc = jc;
    }

    public String getJc(){
        return jc;
    }
    public void setYb(String yb){
        this.yb = yb;
    }

    public String getYb(){
        return yb;
    }
    public void setCtqh(String ctqh){
        this.ctqh = ctqh;
    }

    public String getCtqh(){
        return ctqh;
    }
    public void setPid(Long pid){
        this.pid = pid;
    }

    public Long getPid(){
        return pid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("deleted", getDeleted())
                .append("jb", getJb())
                .append("mc", getMc())
                .append("mcYw", getMcYw())
                .append("color", getColor())
                .append("adcode", getAdcode())
                .append("zoom", getZoom())
                .append("zxdX", getZxdX())
                .append("zxdY", getZxdY())
                .append("qc", getQc())
                .append("jc", getJc())
                .append("yb", getYb())
                .append("ctqh", getCtqh())
                .append("pid", getPid())
                .toString();
    }
}
