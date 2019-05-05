package com.example.trip.service.security;


import com.example.trip.bean.SPermission;
import com.example.trip.bean.SRole;
import com.example.trip.bean.SUser;
import com.example.trip.dao.SPermissionDao;
import com.example.trip.dao.SRoleDao;
import com.example.trip.dao.SUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Security 数据服务
 *
 * @author Gu
 */
@Service
public class SecurityDataService {
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    private SRoleDao sRoleDao;
    @Autowired
    private SPermissionDao sPermissionDao;

    public SUser findSUserByName(String name) {
        return sUserDao.findSUserByName(name);
    }

    public SUser findSUserByEmail(String email) {
        return sUserDao.findSUserByEmail(email);
    }

    public boolean addUser(String userName,String email,String password){
        boolean flag=false;
        flag=sUserDao.addUser(userName,email,password);
        sRoleDao.addUser(userName);
        return flag;
    }

    public List<SRole> findSRoleListBySUserId(int sUserId) {
        return sRoleDao.findSRoleListBySUserId(sUserId);
    }

    public List<SRole> findSRoleListBySPermissionUrl(String sPermissionUrl) {
        return sRoleDao.findSRoleListBySPermissionUrl(sPermissionUrl);
    }

    public List<SPermission> findSPermissionListBySUserId(int sUserId) {
        return sPermissionDao.findSPermissionListBySUserId(sUserId);
    }

    public List<SPermission> findSPermissionListBySPermissionUrl(String sPermissionUrl) {
        return sPermissionDao.findSPermissionListBySPermissionUrl(sPermissionUrl);
    }
}