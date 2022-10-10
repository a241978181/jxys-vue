package com.jxys.web.controller.system;


import com.jxys.common.annotation.Log;
import com.jxys.common.core.controller.BaseController;
import com.jxys.common.core.domain.AjaxResult;
import com.jxys.common.core.page.TableDataInfo;
import com.jxys.common.enums.BusinessType;
import com.jxys.common.utils.poi.ExcelUtil;
import com.jxys.system.domain.SysNotification;
import com.jxys.system.domain.SysNotificationUser;
import com.jxys.system.service.SysNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 通知管理Controller
 *
 * @author 李建
 * @date 2022-10-09
 */
@RestController
@RequestMapping("/system/notification")
public class SysNotificationController extends BaseController {
    @Autowired
    private SysNotificationService sysNotificationService;

    /**
     * 查询通知管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:notification:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNotification sysNotification) {
        startPage();
        List<SysNotification> list = sysNotificationService.selectSysNotificationList(sysNotification);
        return getDataTable(list);
    }

    /**
     * 查询通知公告用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:notification:list')")
    @GetMapping("/userList")
    public TableDataInfo userList(SysNotificationUser sysNotificationUser) {
        startPage();
        List<SysNotificationUser> list = sysNotificationService.selectSysNotificationUserList(sysNotificationUser);
        return getDataTable(list);
    }

    /**
     * 导出通知管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:notification:export')")
    @Log(title = "通知管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysNotification sysNotification) {
        List<SysNotification> list = sysNotificationService.selectSysNotificationList(sysNotification);
        ExcelUtil<SysNotification> util = new ExcelUtil<SysNotification>(SysNotification.class);
        util.exportExcel(response, list, "通知管理数据");
    }

    /**
     * 获取通知管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notification:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysNotificationService.selectSysNotificationById(id));
    }

    /**
     * 新增通知管理
     */
    @PreAuthorize("@ss.hasPermi('system:notification:add')")
    @Log(title = "通知管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysNotification sysNotification) {
        return toAjax(sysNotificationService.insertSysNotification(sysNotification));
    }

    /**
     * 修改通知管理
     */
    @PreAuthorize("@ss.hasPermi('system:notification:edit')")
    @Log(title = "通知管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysNotification sysNotification) {
        return toAjax(sysNotificationService.updateSysNotification(sysNotification));
    }

    /**
     * 删除通知管理
     */
    @PreAuthorize("@ss.hasPermi('system:notification:remove')")
    @Log(title = "通知管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysNotificationService.deleteSysNotificationByIds(ids));
    }
}

