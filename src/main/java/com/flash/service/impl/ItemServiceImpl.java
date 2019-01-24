package com.flash.service.impl;

import com.flash.service.ItemService;
import com.flash.service.model.ItemModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public ItemModel createItem(ItemModel itemModel) {
        return null;
    }

    @Override
    public List<ItemModel> listItem() {
        return null;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        return null;
    }
}
