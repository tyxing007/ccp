package com.chanjet.ccs.ccp.server;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chanjet.ccs.ccp.base.entity.BlackKey;
import com.chanjet.ccs.ccp.base.util.DFAFilter;
import com.chanjet.ccs.ccp.service.BlackKeyService;



public class StartUpService extends HttpServlet {
    
    private static final long serialVersionUID = 3300783329785938163L;

    @Override
    public void init() throws ServletException {
        
        ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        BlackKeyService blackKeyService = (BlackKeyService)act.getBean(BlackKeyService.class);
        List<BlackKey> blackKeys = blackKeyService.getBlackKeys();
        DFAFilter.initDFAFilter(blackKeys);
    }
}
