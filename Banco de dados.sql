/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 5.7.30 : Database - twitter_bot
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`twitter_bot` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `twitter_bot`;

/*Table structure for table `anime` */

DROP TABLE IF EXISTS `anime`;

CREATE TABLE `anime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Frase` longtext COLLATE utf8mb4_unicode_ci,
  `Ingles` longtext COLLATE utf8mb4_unicode_ci,
  `Traducao` longtext COLLATE utf8mb4_unicode_ci,
  `Anime` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Imagem` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Tag` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Postado` int(11) DEFAULT '0',
  `Ativo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `anime` */

/*Table structure for table `musica` */

DROP TABLE IF EXISTS `musica`;

CREATE TABLE `musica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Frase` longtext COLLATE utf8mb4_unicode_ci,
  `Traducao` longtext COLLATE utf8mb4_unicode_ci,
  `Musica` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Cantor` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Imagem` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Tag` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Postado` int(11) DEFAULT '0',
  `Ativo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `musica` */

/*Table structure for table `vocabulario` */

DROP TABLE IF EXISTS `vocabulario`;

CREATE TABLE `vocabulario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Vocabulario` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Frase` longtext COLLATE utf8mb4_unicode_ci,
  `Traducao` longtext COLLATE utf8mb4_unicode_ci,
  `Kanjis` longtext COLLATE utf8mb4_unicode_ci,
  `Nivel` int(11) DEFAULT NULL,
  `JLPT` int(11) DEFAULT NULL,
  `Observacao` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Postado` int(11) DEFAULT '0',
  `Ativo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `vocabulario` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
