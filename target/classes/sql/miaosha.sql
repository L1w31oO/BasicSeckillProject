-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: miaosha
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `miaosha`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `miaosha` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `miaosha`;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `price` double NOT NULL DEFAULT '0',
  `description` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `sales` int NOT NULL DEFAULT '0',
  `img_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (4,'mac1',10099,'mac1',21,'https://picsum.photos/id/0/200/300');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_stock`
--

DROP TABLE IF EXISTS `item_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_stock`
--

LOCK TABLES `item_stock` WRITE;
/*!40000 ALTER TABLE `item_stock` DISABLE KEYS */;
INSERT INTO `item_stock` VALUES (4,91,4);
/*!40000 ALTER TABLE `item_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_info` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL DEFAULT '0',
  `item_price` double NOT NULL DEFAULT '0',
  `amount` int NOT NULL DEFAULT '0',
  `order_price` double NOT NULL DEFAULT '0',
  `promo_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` VALUES ('2021090700000000',10,4,10099,1,10099,0),('2021090700000100',10,4,10099,1,10099,0),('2021090700000200',10,4,10099,1,10099,0),('2021090700000300',10,4,5999,1,5999,1),('2021090800000500',10,4,5999,1,5999,1),('2021090900000600',10,4,10099,1,10099,0),('2021090900000700',10,4,5999,1,5999,1),('2021090900000800',12,4,5999,1,5999,1);
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promo`
--

DROP TABLE IF EXISTS `promo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `item_id` int NOT NULL,
  `promo_item_price` double NOT NULL,
  PRIMARY KEY (`id`,`promo_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promo`
--

LOCK TABLES `promo` WRITE;
/*!40000 ALTER TABLE `promo` DISABLE KEYS */;
INSERT INTO `promo` VALUES (1,'mac1抢购活动！','2021-09-07 13:33:00','2021-09-30 13:34:52',4,5999);
/*!40000 ALTER TABLE `promo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_info`
--

DROP TABLE IF EXISTS `sequence_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sequence_info` (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `current_value` int NOT NULL DEFAULT '0',
  `step` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_info`
--

LOCK TABLES `sequence_info` WRITE;
/*!40000 ALTER TABLE `sequence_info` DISABLE KEYS */;
INSERT INTO `sequence_info` VALUES ('order_info',9,1);
/*!40000 ALTER TABLE `sequence_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` tinyint NOT NULL COMMENT '// 1表示男性，2表示女性',
  `age` int NOT NULL,
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `register_mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '// byphone, bywechat, byalipay',
  `third_part_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `telephone_unique_index` (`telephone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'第一个用户',1,30,'12345678910','byphone',''),(10,'哈哈',1,1,'19800336693','byphone',''),(12,'hehe',1,1,'12345678911','byphone','');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_password`
--

DROP TABLE IF EXISTS `user_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_password` (
  `id` int NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_password`
--

LOCK TABLES `user_password` WRITE;
/*!40000 ALTER TABLE `user_password` DISABLE KEYS */;
INSERT INTO `user_password` VALUES (1,'aabbcc',1),(6,'xMpCOKC5I4INzFCab3WEmw==',10),(7,'xMpCOKC5I4INzFCab3WEmw==',12);
/*!40000 ALTER TABLE `user_password` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-16 11:04:31
