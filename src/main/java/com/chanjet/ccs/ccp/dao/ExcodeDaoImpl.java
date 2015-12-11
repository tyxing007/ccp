package com.chanjet.ccs.ccp.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.chanjet.ccs.base.dao.BaseDaoImpl;
import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.entity.ExtendCode;

@Repository
public class ExcodeDaoImpl extends BaseDaoImpl<ExtendCode> implements ExcodeDao {

    public List<ExtendCode> listBySql(int type, int limit) {
        
        String sql =  " SELECT * FROM t_extend_code c WHERE c.state = " + EnumValues.EXCODE_UNACTIVE + 
                " AND c.type = ? ORDER BY RANDOM() LIMIT ? OFFSET 0 ";
        Query query = em.createNativeQuery(sql, ExtendCode.class);
        query.setParameter(1, type);
        query.setParameter(2, limit);
        return query.getResultList();
    }
    
}
