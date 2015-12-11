package com.chanjet.ccs.ccp.base.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Entity
@Table(name = "t_sms")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Sms extends BaseSms implements Cloneable {
    
    private static Log logger = LogFactory.getLog(Sms.class);
    
    @Override
    public Object clone() {
        Object o = null;
        try {
            o = (Sms)super.clone(); //Object 中的clone()识别出你要复制的是哪一个对象。    
        } catch (CloneNotSupportedException ex) {
            logger.error("clone error", ex);
        }
        return o;
    }
}
