package com.lw.service;

import com.lw.error.BusinessException;
import com.lw.service.model.OrderModel;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 订单模块接口
 */

/**
 * 下单方式:
 * 方式1: 通过前端url上传过来秒杀活动id, 然后下单接口内校验对应id是否属于对应商品, 且 活动已开始
 * - 扩展性: 同一商品, 可能在不同 入口 有不同的 秒杀活动, 必须依赖前端判断是哪个秒杀活动
 *
 * 方式2: 直接在下单接口内 判断对应的商品是否存在秒杀活动, 若存在进行中的秒杀, 则以秒杀价格下单
 * - 如果普通下单, 也要校验的话, 对普通下单, 或者说 对日常性能 是很伤的.
 *
 * 方案: 采用方案1, 前端传送过来则校验, 否则认为是普通下单
 */
public interface OrderService {
    // OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
    OrderModel createOrder(Integer userId, Integer itemId, Integer amount, Integer promoId) throws BusinessException;
}
