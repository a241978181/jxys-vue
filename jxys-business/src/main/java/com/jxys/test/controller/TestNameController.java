package com.jxys.test.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jxys.common.annotation.Log;
import com.jxys.common.core.controller.BaseController;
import com.jxys.common.core.domain.AjaxResult;
import com.jxys.common.enums.BusinessType;
import com.jxys.test.domain.TestName;
import com.jxys.test.service.TestNameService;
import com.jxys.common.utils.poi.ExcelUtil;
import com.jxys.common.core.page.TableDataInfo;

/**
 * 测试名称Controller
 *
 * @author jxys
 * @date 2022-05-06
 */
@RestController
@RequestMapping("/test/name")
public class TestNameController extends BaseController
{
    @Autowired
    private TestNameService testNameService;

    /**
     * 查询测试名称列表
     */
    @PreAuthorize("@ss.hasPermi('test:name:list')")
    @GetMapping("/list")
    public TableDataInfo list(TestName testName)
    {
        startPage();
        List<TestName> list = testNameService.selectTestNameList(testName);
        return getDataTable(list);
    }

    /**
     * 导出测试名称列表
     */
    @PreAuthorize("@ss.hasPermi('test:name:export')")
    @Log(title = "测试名称", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TestName testName)
    {
        List<TestName> list = testNameService.selectTestNameList(testName);
        ExcelUtil<TestName> util = new ExcelUtil<TestName>(TestName.class);
        util.exportExcel(response, list, "测试名称数据");
    }

    /**
     * 获取测试名称详细信息
     */
    @PreAuthorize("@ss.hasPermi('test:name:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(testNameService.selectTestNameById(id));
    }

    /**
     * 新增测试名称
     */
    @PreAuthorize("@ss.hasPermi('test:name:add')")
    @Log(title = "测试名称", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TestName testName)
    {
        return toAjax(testNameService.insertTestName(testName,this.getDeptId()));
    }

    /**
     * 修改测试名称
     */
    @PreAuthorize("@ss.hasPermi('test:name:edit')")
    @Log(title = "测试名称", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TestName testName)
    {
        return toAjax(testNameService.updateTestName(testName));
    }

    /**
     * 删除测试名称
     */
    @PreAuthorize("@ss.hasPermi('test:name:remove')")
    @Log(title = "测试名称", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(testNameService.deleteTestNameByIds(ids));
    }
}
