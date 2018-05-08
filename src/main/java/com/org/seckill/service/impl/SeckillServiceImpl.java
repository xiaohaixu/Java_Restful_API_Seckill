package com.org.seckill.service.impl;

import com.org.seckill.dao.SeckillDao;
import com.org.seckill.dao.SuccessKilledDao;
import com.org.seckill.dto.Exposer;
import com.org.seckill.dto.SeckillExecution;
import com.org.seckill.entity.Seckill;
import com.org.seckill.entity.SuccessKilled;
import com.org.seckill.enums.SeckillStateEnum;
import com.org.seckill.exception.RepeatKillException;
import com.org.seckill.exception.SeckillCloseException;
import com.org.seckill.exception.SeckillException;
import com.org.seckill.service.SeckillService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SeckillServiceImpl implements SeckillService{

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    // 注入Service依赖
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    // md5盐值字符串，用于混淆md5
    private final String slat = "dqwqjiqwi`9`0-23042';;;;po[[]";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if(seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        // 系统当前时间
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // 转化特定字符串的过程，不可逆
        String md5 = null; // TODO
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || md5.equals(getMD5(seckillId))) {
            throw new SeckillException("Seckill data rewrite");
        }
        // 执行秒杀逻辑：减库存 + 记录购买行为
        Date nowTime = new Date();

        try{
            // 减库存
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                // 没有更新到记录，秒杀结束
                throw new SeckillCloseException("Seckill has closed");
            } else {
                // 记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                // 唯一：seckillId, userPhone
                if (insertCount <= 0){
                    // 重复秒杀
                    throw new RepeatKillException("Seckill repeated");
                } else {
                    // 秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1){
            throw e1;
        } catch (RepeatKillException e2){
            throw e2;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            // 所有编译期异常转化为运行期异常
            throw new SeckillException("Seckill inner error: " + e.getMessage());
        }
    }
}