SET FOREIGN_KEY_CHECKS=0;
DROP DATABASE IF EXISTS `appolodorus`;
CREATE DATABASE `appolodorus`;
USE `appolodorus`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `cargo` (
  `idcargo` int(11) NOT NULL AUTO_INCREMENT,
  `nomecargo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idcargo`),
  UNIQUE KEY `idcargo_UNIQUE` (`idcargo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `departamento` (
  `iddepartamento` int(11) NOT NULL AUTO_INCREMENT,
  `idbiblioteca` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  PRIMARY KEY (`iddepartamento`),
  UNIQUE KEY `iddepartamento_UNIQUE` (`iddepartamento`),
  KEY `FK_Departamento_Biblioteca_idx` (`idbiblioteca`),
  CONSTRAINT `FK_Departamento_Biblioteca` FOREIGN KEY (`idbiblioteca`) REFERENCES `biblioteca` (`idbiblioteca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `editora` (
  `ideditora` int(11) NOT NULL AUTO_INCREMENT,
  `nomeeditora` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ideditora`),
  UNIQUE KEY `ideditora_UNIQUE` (`ideditora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `emprestimo` (
  `idemprestimo` int(11) NOT NULL AUTO_INCREMENT,
  `leitor_idleitor` int(11) NOT NULL,
  `leitor_idusuario` int(11) NOT NULL,
  `funcionario_idfuncionario` int(11) NOT NULL,
  PRIMARY KEY (`idemprestimo`,`leitor_idleitor`,`leitor_idusuario`,`funcionario_idfuncionario`),
  UNIQUE KEY `idemprestimo_UNIQUE` (`idemprestimo`),
  KEY `fk_emprestimo_leitor1_idx` (`leitor_idleitor`,`leitor_idusuario`),
  KEY `fk_emprestimo_funcionario1_idx` (`funcionario_idfuncionario`),
  CONSTRAINT `fk_emprestimo_funcionario1` FOREIGN KEY (`funcionario_idfuncionario`) REFERENCES `funcionario` (`idfuncionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_emprestimo_leitor1` FOREIGN KEY (`leitor_idleitor`, `leitor_idusuario`) REFERENCES `leitor` (`idleitor`, `idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `funcionario` (
  `idfuncionario` int(11) NOT NULL AUTO_INCREMENT,
  `idcargo` int(11) NOT NULL,
  `iddepartamento` int(11) NOT NULL,
  `num_conselho` varchar(50) DEFAULT NULL,
  `ctps` varchar(10) DEFAULT NULL,
  `idusuario` int(11) NOT NULL,
  PRIMARY KEY (`idfuncionario`),
  UNIQUE KEY `idfuncionario_UNIQUE` (`idfuncionario`),
  KEY `FK_Funcionario_Departamento_idx` (`iddepartamento`),
  KEY `FK_Funcionario_Cargo_idx` (`idcargo`),
  KEY `FK_Funcionario_Usuario_idx` (`idusuario`),
  CONSTRAINT `FK_Funcionario_Cargo` FOREIGN KEY (`idcargo`) REFERENCES `cargo` (`idcargo`),
  CONSTRAINT `FK_Funcionario_Departamento` FOREIGN KEY (`iddepartamento`) REFERENCES `departamento` (`iddepartamento`),
  CONSTRAINT `FK_Funcionario_Usuario` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `leitor` (
  `idleitor` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `data_associacao` date NOT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`idleitor`,`idusuario`),
  UNIQUE KEY `idleitor_UNIQUE` (`idleitor`),
  KEY `FK_Leitor_Usuario_idx` (`idusuario`),
  CONSTRAINT `FK_Leitor_Usuario` FOREIGN KEY (`idusuario`) REFERENCES `usuario` (`idusuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `livro` (
  `idlivro` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` varchar(50) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `autor` char(100) DEFAULT NULL,
  `datapublicacao` date NOT NULL,
  `ideditora` int(11) NOT NULL,
  PRIMARY KEY (`idlivro`),
  UNIQUE KEY `idlivro_UNIQUE` (`idlivro`),
  KEY `FK_Livro_Editora_idx` (`ideditora`),
  CONSTRAINT `FK_Livro_Editora` FOREIGN KEY (`ideditora`) REFERENCES `editora` (`ideditora`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `livrocatalogado` (
  `idcatalogado` int(11) NOT NULL AUTO_INCREMENT,
  `datacatalogacao` date DEFAULT NULL,
  `idlivro` int(11) NOT NULL,
  `idfuncionario` int(11) NOT NULL,
  PRIMARY KEY (`idcatalogado`,`idlivro`,`idfuncionario`),
  UNIQUE KEY `idlivrocatalogado_UNIQUE` (`idcatalogado`),
  KEY `fk_livrocatalogado_livro1_idx` (`idlivro`),
  KEY `fk_livrocatalogado_funcionario1_idx` (`idfuncionario`),
  CONSTRAINT `FK_Livrocatalogado_Livro` FOREIGN KEY (`idlivro`) REFERENCES `livro` (`idlivro`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livrocatalogado_funcionario1` FOREIGN KEY (`idfuncionario`) REFERENCES `funcionario` (`idfuncionario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `livroemprestado` (
  `idlivroemprestado` int(11) NOT NULL AUTO_INCREMENT,
  `dataemprestimo` date NOT NULL,
  `datadevolucao` date NOT NULL,
  `diasatraso` date NOT NULL,
  `idcatalogado` int(11) NOT NULL,
  `emprestimo_idemprestimo` int(11) NOT NULL,
  `emprestimo_leitor_idleitor` int(11) NOT NULL,
  `emprestimo_leitor_idusuario` int(11) NOT NULL,
  `emprestimo_funcionario_idfuncionario` int(11) NOT NULL,
  PRIMARY KEY (`idlivroemprestado`,`idcatalogado`,`emprestimo_idemprestimo`,`emprestimo_leitor_idleitor`,`emprestimo_leitor_idusuario`,`emprestimo_funcionario_idfuncionario`),
  UNIQUE KEY `idlivroemprestado_UNIQUE` (`idlivroemprestado`),
  KEY `fk_livroemprestado_livrocatalogado1_idx` (`idcatalogado`),
  KEY `fk_livroemprestado_emprestimo1_idx` (`emprestimo_idemprestimo`,`emprestimo_leitor_idleitor`,`emprestimo_leitor_idusuario`,`emprestimo_funcionario_idfuncionario`),
  CONSTRAINT `fk_livroemprestado_emprestimo1` FOREIGN KEY (`emprestimo_idemprestimo`, `emprestimo_leitor_idleitor`, `emprestimo_leitor_idusuario`, `emprestimo_funcionario_idfuncionario`) REFERENCES `emprestimo` (`idemprestimo`, `leitor_idleitor`, `leitor_idusuario`, `funcionario_idfuncionario`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livroemprestado_livrocatalogado1` FOREIGN KEY (`idcatalogado`) REFERENCES `livrocatalogado` (`idcatalogado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `senha` varchar(50) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `endereco` varchar(100) DEFAULT NULL,
  `estado` char(2) DEFAULT NULL,
  `cep` varchar(8) DEFAULT NULL,
  `tipousuario` int(11) NOT NULL,
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `idusuario_UNIQUE` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
# Data for table `appolodorus`.`biblioteca`:
# Table biblioteca has no data.
# Data for table `appolodorus`.`cargo`:
# Table cargo has no data.
# Data for table `appolodorus`.`departamento`:
# Table departamento has no data.
# Data for table `appolodorus`.`editora`:
# Table editora has no data.
# Data for table `appolodorus`.`emprestimo`:
# Table emprestimo has no data.
# Data for table `appolodorus`.`funcionario`:
# Table funcionario has no data.
# Data for table `appolodorus`.`leitor`:
# Table leitor has no data.
# Data for table `appolodorus`.`livro`:
# Table livro has no data.
# Data for table `appolodorus`.`livrocatalogado`:
# Table livrocatalogado has no data.
# Data for table `appolodorus`.`livroemprestado`:
# Table livroemprestado has no data.
# Data for table `appolodorus`.`usuario`:
INSERT INTO `appolodorus`.`usuario` VALUES  (1, 'admin', 'admin', 'karlo mesquita rodrigues', '52625850272', 'karlomr@gmail.com', 'rua agostinho angelo trevisan', 'PR', '81560280', 1),  (2, 'leitor1', 'leitor1', 'Joao Silverio', '4567', 'joa0@yahoo.com.br', 'rua dos remedios', 'AM', '69000000', 2),  (3, 'leitor2', 'leitor2 ', 'Batista da silva', '1234', 'asdads', 'asdasd', 'AL', '888888', 2);
SET FOREIGN_KEY_CHECKS=1;
