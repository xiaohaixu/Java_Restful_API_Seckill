# 数据库初始化脚本
# 创建数据库
CREATE DATABASE seckill;
# 使用数据库
use seckill;
# 创建秒杀库存表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` VARCHAR(120) NOT NULL COMMENT '商品名称',
`number` INT(11) NOT NULL COMMENT '商品数量',
`start_time` TIMESTAMP NOT NULL default 0 COMMENT '秒杀开始时间',
`end_time` TIMESTAMP NOT NULL default 0 COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

# 初始化数据
INSERT INTO seckill(name, number, start_time, end_time)
    VALUES
      ('500元秒杀iphone6s', 100, '2018-05-01 00:00:00', '2018-05-02 00:00:00'),
      ('800元秒杀ipad4', 200, '2018-05-01 00:00:00', '2018-05-02 00:00:00'),
      ('1000元秒杀HUAWEI MATE10', 300, '2018-05-01 00:00:00', '2018-05-02 00:00:00'),
      ('2000元秒杀HUAWEI P20', 400, '2018-05-01 00:00:00', '2018-05-02 00:00:00');

# 秒杀成功明细表
# 用户登录认证相关的信息
CREATE TABLE success_killed(
  `seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
  `user_phone` bigint NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标识：-1：无效 0：成功 1：已付款 2： 已发货',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#   同一个用户只能对同一个商品进行一次秒杀
  PRIMARY KEY (seckill_id, user_phone),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

# 连接数据库控制台
mysql -uroot -p

# 锻炼能力手写DDL
# 上线新版本