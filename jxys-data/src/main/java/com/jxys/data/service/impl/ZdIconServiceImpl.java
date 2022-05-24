package com.jxys.data.service.impl;

import com.jxys.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jxys.system.mapper.SysUserMapper;
import javax.annotation.Resource;
import java.util.stream.Collectors;
import java.util.List;
import com.jxys.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxys.data.mapper.ZdIconMapper;
import com.jxys.data.domain.ZdIcon;
import com.jxys.data.service.ZdIconService;

/**
 * icon图标Service业务层处理
 *
 * @author jxys
 * @date 2022-05-24
 */
@Service
public class ZdIconServiceImpl implements ZdIconService {
    @Autowired
    private ZdIconMapper zdIconMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 查询icon图标
     *
     * @param id icon图标主键
     * @return icon图标
     */
    @Override
    public ZdIcon selectZdIconById(Long id) {
        return zdIconMapper.selectById(id);
    }

    /**
     * 查询icon图标列表
     *
     * @param zdIcon icon图标
     * @return icon图标
     */
    @Override
    public List<ZdIcon> selectZdIconList(ZdIcon zdIcon) {

        //查询参数
        QueryWrapper<ZdIcon> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().orderByDesc(ZdIcon::getCreateTime);

        if(StringUtils.isNotNull(zdIcon.getIcon())  &&StringUtils.isNotEmpty(zdIcon.getIcon()) ){
            queryWrapper.like("icon", zdIcon.getIcon());
        }
        if(StringUtils.isNotNull(zdIcon.getType())  &&StringUtils.isNotEmpty(zdIcon.getType()) ){
            queryWrapper.eq("type", zdIcon.getType());

        }

        //查询数据
        List<ZdIcon> list=zdIconMapper.selectList(queryWrapper);
        //查询创建人和修改人
        list.stream().map(obj -> {
            obj.setCreateMc(this.sysUserMapper.selectNickUserByUserName(obj.getCreateBy()));
            obj.setUpdateMc(this.sysUserMapper.selectNickUserByUserName(obj.getUpdateBy()));
            return obj;
        }).collect(Collectors.toList());

        return list;
    }

    /**
     * 新增icon图标
     *
     * @param zdIcon icon图标
     * @return 结果
     */
    @Override
    public int insertZdIcon(ZdIcon zdIcon) {

        return zdIconMapper.insert(zdIcon);

    }

    /**
     * 修改icon图标
     *
     * @param zdIcon icon图标
     * @return 结果
     */
    @Override
    public int updateZdIcon(ZdIcon zdIcon) {
        return zdIconMapper.updateById(zdIcon);
    }

    /**
     * 批量删除icon图标
     *
     * @param ids 需要删除的icon图标主键
     * @return 结果
     */
    @Override
    public int deleteZdIconByIds(Long[] ids) {

        return zdIconMapper.deleteBatchIds(java.util.Arrays.asList(ids));
    }

    /**
     * 删除icon图标信息
     *
     * @param id icon图标主键
     * @return 结果
     */
    @Override
    public int deleteZdIconById(Long id) {
        return zdIconMapper.deleteById(id);
    }

}
