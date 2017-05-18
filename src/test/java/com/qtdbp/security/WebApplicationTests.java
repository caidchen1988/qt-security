package com.qtdbp.security;

import com.qtdbp.security.base.SysConstant;
import com.qtdbp.security.entity.*;
import com.qtdbp.security.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 钱塘交易中心应用测试
 *
 * @author: caidchen
 * @create: 2017-04-12 14:13
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class WebApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUsersRepository usersRepository ;

    @Autowired
    private SysRolesRepository rolesRepository ;

    @Autowired
    private SysUsersRolesRepository usersRolesRepository ;

    @Autowired
    private SysAuthoritiesRepository authoritiesRepository ;

    @Autowired
    private SysRolesAuthoritiesRepository rolesAuthoritiesRepository ;

    @Autowired
    private SysModulesRepository modulesRepository ;

    @Autowired
    private SysRolesModulesRepository rolesModulesRepository ;

    @Autowired
    private SysResourcesRepository resourcesRepository ;

    @Autowired
    private SysAuthoritiesResourcesRepository authoritiesResourcesRepository ;

    /**
     *  BCryptPasswordEncoder管理密码
     */
    @Test
    public void testPass(){
        String pass = "abel@123";
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder(4);
        String hashPass = encode.encode(pass);
        System.out.println(hashPass);
    }

    @Test
    public void addUser() {

        String pass = "123456";
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder(4);
        String hashPass = encode.encode(pass);

        List<SysUsers> usersList = new ArrayList<>() ;
        SysUsers users = new SysUsers() ;
        users.setUsername("admin");
        users.setName("系统管理员");
        users.setPassword(hashPass);
        users.setDtCreate(new Date());
        users.setEnabled(true);
        users.setAccountNonExpired(true);
        users.setAccountNonLocked(true);
        users.setCredentialsNonExpired(true);
        usersList.add(users);

        users = new SysUsers() ;
        users.setUsername("user");
        users.setName("系统普通用户");
        users.setPassword(hashPass);
        users.setDtCreate(new Date());
        users.setEnabled(true);
        users.setAccountNonExpired(true);
        users.setAccountNonLocked(true);
        users.setCredentialsNonExpired(true);
        usersList.add(users);

        usersRepository.save(usersList) ;
    }

    @Test
    public void addRols() {

        List<SysRoles> rolesList = new ArrayList<>() ;

        SysRoles roles = new SysRoles() ;
        roles.setEnable(true);
        roles.setIssys(true);
        roles.setRoleName("ROLE_USER");
        roles.setRoleDesc("系统普通用户, 不允许删除");
        rolesList.add(roles);

        roles = new SysRoles() ;
        roles.setEnable(true);
        roles.setIssys(true);
        roles.setRoleName("ROLE_ADMIN");
        roles.setRoleDesc("系统管理员, 不允许删除");
        rolesList.add(roles);

        rolesRepository.save(rolesList) ;

        logger.info("role_id: "+roles.getRoleId());
    }


    @Test
    public void addUserRols() {

        List<SysUsersRoles> usersRolesList = new ArrayList<>() ;
        SysUsersRoles usersRoles = new SysUsersRoles() ;
        usersRoles.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        usersRoles.setSysUsers(new SysUsers("40289fa55c152593015c15259e0e0000"));
        usersRolesList.add(usersRoles);

        usersRoles = new SysUsersRoles() ;
        usersRoles.setSysRoles(new SysRoles("40289fa55c197347015c197352a70000"));
        usersRoles.setSysUsers(new SysUsers("40289fa55c197723015c19772f0b0000"));
        usersRolesList.add(usersRoles);

        usersRolesRepository.save(usersRolesList) ;
    }

    @Test
    public void addAuthorities() {

        List<SysAuthorities> authoritiesList = new ArrayList<>() ;
        SysAuthorities authorities = new SysAuthorities() ;
        authorities.setIssys(true);
        authorities.setAuthorityDesc("登录后首页");
        authorities.setAuthorityMark("AUTH_DASHBOARD");
        authorities.setAuthorityName("登录后首页");
        authorities.setEnable(true);
        authorities.setMessage("登录后首页");
        authoritiesList.add(authorities);

        authorities = new SysAuthorities() ;
        authorities.setIssys(true);
        authorities.setAuthorityDesc("产品列表页");
        authorities.setAuthorityMark("AUTH_PRODUCT_LIST");
        authorities.setAuthorityName("产品列表页");
        authorities.setEnable(true);
        authorities.setMessage("产品列表页");
        authoritiesList.add(authorities);

        authorities = new SysAuthorities() ;
        authorities.setIssys(true);
        authorities.setAuthorityDesc("产品列表页 - 添加");
        authorities.setAuthorityMark("AUTH_PRODUCT_ADD");
        authorities.setAuthorityName("产品列表页 - 添加");
        authorities.setEnable(true);
        authorities.setMessage("产品列表页 - 添加");
        authoritiesList.add(authorities);

        authorities = new SysAuthorities() ;
        authorities.setIssys(true);
        authorities.setAuthorityDesc("产品列表页 - 删除");
        authorities.setAuthorityMark("AUTH_PRODUCT_DELETE");
        authorities.setAuthorityName("产品列表页 - 删除");
        authorities.setEnable(true);
        authorities.setMessage("产品列表页 - 删除");
        authoritiesList.add(authorities);

        authorities = new SysAuthorities() ;
        authorities.setIssys(true);
        authorities.setAuthorityDesc("产品列表页 - 编辑");
        authorities.setAuthorityMark("AUTH_PRODUCT_UPDATE");
        authorities.setAuthorityName("产品列表页 - 编辑");
        authorities.setEnable(true);
        authorities.setMessage("产品列表页 - 编辑");
        authoritiesList.add(authorities);

        authorities = new SysAuthorities() ;
        authorities.setIssys(true);
        authorities.setAuthorityDesc("产品列表页 - 上架");
        authorities.setAuthorityMark("AUTH_PRODUCT_UP");
        authorities.setAuthorityName("产品列表页 - 上架");
        authorities.setEnable(true);
        authorities.setMessage("产品列表页 - 上架");
        authoritiesList.add(authorities);

        authorities = new SysAuthorities() ;
        authorities.setIssys(true);
        authorities.setAuthorityDesc("产品列表页 - 下架");
        authorities.setAuthorityMark("AUTH_PRODUCT_DOWN");
        authorities.setAuthorityName("产品列表页 - 下架");
        authorities.setEnable(true);
        authorities.setMessage("产品列表页 - 下架");
        authoritiesList.add(authorities);

        authoritiesRepository.save(authoritiesList) ;
    }

    @Test
    public void addRolesAuthorities() {

        List<SysRolesAuthorities> rolesAuthoritiesList = new ArrayList<>() ;
        SysRolesAuthorities rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032df70000"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesAuthoritiesList.add(rolesAuthorities);

        rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070001"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesAuthoritiesList.add(rolesAuthorities);

        rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070002"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesAuthoritiesList.add(rolesAuthorities);

        rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070003"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesAuthoritiesList.add(rolesAuthorities);

        rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070004"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesAuthoritiesList.add(rolesAuthorities);

        rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070005"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesAuthoritiesList.add(rolesAuthorities);

        rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e080006"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesAuthoritiesList.add(rolesAuthorities);

        rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032df70000"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c197347015c197352a70000"));
        rolesAuthoritiesList.add(rolesAuthorities);

        rolesAuthorities = new SysRolesAuthorities() ;
        rolesAuthorities.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070001"));
        rolesAuthorities.setSysRoles(new SysRoles("40289fa55c197347015c197352a70000"));
        rolesAuthoritiesList.add(rolesAuthorities);


        rolesAuthoritiesRepository.save(rolesAuthoritiesList) ;

        logger.info("##################id: "+rolesAuthorities.getId());
    }

    @Test
    public void addModules() {

        SysModules modules = new SysModules() ;
        modules.setEnable(true);
        modules.setApplication(SysConstant.APP_TRADING_ADMIN);
        modules.setModuleDesc("数据商城");
        modules.setModuleName("数据商城");
        modules.setModuleType(SysConstant.MODULE_TYPE_ALL);
        modules.setModuleUrl(null);
        modules.setController(null);
        modules.setIssys(true);
        modules.setILevel(1);
        modules.setLeaf(false);
        modules.setParent(null);
        modules.setPriority(10);

        modulesRepository.save(modules) ;

        logger.info("##################parent_id: "+modules.getModuleId());

        List<SysModules> subModulesList = new ArrayList<>() ;

        SysModules subModules = new SysModules() ;
        subModules.setEnable(true);
        subModules.setApplication(SysConstant.APP_TRADING_ADMIN);
        subModules.setModuleDesc("数据商城 - 产品管理");
        subModules.setModuleName("产品管理");
        subModules.setModuleType(SysConstant.MODULE_TYPE_ALL);
        subModules.setModuleUrl("/products");
        subModules.setController(null);
        subModules.setIssys(true);
        subModules.setILevel(2);
        subModules.setLeaf(true);
        subModules.setParent(modules.getModuleId());
        subModules.setPriority(100);
        subModulesList.add(subModules);

        subModules = new SysModules() ;
        subModules.setEnable(true);
        subModules.setApplication(SysConstant.APP_TRADING_ADMIN);
        subModules.setModuleDesc("数据商城 - 产品管理");
        subModules.setModuleName("类目管理");
        subModules.setModuleType(SysConstant.MODULE_TYPE_ALL);
        subModules.setModuleUrl("/types");
        subModules.setController(null);
        subModules.setIssys(true);
        subModules.setILevel(2);
        subModules.setLeaf(true);
        subModules.setParent(modules.getModuleId());
        subModules.setPriority(110);
        subModulesList.add(subModules);

        modulesRepository.save(subModulesList) ;
    }

    @Test
    public void addRolesModules() {

        List<SysRolesModules> rolesModulesList = new ArrayList<>() ;

        SysRolesModules rolesModules = new SysRolesModules() ;
        rolesModules.setSysModules(new SysModules("40289fa55c158562015c15856d4c0000"));
        rolesModules.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesModulesList.add(rolesModules);

        rolesModules = new SysRolesModules() ;
        rolesModules.setSysModules(new SysModules("40289fa55c158562015c15856d7c0001"));
        rolesModules.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesModulesList.add(rolesModules);

        rolesModules = new SysRolesModules() ;
        rolesModules.setSysModules(new SysModules("40289fa55c158562015c15856d7d0002"));
        rolesModules.setSysRoles(new SysRoles("40289fa55c15371d015c153728570000"));
        rolesModulesList.add(rolesModules);

        rolesModules = new SysRolesModules() ;
        rolesModules.setSysModules(new SysModules("40289fa55c158562015c15856d4c0000"));
        rolesModules.setSysRoles(new SysRoles("40289fa55c197347015c197352a70000"));
        rolesModulesList.add(rolesModules);

        rolesModulesRepository.save(rolesModulesList) ;
    }


    @Test
    public void addResources() {

        List<SysResources> resourcesList = new ArrayList<>() ;

        SysResources resources = new SysResources() ;
        resources.setEnable(true);
        resources.setIssys(true);
        resources.setPriority(1);
        resources.setResourceDesc("添加产品");
        resources.setResourceName("添加产品");
        resources.setResourcePath("/products/add");
        resources.setResourceType(SysConstant.RESOURCE_TYPE_METHOD);
        resourcesList.add(resources);

        resources = new SysResources() ;
        resources.setEnable(true);
        resources.setIssys(true);
        resources.setPriority(2);
        resources.setResourceDesc("修改产品");
        resources.setResourceName("修改产品");
        resources.setResourcePath("/products/update");
        resources.setResourceType(SysConstant.RESOURCE_TYPE_METHOD);
        resourcesList.add(resources);

        resources = new SysResources() ;
        resources.setEnable(true);
        resources.setIssys(true);
        resources.setPriority(3);
        resources.setResourceDesc("产品下架");
        resources.setResourceName("下架");
        resources.setResourcePath("/products/down");
        resources.setResourceType(SysConstant.RESOURCE_TYPE_METHOD);
        resourcesList.add(resources);

        resources = new SysResources() ;
        resources.setEnable(true);
        resources.setIssys(true);
        resources.setPriority(4);
        resources.setResourceDesc("产品上架");
        resources.setResourceName("上架");
        resources.setResourcePath("/products/up");
        resources.setResourceType(SysConstant.RESOURCE_TYPE_METHOD);
        resourcesList.add(resources);

        resources = new SysResources() ;
        resources.setEnable(true);
        resources.setIssys(true);
        resources.setPriority(5);
        resources.setResourceDesc("产品列表");
        resources.setResourceName("产品列表");
        resources.setResourcePath("/products");
        resources.setResourceType(SysConstant.RESOURCE_TYPE_URL);
        resourcesList.add(resources);

        resources = new SysResources() ;
        resources.setEnable(true);
        resources.setIssys(true);
        resources.setPriority(6);
        resources.setResourceDesc("登录后首页");
        resources.setResourceName("登录后首页");
        resources.setResourcePath("/dashboard");
        resources.setResourceType(SysConstant.RESOURCE_TYPE_URL);
        resourcesList.add(resources);

        resources = new SysResources() ;
        resources.setEnable(true);
        resources.setIssys(true);
        resources.setPriority(7);
        resources.setResourceDesc("产品删除");
        resources.setResourceName("删除");
        resources.setResourcePath("/products/delete");
        resources.setResourceType(SysConstant.RESOURCE_TYPE_METHOD);
        resourcesList.add(resources);

        resourcesRepository.save(resourcesList) ;
    }

    @Test
    public void addAuthoritiesResources() {

        List<SysAuthoritiesResources> authoritiesResourcesList = new ArrayList<>() ;

        SysAuthoritiesResources authoritiesResources = new SysAuthoritiesResources() ;
        authoritiesResources.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070001"));
        authoritiesResources.setSysResources(new SysResources("40289fa55c1a0dcc015c1a0ddd570000"));
        authoritiesResourcesList.add(authoritiesResources);

        authoritiesResources = new SysAuthoritiesResources() ;
        authoritiesResources.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070002"));
        authoritiesResources.setSysResources(new SysResources("40289fa55c15a4c7015c15a4d2750000"));
        authoritiesResourcesList.add(authoritiesResources);

        authoritiesResources = new SysAuthoritiesResources() ;
        authoritiesResources.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070003"));
        authoritiesResources.setSysResources(new SysResources("40289fa55c1a220e015c1a221f300000"));
        authoritiesResourcesList.add(authoritiesResources);

        authoritiesResources = new SysAuthoritiesResources() ;
        authoritiesResources.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070004"));
        authoritiesResources.setSysResources(new SysResources("40289fa55c15a4c7015c15a4d2840001"));
        authoritiesResourcesList.add(authoritiesResources);

        authoritiesResources = new SysAuthoritiesResources() ;
        authoritiesResources.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e070005"));
        authoritiesResources.setSysResources(new SysResources("40289fa55c15a4c7015c15a4d2840003"));
        authoritiesResourcesList.add(authoritiesResources);

        authoritiesResources = new SysAuthoritiesResources() ;
        authoritiesResources.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032e080006"));
        authoritiesResources.setSysResources(new SysResources("40289fa55c15a4c7015c15a4d2840002"));
        authoritiesResourcesList.add(authoritiesResources);

        authoritiesResources = new SysAuthoritiesResources() ;
        authoritiesResources.setSysAuthorities(new SysAuthorities("40289fa55c1a0322015c1a032df70000"));
        authoritiesResources.setSysResources(new SysResources("40289fa55c1a0dcc015c1a0ddd720001"));
        authoritiesResourcesList.add(authoritiesResources);

        authoritiesResourcesRepository.save(authoritiesResourcesList) ;
    }

    @Test
    @Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
    public void update() {

        SysUsers user = new SysUsers() ;
        user.setUserId("40289fa55c152593015c15259e0e0000");
        user.setUsername("admin");
        user.setPassword("$2a$04$dYqUiNaZDypNFtGZroWMCOMsHQrzNNYpP.q3uegoB3VJajfSeuJ3K");
        user.setLoginIp("127.0.0.1");
        user.setLastLogin(new Date());

        usersRepository.saveAndFlush(user);
    }

}
