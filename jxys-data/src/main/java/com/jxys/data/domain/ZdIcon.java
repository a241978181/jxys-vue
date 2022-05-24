package com.jxys.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxys.common.core.domain.BaseEntity;
import com.jxys.common.annotation.Excel;

/**
 * icon图标对象 zd_icon
 *
 * @author jxys
 * @date 2022-05-24
 */
@TableName(value = "zd_icon")
public class ZdIcon  extends BaseEntity  {

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** icon图标 */
    @Excel(name = "icon图标")
    private String icon;

    /** 类型1.element2.iview */
    @Excel(name = "类型1.element2.iview")
    private String type;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getIcon(){
        return icon;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
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
                .append("icon", getIcon())
                .append("type", getType())
                .toString();
    }
}
