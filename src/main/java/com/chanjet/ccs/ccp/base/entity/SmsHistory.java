package com.chanjet.ccs.ccp.base.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * 历史短信（迁移到历史库中的短信）
 * @Date 2013-7-24 上午11:21:03
 */

@Entity
@Table(name="t_sms_history")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SmsHistory extends BaseSms{
    
}
