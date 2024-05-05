-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: TiendaShiny
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `TiendaShiny`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `TiendaShiny` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `TiendaShiny`;

--
-- Table structure for table `Cliente`
--

DROP TABLE IF EXISTS `Cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cliente` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `NombreCliente` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `ApellidoP` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `ApellidoM` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `FechaCreacion` datetime NOT NULL,
  `FechaNacimiento` date NOT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `idCliente_UNIQUE` (`idCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=9964 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cliente`
--

LOCK TABLES `Cliente` WRITE;
/*!40000 ALTER TABLE `Cliente` DISABLE KEYS */;
INSERT INTO `Cliente` VALUES (1,'cliente A','apellido paterno A','apellido materno A','2011-11-11 00:00:00','2011-11-11'),(2,'cliente B','apellido paterno B','apellido materno B','2011-11-12 00:00:00','2011-11-12'),(3,'cliente C','apellido paterno C','apellido materno C','2011-11-13 00:00:00','2011-11-13');
/*!40000 ALTER TABLE `Cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Empleado`
--

DROP TABLE IF EXISTS `Empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Empleado` (
  `idEmpleado` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `ApellidoPaterno` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `ApellidoMaterno` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `Correo` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `FechaInicio` date NOT NULL,
  PRIMARY KEY (`idEmpleado`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Empleado`
--

LOCK TABLES `Empleado` WRITE;
/*!40000 ALTER TABLE `Empleado` DISABLE KEYS */;
INSERT INTO `Empleado` VALUES (1,'empleado A','apellido paterno A','apellido materno A','a@a.com','2011-11-11'),(2,'empleado B','apellido paterno B','apellido materno B','b@b.com','2011-11-12'),(3,'empleado C','apellido paterno C','apellido materno C','c@c.com','2011-11-13');
/*!40000 ALTER TABLE `Empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pedido`
--

DROP TABLE IF EXISTS `Pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Pedido` (
  `idPedido` int NOT NULL AUTO_INCREMENT,
  `Cantidad` int NOT NULL,
  `FechaCreacion` datetime NOT NULL,
  `Observaciones` varchar(45) COLLATE utf8mb3_bin DEFAULT NULL,
  `Entregado` tinyint NOT NULL,
  `TotalPago` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idPedido`),
  UNIQUE KEY `idPedido_UNIQUE` (`idPedido`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pedido`
--

LOCK TABLES `Pedido` WRITE;
/*!40000 ALTER TABLE `Pedido` DISABLE KEYS */;
INSERT INTO `Pedido` VALUES (1,1,'2011-11-11 00:00:00','observacion A',1,111.00),(2,2,'2011-11-12 00:00:00','observacion B',0,222.00),(3,3,'2011-11-13 00:00:00','observacion C',1,333.00);
/*!40000 ALTER TABLE `Pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Producto`
--

DROP TABLE IF EXISTS `Producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Producto` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `Stock` int NOT NULL,
  `Precio` decimal(10,2) NOT NULL,
  `Descripcion` text COLLATE utf8mb3_bin,
  `FechaCreacion` datetime DEFAULT NULL,
  PRIMARY KEY (`idProducto`),
  UNIQUE KEY `idStore_UNIQUE` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Producto`
--

LOCK TABLES `Producto` WRITE;
/*!40000 ALTER TABLE `Producto` DISABLE KEYS */;
INSERT INTO `Producto` VALUES (1,'producto A',1,100.00,'descripcion A','2011-11-11 00:00:00'),(2,'producto B',2,200.00,'descripcion B','2011-11-12 00:00:00'),(3,'producto C',3,300.00,'descripcion C','2011-11-13 00:00:00');
/*!40000 ALTER TABLE `Producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Proveedor`
--

DROP TABLE IF EXISTS `Proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Proveedor` (
  `idProveedor` int NOT NULL AUTO_INCREMENT,
  `NombreProveedor` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `Telefono` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `FechaCreacion` date NOT NULL,
  `Correo` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `Estatus` enum('Activo','Inactivo') COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`idProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Proveedor`
--

LOCK TABLES `Proveedor` WRITE;
/*!40000 ALTER TABLE `Proveedor` DISABLE KEYS */;
INSERT INTO `Proveedor` VALUES (1,'proveedor A','5513780801','2011-11-11','a@a.com','Activo'),(2,'proveedor B','5513780802','2011-11-12','b@b.com','Inactivo'),(3,'proveedor C','5513780803','2011-11-13','c@c.com','Activo');
/*!40000 ALTER TABLE `Proveedor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-05 22:22:30
