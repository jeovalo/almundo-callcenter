package com.jeovalo.almundo.callcenter.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String index() {
        System.out.println("api-documentacion.html");
        return "redirect:swagger-ui.html";
    }
}
