package com.qtdbp.security.repository;

import com.qtdbp.security.entity.SysUsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 系统用户角色关联数据库操作集
 *
 * @author: caidchen
 * @create: 2017-05-17 15:05
 * To change this template use File | Settings | File Templates.
 */
public interface SysUsersRolesRepository extends JpaRepository<SysUsersRoles, String>{
}
