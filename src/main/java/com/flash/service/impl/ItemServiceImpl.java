package com.flash.service.impl;

import com.flash.dao.ItemDOMapper;
import com.flash.dao.ItemStockDOMapper;
import com.flash.dataObject.ItemDO;
import com.flash.dataObject.ItemStockDO;
import com.flash.error.BusinessExecption;
import com.flash.error.EmBusinessError;
import com.flash.service.ItemService;
import com.flash.service.model.ItemModel;
import com.flash.validator.ValidationResult;
import com.flash.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws Exception {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if(result.getHasErrors()) {
            throw new BusinessExecption(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        //转换model->dataObject
        ItemDO itemDO = ItemDO.convertFromModel(itemModel);
        itemDOMapper.insertSelective(itemDO);

        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = ItemStockDO.convertItemStockFromModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        //返回数据
        return this.getItemById(itemModel.getId());
    }



    @Override
    public List<ItemModel> listItem() {
        return null;
    }

    @Override
    public ItemModel getItemById(Integer id) throws Exception{
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null) {
            throw new BusinessExecption(EmBusinessError.ITEM_NOT_EXIST);
        }
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        if(itemStockDO == null) {
            throw new BusinessExecption(EmBusinessError.ITEM_STOCK_NOT_EXIST);
        }
        return ItemModel.convertFromDataObject(itemDO, itemStockDO);
    }
}
