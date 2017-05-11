/**
 * 
 */
package com.qtdbp.security.annotations;

import com.qtdbp.security.base.enums.QueryType;

import java.lang.annotation.*;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2016-2017 qtbigdata.com Inc.</p>
 * @author caidchen
 * @version v1.0
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryBuilder {
	QueryType value();
}
