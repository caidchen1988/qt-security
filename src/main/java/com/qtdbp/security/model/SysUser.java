package com.qtdbp.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统用户
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Deprecated
@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_USER", schema = "FYBJ")
@DynamicUpdate(value=true)
@DynamicInsert(value=true)
public class SysUser implements java.io.Serializable {

	// Fields

	private String CCzybh;
	private String depId;
	private String depName;
	private String VJgid;
	private String VJgmc;
	private String VZcmc;
	private String VDlkl;
	private String VYhsf;
	private Integer ICsh;
	private Integer NCjyh;
	private Integer NZt;
	private Date dtZhdl;
	private Date dtJzsj;
	private String CCzymc;
	private Date dtCreate;
	private String VCreator;
	private String VBz;

	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** minimal constructor */
	public SysUser(String CCzybh) {
		this.CCzybh = CCzybh;
	}

	/** full constructor */
	public SysUser(String CCzybh, String depId, String depName, String VJgid,
			String VJgmc, String VZcmc, String VDlkl, Integer ICsh,
			Integer NCjyh, Integer NZt, Date dtZhdl, Date dtJzsj,
			String CCzymc, Date dtCreate, String VCreator, String VBz) {
		this.CCzybh = CCzybh;
		this.depId = depId;
		this.depName = depName;
		this.VJgid = VJgid;
		this.VJgmc = VJgmc;
		this.VZcmc = VZcmc;
		this.VDlkl = VDlkl;
		this.ICsh = ICsh;
		this.NCjyh = NCjyh;
		this.NZt = NZt;
		this.dtZhdl = dtZhdl;
		this.dtJzsj = dtJzsj;
		this.CCzymc = CCzymc;
		this.dtCreate = dtCreate;
		this.VCreator = VCreator;
		this.VBz = VBz;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@GeneratedValue(generator = "uuid")
	@Column(name = "C_CZYBH", unique = true, nullable = false, length = 100)
	public String getCCzybh() {
		return this.CCzybh;
	}

	public void setCCzybh(String CCzybh) {
		this.CCzybh = CCzybh;
	}

	@Column(name = "DEP_ID", length = 100)
	public String getDepId() {
		return this.depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	@Column(name = "DEP_NAME", length = 100)
	public String getDepName() {
		return this.depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	@Column(name = "V_JGID", length = 100)
	public String getVJgid() {
		return this.VJgid;
	}

	public void setVJgid(String VJgid) {
		this.VJgid = VJgid;
	}

	@Column(name = "V_JGMC", length = 100)
	public String getVJgmc() {
		return this.VJgmc;
	}

	public void setVJgmc(String VJgmc) {
		this.VJgmc = VJgmc;
	}

	@Column(name = "V_ZCMC", length = 40)
	public String getVZcmc() {
		return this.VZcmc;
	}

	public void setVZcmc(String VZcmc) {
		this.VZcmc = VZcmc;
	}

	@Column(name = "V_DLKL", length = 100)
	@JsonIgnore
	public String getVDlkl() {
		return this.VDlkl;
	}

	public void setVDlkl(String VDlkl) {
		this.VDlkl = VDlkl;
	}

	@Column(name = "I_CSH", precision = 22, scale = 0)
	public Integer getICsh() {
		return this.ICsh;
	}

	public void setICsh(Integer ICsh) {
		this.ICsh = ICsh;
	}

	@Column(name = "N_CJYH", precision = 22, scale = 0)
	public Integer getNCjyh() {
		return this.NCjyh;
	}

	public void setNCjyh(Integer NCjyh) {
		this.NCjyh = NCjyh;
	}

	@Column(name = "N_ZT", precision = 22, scale = 0)
	public Integer getNZt() {
		return this.NZt;
	}

	public void setNZt(Integer NZt) {
		this.NZt = NZt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_ZHDL", length = 7)
	public Date getDtZhdl() {
		return this.dtZhdl;
	}

	public void setDtZhdl(Date dtZhdl) {
		this.dtZhdl = dtZhdl;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_JZSJ", length = 7)
	public Date getDtJzsj() {
		return this.dtJzsj;
	}

	public void setDtJzsj(Date dtJzsj) {
		this.dtJzsj = dtJzsj;
	}

	@Column(name = "C_CZYMC", length = 40)
	public String getCCzymc() {
		return this.CCzymc;
	}

	public void setCCzymc(String CCzymc) {
		this.CCzymc = CCzymc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CREATE", length = 7,insertable=false,updatable=false)
	public Date getDtCreate() {
		return this.dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

	@Column(name = "V_CREATOR", length = 100)
	public String getVCreator() {
		return this.VCreator;
	}

	public void setVCreator(String VCreator) {
		this.VCreator = VCreator;
	}

	@Column(name = "V_BZ", length = 100)
	public String getVBz() {
		return this.VBz;
	}

	public void setVBz(String VBz) {
		this.VBz = VBz;
	}
	
	@Column(name = "V_YHSF", length = 20)
	public String getVYhsf() {
		return VYhsf;
	}

	public void setVYhsf(String vYhsf) {
		VYhsf = vYhsf;
	}

	
}