package com.tareas.organizador.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
     @RequestMapping(value = { "/", "/tareas", "/tareas/nueva", "/tareas/**" })
    public String redirect() {
        return "forward:/browser/index.html";
    }
}
