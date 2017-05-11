package com.qtdbp.security.dao;

import com.qtdbp.security.base.ResultModel;

import java.util.List;
import java.util.Map;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-3 下午4:30:19
 * @version v1.0
 *
 */

@SuppressWarnings("rawtypes")
public interface BaseDao {
	
	public void save(Object entity) throws Exception;
	public void update(Object entity) throws Exception;
	public void updateEx(Object entity) throws Exception;
	public <T> T get(Class<T> clz, String id) throws Exception;
	public void delete(Object entity) throws Exception;
	public void delete(Class clz, String id) throws Exception;
	public void delete(String className, String id) throws Exception;
	
	public <T> List<T> query(Class<T> clz, String property, Object value) throws Exception;
	public <T> List<T> query(Class<T> clz, String property, Object value, String[] orders) throws Exception;
	public <T> List<T> query(Class<T> clz, Map<String, Object> params) throws Exception;
	public <T> List<T> query(Class<T> clz, Map<String, Object> params, String[] orders) throws Exception;
	public <T> List<T> fuzzyQuery(Class<T> clz, Map<String, Object> params, String[] orders) throws Exception;
	
	public ResultModel queryByJPQL(Class clz, Map<String, Object> params) throws Exception;
	public ResultModel queryByJPQL(Class clz, Map<String, Object> params, int start, int limit) throws Exception;
	public ResultModel queryByJPQL(Class clz, Map<String, Object> params, String[] orders, int start, int limit) throws Exception;
	public ResultModel queryByJPQL(Class clz, Map<String, Object> params, String[] orders) throws Exception;
	
	public ResultModel queryByExample(Object obj, String[] orders) throws Exception;
	public ResultModel queryByExample(Object obj) throws Exception;
	public ResultModel queryByExample(Object obj, String[] orders, Integer start, Integer limit) throws Exception;
	public ResultModel queryByExample(Object obj, Integer start, Integer limit) throws Exception;
	

}
