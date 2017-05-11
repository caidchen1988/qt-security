package com.qtdbp.security.service.impl;

import com.qtdbp.security.base.model.Message;
import com.qtdbp.security.service.DefaultService;
import com.zrhis.base.model.Parameters;
import com.zrhis.base.model.ResultModel;
import com.zrhis.common.utils.MessageManager;
import com.zrhis.common.web.JSONFormat;
import com.zrhis.common.web.controller.ControllerTools;
import com.zrhis.system.bean.BaseXzqh;
import com.zrhis.system.dao.BaseDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SuppressWarnings({"rawtypes","unchecked"})
@Service
public class DefaultServiceImpl implements DefaultService {
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message getEntity(String className, String id)
			throws Exception {
		Object entity = baseDao.get(Class.forName(className), id);
		Message msg = new Message();
		msg.setSuccess(true);
		msg.setFlag(true);
		msg.setData(entity);
		return msg;
	}
	
	@Override
	@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message getEntity(Class clz,String id)
			throws Exception {
		Object entity = baseDao.get(clz, id);
		Message msg = new Message();
		msg.setSuccess(true);
		msg.setFlag(true);
		msg.setData(entity);
		return msg;
	}
	
	@Override
	@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message savaEntity(HttpServletRequest request, Parameters params)
			throws Exception {
		Object entity = ControllerTools.resolvePayloadEX(request);
		baseDao.save(entity);
		Message msg = MessageManager.save();
		return msg;
	}

	@Override
	@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message updateEntity(HttpServletRequest request, Parameters params)
			throws Exception {
		Object entity = ControllerTools.resolvePayloadEX(request);
		baseDao.update(entity);
		Message msg = MessageManager.save();
		return msg;
	}

	@Override
	@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message deleteEntity(String className,String id)
			throws Exception {
		baseDao.delete(className, id);
		Message msg = MessageManager.delete();
		return msg;
	}

	@Override
	//@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message query(HttpServletRequest request, Parameters params)
			throws Exception {
		Class clz = null;
		clz = Class.forName(params.getClassName());
		Map<String,Object> map = null;
		if(params.getParams()!=null && !"".equals(params.getParams())){
			map = JSONObject.fromObject(params.getParams());
			map = JSONFormat.bindType(clz, map);
		}
		ResultModel rsm = baseDao.queryByJPQL(clz, map, params.getOrderarr(), params.getStart(), params.getLimit());
		Message msg = new Message(rsm.getTotalCount(),rsm.getList());
		return msg;
	}
	
	@Override
	//@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message queryAll(HttpServletRequest request, Parameters params)
			throws Exception {
		Class clz = null;
		clz = Class.forName(params.getClassName());
		Map<String,Object> map = null;
		if(params.getParams()!=null && !"".equals(params.getParams())){
			map = JSONObject.fromObject(params.getParams());
			map = JSONFormat.bindType(clz, map);
		}
		ResultModel rs = baseDao.queryByJPQL(clz, map,params.getOrderarr());  
		Message msg = new Message(rs.getList());
		return msg;
	}
	
	public Message fuzzyQueryAll(HttpServletRequest request, Parameters params)
			throws Exception {
		Class clz = null;
		clz = Class.forName(params.getClassName());
		Map<String,Object> map = null;
		if(params.getParams()!=null && !"".equals(params.getParams())){
			map = JSONObject.fromObject(params.getParams());
			map = JSONFormat.bindType(clz, map);
		}
		List rs = baseDao.fuzzyQuery(clz, map,params.getOrderarr());  
		Message msg = new Message(rs);
		return msg;
	}

	@Override
	@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message updateEntityEx(HttpServletRequest request, Parameters params)
			throws Exception {
		Object entity = ControllerTools.resolvePayloadEX(request);
		/*DebugUtil.println(entity);
		WsjdSqjgxx en = (WsjdSqjgxx) entity;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	    String str = formatDate.format(en.getDtTgsj());
	    System.out.println("entity:" + en.getDtTgsj());
		System.out.println("str:" + str);*/
	    this.baseDao.updateEx(entity);
		Message msg = MessageManager.update();
		return msg;
	}
	
	public Message getDepTreeNode(HttpServletRequest request, String id) 
			throws Exception{
		Message msg = new Message();
		List<BaseXzqh> list = new ArrayList<BaseXzqh>();
		
		if(id==null || "".equals(id)){
			msg.setSuccess(true);
			msg.setRoots(list);
		}else if("root".equals(id)){
			list = this.baseDao.query(BaseXzqh.class,"ilevel", 1);
		}else{
			list = this.baseDao.query(BaseXzqh.class,"parentId",id);
		}
		for(int i=0;i<list.size();i++){
			BaseXzqh bean = list.get(i);
			if(bean.getIlevel()==3)bean.setLeaf(true);
		}
		msg.setRoots(list);
		msg.setSuccess(true);
		return msg;
	}
	
	/* (non-Javadoc)
	 * @see com.zrhis.system.service.DefaultService#savaEntity(java.lang.Object)
	 */
	@Override
	@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public Message savaEntity(Object obj) throws Exception {
		baseDao.save(obj);
		Message msg = MessageManager.save();
		return msg;
	}

	

}
