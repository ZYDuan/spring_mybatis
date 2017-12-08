package com.zyd.ssm.service;

import java.util.List;

import com.zyd.ssm.pojo.Items;
import com.zyd.ssm.pojo.ItemsCustom;
import com.zyd.ssm.pojo.ItemsQueryVo;

/**
 * 商品管理service
 * @author zyd
 *
 */
public interface ItemsService {
	//商品查询列表
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)throws Exception;
	//根据id查询
	public ItemsCustom findItemsById(int id)throws Exception;
	//修改商品信息
	public void updateItems(Integer id,ItemsCustom itemsCustom)throws Exception;
	//删除商品信息
	public void deleteItems(Integer[] id)throws Exception;
	//批量修改商品信息
	public void updateItemsAll(List<ItemsCustom> itemsCustoms)throws Exception;
}
