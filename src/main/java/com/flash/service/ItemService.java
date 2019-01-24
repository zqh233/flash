package com.flash.service;

import com.flash.service.model.ItemModel;

import java.util.List;

public interface ItemService {

    /**
     *    创建商品
     */
    ItemModel createItem(ItemModel itemModel);

    /**
     *   商品列表浏览
     */
    List<ItemModel> listItem();

    /**
     *    商品详情
     */
    ItemModel getItemById(Integer id);
}
