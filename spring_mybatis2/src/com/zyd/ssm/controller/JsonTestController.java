package com.zyd.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyd.ssm.pojo.Items;
import com.zyd.ssm.pojo.ItemsCustom;

@Controller
public class JsonTestController {
	
	@RequestMapping("/requestJson")
	public @ResponseBody ItemsCustom jsonTest(@RequestBody ItemsCustom itemsCustom)throws Exception{
		return itemsCustom; 
	}
	
	@RequestMapping("/responseJson")
	public @ResponseBody ItemsCustom jsonTest1(ItemsCustom itemsCustom)throws Exception{
		return itemsCustom;
	}
}
