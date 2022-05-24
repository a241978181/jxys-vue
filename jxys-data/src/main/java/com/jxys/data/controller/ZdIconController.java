package com.jxys.data.controller;

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
import com.jxys.data.domain.ZdIcon;
import com.jxys.data.service.ZdIconService;
import com.jxys.common.utils.poi.ExcelUtil;
import com.jxys.common.core.page.TableDataInfo;

/**
 * icon图标Controller
 *
 * @author jxys
 * @date 2022-05-24
 */
@RestController
@RequestMapping("/data/icon")
public class ZdIconController extends BaseController
{
    @Autowired
    private ZdIconService zdIconService;

    /**
     * 查询icon图标列表
     */
    @PreAuthorize("@ss.hasPermi('data:icon:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZdIcon zdIcon)
    {
        startPage();
        List<ZdIcon> list = zdIconService.selectZdIconList(zdIcon);
        return getDataTable(list);
    }

    /**
     * 导出icon图标列表
     */
    @PreAuthorize("@ss.hasPermi('data:icon:export')")
    @Log(title = "icon图标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZdIcon zdIcon)
    {
        List<ZdIcon> list = zdIconService.selectZdIconList(zdIcon);
        ExcelUtil<ZdIcon> util = new ExcelUtil<ZdIcon>(ZdIcon.class);
        util.exportExcel(response, list, "icon图标数据");
    }

    /**
     * 获取icon图标详细信息
     */
    @PreAuthorize("@ss.hasPermi('data:icon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(zdIconService.selectZdIconById(id));
    }

    /**
     * 新增icon图标
     */
    @PreAuthorize("@ss.hasPermi('data:icon:add')")
    @Log(title = "icon图标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZdIcon zdIcon)
    {
        return toAjax(zdIconService.insertZdIcon(zdIcon));
    }

    /**
     * 修改icon图标
     */
    @PreAuthorize("@ss.hasPermi('data:icon:edit')")
    @Log(title = "icon图标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZdIcon zdIcon)
    {
        return toAjax(zdIconService.updateZdIcon(zdIcon));
    }

    /**
     * 删除icon图标
     */
    @PreAuthorize("@ss.hasPermi('data:icon:remove')")
    @Log(title = "icon图标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(zdIconService.deleteZdIconByIds(ids));
    }
}
