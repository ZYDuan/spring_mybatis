package com.zyd.ssm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import com.zyd.ssm.controller.validation.ValidGroup1;
import com.zyd.ssm.exception.CustomException;
import com.zyd.ssm.pojo.Items;
import com.zyd.ssm.pojo.ItemsCustom;
import com.zyd.ssm.pojo.ItemsQueryVo;
import com.zyd.ssm.service.ItemsService;
import com.zyd.ssm.service.impl.ItemsServiceImpl;

@Controller
@RequestMapping("/items")
public class ItemsController {

	@Autowired
	private ItemsService itemsService;
	
	// 商品分类
	// itemtypes表示最终将方法返回值放在request中的key
	@ModelAttribute("itemtypes")
	public Map<String, String> getItemTypes() {

		Map<String, String> itemTypes = new HashMap<String, String>();
		itemTypes.put("101", "饮料");
		itemTypes.put("102", "食品");

		return itemTypes;
	}
	
	//商品查询
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest re,ItemsQueryVo itemsQueryVo)throws Exception
	{
		System.out.println(re.getParameter("id"));
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("itemsList", itemsList);
		
		modelAndView.setViewName("items/itemsList");
		return modelAndView;
		
	}
	
	
	@RequestMapping("/itemsView/{id}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id)throws Exception{
		//调用service查询商品信息
				ItemsCustom itemsCustom = itemsService.findItemsById(id);
				
				return itemsCustom;
	}
	//商品修改界面显示
	//限制http请求
//	@RequestMapping(value = "/editItems",method = {RequestMethod.GET,RequestMethod.POST})
//	public ModelAndView editItems()throws Exception{
//		
//		ItemsCustom itemsCustom = itemsService.findItemsById(1);
//		
//		ModelAndView modelAndView = new ModelAndView();
//		
//		modelAndView.addObject("itemsCustom", itemsCustom);
//		modelAndView.setViewName("items/editItems");
//		return modelAndView;
//	}

	// @RequestParam里边指定request传入参数名称和形参进行绑定。
	// 通过required属性指定参数是否必须要传入
	// 通过defaultValue可以设置默认值，如果id参数没有传入，将默认值和形参绑定。
//	修改商品界面
	@RequestMapping(value = "/editItems",method = {RequestMethod.GET,RequestMethod.POST})
	public String editItems(Model model,@RequestParam(value="id",required=true) Integer items_id)throws Exception{
		
		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
		
//		if(itemsCustom == null) {
//			throw new CustomException("选择的用户不存在！");
//		}
		ModelAndView modelAndView = new ModelAndView();
		
		model.addAttribute("items", itemsCustom);
		return "items/editItems";
	}
	
	//商品的提交
//	@RequestMapping("/editItemsSubmit")
//	public ModelAndView editItemsSubmit()throws Exception{
//		ModelAndView modelAndView = new ModelAndView();
//		
//		modelAndView.setViewName("success");
//		return modelAndView;
//	}

	// 修改商品界面提交
	// 在需要校验的pojo前边添加@Validated，在需要校验的pojo后边添加BindingResult
	// bindingResult接收校验出错信息
	// 注意：@Validated和BindingResult bindingResult是配对出现，并且形参顺序是固定的（一前一后）。
	// value={ValidGroup1.class}指定使用ValidGroup1分组的 校验
	// @ModelAttribute可以指定pojo回显到页面在request中的key
	@RequestMapping("/editItemsSubmit")
	public String editItemsSubmit(Model model, HttpServletRequest request, Integer id,
			@ModelAttribute("items") @Validated(value = { ValidGroup1.class }) ItemsCustom itemsCustom,
			BindingResult bindingResult,
			MultipartFile items_pic) throws Exception {

		if(bindingResult.hasErrors()) {
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for(ObjectError objectError:allErrors) {
				System.out.println(objectError.getDefaultMessage());
			}
			model.addAttribute("allErrors", allErrors);
		return "items/editItems";
		}
		

		String originalFilename= items_pic.getOriginalFilename();
		
		if(items_pic != null && originalFilename != null && originalFilename.length() > 0) {
			String pic_path = "/Users/zyd/pic/";
			
			String newFilename=UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
			File newFile=new File(pic_path+newFilename);
			items_pic.transferTo(newFile);
			itemsCustom.setPic(newFilename);
		}
		itemsService.updateItems(id, itemsCustom);
		
//		return "redirect:queryItems.action";
		return "forward:queryItems.action";
	}
	
//	批量删除选中商品
	@RequestMapping("/deleteItems")
	public String deleteItems(Integer[] items_id)throws Exception{
		itemsService.deleteItems(items_id);
		return "success";
	}
	
//	批量修改商品界面
	@RequestMapping("/editItemsQuery")
	public ModelAndView queryItemsQuery(HttpServletRequest re,ItemsQueryVo itemsQueryVo)throws Exception
	{
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("itemsList", itemsList);
		
		modelAndView.setViewName("items/editItemsQuery");
		return modelAndView;
		
	}
	
	//批量修改商品提交
	@RequestMapping("/editItemsAllSubmit")
	public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo)throws Exception{
		List<ItemsCustom> itemsList = itemsQueryVo.getItemsList();
		return "success";
	}
}
