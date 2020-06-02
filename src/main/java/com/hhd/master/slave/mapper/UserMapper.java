package com.hhd.master.slave.mapper;

import com.hhd.master.slave.bean.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by kongling on 2020/5/26 16:59
 */
@Repository
public interface UserMapper extends BaseMapper<User>, MySqlMapper<User> {
}
