package com.sbs.untact1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdmHomeController {
	@RequestMapping("/adm/home/main")
	public String showmain() {
		return "adm/home/main";
	}
}
