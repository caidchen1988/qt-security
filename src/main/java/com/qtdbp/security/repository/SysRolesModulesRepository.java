package com.qtdbp.security.repository;

import com.qtdbp.security.entity.SysRolesModules;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 系统角色模块关系数据操作集
 *
 * @author: caidchen
 * @create: 2017-05-17 16:29
 * To change this template use File | Settings | File Templates.
 */
public interface SysRolesModulesRepository extends JpaRepository<SysRolesModules, String> {
}
