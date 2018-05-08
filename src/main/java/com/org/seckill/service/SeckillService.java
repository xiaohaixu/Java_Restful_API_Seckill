package com.org.seckill.service;

import com.org.seckill.dto.Exposer;
import com.org.seckill.dto.SeckillExecution;
import com.org.seckill.entity.Seckill;
import com.org.seckill.exception.RepeatKillException;
import com.org.seckill.exception.SeckillCloseException;
import com.org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Design interface from the perspective of users
 * 方法定义粒度， 参数， 返回类型（return类型友好/异常）
 */
public interface SeckillService {
    /**
     * Query all seckill records
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * Query seckill by id
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException;

}
