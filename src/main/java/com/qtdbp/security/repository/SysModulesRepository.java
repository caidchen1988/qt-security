package com.qtdbp.security.repository;

import com.qtdbp.security.entity.SysModules;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 系统模块数据操作集
 *
 * @author: caidchen
 * @create: 2017-05-17 15:41
 * To change this template use File | Settings | File Templates.
 */
public interface SysModulesRepository extends JpaRepository<SysModules, String> {
}
