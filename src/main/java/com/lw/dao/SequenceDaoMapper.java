package com.lw.dao;

import com.lw.dataObject.SequenceDao;
import org.springframework.stereotype.Repository;
/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 记录mapper
 */
@Repository
public interface SequenceDaoMapper {

    int deleteByPrimaryKey(String name);

    int insert(SequenceDao record);

    int insertSelective(SequenceDao record);

    SequenceDao selectByPrimaryKey(String name);

    SequenceDao getSequenceByName(String name);

    int updateByPrimaryKeySelective(SequenceDao record);

    int updateByPrimaryKey(SequenceDao record);
}