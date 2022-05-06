package com.jxys.test.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxys.common.core.domain.BaseEntity;
import com.jxys.common.annotation.Excel;

/**
 * 测试名称对象 test_name
 *
 * @author jxys
 * @date 2022-05-06
 */
@TableName(value = "test_name")
public class TestName  extends BaseEntity  {

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 测试文本 */
    @Excel(name = "测试文本")
    private String test;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public void setTest(String test){
        this.test = test;
    }

    public String getTest(){
        return test;
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
                .append("name", getName())
                .append("test", getTest())
                .toString();
    }
}
