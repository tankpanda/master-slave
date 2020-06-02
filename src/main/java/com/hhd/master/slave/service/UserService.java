package com.hhd.master.slave.service;

import com.hhd.master.slave.bean.User;

import java.util.List;

/**
 * Created by kongling on 2020/5/26 17:00
 */
public interface UserService {
    List<User> list();

    User getById(Integer id);

    User update(User user);

    User insert(User user);
}
