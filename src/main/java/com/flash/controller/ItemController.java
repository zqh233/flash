package com.flash.controller;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.flash.Response.CommonReturn;
import com.flash.controller.viewObject.ItemVO;
import com.flash.service.ItemService;
import com.flash.service.model.ItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturn createItem(@RequestParam(name = "title") String title,
                                   @RequestParam(name = "description") String description,
                                   @RequestParam(name = "stock") Integer stock,
                                   @RequestParam(name = "price")BigDecimal price,
                                   @RequestParam(name = "imgUrl") String imgUrl) throws Exception {
        //创建商品model
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);

        ItemModel model = itemService.createItem(itemModel);

        return CommonReturn.create(ItemVO.convertFromModel(model));
    }
}
