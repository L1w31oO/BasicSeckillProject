package com.lw.dao;

import com.lw.dataObject.PromoDao;
import org.springframework.stereotype.Repository;
/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 秒杀活动mapper
 */
@Repository
public interface PromoDaoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PromoDao record);

    int insertSelective(PromoDao record);

    PromoDao selectByItemId(Integer ItemId);

    PromoDao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PromoDao record);

    int updateByPrimaryKey(PromoDao record);
}