package com.jxys.test.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.jxys.test.domain.TestName;

/**
 * 测试名称Mapper接口
 *
 * @author jxys
 * @date 2022-05-06
 */
@Mapper
public interface TestNameMapper extends MPJBaseMapper<TestName> {

}
