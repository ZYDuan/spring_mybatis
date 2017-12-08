package com.zyd.ssm.mapper;

import com.zyd.ssm.pojo.Items;
import com.zyd.ssm.pojo.ItemsCustom;
import com.zyd.ssm.pojo.ItemsExample;
import com.zyd.ssm.pojo.ItemsQueryVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemsCustMapper {
   public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)throws Exception;
}