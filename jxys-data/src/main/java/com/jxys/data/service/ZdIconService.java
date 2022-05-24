package com.jxys.data.service;

import java.util.List;
import com.jxys.data.domain.ZdIcon;

/**
 * icon图标Service接口
 *
 * @author jxys
 * @date 2022-05-24
 */
public interface ZdIconService
{
    /**
     * 查询icon图标
     *
     * @param id icon图标主键
     * @return icon图标
     */
    public ZdIcon selectZdIconById(Long id);

    /**
     * 查询icon图标列表
     *
     * @param zdIcon icon图标
     * @return icon图标集合
     */
    public List<ZdIcon> selectZdIconList(ZdIcon zdIcon);

    /**
     * 新增icon图标
     *
     * @param zdIcon icon图标
     * @return 结果
     */
    public int insertZdIcon(ZdIcon zdIcon);

    /**
     * 修改icon图标
     *
     * @param zdIcon icon图标
     * @return 结果
     */
    public int updateZdIcon(ZdIcon zdIcon);

    /**
     * 批量删除icon图标
     *
     * @param ids 需要删除的icon图标主键集合
     * @return 结果
     */
    public int deleteZdIconByIds(Long[] ids);

    /**
     * 删除icon图标信息
     *
     * @param id icon图标主键
     * @return 结果
     */
    public int deleteZdIconById(Long id);
}
