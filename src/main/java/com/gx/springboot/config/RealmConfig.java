package com.gx.springboot.config;

import com.gx.springboot.domain.UserInfo;
import com.gx.springboot.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author guoxing
 * @version V1.0
 * @Package com.gx.springboot.config
 * @date 2021/1/27 15:27
 */
@Configuration
public class RealmConfig extends AuthorizingRealm {

    @Autowired
    IUserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 授权
     *
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        logger.info("调用授权......");
        //获取当前登录用户
        UserInfo user = (UserInfo) principal.getPrimaryPrincipal();
        //通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加角色
        simpleAuthorizationInfo.addRole(user.getRole());
        //添加权限
        simpleAuthorizationInfo.addStringPermissions(user.getPermissions());

        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        logger.info("认证......");
        //获取用户输入的用户名
        String userName = (String) token.getPrincipal();


        //2.通过username从数据库中查找到user实体
        UserInfo user = userService.findUserByUserName(userName);

        if (user == null) {
            return null;
        }

        //3.通过SimpleAuthenticationInfo做身份处理
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        //4.用户账号状态验证等其他业务操作
        if (!user.getAvailable()) {
            throw new AuthenticationException("该账号已经被禁用");
        }
        //5.返回身份处理对象
        return simpleAuthenticationInfo;
    }


}
