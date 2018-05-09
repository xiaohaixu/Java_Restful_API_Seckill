package com.org.seckill.service;

import com.org.seckill.dto.Exposer;
import com.org.seckill.dto.SeckillExecution;
import com.org.seckill.entity.Seckill;
import com.org.seckill.exception.RepeatKillException;
import com.org.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final org.slf4j.Logger logger = (org.slf4j.Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void testSeckillLogic() {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            logger.info("exposer={}", exposer);
            long phone = 13502452232L;
            String md5 = exposer.getMd5();
            try{
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }
        }else{
            // 秒杀未开启
            logger.warn("Exposer={}", exposer);
        }

    }

    //   @Test
//    public void executeSeckill() {
//        long id = 1000;
//        long phone = 13502452233L;
//        String md5 = "21b67a70f67eee13aed73e6f5d2a19c9";
//        try{
//            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
//            logger.info("result={}", execution);
//        }catch (RepeatKillException e){
//            logger.error(e.getMessage());
//        }catch (SeckillCloseException e){
//            logger.error(e.getMessage());
//        }
//
//    }
}