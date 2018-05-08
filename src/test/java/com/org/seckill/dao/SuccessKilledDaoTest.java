package com.org.seckill.dao;

import com.org.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

// Junit运行时候加载spring-ioc容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉Junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;


    @Test
    public void insertSuccessKilled() {
        Long id = 1001L;
        Long phone = 15271917678L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount:"+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        Long id = 1001L;
        Long phone = 15271917678L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}