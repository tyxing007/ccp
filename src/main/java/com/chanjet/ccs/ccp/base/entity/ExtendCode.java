package com.chanjet.ccs.ccp.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.base.entity.BaseEntity;
import com.chanjet.ccs.ccp.base.basic.EnumValues;

/**
 * 扩展号
 * @Date 2013-7-24 下午4:05:39
 */

@Entity
@Table(name="t_extend_code")
@NamedQueries({ 
    @NamedQuery(name = QueryNames.excodeListSta, 
                query = " SELECT c FROM ExtendCode c WHERE c.state = " + EnumValues.EXCODE_UNACTIVE + 
                        " AND c.type = " + EnumValues.EXCODE_STATIC),
    @NamedQuery(name = QueryNames.excodeListDyn, 
                query = " SELECT c FROM ExtendCode c WHERE c.state = " + EnumValues.EXCODE_UNACTIVE + 
                        " AND c.type = " + EnumValues.EXCODE_DYNAMIC),
    @NamedQuery(name = QueryNames.excodeUpdate,
                query = " UPDATE ExtendCode c SET c.state = " + EnumValues.EXCODE_ACTIVE + "WHERE c.codeName = ? " + 
                        "  AND c.type = ? AND c.state = " + EnumValues.EXCODE_UNACTIVE)
})
public class ExtendCode extends BaseEntity {

    private String codeName;        // 扩展号
    private int type = 1;           // 1:固定扩展号，2:动态分配扩展号
    private int state = -1;         // 扩展号状态（-2:不可用，-1:尚未使用，1:已被使用）
    
    @Column(name = "code_name", unique = true, nullable = false)
    public String getCodeName() {
        return codeName;
    }
    
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    
    @Column(name = "type", nullable = false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "state", nullable = false)
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    
}
