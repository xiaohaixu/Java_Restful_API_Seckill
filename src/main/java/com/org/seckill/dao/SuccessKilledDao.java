package com.org.seckill.dao;

import com.org.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {

    /**
     * 秒杀成功，插入购买明细，可过滤重复
     *
     * @param seckillId 秒杀商品id
     * @param userPhone 用户电话号码
     * @return 插入的行数，0 表示插入失败
     */
    int insertSuccessKilled(@Param("seckillId") Long seckillId, @Param("userPhone") Long userPhone);

    /**
     * 根据id查询SuccessKilled 并携带秒杀产品实体
     *
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") Long seckillId, @Param("userPhone") Long userPhone);

}
