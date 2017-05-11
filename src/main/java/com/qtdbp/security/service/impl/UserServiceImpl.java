/**
 * 
 */
package com.qtdbp.security.service.impl;

import com.zrhis.base.model.Message;
import com.zrhis.common.web.MD5Encoder;
import com.zrhis.system.bean.SysUser;
import com.zrhis.system.dao.BaseDao;
import com.zrhis.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类功能说明：
 * 
 * <p>Copyright: Copyright © 2012-2013 zrhis.com Inc.</p>
 * <p>Company:新中软科技有限公司</p>
 * @author 王成委
 * @date 2013-12-13 下午1:38:43
 * @version v1.0
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BaseDao baseDao;
	
	/* (non-Javadoc)
	 * @see com.zrhis.system.service.UserService#add(com.zrhis.system.bean.SysUser)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public synchronized Message add(SysUser user) throws Exception {
		String username = user.getVZcmc();
		boolean repeat = this.checkRepeat(username);
		
		System.out.println("检查用户是否重复，结果："+repeat);
		
		Message message = new Message();
		message.setSuccess(true);
		if(repeat){
			message.setFlag(false);
			message.setMessage("用户名已存在！");
		}else{
			user.setVDlkl(MD5Encoder.encoder(user.getVDlkl()));
			this.baseDao.save(user);
			message.setFlag(true);
			message.setMessage("添加成功！");
		}
		
		return message;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.UserService#remove(com.zrhis.system.bean.SysUser)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Message delete(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.UserService#remove(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Message delete(String userId) throws Exception {
		this.baseDao.delete(SysUser.class, userId);
		Message message = new Message(true,"删除成功");
		return message;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.UserService#update(com.zrhis.system.bean.SysUser)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Message update(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.UserService#resetPassword(com.zrhis.system.bean.SysUser)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Message resetPassword(SysUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.UserService#resetPassword(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Message resetPassword(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zrhis.system.service.UserService#checkReport(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean checkRepeat(String username) throws Exception {
		List<SysUser> list = baseDao.query(SysUser.class, "VZcmc", username);
		return list.size()>0;
	}

}
