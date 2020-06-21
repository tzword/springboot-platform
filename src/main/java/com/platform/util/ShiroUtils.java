package com.platform.util;


import com.platform.common.shiro.MyShiroRealm;
import com.platform.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;


/**
 * @author jianghy
 * @Description: shiro工具类
 * @date 2020/4/27 15:58
 */
public class ShiroUtils {

    private ShiroUtils() {
    }

    /**
     * 获取shiro subject
     *
     * @return
     */
    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取登录session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        getSubjct().logout();
    }

    /**
     * 获取登录用户model
     */
    public static User getUser() {
        User user = null;
        Object obj = getSubjct().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new User();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    /**
     * set用户
     *
     * @param user
     */
    public static void setUser(User user) {
        Subject subject = getSubjct();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    /**
     * 清除授权信息
     */
    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm realm = (MyShiroRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }


    /**
     * 获取登录用户ip
     */
    public static String getIp() {
        return getSubjct().getSession().getHost();
    }

    /**
     * 获取登录用户sessionid
     *
     * @return
     */
    public static String getSessionId() {
        return String.valueOf(getSubjct().getSession().getId());
    }
}
