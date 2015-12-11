package com.chanjet.ccs.ccp.base.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.base.entity.BaseEntity;
import com.chanjet.ccs.ccp.base.basic.EnumValues;

// TODO　MEMO 修改了user_name，原先:username，及表中的user_name

/**
 * 通道代理商
 * 
 * @Date 2013-7-24 上午11:19:36
 */

@Entity
@Table(name = "t_proxy")
@NamedQueries({@NamedQuery(name = QueryNames.proxyList,
    query = "SELECT p FROM Proxy p WHERE p.state = " + EnumValues.STATUS_ENABLE)})
public class Proxy extends BaseEntity {

  private String name; // 通道代理名称
  private int level; // 级别
  private int specialNo; // 特服号码
  private String balance; // 余额
  private String userName; // 用户名
  private String password; // 密码
  private int state; // 状态（0:不可用,1:可用）
  private String contact; // 通道联系人
  private String tel; // 联系人电话
  private String remark; // 备注
  private String reflection; // 反射路径
  private int weights; // 权重
  private int isDeliver; // 是否支持上行（0:不支持,1:支持）
  private int isReport; // 是否支持状态报告（0:不支持,1:支持）
  private int isEnable; // 是否启用(0:不启用;1:启用)

  public Proxy() {}

  public Proxy(int id, String name, int level, int specialNo, String balance, String userName,
      String password, int state, String contact, String tel, String remark, String reflection,
      int weights, int isDeliver, int isReport) {
    this.id = id;
    this.name = name;
    this.level = level;
    this.specialNo = specialNo;
    this.balance = balance;
    this.userName = userName;
    this.password = password;
    this.state = state;
    this.contact = contact;
    this.tel = tel;
    this.remark = remark;
    this.reflection = reflection;
    this.weights = weights;
    this.isDeliver = isDeliver;
    this.isReport = isReport;
  }

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "level")
  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Column(name = "special_no")
  public int getSpecialNo() {
    return specialNo;
  }

  public void setSpecialNo(int specialNo) {
    this.specialNo = specialNo;
  }

  @Column(name = "balance")
  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  @Column(name = "user_name")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Column(name = "password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "state")
  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  @Column(name = "contact")
  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  @Column(name = "tel")
  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  @Column(name = "remark")
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Column(name = "reflection")
  public String getReflection() {
    return reflection;
  }

  public void setReflection(String reflection) {
    this.reflection = reflection;
  }

  @Column(name = "weights")
  public int getWeights() {
    return weights;
  }

  public void setWeights(int weights) {
    this.weights = weights;
  }

  @Column(name = "is_deliver")
  public int getIsDeliver() {
    return isDeliver;
  }

  public void setIsDeliver(int isDeliver) {
    this.isDeliver = isDeliver;
  }

  @Column(name = "is_report")
  public int getIsReport() {
    return isReport;
  }

  public void setIsReport(int isReport) {
    this.isReport = isReport;
  }

  @Column(name = "is_enable")
  public int getIsEnable() {
    return isEnable;
  }

  public void setIsEnable(int isEnable) {
    this.isEnable = isEnable;
  }



}
