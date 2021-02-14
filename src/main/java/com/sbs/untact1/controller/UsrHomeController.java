package com.sbs.untact1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showmain() {
		System.out.println("hello");
		return "안녕";
	}
}
