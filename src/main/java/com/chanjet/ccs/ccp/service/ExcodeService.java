package com.chanjet.ccs.ccp.service;

import java.util.List;

import com.chanjet.ccs.ccp.base.entity.ExtendCode;

public interface ExcodeService {

    public void save(ExtendCode excode);
    public List<ExtendCode> listSta();
    public List<ExtendCode> listDyn();
    public List<ExtendCode> listStaBySql();
    public List<ExtendCode> listDynBySql();
    public boolean update(String codeName, int excodeType);
}
