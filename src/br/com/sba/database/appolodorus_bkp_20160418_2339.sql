CREATE DATABASE  IF NOT EXISTS `appolodorus` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `appolodorus`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win32 (AMD64)
--
-- Host: localhost    Database: appolodorus
-- ------------------------------------------------------
-- Server version	5.6.27-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `biblioteca`
--

DROP TABLE IF EXISTS `biblioteca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biblioteca` (
  `idbiblioteca` int(11) NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(14) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `rua` varchar(100) DEFAULT NULL,
  `razaoSocial` varchar(100) DEFAULT NULL,
  `cep` varchar(8) DEFAULT NULL,
  `estado` char(2) DEFAULT NULL,
  PRIMARY KEY (`idbiblioteca`),
  UNIQUE KEY `idBiblioteca_UNIQUE` (`idbiblioteca`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biblioteca`
--

LOCK TABLES `biblioteca` WRITE;
/*!40000 ALTER TABLE `biblioteca` DISABLE KEYS */;
INSERT INTO `biblioteca` VALUES (1,'99999999','Biblioteca central','rua dos farrapos','Biblioteca central publica','81560280','PR');
/*!40000 ALTER TABLE `biblioteca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cargo`
--

DROP TABLE IF EXISTS `cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cargo` (
  `idcargo` int(11) NOT NULL AUTO_INCREMENT,
  `nomecargo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idcargo`),
  UNIQUE KEY `idcargo_UNIQUE` (`idcargo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cargo`
--

LOCK TABLES `cargo` WRITE;
/*!40000 ALTER TABLE `cargo` DISABLE KEYS */;
/*!40000 ALTER TABLE `cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departamento`
--

DROP TABLE IF EXISTS `departamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `departamento` (
  `iddepartamento` int(11) NOT NULL AUTO_INCREMENT,
  `idbiblioteca` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`iddepartamento`),
  UNIQUE KEY `iddepartamento_UNIQUE` (`iddepartamento`),
  KEY `FK_Departamento_Biblioteca_idx` (`idbiblioteca`),
  CONSTRAINT `FK_Departamento_Biblioteca` FOREIGN KEY (`idbiblioteca`) REFERENCES `biblioteca` (`idbiblioteca`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departamento`
--

LOCK TABLES `departamento` WRITE;
/*!40000 ALTER TABLE `departamento` DISABLE KEYS */;
INSERT INTO `departamento` VALUES (1,1,'Administracao'),(2,1,'Empréstimo'),(3,1,'Catalogação');
/*!40000 ALTER TABLE `departamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `editora`
--

DROP TABLE IF EXISTS `editora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `editora` (
  `ideditora` int(11) NOT NULL AUTO_INCREMENT,
  `nomeeditora` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ideditora`),
  UNIQUE KEY `ideditora_UNIQUE` (`ideditora`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editora`
--

LOCK TABLES `editora` WRITE;
/*!40000 ALTER TABLE `editora` DISABLE KEYS */;
INSERT INTO `editora` VALUES (1,'Atlas'),(2,'Novatec'),(3,'Cesumar');
/*!40000 ALTER TABLE `editora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emprestimo`
--

DROP TABLE IF EXISTS `emprestimo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emprestimo` (
  `idemprestimo` int(11) NOT NULL AUTO_INCREMENT,
  `idleitor` int(11) NOT NULL,
  `idfuncionario` int(11) NOT NULL,
  PRIMARY KEY (`idemprestimo`,`idleitor`,`idfuncionario`),
  UNIQUE KEY `idemprestimo_UNIQUE` (`idemprestimo`),
  KEY `fk_emprestimo_funcionario1_idx` (`idfuncionario`),
  KEY `fk_emprestimo_leitor1_idx` (`idleitor`),
  CONSTRAINT `fk_emprestimo_funcionario1` FOREIGN KEY (`idfuncionario`) REFERENCES `funcionario` (`idfuncionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_emprestimo_leitor1` FOREIGN KEY (`idleitor`) REFERENCES `leitor` (`idleitor`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emprestimo`
--

LOCK TABLES `emprestimo` WRITE;
/*!40000 ALTER TABLE `emprestimo` DISABLE KEYS */;
/*!40000 ALTER TABLE `emprestimo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funcionario` (
  `idfuncionario` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `idcargo` int(11) NOT NULL,
  `iddepartamento` int(11) NOT NULL,
  `num_conselho` varchar(50) DEFAULT NULL,
  `ctps` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idfuncionario`),
  UNIQUE KEY `idfuncionario_UNIQUE` (`idfuncionario`),
  KEY `FK_Funcionario_Departamento_idx` (`iddepartamento`),
  KEY `FK_Funcionario_Cargo_idx` (`idcargo`),
  KEY `FK_Funcionario_Usuario_idx` (`idusuario`),
  CONSTRAINT `FK_Funcionario_Cargo` FOREIGN KEY (`idcargo`) REFERENCES `cargo` (`idcargo`),
  CONSTRAINT `FK_Funcionario_Departamento` FOREIGN KEY (`iddepartamento`) REFERENCES `departamento` (`iddepartamento`),
  CONSTRAINT `FK_Funcionario_Usuario` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leitor`
--

DROP TABLE IF EXISTS `leitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leitor` (
  `idleitor` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `data_associacao` date NOT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`idleitor`),
  UNIQUE KEY `idleitor_UNIQUE` (`idleitor`),
  KEY `FK_Leitor_Usuario_idx` (`idusuario`),
  CONSTRAINT `FK_Leitor_Usuario` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leitor`
--

LOCK TABLES `leitor` WRITE;
/*!40000 ALTER TABLE `leitor` DISABLE KEYS */;
INSERT INTO `leitor` VALUES (1,1,'2016-04-05','1');
/*!40000 ALTER TABLE `leitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livro`
--

DROP TABLE IF EXISTS `livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `livro` (
  `idlivro` int(11) NOT NULL AUTO_INCREMENT,
  `ideditora` int(11) NOT NULL,
  `isbn` varchar(50) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `autor` char(100) DEFAULT NULL,
  `datapublicacao` date NOT NULL,
  PRIMARY KEY (`idlivro`),
  UNIQUE KEY `idlivro_UNIQUE` (`idlivro`),
  KEY `FK_Livro_Editora_idx` (`ideditora`),
  CONSTRAINT `FK_Livro_Editora` FOREIGN KEY (`ideditora`) REFERENCES `editora` (`ideditora`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livro`
--

LOCK TABLES `livro` WRITE;
/*!40000 ALTER TABLE `livro` DISABLE KEYS */;
/*!40000 ALTER TABLE `livro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livro_emprestimo`
--

DROP TABLE IF EXISTS `livro_emprestimo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `livro_emprestimo` (
  `idemprestimo` int(11) NOT NULL,
  `seqlivroemprestado` int(11) NOT NULL AUTO_INCREMENT,
  `idcatalogado` int(11) NOT NULL,
  `dataemprestimo` date NOT NULL,
  `datadevolucao` date NOT NULL,
  PRIMARY KEY (`idemprestimo`,`seqlivroemprestado`),
  UNIQUE KEY `idlivroemprestado_UNIQUE` (`seqlivroemprestado`),
  UNIQUE KEY `idemprestimo_UNIQUE` (`idemprestimo`),
  KEY `fk_livro_emprestimo_livrocatalogado1_idx` (`idcatalogado`),
  KEY `fk_livroemprestado_emprestimo1_idx` (`idemprestimo`),
  CONSTRAINT `fk_livro_emprestimo_livrocatalogado1` FOREIGN KEY (`idcatalogado`) REFERENCES `livrocatalogado` (`idcatalogado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livroemprestado_emprestimo1` FOREIGN KEY (`idemprestimo`) REFERENCES `emprestimo` (`idemprestimo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livro_emprestimo`
--

LOCK TABLES `livro_emprestimo` WRITE;
/*!40000 ALTER TABLE `livro_emprestimo` DISABLE KEYS */;
/*!40000 ALTER TABLE `livro_emprestimo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livrocatalogado`
--

DROP TABLE IF EXISTS `livrocatalogado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `livrocatalogado` (
  `idcatalogado` int(11) NOT NULL,
  `idlivro` int(11) NOT NULL,
  `idfuncionario` int(11) NOT NULL,
  `datacatalogacao` date DEFAULT NULL,
  PRIMARY KEY (`idcatalogado`),
  KEY `fk_livrocatalogado_livro1_idx` (`idlivro`),
  KEY `fk_livrocatalogado_funcionario1_idx` (`idfuncionario`),
  CONSTRAINT `fk_livrocatalogado_funcionario1` FOREIGN KEY (`idfuncionario`) REFERENCES `funcionario` (`idfuncionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livrocatalogado_livro1` FOREIGN KEY (`idlivro`) REFERENCES `livro` (`idlivro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livrocatalogado`
--

LOCK TABLES `livrocatalogado` WRITE;
/*!40000 ALTER TABLE `livrocatalogado` DISABLE KEYS */;
/*!40000 ALTER TABLE `livrocatalogado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `senha` varchar(50) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `cpf` varchar(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `endereco` varchar(100) DEFAULT NULL,
  `cep` varchar(8) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `idusuario_UNIQUE` (`idusuario`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','21232F297A57A5A743894A0E4A801FC3','karlo mesquita rodrigues','52625850272','karlomr@gmail.com','rua agostinho angelo trevisan','999999',NULL),(2,'leitor1','leitor1','Joao Silverio','4567','joa0@yahoo.com.br','rua dos remedios','69000000',NULL),(6,'karlomr','2B591A9DBA3AC6DA741825A415492B93','karlo','55555555555','karlomr@gmail.com','karlo','81560280',NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'appolodorus'
--

--
-- Dumping routines for database 'appolodorus'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-18 23:39:16
