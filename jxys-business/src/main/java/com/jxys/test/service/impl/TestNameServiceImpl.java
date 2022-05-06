package com.jxys.test.service.impl;

import com.jxys.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxys.system.mapper.SysUserMapper;
import javax.annotation.Resource;
import java.util.stream.Collectors;
import java.util.List;
import com.jxys.common.utils.DateUtils;
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
     *
     * @param testName 测试名称
     * @return 测试名称
     */
    @Override
    public List<TestName> selectTestNameList(TestName testName) {

        //查询参数
        QueryWrapper<TestName> queryWrapper = new QueryWrapper();

        if(StringUtils.isNotNull(testName.getName())  &&StringUtils.isNotEmpty(testName.getName()) ){
            queryWrapper.like("name", testName.getName());
        }

        //查询数据
        List<TestName> list=testNameMapper.selectList(queryWrapper);
        //查询创建人和修改人
        list.stream().map(obj -> {
            obj.setCreateMc(this.sysUserMapper.selectNickUserByUserName(obj.getCreateBy()));
            obj.setUpdateMc(this.sysUserMapper.selectNickUserByUserName(obj.getUpdateBy()));
            return obj;
        }).collect(Collectors.toList());

        return list;
    }

    /**
     * 新增测试名称
     *
     * @param testName 测试名称
     * @return 结果
     */
    @Override
    public int insertTestName(TestName testName) {

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
