package com.jxys.data.service;


import com.jxys.data.domain.ZdMap;

import java.util.List;

/**
 * 字典-地图Service接口
 *
 * @author 李建
 * @date 2022-05-13
 */
public interface ZdMapService {
    /**
     * 查询字典-地图
     *
     * @param id 字典-地图主键
     * @return 字典-地图
     */
    public ZdMap selectZdMapById(Long id);

    /**
     * 查询字典-地图列表
     *
     * @param zdMap 字典-地图
     * @return 字典-地图集合
     */
    public List<ZdMap> selectZdMapList(ZdMap zdMap);

    /**
     * 新增字典-地图
     *
     * @param zdMap 字典-地图
     * @return 结果
     */
    public int insertZdMap(ZdMap zdMap);

    /**
     * 修改字典-地图
     *
     * @param zdMap 字典-地图
     * @return 结果
     */
    public int updateZdMap(ZdMap zdMap);

    /**
     * 批量删除字典-地图
     *
     * @param ids 需要删除的字典-地图主键集合
     * @return 结果
     */
    public int deleteZdMapByIds(Long[] ids);

    /**
     * 删除字典-地图信息
     *
     * @param id 字典-地图主键
     * @return 结果
     */
    public int deleteZdMapById(Long id);
}

