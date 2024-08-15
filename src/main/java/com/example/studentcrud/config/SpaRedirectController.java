package com.example.studentcrud.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaRedirectController {

    @RequestMapping(value = { "/user-app/**" })
    public String redirect() {
        // Redirect all requests to `index.html`
        return "forward:/index.html";
    }
}
