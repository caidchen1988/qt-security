package com.qtdbp.security.dao.impl;

/*import com.zrhis.base.model.ResultModel;
import com.zrhis.common.debug.DebugUtil;
import com.zrhis.common.utils.EntityUtil;
import com.zrhis.common.utils.JPQLBuilder;
import com.zrhis.common.utils.PropertyFilter;
import com.zrhis.system.dao.BaseDao;
import oracle.jdbc.OracleTypes;*/
import com.qtdbp.security.base.ResultModel;
import com.qtdbp.security.dao.BaseDao;
import com.qtdbp.security.utils.DebugUtil;
import com.qtdbp.security.utils.JPQLBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Repository
@SuppressWarnings({"unchecked","rawtypes"})
public class BaseDaoImpl implements BaseDao {

	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	protected DataSource dataSource;
	
	public void save(Object entity) throws Exception{
		try {
			PropertyDescriptor property = EntityUtil.getPrimaryKey(entity.getClass());
			Object val = EntityUtil.getValue(entity, property);
			if("".equals(val))	EntityUtil.setValue(entity, property, null);
			this.entityManager.persist(entity);
			logger.info("save entity "+entity.getClass().getSimpleName());
		}catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			logger.info("save entity "+entity.getClass().getSimpleName()+" failed!");
			throw new Exception(e.getMessage());
		}
		
	}
	
	public void update(Object entity) throws Exception{
		try {
			this.entityManager.merge(entity);
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public <T> T get(Class<T> clz,String id) throws Exception{
		T entity = null;
		try {
			entity = this.entityManager.find(clz, id);
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return entity;
	}
	
	public void delete(Object entity) throws Exception{
		try {
			this.entityManager.remove(entity);
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public void delete(Class clz,String id) throws Exception{
		try {
			Object entity = this.get(clz, id);
			this.delete(entity);
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public void delete(String className,String id) throws Exception{
		try {
			Class clz = Class.forName(className);
			this.delete(clz, id);
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	public void updateEx(Object entity) throws Exception{
		/*final String  SQL = JPQLBuilder.createUpdateSQL(entity);
		Query query = this.entityManager.createQuery(SQL);*/
		PropertyDescriptor primaryKey = EntityUtil.getPrimaryKey(entity.getClass());
		String idValue = (String) EntityUtil.getPropertyValue(entity, primaryKey);
		if(idValue == null)throw new NullPointerException();
		Object oldEntity = this.get(entity.getClass(), idValue);
		oldEntity = EntityUtil.beanCopay(entity, oldEntity, new PropertyFilter() {
			@Override
			public boolean doFilter(Object obj, String property, Object value) {
				if(value == null)return false;
				return true;
			}
		});
		this.entityManager.merge(oldEntity);
	}
	
	public <T> List<T> query(Class<T> clz,String property,Object value) throws Exception{
		try {
			CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(clz);
			Root<T> root = cq.from(clz);
			EntityType<T> ent = root.getModel(); 
			cq.select(root);

			Predicate condition = cb.equal(root.get(ent.getSingularAttribute(property)), value);
			cq.where(condition);
			//添加默认排序，按照主键排序
			cq.orderBy(cb.asc(root.get(ent.getSingularAttribute(EntityUtil.getPrimaryKey(clz).getName()))));
			
			
			TypedQuery<T> typedQuery = this.entityManager.createQuery(cq);
			List<T> rs = typedQuery.getResultList();
			return rs;
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public <T> List<T> query(Class<T> clz,String property,Object value,String[] orders) throws Exception{
		try {
			CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(clz);
			Root<T> root = cq.from(clz);
			EntityType<T> ent = root.getModel(); 
			cq.select(root);

			Predicate condition = cb.equal(root.get(ent.getSingularAttribute(property)), value);
			cq.where(condition);
			if(orders != null){
				if(orders.length>0){
					Order[] arrOrder = (Order[]) new Order[orders.length];
					for(int i=0;i<orders.length;i++){
						arrOrder[i] = (Order) cb.asc(root.get(ent.getSingularAttribute(orders[i])));
					}
					cq.orderBy((javax.persistence.criteria.Order[]) arrOrder);
				}
			}
			TypedQuery<T> typedQuery = this.entityManager.createQuery(cq);
			List<T> rs = typedQuery.getResultList();

			return rs;
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public <T> List<T> query(Class<T> clz,Map<String ,Object> params) throws Exception{
		return query(clz,params,null);
	}

	public <T> List<T> query(Class<T> clz,Map<String ,Object> params,String[] orders) throws Exception{
		try {
			CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(clz);
			Root<T> root = cq.from(clz);
			EntityType<T> ent = root.getModel();
			cq.select(root);
			if(params != null){
				Set<String> keyset = params.keySet();
				Iterator<String> it = keyset.iterator();
				Predicate[] conditions = new Predicate[keyset.size()];
				int index = 0;
				while(it.hasNext()){
					String key = it.next();
					Object value = params.get(key);
					root.get(key).equals(value);
					Predicate condition = cb.equal(root.get(ent.getSingularAttribute(key)), value);
					conditions[index] = condition;
					index ++;
				}
				cq.where(conditions);
			}
			if(orders != null){
				if(orders.length>0){
					Order[] arrOrder = (Order[]) new Order[orders.length];
					for(int i=0;i<orders.length;i++){
						arrOrder[i] = (Order) cb.asc(root.get(ent.getSingularAttribute(orders[i])));
					}
					cq.orderBy((javax.persistence.criteria.Order[]) arrOrder);
				}
			}
			TypedQuery<T> typedQuery = this.entityManager.createQuery(cq);
			List<T> rs = typedQuery.getResultList();
			return rs;
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	/**
	 * 模糊查找，自动识别值中带%号的字段
	 * @param clz
	 * @param params
	 * @param orders  只支持默认排序
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> fuzzyQuery(Class<T> clz,Map<String ,Object> params,String[] orders) throws Exception{
		try {
			CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(clz);
			Root<T> root = cq.from(clz);
			EntityType<T> ent = root.getModel();
			cq.select(root);
			if(params != null){
				Set<String> keyset = params.keySet();
				Iterator<String> it = keyset.iterator();
				Predicate[] conditions = new Predicate[keyset.size()];
				int index = 0;
				while(it.hasNext()){
					String key = it.next();
					Object value = params.get(key);
					root.get(key).equals(value);
					Predicate condition ;
//					DebugUtil.println(value);
					if(value.toString().indexOf("%")>0){
						condition = cb.like((Expression<String>) root.get(ent.getSingularAttribute(key)), value.toString());
					}else{
						condition = cb.equal(root.get(ent.getSingularAttribute(key)), value);
					}
					conditions[index] = condition;
					index ++;
				}
				cq.where(conditions);
			}
			if(orders != null){
				if(orders.length>0){
					Order[] arrOrder = (Order[]) new Order[orders.length];
					for(int i=0;i<orders.length;i++){
						arrOrder[i] = (Order) cb.asc(root.get(ent.getSingularAttribute(orders[i])));
					}
					cq.orderBy((javax.persistence.criteria.Order[]) arrOrder);
				}
			}
			TypedQuery<T> typedQuery = this.entityManager.createQuery(cq);
			List<T> rs = typedQuery.getResultList();
			return rs;
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public ResultModel queryByJPQL(Class clz, Map<String, Object> params) throws Exception{

		ResultModel rsm = new ResultModel();
		try {
			Set<String> keyset = params.keySet();
			String querySQL = JPQLBuilder.createQuerySQL(clz, null);
			String conutSQL = JPQLBuilder.createCountSQL(clz, null);
			Query query = this.entityManager.createQuery(querySQL);
			Query count = this.entityManager.createQuery(conutSQL);
			Iterator<String> it = keyset.iterator();
			while(it.hasNext()){
				String key = it.next();Object value = params.get(key);
				query.setParameter(key, value);
				count.setParameter(key, value);
			}
			List list = query.getResultList();
			Integer totalCount = Integer.valueOf(count.getSingleResult().toString());
			rsm.setList(list);
			rsm.setTotalCount(totalCount);
			return rsm;
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public ResultModel queryByJPQL(Class clz,Map<String, Object> params,int start,int limit) throws Exception{
		ResultModel rsm = new ResultModel();
		try {
			String querySQL = JPQLBuilder.createQuerySQL(clz, null);
			String conutSQL = JPQLBuilder.createCountSQL(clz, null);
			Query query = this.entityManager.createQuery(querySQL);
			Query count = this.entityManager.createQuery(conutSQL);
			
			if(params != null){
				Set<String> keyset = params.keySet();
				Iterator<String> it = keyset.iterator();
				while(it.hasNext()){
					String key = it.next();Object value = params.get(key);
					query.setParameter(key, value);
					count.setParameter(key, value);
				}
			}
			
			query.setFirstResult(start);
			query.setMaxResults(limit);
			List list = query.getResultList();
			Integer totalCount = Integer.valueOf(count.getSingleResult().toString());
			rsm.setList(list);
			rsm.setTotalCount(totalCount);
			return rsm;
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public ResultModel queryByJPQL(Class clz,Map<String, Object> params,String[] orders,int start,int limit) throws Exception{
		ResultModel rsm = new ResultModel();
		try {
			Set<String> keyset = null;
			if(params != null)keyset = params.keySet();
			String querySQL = JPQLBuilder.createQuerySQL(clz, keyset,orders);
			String conutSQL = JPQLBuilder.createCountSQL(clz, keyset);
			Query query = this.entityManager.createQuery(querySQL);
			Query count = this.entityManager.createQuery(conutSQL);
			if(keyset != null){
				Iterator<String> it = keyset.iterator();
				while(it.hasNext()){
					String key = it.next();Object value = params.get(key);
					query.setParameter(key, value);
					count.setParameter(key, value);
				}
			}
			query.setFirstResult(start);
			query.setMaxResults(limit);
			List list = query.getResultList();
			Integer totalCount = Integer.valueOf(count.getSingleResult().toString());
			rsm.setList(list);
			rsm.setTotalCount(totalCount);
			return rsm;
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	public Integer getMaxByPro(String table,String pid) throws Exception{
		Connection conn = null;
		CallableStatement cs = null;
		Integer max = null;
		conn = this.dataSource.getConnection();
		cs = conn.prepareCall("{call PRO_WSJD_GETXHMAX(?,?,?)}");
		cs.setString(1, table);
		cs.setString(2, pid);
		cs.registerOutParameter(3, OracleTypes.NUMBER);
		cs.executeQuery();
		max = cs.getInt(3);
		try {
			cs.close();
			conn.close();
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
		}
		return max;
	}
	
	/**
	 * 去掉分页 
	 * @param clz
	 * @param params
	 * @param orders
	 * @return
	 * @throws Exception
	 */
	public ResultModel queryByJPQL(Class clz,Map<String, Object> params,String[] orders) throws Exception{
		ResultModel rsm = new ResultModel();
		try {
			Set<String> keyset = null;
			if(params != null)keyset = params.keySet();
			String querySQL = JPQLBuilder.createQuerySQL(clz, keyset,orders);
			String conutSQL = JPQLBuilder.createCountSQL(clz, keyset);
			Query query = this.entityManager.createQuery(querySQL);
			Query count = this.entityManager.createQuery(conutSQL);
			if(keyset != null){
				Iterator<String> it = keyset.iterator();
				while(it.hasNext()){
					String key = it.next();Object value = params.get(key);
					query.setParameter(key, value);
					count.setParameter(key, value);
				}
			}
			List list = query.getResultList();
			Integer totalCount = Integer.valueOf(count.getSingleResult().toString());
			rsm.setList(list);
			rsm.setTotalCount(totalCount);
			return rsm;
		} catch (Exception e) {
			if(DebugUtil.IS_DEBUG)
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.dao.BaseDao#queryByExample(java.lang.Object, java.lang.String[])
	 */
	@Override
	public ResultModel queryByExample(Object obj, String[] orders)
			throws Exception {
		
		return this.queryByExample(obj, orders, null, null);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.dao.BaseDao#queryByExample(java.lang.Object)
	 */
	@Override
	public ResultModel queryByExample(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return this.queryByExample(obj, null);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.dao.BaseDao#queryByExample(java.lang.Object, java.lang.String[], int, int)
	 */
	@Override
	public ResultModel queryByExample(Object obj, String[] orders, Integer start,
			Integer limit) throws Exception {
		String querySQL = JPQLBuilder.queryByExample(obj, orders);
		String conutSQL = JPQLBuilder.countByExample(obj);
		Map<String,Object> map = JPQLBuilder.exampleToParams(obj);
		
		Query query = this.entityManager.createQuery(querySQL);
		Query count = this.entityManager.createQuery(conutSQL);
		if(!map.isEmpty()){
			for(Map.Entry<String,Object> entry : map.entrySet()){
				query.setParameter(entry.getKey(), entry.getValue());
				count.setParameter(entry.getKey(), entry.getValue());
			}
		}
		
		if(start != null && limit != null){
			if(limit>0){
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
		}
		
		List list = query.getResultList();
		Integer totalCount = Integer.valueOf(count.getSingleResult().toString());
		
		return new ResultModel(totalCount,list);
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.dao.BaseDao#queryByExample(java.lang.Object, int, int)
	 */
	@Override
	public ResultModel queryByExample(Object obj, Integer start, Integer limit)
			throws Exception {
		return this.queryByExample(obj, null, start, limit);
	}
	
	
}
