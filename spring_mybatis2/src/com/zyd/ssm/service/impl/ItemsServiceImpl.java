package com.zyd.ssm.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyd.ssm.exception.CustomException;
import com.zyd.ssm.mapper.ItemsCustMapper;
import com.zyd.ssm.mapper.ItemsMapper;
import com.zyd.ssm.pojo.Items;
import com.zyd.ssm.pojo.ItemsCustom;
import com.zyd.ssm.pojo.ItemsQueryVo;
import com.zyd.ssm.service.ItemsService;


public class ItemsServiceImpl implements ItemsService {

	@Autowired
	private ItemsCustMapper itemsCustMapper;
	
	@Autowired
	private ItemsMapper itemsMapper;
	
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {
		
		return itemsCustMapper.findItemsList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsById(int id) throws Exception {
		Items items = itemsMapper.selectByPrimaryKey(id);
		if(items == null) {
			throw new CustomException("修改商品的信息不存在!");
		}
		//中间商品信息进行业务处理
		ItemsCustom itemsCustom = null;
		//拷贝items属性到itemscustom
		if(items != null) {
			itemsCustom = new ItemsCustom();
			BeanUtils.copyProperties(items,itemsCustom);
		}
		return itemsCustom ;
	}

	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) {
		//添加业务校验
		//id为空则抛出异常
		
		//updateByPrimaryKeyWithBLOBs根据id更新items表中的所有字段包括大文本类型
		//updateByPrimaryKeyWithBLOBs要求必须转入id
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}

	@Override
	public void deleteItems(Integer[] id) throws Exception {
		for(Integer itemId:id) {
			itemsMapper.deleteByPrimaryKey(itemId);
		}
	}

	@Override
	public void updateItemsAll(List<ItemsCustom> itemsCustoms) throws Exception {
		
		
	}

}
