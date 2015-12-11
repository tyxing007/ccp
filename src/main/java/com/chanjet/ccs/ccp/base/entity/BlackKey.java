package com.chanjet.ccs.ccp.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.base.entity.BaseEntity;

/**
 * 黑关键字
 * 2013-7-24 上午11:19:12
 */

@Entity
@Table(name="t_black_key")
@NamedQueries({
    @NamedQuery(name = QueryNames.bkList,
                query = "SELECT bk FROM BlackKey bk"),
    @NamedQuery(name = QueryNames.bkListByLevel,
                query = "SELECT bk FROM BlackKey bk WHERE bk.level = ?")
})
public class BlackKey extends BaseEntity {
    
    private String blackKey;        // 黑关键字
    private int level;              // 黑关键字级别（1:第一级, 2:第二级, 3:第三级, 4:第四级）

    public BlackKey() { }
    
    public BlackKey(String blackKey, int level) {
        this.blackKey = blackKey;
        this.level = level;
    }
    
    @Column(name = "black_key", length = 255, nullable = false)
    public String getBlackKey() {
        return blackKey;
    }
    
    public void setBlackKey(String blackKey) {
        this.blackKey = blackKey;
    }
    
    @Column(name = "level", length = 2, nullable = false)
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
}
