package com.jxys.data.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxys.common.utils.StringUtils;
import com.jxys.data.domain.ZdMap;
import com.jxys.data.mapper.ZdMapMapper;
import com.jxys.data.service.ZdMapService;
import com.jxys.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典-地图Service业务层处理
 *
 * @author 李建
 * @date 2022-05-13
 */
@Service
public class ZdMapServiceImpl implements ZdMapService {
    @Autowired
    private ZdMapMapper zdMapMapper;
    @Resource
    private SysUserMapper sysUserMapper;


    /**
     * 查询字典-地图
     *
     * @param id 字典-地图主键
     * @return 字典-地图
     */
    @Override
    public ZdMap selectZdMapById(Long id) {
        return zdMapMapper.selectById(id);
    }

    /**
     * 查询字典-地图列表
     *
     * @param zdMap 字典-地图
     * @return 字典-地图
     */
    @Override
    public List<ZdMap> selectZdMapList(ZdMap zdMap) {

        //查询参数
        QueryWrapper<ZdMap> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().orderByAsc(ZdMap::getJb);
        if(StringUtils.isNotNull(zdMap.getMc())  &&StringUtils.isNotEmpty(zdMap.getMc()) ){
            queryWrapper.like("mc", zdMap.getMc());
        }
        if(StringUtils.isNotNull(zdMap.getAdcode()) ){
            queryWrapper.eq("adcode", zdMap.getAdcode());

        }
        if(StringUtils.isNotNull(zdMap.getJb())  &&StringUtils.isNotEmpty(zdMap.getJb()) ){
            queryWrapper.eq("jb", zdMap.getJb());

        }
        if(StringUtils.isNotNull(zdMap.getPid())){
            queryWrapper.eq("pid", zdMap.getPid());
        }
        //查询数据
        List<ZdMap> list=zdMapMapper.selectList(queryWrapper);
        //查询创建人和修改人
        list.stream().map(obj -> {
            obj.setCreateMc(this.sysUserMapper.selectNickUserByUserName(obj.getCreateBy()));
            obj.setUpdateMc(this.sysUserMapper.selectNickUserByUserName(obj.getUpdateBy()));
            return obj;
        }).collect(Collectors.toList());

        return list;
    }

    /**
     * 新增字典-地图
     *
     * @param zdMap 字典-地图
     * @return 结果
     */
    @Override
    public int insertZdMap(ZdMap zdMap) {

        return zdMapMapper.insert(zdMap);
    }

    /**
     * 修改字典-地图
     *
     * @param zdMap 字典-地图
     * @return 结果
     */
    @Override
    public int updateZdMap(ZdMap zdMap) {
        return zdMapMapper.updateById(zdMap);
    }

    /**
     * 批量删除字典-地图
     *
     * @param ids 需要删除的字典-地图主键
     * @return 结果
     */
    @Override
    public int deleteZdMapByIds(Long[] ids) {

        return zdMapMapper.deleteBatchIds(java.util.Arrays.asList(ids));
    }

    /**
     * 删除字典-地图信息
     *
     * @param id 字典-地图主键
     * @return 结果
     */
    @Override
    public int deleteZdMapById(Long id) {
        return zdMapMapper.deleteById(id);
    }

}
