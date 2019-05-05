package com.example.trip.service.security;


import com.example.trip.bean.SPermission;
import com.example.trip.bean.SRole;
import com.example.trip.service.security.SecurityDataService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 权限资源管理器
 * 根据用户请求的地址，获取访问该地址需要的所需权限
 * @author Gu
 */
@Component
public class VFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SecurityDataService securityDataService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求起源路径
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        logger.info("VFilterInvocationSecurityMetadataSource getAttributes [requestUrl={}]", requestUrl);
        //登录页面就不需要权限
        if ("/login".equals(requestUrl)||"/register".equals(requestUrl)) {
            return null;
        }
        //用来存储访问路径需要的角色或权限信息
        List<String> tempPermissionList = new ArrayList<String>();
        //获取角色列表
        List<SRole> sRoleList = securityDataService.findSRoleListBySPermissionUrl(requestUrl);
        logger.info("VFilterInvocationSecurityMetadataSource getAttributes [sRoleList={}]", gson.toJson(sRoleList));
        for(SRole sRole : sRoleList) {
            tempPermissionList.add(sRole.getRole());
        }
        //径获取资源权限列表
        List<SPermission> sPermissionList = securityDataService.findSPermissionListBySPermissionUrl(requestUrl);
        logger.info("VFilterInvocationSecurityMetadataSource getAttributes [sPermissionList={}]", gson.toJson(sPermissionList));
        for(SPermission sPermission : sPermissionList) {
            tempPermissionList.add(sPermission.getPermission());
        }
        //如果没有权限控制的url，可以设置都可以访问，也可以设置默认不许访问
        if(tempPermissionList.isEmpty()) {
            return null;//都可以访问
            //tempPermissionList.add("DEFAULT_FORBIDDEN");//默认禁止
        }
        String[] permissionArray = tempPermissionList.toArray(new String[0]);
        logger.info("VFilterInvocationSecurityMetadataSource getAttributes [permissionArray={}]", gson.toJson(permissionArray));
        return SecurityConfig.createList(permissionArray);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
