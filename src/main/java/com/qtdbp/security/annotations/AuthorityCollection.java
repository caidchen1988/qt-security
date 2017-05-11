/**
 * 
 */
package com.qtdbp.security.annotations;

import com.qtdbp.security.base.enums.AuthorityType;

import java.lang.annotation.*;

/**
 * 类功能说明：权限集合
 * 
 * <p>Copyright: Copyright © 2016-2017 qtbigdata.com Inc.</p>
 * @version v1.0
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorityCollection {

	AuthorityType[] value();

}
