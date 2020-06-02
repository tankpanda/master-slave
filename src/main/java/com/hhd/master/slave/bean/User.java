package com.hhd.master.slave.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * Created by kongling on 2020/5/27 9:41
 */
@Data
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String userPhone;
    private String address;
    private Integer weight;
    private Date createTime;
    private Date updateTime;
}
