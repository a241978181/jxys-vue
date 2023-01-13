package com.jxys.test.service.impl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jxys.common.annotation.DataScope;
import com.jxys.common.constant.Constants;
import com.jxys.common.core.domain.entity.SysDept;
import com.jxys.common.utils.StringUtils;
import com.jxys.framework.aspectj.DataScopeAspect;
import com.jxys.system.mapper.SysUserMapper;
import javax.annotation.Resource;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxys.test.mapper.TestNameMapper;
import com.jxys.test.domain.TestName;
import com.jxys.test.service.TestNameService;

/**
 * 测试名称Service业务层处理
 *
 * @author jxys
 * @date 2022-05-06
 */
@Service
public class TestNameServiceImpl implements TestNameService {
    @Autowired
    private TestNameMapper testNameMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 查询测试名称
     *
     * @param id 测试名称主键
     * @return 测试名称
     */
    @Override
    public TestName selectTestNameById(Long id) {
        return testNameMapper.selectById(id);
    }

    /**
     * 查询测试名称列表
     * 注意：如果需要使用mybatisplus连表则需要添加括号下面两个数据，如果是单表查询则不需要添加括号内的数据
     * @param testName 测试名称
     * @return 测试名称
     */
    @Override
    @DataScope(deptAlias = "t",userAlias = "t")
    public List<TestName> selectTestNameList(TestName testName) {
        //查询参数
        MPJLambdaWrapper<TestName> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.orderByDesc(TestName::getCreateTime);
        queryWrapper.selectAll(TestName.class);
        queryWrapper.selectAs(SysDept::getDeptName, TestName::getDeptName);
        queryWrapper.leftJoin(SysDept.class, SysDept::getDeptId, TestName::getDeptId);
        if(StringUtils.isNotNull(testName.getName())  &&StringUtils.isNotEmpty(testName.getName()) ){
            queryWrapper.like(TestName::getName, testName.getName());
        }
        if (testName.getParams().size() > 0 && StringUtils.isNotBlank((String.valueOf(testName.getParams().get(DataScopeAspect.DATA_SCOPE))))) {
            queryWrapper.apply(String.valueOf(testName.getParams().get(DataScopeAspect.DATA_SCOPE)));
        }
        List<TestName> list=testNameMapper.selectJoinList(TestName.class,queryWrapper);
        return list;
    }

    /**
     * 新增测试名称
     *
     * @param testName 测试名称
     * @return 结果
     */
    @Override
    public int insertTestName(TestName testName,Long deptId) {
        testName.setDeptId(deptId);

        return testNameMapper.insert(testName);

    }

    /**
     * 修改测试名称
     *
     * @param testName 测试名称
     * @return 结果
     */
    @Override
    public int updateTestName(TestName testName) {
        return testNameMapper.updateById(testName);
    }

    /**
     * 批量删除测试名称
     *
     * @param ids 需要删除的测试名称主键
     * @return 结果
     */
    @Override
    public int deleteTestNameByIds(Long[] ids) {

        return testNameMapper.deleteBatchIds(java.util.Arrays.asList(ids));
    }

    /**
     * 删除测试名称信息
     *
     * @param id 测试名称主键
     * @return 结果
     */
    @Override
    public int deleteTestNameById(Long id) {
        return testNameMapper.deleteById(id);
    }

}
