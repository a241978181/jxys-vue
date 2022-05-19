package com.jxys.data.controller;


import com.jxys.common.annotation.Log;
import com.jxys.common.core.controller.BaseController;
import com.jxys.common.core.domain.AjaxResult;
import com.jxys.common.core.page.TableDataInfo;
import com.jxys.common.enums.BusinessType;
import com.jxys.common.utils.poi.ExcelUtil;
import com.jxys.data.domain.ZdMap;
import com.jxys.data.service.ZdMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 字典-地图Controller
 *
 * @author 李建
 * @date 2022-05-13
 */
@RestController
@RequestMapping("/zd/map")
public class ZdMapController extends BaseController {
    @Autowired
    private ZdMapService zdMapService;

    /**
     * 查询字典-地图列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ZdMap zdMap)
    {
        startPage();
        List<ZdMap> list = zdMapService.selectZdMapList(zdMap);
        return getDataTable(list);
    }

    /**
     * 导出字典-地图列表
     */
    @Log(title = "字典-地图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZdMap zdMap) {
        List<ZdMap> list = zdMapService.selectZdMapList(zdMap);
        ExcelUtil<ZdMap> util = new ExcelUtil<ZdMap>(ZdMap.class);
        util.exportExcel(response, list, "字典-地图数据");
    }

    /**
     * 获取字典-地图详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(zdMapService.selectZdMapById(id));
    }

    /**
     * 新增字典-地图
     */
    @Log(title = "字典-地图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZdMap zdMap) {
        return toAjax(zdMapService.insertZdMap(zdMap));
    }

    /**
     * 修改字典-地图
     */
    @Log(title = "字典-地图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZdMap zdMap) {
        return toAjax(zdMapService.updateZdMap(zdMap));
    }

    /**
     * 删除字典-地图
     */
    @Log(title = "字典-地图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(zdMapService.deleteZdMapByIds(ids));
    }
}

