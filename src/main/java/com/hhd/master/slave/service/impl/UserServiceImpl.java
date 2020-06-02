package com.hhd.master.slave.service.impl;

import com.hhd.master.slave.bean.User;
import com.hhd.master.slave.dynamoic.data.source.DataSourceSelector;
import com.hhd.master.slave.dynamoic.data.source.DynamicDataSourceEnum;
import com.hhd.master.slave.mapper.UserMapper;
import com.hhd.master.slave.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by kongling on 2020/5/27 9:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @DataSourceSelector(DynamicDataSourceEnum.SLAVE)
    public List<User> list() {
        return userMapper.selectAll();
    }

    @Override
    @DataSourceSelector(DynamicDataSourceEnum.SLAVE)
    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @DataSourceSelector(DynamicDataSourceEnum.MASTER)
    public User update(User user) {
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    @Override
    @DataSourceSelector(DynamicDataSourceEnum.MASTER)
    public User insert(User user) {
        user.setCreateTime(new Date());
        userMapper.insert(user);
        return user;
    }
}
