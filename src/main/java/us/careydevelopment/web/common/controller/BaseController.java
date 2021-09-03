package us.careydevelopment.web.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import us.careydevelopment.web.common.util.SecurityUtil;

@Controller
public abstract class BaseController {
    
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    
    @Value("${assets.base}")
    protected String assetsBase;
    
    @Autowired
    private SecurityUtil securityUtil;

    
    @GetMapping("/error")
    public String handleError() {
        return "error";
    }
    
    
    protected void setCommon(Model model, String jwtToken) {
        handleAssetsBase(model);
        handleSecurity(model, jwtToken);
        handlePagination(model);
    }
    
    
    private void handlePagination(Model model) {
        model.addAttribute("pathSegment", "/trendhound");
        model.addAttribute("pageNumSeparator", "?");
    }
    
    
    private void handleAssetsBase(Model model) {
        model.addAttribute("assetsBase", assetsBase);        
    }
    
    
    private void handleSecurity(Model model, String jwtToken) {
        String user = securityUtil.getCurrentUser(jwtToken);
        LOG.debug("user is " + user);
        
        if (SecurityUtil.ANONYMOUS_USER_NAME.equals(user)) {
            model.addAttribute("anonymousUser", true);
        } else {
            model.addAttribute("username", user);
        }
    }
}
