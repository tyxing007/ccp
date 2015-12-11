package com.chanjet.ccs.ccp.base.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.chanjet.ccs.base.dao.BaseDaoImpl;
import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.entity.Deliver;

@Repository
public class DeliverDaoImpl extends BaseDaoImpl<Deliver> implements DeliverDao {

    public List<Deliver> modifyDeliversBySql(String codeName, boolean forUpdate) {
        
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM t_deliver d WHERE d.extend_code = ? AND d.has_accessed = ? ");
        if (forUpdate)
            sb.append(" FOR UPDATE ");
        
        Query query = em.createNativeQuery(sb.toString(),Deliver.class);
        
        query.setParameter(1, codeName);
        query.setParameter(2, EnumValues.SYNC_FLAG_UNSYNCED);
        List<Deliver> delivers = query.getResultList();
        for(Deliver deliver : delivers) {
            deliver.setSyncFlag(EnumValues.SYNC_FLAG_SYNCED);
            this.saveT(deliver);
        }
        return delivers;
    }
}
