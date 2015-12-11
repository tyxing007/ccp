package com.chanjet.ccs.ccp.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.chanjet.ccs.base.entity.BaseEntity;

//TODO MEMO 修改了createTime，原先是time，及表中的create_time

@Entity
@Table(name = "t_report")
public class Report extends BaseEntity {

    private int specialNo;          // 特服号码
    private String tel;             // 手机号
    private String identifier;      // 唯一标识
    private String state;           // 状态(接收成功：返回DELIVRD或者0 接收失败：其他值)
    private String createTime;      // 创建时间
    private int num;                // 编号
    private int proxyId;            // 代理通道id

	public Report(int id, int specialNo, String tel, String identifier,
			String state, String createTime, int num, int proxyId) {
		this.id = id;
		this.specialNo = specialNo;
		this.tel = tel;
		this.identifier = identifier;
		this.state = state;
		this.createTime = createTime;
		this.num = num;
		this.proxyId = proxyId;
	}

	public Report() { }

	@Column(name = "special_no")
	public int getSpecialNo() {
		return specialNo;
	}

	public void setSpecialNo(int specialNo) {
		this.specialNo = specialNo;
	}

	@Column(name = "tel")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "identifier")
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "num")
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Column(name = "proxy_id")
	public int getProxyId() {
		return proxyId;
	}

	public void setProxyId(int proxyId) {
		this.proxyId = proxyId;
	}

}
