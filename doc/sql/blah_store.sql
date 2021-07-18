-- MySQL dump 10.13  Distrib 5.7.33, for Linux (x86_64)
--
-- Host: localhost    Database: blah_store
-- ------------------------------------------------------
-- Server version	5.7.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `created_at` datetime                                DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime                                DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods`
    DISABLE KEYS */;
INSERT INTO `goods`
VALUES (1, 'aa', '2021-07-07 22:19:04', NULL),
       (2, 'bb', '2021-07-07 22:19:09', NULL),
       (3, 'cc', '2021-07-07 22:19:14', NULL);
/*!40000 ALTER TABLE `goods`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seckill_goods`
--

DROP TABLE IF EXISTS `seckill_goods`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seckill_goods`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `goods_id`   bigint(20)     DEFAULT NULL COMMENT '商品id',
    `stock`      int(11)        DEFAULT NULL COMMENT '库存',
    `price`      decimal(10, 2) DEFAULT NULL COMMENT '价格',
    `start_time` datetime       DEFAULT NULL,
    `end_time`   datetime       DEFAULT NULL,
    `created_at` datetime       DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime       DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seckill_goods`
--

LOCK TABLES `seckill_goods` WRITE;
/*!40000 ALTER TABLE `seckill_goods`
    DISABLE KEYS */;
INSERT INTO `seckill_goods`
VALUES (1, 1, 1000, 1.01, '2021-07-07 22:29:34', '2021-07-09 22:29:52', '2021-07-07 22:28:57',
        '2021-07-07 22:29:58'),
       (2, 2, 1000, 1.01, '2021-07-07 22:29:45', '2021-07-09 22:29:59', '2021-07-07 22:28:59',
        '2021-07-07 22:30:01'),
       (3, 3, 1000, 1.01, '2021-07-07 22:29:48', '2021-07-09 22:30:02', '2021-07-07 22:29:01',
        '2021-07-07 22:30:10');
/*!40000 ALTER TABLE `seckill_goods`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_order`
--

DROP TABLE IF EXISTS `shop_order`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop_order`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `goods_id`    bigint(20) NOT NULL COMMENT '商品id',
    `user_id`     bigint(20) NOT NULL,
    `delete_time` datetime       DEFAULT NULL,
    `create_time` datetime       DEFAULT NULL,
    `pay_time`    datetime       DEFAULT NULL,
    `count`       int(11)        DEFAULT NULL,
    `price`       decimal(10, 2) DEFAULT NULL,
    `created_at`  datetime       DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  datetime       DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted`     tinyint(1)     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_order`
--

LOCK TABLES `shop_order` WRITE;
/*!40000 ALTER TABLE `shop_order`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `shop_order`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2021-07-18 14:47:10
