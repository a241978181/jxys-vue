package com.jxys.test.service;

import java.util.List;
import com.jxys.test.domain.TestName;

/**
 * 测试名称Service接口
 *
 * @author jxys
 * @date 2022-05-06
 */
public interface TestNameService
{
    /**
     * 查询测试名称
     *
     * @param id 测试名称主键
     * @return 测试名称
     */
    public TestName selectTestNameById(Long id);

    /**
     * 查询测试名称列表
     *
     * @param testName 测试名称
     * @return 测试名称集合
     */
    public List<TestName> selectTestNameList(TestName testName);

    /**
     * 新增测试名称
     *
     * @param testName 测试名称
     * @return 结果
     */
    public int insertTestName(TestName testName,Long deptId);

    /**
     * 修改测试名称
     *
     * @param testName 测试名称
     * @return 结果
     */
    public int updateTestName(TestName testName);

    /**
     * 批量删除测试名称
     *
     * @param ids 需要删除的测试名称主键集合
     * @return 结果
     */
    public int deleteTestNameByIds(Long[] ids);

    /**
     * 删除测试名称信息
     *
     * @param id 测试名称主键
     * @return 结果
     */
    public int deleteTestNameById(Long id);
}
