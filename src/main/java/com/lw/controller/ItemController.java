package com.lw.controller;

import com.lw.controller.viewObject.ItemVO;
import com.lw.error.BusinessException;
import com.lw.response.CommonReturnType;
import com.lw.service.ItemService;
import com.lw.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 商品视图控制器
 */
@Controller("item")
@RequestMapping("/item")
@CrossOrigin(
        allowCredentials = "true",
        allowedHeaders = "*"
)
public class ItemController extends BaseController{
    /**
     * 在企业级应用里, 我们所要达到的目的很简单, 就是尽可能让 Controller层简单, 让Service层复杂
     * 得把我们的逻辑, 尽可能聚合在Service内部, 用于实现它内部的 流转 处理
     */

    @Autowired
    private ItemService itemService;

    @RequestMapping(
            value = "/list",
            method = {RequestMethod.GET}
            )
    @ResponseBody
    //商品列表页面浏览
    public CommonReturnType listItem() {
        List<ItemModel> itemModelList = itemService.listItems();

        //使用 stream api 将list内的itemModel转化为ItemVO
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertItemVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }

    @RequestMapping(
            value = "/get",
            method = {RequestMethod.GET}
    )
    @ResponseBody
    //商品详情页浏览
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);

        ItemVO itemVO = this.convertItemVOFromModel(itemModel);

        return CommonReturnType.create(itemVO);
    }

    @RequestMapping("/create")
    @ResponseBody
    //创建商品
    public CommonReturnType createItem(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "price") BigDecimal price,
            @RequestParam(name = "stock") Integer stock,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "imgUrl") String imgUrl
    ) throws BusinessException {

        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertItemVOFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);
    }
    private ItemVO convertItemVOFromModel(ItemModel itemModel) {
        /**
         * 企业级应用里面, 许多时候 VO层 的定义, 和 M层 的定义, 是完全不一样的.
         * 而且许多都会用到聚合操作. --> 比如说, M层 到 DO层, 已经用到了一些 比如库存字段是通过DO层聚合出来的.
         * 对于 VO层, 我们为了一些前端交互逻辑上的方便, 很多时候把 VO 定义得 M层更大. --> 比如说, 为了前端的性能考虑, 我们会聚合上活动价格信息, 等等.
         * 因此, 对应的一些用户请求, 在VO层, 会变得非常非常复杂, 分层是必须的.
         */
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        //添加 秒杀 逻辑
        if (itemModel.getPromoModel() != null) {
            //有 正在进行 或 即将进行的 秒杀活动
            //ItemVO 则会 出现 static问题, 因为是根据类直接调用
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());

            // 注意是 DateTimeFormat 而不是 DateTimeFormatter
            // 由于 DateTime 序列化后, 有很多多余东西,故改为String 并用 .toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")) 格式化
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            itemVO.setPromoStatus(0);
        }

        return itemVO;
    }


}
