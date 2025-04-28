-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-11-2024 a las 00:00:37
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: bdClinica
--
DROP DATABASE IF EXISTS bdClinica;
CREATE DATABASE bdClinica DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE bdClinica;

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `actCita` (`xIdCita` VARCHAR(4), `xFK_Paciente` VARCHAR(4), `xFK_Especialidad` VARCHAR(4), `xFK_Medico` VARCHAR(4), `xFecha` DATE, `xHora` TIME(6))   update Cita set FK_Paciente=xFK_Paciente, FK_Especialidad=xFK_Especialidad, FK_Medico=xFK_Medico, Fecha=xFecha, Hora=xHora where IdCita=xIdCita$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actEspecialidad` (`xIdEspecialidad` VARCHAR(4), `xEspecialidad` VARCHAR(75))   update especialidad set Especialidad=xEspecialidad where IdEspecialidad=xIdEspecialidad$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actEspmed` (`xIdEspmed` VARCHAR(4), `xFK_Especialidad` VARCHAR(4), `xFK_Medico` VARCHAR(4))   update espmed set FK_Especialidad=xFK_Especialidad, FK_Medico=xFK_Medico where IdEspmed=xIdEspmed$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actMedico` (`xIdMedico` VARCHAR(4), `xPassword` VARCHAR(8), `xApellidos` VARCHAR(75), `xNombres` VARCHAR(75), `xDNI` INT(8), `xCMP` VARCHAR(6), `xDireccion` VARCHAR(150), `xCelular` INT(9))   update medico set Password=xPassword, Apellidos=xApellidos, Nombres=xNombres, DNI=xDNI, CMP=xCMP, Direccion=xDireccion, Celular=xCelular where IdMedico=xIdMedico$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actPaciente` (`xIdPaciente` VARCHAR(4), `xPassword` VARCHAR(8), `xApellidos` VARCHAR(75), `xNombres` VARCHAR(75), `xDNI` INT(8), `xSexo` VARCHAR(1), `xNacimiento` DATE, `xCorreo` VARCHAR(75), `xDireccion` VARCHAR(150), `xCelular` INT(9))   update paciente set Password=xPassword, Apellidos=xApellidos, Nombres=xNombres, DNI=xDNI, Sexo=xSexo, Nacimiento=xNacimiento, Correo=xCorreo, Direccion=xDireccion, Celular=xCelular where IdPaciente=xIdPaciente$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `actUsuario` (`xIdUsuario` VARCHAR(4), `xPassword` VARCHAR(8), `xRol` VARCHAR(75), `xApellidos` VARCHAR(75), `xNombres` VARCHAR(75), `xCorreo` VARCHAR(75))   update usuario set Password=xPassword, Rol=xRol, Apellidos=xApellidos, Nombres=xNombres, Correo=xCorreo where IdUsuario=xIdUsuario$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agrCita` (`FK_Paciente` VARCHAR(4), `FK_Especialidad` VARCHAR(4), `FK_Medico` VARCHAR(4), `Fecha` DATE, `Hora` TIME(6))   begin
declare id varchar(4);
declare cuenta int;
select right(max(IdCita),3)+1 into cuenta from Cita;
set id=concat('C',lpad(cuenta,3,'0'));
insert into Cita values(id, FK_Paciente, FK_Especialidad, FK_Medico, Fecha, Hora);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agrEspecialidad` (`Especialidad` VARCHAR(75))   begin
declare id varchar(4);
declare cuenta int;
select right(max(IdEspecialidad),3)+1 into cuenta from especialidad;
set id=concat('E',lpad(cuenta,3,'0'));
insert into especialidad values(id, Especialidad);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agrEspmed` (`FK_Especialidad` VARCHAR(4), `FK_Medico` VARCHAR(4))   begin
declare id varchar(4);
declare cuenta int;
select right(max(IdEspmed),3)+1 into cuenta from espmed;
set id=concat('X',lpad(cuenta,3,'0'));
insert into espmed values(id, FK_Especialidad, FK_Medico);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agrMedico` (`Password` VARCHAR(8), `Apellidos` VARCHAR(75), `Nombres` VARCHAR(75), `DNI` INT(8), `CMP` VARCHAR(6), `Direccion` VARCHAR(150), `Celular` INT(9))   begin
declare id varchar(4);
declare cuenta int;
select right(max(IdMedico),3)+1 into cuenta from medico;
set id=concat('M',lpad(cuenta,3,'0'));
insert into medico values(id, Password, Apellidos, Nombres, DNI, CMP, Direccion, Celular);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agrPaciente` (`Password` VARCHAR(8), `Apellidos` VARCHAR(75), `Nombres` VARCHAR(75), `DNI` INT(8), `Sexo` VARCHAR(1), `Nacimiento` DATE, `Correo` VARCHAR(75), `Direccion` VARCHAR(150), `Celular` INT(9))   begin
declare id varchar(4);
declare cuenta int;
select right(max(IdPaciente),3)+1 into cuenta from paciente;
set id=concat('P',lpad(cuenta,3,'0'));
insert into paciente values(id, Password, Apellidos, Nombres, DNI, Sexo, Nacimiento, Correo, Direccion, Celular);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `agrUsuario` (`Password` VARCHAR(8), `Rol` VARCHAR(75), `Apellidos` VARCHAR(75), `Nombres` VARCHAR(75), `Correo` VARCHAR(75))   begin
declare id varchar(4);
declare cuenta int;
select right(max(IdUsuario),3)+1 into cuenta from usuario;
set id=concat('U',lpad(cuenta,3,'0'));
insert into usuario values(id, Password, Rol, Apellidos, Nombres, Correo);
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla cita
--
-- Creación: 16-11-2024 a las 22:58:23
--

CREATE TABLE cita (
  IdCita varchar(4) NOT NULL,
  FK_Paciente varchar(4) NOT NULL,
  FK_Especialidad varchar(4) NOT NULL,
  FK_Medico varchar(4) NOT NULL,
  Fecha date DEFAULT NULL,
  Hora time(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla cita
--

INSERT INTO cita (IdCita, FK_Paciente, FK_Especialidad, FK_Medico, Fecha, Hora) VALUES
('C001', 'P036', 'E003', 'M008', '2024-08-05', '08:10:29.000000'),
('C002', 'P010', 'E001', 'M002', '2024-08-05', '08:11:33.000000'),
('C003', 'P048', 'E004', 'M010', '2024-08-05', '08:11:33.000000'),
('C004', 'P137', 'E010', 'M028', '2024-08-05', '08:12:14.000000'),
('C005', 'P095', 'E007', 'M019', '2024-08-05', '08:14:32.000000'),
('C006', 'P084', 'E006', 'M017', '2024-08-05', '08:16:23.000000'),
('C007', 'P138', 'E010', 'M028', '2024-08-05', '08:19:38.000000'),
('C008', 'P153', 'E011', 'M031', '2024-08-05', '08:19:49.000000'),
('C009', 'P076', 'E006', 'M016', '2024-08-05', '08:20:42.000000'),
('C010', 'P096', 'E007', 'M020', '2024-08-05', '08:21:08.000000'),
('C011', 'P099', 'E007', 'M020', '2024-08-05', '08:21:52.000000'),
('C012', 'P008', 'E001', 'M002', '2024-08-05', '08:23:52.000000'),
('C013', 'P156', 'E011', 'M032', '2024-08-05', '08:25:31.000000'),
('C014', 'P034', 'E003', 'M007', '2024-08-05', '08:25:49.000000'),
('C015', 'P050', 'E004', 'M010', '2024-08-05', '08:30:12.000000'),
('C016', 'P001', 'E001', 'M001', '2024-08-05', '08:32:26.000000'),
('C017', 'P003', 'E001', 'M001', '2024-08-05', '08:32:35.000000'),
('C018', 'P214', 'E015', 'M043', '2024-08-05', '08:33:07.000000'),
('C019', 'P018', 'E002', 'M004', '2024-08-05', '08:36:34.000000'),
('C020', 'P007', 'E001', 'M002', '2024-08-05', '08:37:57.000000'),
('C021', 'P190', 'E013', 'M038', '2024-08-05', '08:38:56.000000'),
('C022', 'P173', 'E012', 'M035', '2024-08-05', '08:40:11.000000'),
('C023', 'P145', 'E010', 'M029', '2024-08-05', '08:41:49.000000'),
('C024', 'P064', 'E005', 'M013', '2024-08-05', '08:42:27.000000'),
('C025', 'P039', 'E003', 'M008', '2024-08-05', '08:42:47.000000'),
('C026', 'P115', 'E008', 'M023', '2024-08-05', '08:44:46.000000'),
('C027', 'P109', 'E008', 'M022', '2024-08-05', '08:45:47.000000'),
('C028', 'P181', 'E013', 'M037', '2024-08-05', '08:48:00.000000'),
('C029', 'P123', 'E009', 'M025', '2024-08-05', '08:48:19.000000'),
('C030', 'P122', 'E009', 'M025', '2024-08-05', '08:48:36.000000'),
('C031', 'P143', 'E010', 'M029', '2024-08-05', '08:48:54.000000'),
('C032', 'P218', 'E015', 'M044', '2024-08-05', '08:49:04.000000'),
('C033', 'P004', 'E001', 'M001', '2024-08-05', '08:50:22.000000'),
('C034', 'P199', 'E014', 'M040', '2024-08-05', '08:50:34.000000'),
('C035', 'P055', 'E004', 'M011', '2024-08-05', '08:52:07.000000'),
('C036', 'P006', 'E001', 'M002', '2024-08-05', '08:52:31.000000'),
('C037', 'P108', 'E008', 'M022', '2024-08-05', '08:53:09.000000'),
('C038', 'P097', 'E007', 'M020', '2024-08-05', '08:53:56.000000'),
('C039', 'P100', 'E007', 'M020', '2024-08-05', '08:54:19.000000'),
('C040', 'P141', 'E010', 'M029', '2024-08-05', '08:55:27.000000'),
('C041', 'P200', 'E014', 'M040', '2024-08-05', '08:57:19.000000'),
('C042', 'P021', 'E002', 'M005', '2024-08-05', '08:57:46.000000'),
('C043', 'P157', 'E011', 'M032', '2024-08-05', '09:01:13.000000'),
('C044', 'P091', 'E007', 'M019', '2024-08-05', '09:01:39.000000'),
('C045', 'P032', 'E003', 'M007', '2024-08-05', '09:02:32.000000'),
('C046', 'P202', 'E014', 'M041', '2024-08-05', '09:03:37.000000'),
('C047', 'P005', 'E001', 'M001', '2024-08-05', '09:04:01.000000'),
('C048', 'P017', 'E002', 'M004', '2024-08-05', '09:04:16.000000'),
('C049', 'P183', 'E013', 'M037', '2024-08-05', '09:05:13.000000'),
('C050', 'P213', 'E015', 'M043', '2024-08-05', '09:07:32.000000'),
('C051', 'P020', 'E002', 'M004', '2024-08-05', '09:13:16.000000'),
('C052', 'P040', 'E003', 'M008', '2024-08-05', '09:14:39.000000'),
('C053', 'P038', 'E003', 'M008', '2024-08-05', '09:17:47.000000'),
('C054', 'P215', 'E015', 'M043', '2024-08-05', '09:18:09.000000'),
('C055', 'P068', 'E005', 'M014', '2024-08-05', '09:20:57.000000'),
('C056', 'P171', 'E012', 'M035', '2024-08-05', '09:21:43.000000'),
('C057', 'P061', 'E005', 'M013', '2024-08-05', '09:24:17.000000'),
('C058', 'P080', 'E006', 'M016', '2024-08-05', '09:25:50.000000'),
('C059', 'P203', 'E014', 'M041', '2024-08-05', '09:26:25.000000'),
('C060', 'P049', 'E004', 'M010', '2024-08-05', '09:31:19.000000'),
('C061', 'P166', 'E012', 'M034', '2024-08-05', '09:32:10.000000'),
('C062', 'P142', 'E010', 'M029', '2024-08-05', '09:32:53.000000'),
('C063', 'P196', 'E014', 'M040', '2024-08-05', '09:36:19.000000'),
('C064', 'P130', 'E009', 'M026', '2024-08-05', '09:39:34.000000'),
('C065', 'P189', 'E013', 'M038', '2024-08-05', '09:40:19.000000'),
('C066', 'P054', 'E004', 'M011', '2024-08-05', '09:40:21.000000'),
('C067', 'P107', 'E008', 'M022', '2024-08-05', '09:41:38.000000'),
('C068', 'P217', 'E015', 'M044', '2024-08-05', '09:42:14.000000'),
('C069', 'P065', 'E005', 'M013', '2024-08-05', '09:42:52.000000'),
('C070', 'P167', 'E012', 'M034', '2024-08-05', '09:44:14.000000'),
('C071', 'P112', 'E008', 'M023', '2024-08-05', '09:46:08.000000'),
('C072', 'P066', 'E005', 'M014', '2024-08-05', '09:46:37.000000'),
('C073', 'P033', 'E003', 'M007', '2024-08-05', '09:48:44.000000'),
('C074', 'P022', 'E002', 'M005', '2024-08-05', '09:51:03.000000'),
('C075', 'P160', 'E011', 'M032', '2024-08-05', '09:51:13.000000'),
('C076', 'P170', 'E012', 'M034', '2024-08-05', '09:56:38.000000'),
('C077', 'P198', 'E014', 'M040', '2024-08-05', '09:57:44.000000'),
('C078', 'P047', 'E004', 'M010', '2024-08-05', '10:01:24.000000'),
('C079', 'P172', 'E012', 'M035', '2024-08-05', '10:02:22.000000'),
('C080', 'P220', 'E015', 'M044', '2024-08-05', '10:02:22.000000'),
('C081', 'P144', 'E010', 'M029', '2024-08-05', '10:07:18.000000'),
('C082', 'P098', 'E007', 'M020', '2024-08-05', '10:09:21.000000'),
('C083', 'P063', 'E005', 'M013', '2024-08-05', '10:11:47.000000'),
('C084', 'P016', 'E002', 'M004', '2024-08-05', '10:12:21.000000'),
('C085', 'P106', 'E008', 'M022', '2024-08-05', '10:14:06.000000'),
('C086', 'P121', 'E009', 'M025', '2024-08-05', '10:15:55.000000'),
('C087', 'P062', 'E005', 'M013', '2024-08-05', '10:17:36.000000'),
('C088', 'P082', 'E006', 'M017', '2024-08-05', '10:18:18.000000'),
('C089', 'P155', 'E011', 'M031', '2024-08-05', '10:19:31.000000'),
('C090', 'P158', 'E011', 'M032', '2024-08-05', '10:20:57.000000'),
('C091', 'P140', 'E010', 'M028', '2024-08-05', '10:22:41.000000'),
('C092', 'P174', 'E012', 'M035', '2024-08-05', '10:25:41.000000'),
('C093', 'P093', 'E007', 'M019', '2024-08-05', '10:25:45.000000'),
('C094', 'P212', 'E015', 'M043', '2024-08-05', '10:26:19.000000'),
('C095', 'P216', 'E015', 'M044', '2024-08-05', '10:29:20.000000'),
('C096', 'P127', 'E009', 'M026', '2024-08-05', '10:29:58.000000'),
('C097', 'P077', 'E006', 'M016', '2024-08-05', '10:30:04.000000'),
('C098', 'P185', 'E013', 'M037', '2024-08-05', '10:30:09.000000'),
('C099', 'P151', 'E011', 'M031', '2024-08-05', '10:30:18.000000'),
('C100', 'P070', 'E005', 'M014', '2024-08-05', '10:30:25.000000'),
('C101', 'P051', 'E004', 'M011', '2024-08-05', '10:31:07.000000'),
('C102', 'P024', 'E002', 'M005', '2024-08-05', '10:31:36.000000'),
('C103', 'P078', 'E006', 'M016', '2024-08-05', '10:32:53.000000'),
('C104', 'P019', 'E002', 'M004', '2024-08-05', '10:32:55.000000'),
('C105', 'P187', 'E013', 'M038', '2024-08-05', '10:33:03.000000'),
('C106', 'P110', 'E008', 'M022', '2024-08-05', '10:36:37.000000'),
('C107', 'P124', 'E009', 'M025', '2024-08-05', '10:37:03.000000'),
('C108', 'P205', 'E014', 'M041', '2024-08-05', '10:39:08.000000'),
('C109', 'P169', 'E012', 'M034', '2024-08-05', '10:39:34.000000'),
('C110', 'P128', 'E009', 'M026', '2024-08-05', '10:39:41.000000'),
('C111', 'P053', 'E004', 'M011', '2024-08-05', '10:39:51.000000'),
('C112', 'P182', 'E013', 'M037', '2024-08-05', '10:41:29.000000'),
('C113', 'P139', 'E010', 'M028', '2024-08-05', '10:42:01.000000'),
('C114', 'P114', 'E008', 'M023', '2024-08-05', '10:42:02.000000'),
('C115', 'P184', 'E013', 'M037', '2024-08-05', '10:46:32.000000'),
('C116', 'P031', 'E003', 'M007', '2024-08-05', '10:47:00.000000'),
('C117', 'P129', 'E009', 'M026', '2024-08-05', '10:47:52.000000'),
('C118', 'P081', 'E006', 'M017', '2024-08-05', '10:48:21.000000'),
('C119', 'P035', 'E003', 'M007', '2024-08-05', '10:48:26.000000'),
('C120', 'P094', 'E007', 'M019', '2024-08-05', '10:51:46.000000'),
('C121', 'P154', 'E011', 'M031', '2024-08-05', '10:59:07.000000'),
('C122', 'P204', 'E014', 'M041', '2024-08-05', '11:00:33.000000'),
('C123', 'P136', 'E010', 'M028', '2024-08-05', '11:01:30.000000'),
('C124', 'P125', 'E009', 'M025', '2024-08-05', '11:01:59.000000'),
('C125', 'P002', 'E001', 'M001', '2024-08-05', '11:02:43.000000'),
('C126', 'P085', 'E006', 'M017', '2024-08-05', '11:05:34.000000'),
('C127', 'P201', 'E014', 'M041', '2024-08-05', '11:06:59.000000'),
('C128', 'P083', 'E006', 'M017', '2024-08-05', '11:07:39.000000'),
('C129', 'P025', 'E002', 'M005', '2024-08-05', '11:11:18.000000'),
('C130', 'P186', 'E013', 'M038', '2024-08-05', '11:11:23.000000'),
('C131', 'P126', 'E009', 'M026', '2024-08-05', '11:13:14.000000'),
('C132', 'P052', 'E004', 'M011', '2024-08-05', '11:13:17.000000'),
('C133', 'P067', 'E005', 'M014', '2024-08-05', '11:13:24.000000'),
('C134', 'P219', 'E015', 'M044', '2024-08-05', '11:13:39.000000'),
('C135', 'P092', 'E007', 'M019', '2024-08-05', '11:14:12.000000'),
('C136', 'P175', 'E012', 'M035', '2024-08-05', '11:14:41.000000'),
('C137', 'P023', 'E002', 'M005', '2024-08-05', '11:14:46.000000'),
('C138', 'P037', 'E003', 'M008', '2024-08-05', '11:14:57.000000'),
('C139', 'P079', 'E006', 'M016', '2024-08-05', '11:16:35.000000'),
('C140', 'P069', 'E005', 'M014', '2024-08-05', '11:18:04.000000'),
('C141', 'P113', 'E008', 'M023', '2024-08-05', '11:19:23.000000'),
('C142', 'P197', 'E014', 'M040', '2024-08-05', '11:21:22.000000'),
('C143', 'P188', 'E013', 'M038', '2024-08-05', '11:23:05.000000'),
('C144', 'P159', 'E011', 'M032', '2024-08-05', '11:23:17.000000'),
('C145', 'P009', 'E001', 'M002', '2024-08-05', '11:23:42.000000'),
('C146', 'P152', 'E011', 'M031', '2024-08-05', '11:25:33.000000'),
('C147', 'P211', 'E015', 'M043', '2024-08-05', '11:26:08.000000'),
('C148', 'P046', 'E004', 'M010', '2024-08-05', '11:29:17.000000'),
('C149', 'P168', 'E012', 'M034', '2024-08-05', '11:30:45.000000'),
('C150', 'P111', 'E008', 'M023', '2024-08-05', '11:31:08.000000'),
('C151', 'P210', 'E014', 'M042', '2024-08-05', '14:13:48.000000'),
('C152', 'P045', 'E003', 'M009', '2024-08-05', '14:18:03.000000'),
('C153', 'P026', 'E002', 'M006', '2024-08-05', '14:18:19.000000'),
('C154', 'P029', 'E002', 'M006', '2024-08-05', '14:20:37.000000'),
('C155', 'P135', 'E009', 'M027', '2024-08-05', '14:22:19.000000'),
('C156', 'P015', 'E001', 'M003', '2024-08-05', '14:27:35.000000'),
('C157', 'P073', 'E005', 'M015', '2024-08-05', '14:27:45.000000'),
('C158', 'P133', 'E009', 'M027', '2024-08-05', '14:30:08.000000'),
('C159', 'P057', 'E004', 'M012', '2024-08-05', '14:31:12.000000'),
('C160', 'P162', 'E011', 'M033', '2024-08-05', '14:38:38.000000'),
('C161', 'P179', 'E012', 'M036', '2024-08-05', '14:39:34.000000'),
('C162', 'P030', 'E002', 'M006', '2024-08-05', '14:41:42.000000'),
('C163', 'P222', 'E015', 'M045', '2024-08-05', '14:45:49.000000'),
('C164', 'P074', 'E005', 'M015', '2024-08-05', '14:46:58.000000'),
('C165', 'P149', 'E010', 'M030', '2024-08-05', '14:47:49.000000'),
('C166', 'P101', 'E007', 'M021', '2024-08-05', '14:47:53.000000'),
('C167', 'P056', 'E004', 'M012', '2024-08-05', '14:48:13.000000'),
('C168', 'P116', 'E008', 'M024', '2024-08-05', '14:50:05.000000'),
('C169', 'P120', 'E008', 'M024', '2024-08-05', '14:58:20.000000'),
('C170', 'P012', 'E001', 'M003', '2024-08-05', '14:58:51.000000'),
('C171', 'P014', 'E001', 'M003', '2024-08-05', '14:59:04.000000'),
('C172', 'P209', 'E014', 'M042', '2024-08-05', '15:01:00.000000'),
('C173', 'P223', 'E015', 'M045', '2024-08-05', '15:01:54.000000'),
('C174', 'P043', 'E003', 'M009', '2024-08-05', '15:07:09.000000'),
('C175', 'P180', 'E012', 'M036', '2024-08-05', '15:12:41.000000'),
('C176', 'P132', 'E009', 'M027', '2024-08-05', '15:17:04.000000'),
('C177', 'P072', 'E005', 'M015', '2024-08-05', '15:20:36.000000'),
('C178', 'P044', 'E003', 'M009', '2024-08-05', '15:21:28.000000'),
('C179', 'P165', 'E011', 'M033', '2024-08-05', '15:22:01.000000'),
('C180', 'P103', 'E007', 'M021', '2024-08-05', '15:25:32.000000'),
('C181', 'P131', 'E009', 'M027', '2024-08-05', '15:34:05.000000'),
('C182', 'P059', 'E004', 'M012', '2024-08-05', '15:35:08.000000'),
('C183', 'P058', 'E004', 'M012', '2024-08-05', '15:36:30.000000'),
('C184', 'P118', 'E008', 'M024', '2024-08-05', '15:38:13.000000'),
('C185', 'P224', 'E015', 'M045', '2024-08-05', '15:41:15.000000'),
('C186', 'P088', 'E006', 'M018', '2024-08-05', '15:43:47.000000'),
('C187', 'P191', 'E013', 'M039', '2024-08-05', '15:44:10.000000'),
('C188', 'P148', 'E010', 'M030', '2024-08-05', '15:44:30.000000'),
('C189', 'P207', 'E014', 'M042', '2024-08-05', '15:44:59.000000'),
('C190', 'P225', 'E015', 'M045', '2024-08-05', '15:47:14.000000'),
('C191', 'P087', 'E006', 'M018', '2024-08-05', '15:48:15.000000'),
('C192', 'P089', 'E006', 'M018', '2024-08-05', '15:55:05.000000'),
('C193', 'P164', 'E011', 'M033', '2024-08-05', '15:58:33.000000'),
('C194', 'P075', 'E005', 'M015', '2024-08-05', '15:59:19.000000'),
('C195', 'P041', 'E003', 'M009', '2024-08-05', '16:03:36.000000'),
('C196', 'P011', 'E001', 'M003', '2024-08-05', '16:10:05.000000'),
('C197', 'P119', 'E008', 'M024', '2024-08-05', '16:16:35.000000'),
('C198', 'P090', 'E006', 'M018', '2024-08-05', '16:18:29.000000'),
('C199', 'P221', 'E015', 'M045', '2024-08-05', '16:19:29.000000'),
('C200', 'P060', 'E004', 'M012', '2024-08-05', '16:21:31.000000'),
('C201', 'P163', 'E011', 'M033', '2024-08-05', '16:23:18.000000'),
('C202', 'P206', 'E014', 'M042', '2024-08-05', '16:24:16.000000'),
('C203', 'P027', 'E002', 'M006', '2024-08-05', '16:24:42.000000'),
('C204', 'P134', 'E009', 'M027', '2024-08-05', '16:24:52.000000'),
('C205', 'P117', 'E008', 'M024', '2024-08-05', '16:24:58.000000'),
('C206', 'P086', 'E006', 'M018', '2024-08-05', '16:34:53.000000'),
('C207', 'P178', 'E012', 'M036', '2024-08-05', '16:35:29.000000'),
('C208', 'P161', 'E011', 'M033', '2024-08-05', '16:36:55.000000'),
('C209', 'P193', 'E013', 'M039', '2024-08-05', '16:45:14.000000'),
('C210', 'P104', 'E007', 'M021', '2024-08-05', '16:46:04.000000'),
('C211', 'P028', 'E002', 'M006', '2024-08-05', '16:48:55.000000'),
('C212', 'P176', 'E012', 'M036', '2024-08-05', '16:51:51.000000'),
('C213', 'P195', 'E013', 'M039', '2024-08-05', '16:54:39.000000'),
('C214', 'P042', 'E003', 'M009', '2024-08-05', '16:55:30.000000'),
('C215', 'P013', 'E001', 'M003', '2024-08-05', '16:57:36.000000'),
('C216', 'P105', 'E007', 'M021', '2024-08-05', '16:57:38.000000'),
('C217', 'P208', 'E014', 'M042', '2024-08-05', '16:57:46.000000'),
('C218', 'P147', 'E010', 'M030', '2024-08-05', '17:04:10.000000'),
('C219', 'P102', 'E007', 'M021', '2024-08-05', '17:05:22.000000'),
('C220', 'P071', 'E005', 'M015', '2024-08-05', '17:14:48.000000'),
('C221', 'P146', 'E010', 'M030', '2024-08-05', '17:19:08.000000'),
('C222', 'P192', 'E013', 'M039', '2024-08-05', '17:21:10.000000'),
('C223', 'P150', 'E010', 'M030', '2024-08-05', '17:22:22.000000'),
('C224', 'P194', 'E013', 'M039', '2024-08-05', '17:25:39.000000'),
('C225', 'P177', 'E012', 'M036', '2024-08-05', '17:29:09.000000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla especialidad
--
-- Creación: 16-11-2024 a las 22:58:23
--

CREATE TABLE especialidad (
  IdEspecialidad varchar(4) NOT NULL,
  Especialidad varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla especialidad
--

INSERT INTO especialidad (IdEspecialidad, Especialidad) VALUES
('E001', 'Cardiología'),
('E002', 'Cirugía'),
('E003', 'Dermatología'),
('E004', 'Endocrinología'),
('E005', 'Gastroenterología'),
('E006', 'Medicina Interna'),
('E007', 'Nefrología'),
('E008', 'Neumología'),
('E009', 'Neurología'),
('E010', 'Oftalmología'),
('E011', 'Otorrinolaringología'),
('E012', 'Reumatología'),
('E013', 'Salud Mental'),
('E014', 'Traumatología'),
('E015', 'Urología');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla espmed
--
-- Creación: 16-11-2024 a las 22:58:23
--

CREATE TABLE espmed (
  IdEspmed varchar(4) NOT NULL,
  FK_Especialidad varchar(4) NOT NULL,
  FK_Medico varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla espmed
--

INSERT INTO espmed (IdEspmed, FK_Especialidad, FK_Medico) VALUES
('X001', 'E001', 'M001'),
('X002', 'E001', 'M002'),
('X003', 'E001', 'M003'),
('X004', 'E002', 'M004'),
('X005', 'E002', 'M005'),
('X006', 'E002', 'M006'),
('X007', 'E003', 'M007'),
('X008', 'E003', 'M008'),
('X009', 'E003', 'M009'),
('X010', 'E004', 'M010'),
('X011', 'E004', 'M011'),
('X012', 'E004', 'M012'),
('X013', 'E005', 'M013'),
('X014', 'E005', 'M014'),
('X015', 'E005', 'M015'),
('X016', 'E006', 'M016'),
('X017', 'E006', 'M017'),
('X018', 'E006', 'M018'),
('X019', 'E007', 'M019'),
('X020', 'E007', 'M020'),
('X021', 'E007', 'M021'),
('X022', 'E008', 'M022'),
('X023', 'E008', 'M023'),
('X024', 'E008', 'M024'),
('X025', 'E009', 'M025'),
('X026', 'E009', 'M026'),
('X027', 'E009', 'M027'),
('X028', 'E010', 'M028'),
('X029', 'E010', 'M029'),
('X030', 'E010', 'M030'),
('X031', 'E011', 'M031'),
('X032', 'E011', 'M032'),
('X033', 'E011', 'M033'),
('X034', 'E012', 'M034'),
('X035', 'E012', 'M035'),
('X036', 'E012', 'M036'),
('X037', 'E013', 'M037'),
('X038', 'E013', 'M038'),
('X039', 'E013', 'M039'),
('X040', 'E014', 'M040'),
('X041', 'E014', 'M041'),
('X042', 'E014', 'M042'),
('X043', 'E015', 'M043'),
('X044', 'E015', 'M044'),
('X045', 'E015', 'M045');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla medico
--
-- Creación: 16-11-2024 a las 22:58:23
--

CREATE TABLE medico (
  IdMedico varchar(4) NOT NULL,
  Password varchar(8) NOT NULL,
  Apellidos varchar(75) NOT NULL,
  Nombres varchar(75) NOT NULL,
  DNI int(8) NOT NULL,
  CMP varchar(6) NOT NULL,
  Direccion varchar(150) DEFAULT NULL,
  Celular int(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla medico
--

INSERT INTO medico (IdMedico, Password, Apellidos, Nombres, DNI, CMP, Direccion, Celular) VALUES
('M001', '12345678', 'Chávez Ruiz', 'Agustín David', 67308441, '051449', 'San Borja', 947148878),
('M002', '12345678', 'Mendoza Medina', 'Carla Florencia', 54595120, '065309', 'La Victoria', 967563919),
('M003', '12345678', 'Vásquez Reyes', 'Ethan Jacob', 43740820, '076064', 'San Martín de Porres', 990486463),
('M004', '12345678', 'Cruz Aguilar', 'Alejandro Aiden', 59584861, '059981', 'Breña', 935716638),
('M005', '12345678', 'Flores Castro', 'Agustín Joaquín', 50276463, '045502', 'Rímac', 985750208),
('M006', '12345678', 'Rodríguez Vargas', 'Sophia Florencia', 50454016, '079923', 'Punta Negra', 947261137),
('M007', '12345678', 'Condori Espinoza', 'Isabella Martina', 55389165, '072631', 'Santa Rosa', 930528845),
('M008', '12345678', 'Díaz Silva', 'Matías Adrián', 52352776, '050696', 'San Juan de Lurigancho', 968718817),
('M009', '12345678', 'Rodríguez Vásquez', 'Maximiliano Michael', 43825278, '058955', 'Carabayllo', 968439318),
('M010', '12345678', 'De La Cruz Rodríguez', 'Daniela Julia', 63645738, '062687', 'Jesús María', 978340449),
('M011', '12345678', 'Díaz Silva', 'Matías Adrián', 58114756, '041073', 'Villa María del Triunfo', 920296151),
('M012', '12345678', 'Gutiérrez Paredes', 'Michael Daniel', 64613431, '053376', 'Independencia', 942013930),
('M013', '12345678', 'Huamán Gutiérrez', 'Jacob Daniel', 55772983, '061672', 'San Juan de Miraflores', 977380771),
('M014', '12345678', 'Rivera Vargas', 'Carla Emilia', 47886069, '041192', 'Magdalena del Mar', 990502129),
('M015', '12345678', 'Vásquez Rojas', 'Hugo Cristóbal', 67036387, '043148', 'San Luis', 939460460),
('M016', '12345678', 'De La Cruz Ramos', 'Lucila Isabella', 62806564, '078350', 'Santa María del Mar', 948369349),
('M017', '12345678', 'Ramírez Gómez', 'Matías William', 55792019, '073995', 'Villa María del Triunfo', 926004492),
('M018', '12345678', 'Vásquez Condori', 'Jayden Mason', 63395724, '076917', 'Ancón', 989262664),
('M019', '12345678', 'López Gutiérrez', 'Noah Joaquín', 62592556, '061349', 'Villa María del Triunfo', 965606070),
('M020', '12345678', 'Quispe Gutiérrez', 'Jacob Javier', 69940632, '068542', 'Breña', 935209842),
('M021', '12345678', 'Rojas Flores', 'Florencia Sophia', 48892439, '057386', 'Breña', 943782445),
('M022', '12345678', 'Cruz Rojas', 'Benjamín Michael', 47542734, '079247', 'Punta Hermosa', 937366313),
('M023', '12345678', 'Rodríguez Cruz', 'Maximiliano Nilton', 48320777, '074810', 'Villa María del Triunfo', 920131129),
('M024', '12345678', 'Rojas Reyes', 'Martina Fernanda', 54114446, '056515', 'Magdalena del Mar', 990871312),
('M025', '12345678', 'Castillo Rivera', 'Matías Diego', 70508136, '059529', 'San Juan de Miraflores', 970558248),
('M026', '12345678', 'Fernández Condori', 'Julia Catalina', 48489415, '060741', 'Ate', 923014200),
('M027', '12345678', 'Rivera Fernández', 'Mario Alejandro', 69008958, '053876', 'Lima', 932303807),
('M028', '12345678', 'Gutiérrez Rivera', 'Lucía Daniela', 53006551, '073760', 'Jesús María', 939038119),
('M029', '12345678', 'Martínez Rodríguez', 'Maximiliano Benjamín', 45843016, '047234', 'El Agustino', 936543016),
('M030', '12345678', 'Quispe Martínez', 'Cristóbal Sebastián', 48569932, '063096', 'El Agustino', 941970595),
('M031', '12345678', 'Sánchez Ramos', 'Noah Jayden', 51802597, '041612', 'San Juan de Miraflores', 962368911),
('M032', '12345678', 'Silva Rodríguez', 'Pablo Javier', 58785382, '041718', 'La Victoria', 949051529),
('M033', '12345678', 'Vargas López', 'Olivia Valentina', 44140541, '052891', 'Lince', 991826162),
('M034', '12345678', 'Aguilar De La Cruz', 'Álvaro Hugo', 66888937, '047691', 'Puente Piedra', 975064749),
('M035', '12345678', 'Castro Quispe', 'Noah Sebastián', 50797274, '065871', 'La Victoria', 992666213),
('M036', '12345678', 'Quispe Huamán', 'Valentina Paula', 49322506, '046242', 'Punta Hermosa', 967377814),
('M037', '12345678', 'Córdova Díaz', 'Ava Alba', 45512431, '049696', 'Barranco', 976905427),
('M038', '12345678', 'Rivera Espinoza', 'Hugo Agustín', 52486510, '062521', 'Villa María del Triunfo', 974139148),
('M039', '12345678', 'Rivera Mamani', 'Vicente William', 70355854, '070727', 'Chorrillos', 958993840),
('M040', '12345678', 'Castro Chávez', 'Alejandro Matías', 56830719, '044946', 'Santa Anita', 951560799),
('M041', '12345678', 'Mendoza Fernández', 'William Matías', 53385488, '078486', 'Los Olivos', 973074761),
('M042', '12345678', 'Rodríguez Aguilar', 'Martina Emilia', 56207649, '047852', 'Magdalena del Mar', 939157929),
('M043', '12345678', 'García Condori', 'Isabella Antonella', 50207926, '072096', 'Santiago de Surco', 977427248),
('M044', '12345678', 'Mamani Castro', 'Agustín Tomás', 59142863, '077968', 'San Juan de Miraflores', 942880308),
('M045', '12345678', 'Medina Mamani', 'Pablo Noah', 62461551, '047199', 'Jesús María', 947557583);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla paciente
--
-- Creación: 16-11-2024 a las 22:58:23
--

CREATE TABLE paciente (
  IdPaciente varchar(4) NOT NULL,
  Password varchar(8) NOT NULL,
  Apellidos varchar(75) NOT NULL,
  Nombres varchar(75) NOT NULL,
  DNI int(8) NOT NULL,
  Sexo varchar(1) DEFAULT NULL,
  Nacimiento date DEFAULT NULL,
  Correo varchar(75) DEFAULT NULL,
  Direccion varchar(150) DEFAULT NULL,
  Celular int(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla paciente
--

INSERT INTO paciente (IdPaciente, Password, Apellidos, Nombres, DNI, Sexo, Nacimiento, Correo, Direccion, Celular) VALUES
('P001', '12345678', 'Castillo Quispe', 'Antonella Lucila', 46333506, 'F', '1975-03-29', 'casant@outlook.com', 'Punta Hermosa', 931169326),
('P002', '12345678', 'Gonzales Chávez', 'Adrián Michael', 47442778, 'M', '1986-01-09', 'gonadr@ymail.com', 'Villa María del Triunfo', 968522406),
('P003', '12345678', 'Salazar Quispe', 'Martina Ava', 62379998, 'F', '1986-09-10', 'salmar@gmail.com', 'San Juan de Lurigancho', 952425319),
('P004', '12345678', 'Paredes Flores', 'Adrián Javier', 65537357, 'M', '1992-01-26', 'paradr@gmail.com', 'Jesús María', 985970237),
('P005', '12345678', 'Flores Salazar', 'Matías Noah', 55061889, 'M', '1993-09-06', 'flomat@ymail.com', 'Lima', 966031883),
('P006', '12345678', 'Paredes Ramírez', 'Abigail Emma', 48929294, 'F', '1977-04-30', 'parabi@ymail.com', 'Puente Piedra', 942717796),
('P007', '12345678', 'García Salazar', 'Sophia Antonella', 55564539, 'F', '1983-10-10', 'garsop@gmail.com', 'Chorrillos', 931164629),
('P008', '12345678', 'Rodríguez Fernández', 'Emily Emma', 64042335, 'F', '1992-08-26', 'rodemi@gmail.com', 'Pueblo Libre', 946078874),
('P009', '12345678', 'De La Cruz Fernández', 'Martina Madison', 63226813, 'F', '1973-11-09', 'demar@outlook.com', 'Pueblo Libre', 984381476),
('P010', '12345678', 'Morales Medina', 'Ava Emma', 49322781, 'F', '1991-07-02', 'morava@ymail.com', 'Cieneguilla', 973834320),
('P011', '12345678', 'Rojas Espinoza', 'Antonella Abigail', 43173413, 'F', '1974-11-03', 'rojant@ymail.com', 'El Agustino', 948971226),
('P012', '12345678', 'Martínez Díaz', 'Emma Antonella', 54445021, 'F', '1977-01-15', 'maremm@ymail.com', 'Pachacámac', 984835170),
('P013', '12345678', 'Quispe Vargas', 'Mia Emma', 58912311, 'F', '1976-09-05', 'quimia@ymail.com', 'San Luis', 959839310),
('P014', '12345678', 'Cruz Sánchez', 'Emily Mia', 68341410, 'F', '1969-09-14', 'cruemi@outlook.com', 'Breña', 986144992),
('P015', '12345678', 'Rojas Martínez', 'Daniela Lucía', 52926937, 'F', '1987-06-27', 'rojdan@gmail.com', 'Ancón', 923395473),
('P016', '12345678', 'Condori Castro', 'Valentina Lucía', 42545507, 'F', '1987-02-08', 'conval@outlook.com', 'Villa María del Triunfo', 942122883),
('P017', '12345678', 'Mamani Vásquez', 'Álvaro Benjamín', 66525205, 'M', '1973-11-10', 'mamalv@outlook.com', 'La Victoria', 928198747),
('P018', '12345678', 'De La Cruz Castro', 'Alexander Sebastián', 44208573, 'M', '1975-09-19', 'deale@gmail.com', 'Carabayllo', 972250895),
('P019', '12345678', 'Vásquez Rojas', 'Sebastián Pablo', 49449645, 'M', '1979-03-18', 'vasseb@gmail.com', 'San Juan de Miraflores', 989211877),
('P020', '12345678', 'Torres Vargas', 'Alba Valentina', 70011714, 'F', '1971-03-18', 'toralb@ymail.com', 'La Molina', 929426473),
('P021', '12345678', 'Pérez Mendoza', 'Daniela Fernanda', 63012523, 'F', '1974-04-18', 'perdan@outlook.com', 'Cieneguilla', 927648825),
('P022', '12345678', 'Pérez Ramírez', 'Ava Valentina', 54410479, 'F', '1982-01-15', 'perava@gmail.com', 'Villa El Salvador', 926497397),
('P023', '12345678', 'Torres Pérez', 'Emilia Paula', 64698733, 'F', '1972-07-03', 'toremi@gmail.com', 'Santa Rosa', 944246791),
('P024', '12345678', 'Silva Morales', 'Daniel Pablo', 49281488, 'M', '1974-11-07', 'sildan@gmail.com', 'Los Olivos', 957853169),
('P025', '12345678', 'Espinoza Fernández', 'Lucila Daniela', 44419163, 'F', '1974-10-04', 'espluc@outlook.com', 'Lurigancho', 969951591),
('P026', '12345678', 'Castro Chávez', 'Olivia Martina', 48675345, 'F', '1973-04-25', 'casoli@ymail.com', 'Breña', 960233466),
('P027', '12345678', 'Fernández Chávez', 'Julia', 67006494, 'F', '1981-03-12', 'ferjul@gmail.com', 'Chaclacayo', 943750007),
('P028', '12345678', 'Mamani Medina', 'Michael Pablo', 48428451, 'M', '1976-06-10', 'mammic@outlook.com', 'Santa Rosa', 956202067),
('P029', '12345678', 'Paredes Medina', 'Pablo Alberto', 53753438, 'M', '1974-03-02', 'parpab@outlook.com', 'Los Olivos', 948680005),
('P030', '12345678', 'Reyes Córdova', 'Cristóbal Nilton', 46925088, 'M', '1971-11-09', 'reycri@gmail.com', 'Pueblo Libre', 979427153),
('P031', '12345678', 'Salazar Córdova', 'Antonella Ava', 43830770, 'F', '1979-11-20', 'salant@outlook.com', 'Rímac', 932134837),
('P032', '12345678', 'Quispe Aguilar', 'Martina Alva', 68019760, 'F', '1992-06-15', 'quimar@gmail.com', 'Santa Rosa', 973687177),
('P033', '12345678', 'Castillo Chávez', 'Jacob Agustín', 59965811, 'M', '1980-01-17', 'casjac@gmail.com', 'Los Olivos', 962746634),
('P034', '12345678', 'Silva Paredes', 'Isabella Daniela', 61406120, 'F', '1978-04-30', 'silisa@outlook.com', 'Jesús María', 983106855),
('P035', '12345678', 'Aguilar Córdova', 'Pablo Adrián', 50018517, 'M', '1981-09-08', 'agupab@outlook.com', 'San Bartolo', 954582479),
('P036', '12345678', 'Condori Mendosa', 'Cesar', 43143483, 'M', '1979-06-21', 'conces@gmail.com', 'San Juan de Lurigancho', 933493327),
('P037', '12345678', 'Paredes Condori', 'Joaquín Hugo', 49329458, 'M', '1975-06-07', 'parjoa@gmail.com', 'Jesús María', 932031921),
('P038', '12345678', 'Salazar Aguilar', 'Mia Paula', 54569577, 'F', '1985-02-22', 'salmia@ymail.com', 'Miraflores', 929648785),
('P039', '12345678', 'Fernández Ruiz', 'Mason William', 45558222, 'M', '1978-02-14', 'fermas@outlook.com', 'San Martín de Porres', 933910859),
('P040', '12345678', 'García Quispe', 'Martina Sofia', 44793834, 'F', '1986-04-17', 'garmar@gmail.com', 'Chaclacayo', 992665342),
('P041', '12345678', 'Quispe Vargas', 'Vicente Michael', 48955456, 'M', '1985-10-26', 'quivic@ymail.com', 'Comas', 984814064),
('P042', '12345678', 'Rivera Paredes', 'Abigail Fernanda', 68574769, 'F', '1981-05-25', 'rivabi@outlook.com', 'Santa Anita', 990686925),
('P043', '12345678', 'Medina Ruiz', 'Alexander Sebastián', 69876531, 'M', '1969-01-14', 'medale@ymail.com', 'Villa María del Triunfo', 936437517),
('P044', '12345678', 'Rivera Huamán', 'Sophia', 64084842, 'F', '1978-05-03', 'rivsop@outlook.com', 'Villa El Salvador', 987810797),
('P045', '12345678', 'Mamani Cruz', 'Diego Nilton', 64176452, 'M', '1973-02-08', 'mamdie@ymail.com', 'Chorrillos', 934798341),
('P046', '12345678', 'Vásquez García', 'Álvaro Mason', 53558529, 'M', '1989-10-13', 'vasalv@gmail.com', 'Comas', 958269415),
('P047', '12345678', 'Silva Aguilar', 'Daniela Julia', 52703856, 'F', '1979-11-30', 'sildan@gmail.com', 'San Borja', 991346807),
('P048', '12345678', 'Vásquez Torres', 'Mario Nilton', 47001462, 'M', '1972-09-24', 'vasmar@outlook.com', 'El Agustino', 993550609),
('P049', '12345678', 'Quispe Castro', 'Catalina Sara', 58258055, 'F', '1982-02-07', 'quicat@ymail.com', 'Ate', 924401450),
('P050', '12345678', 'Ramos Rodríguez', 'Catalina María', 65631006, 'F', '1976-09-26', 'ramcat@ymail.com', 'Pachacámac', 927979727),
('P051', '12345678', 'Castro Vásquez', 'Elizabeth Lucía', 49635254, 'F', '1971-05-02', 'caseli@ymail.com', 'Santa Anita', 964218583),
('P052', '12345678', 'Romero Paredes', 'Alejandro Jayden', 42783543, 'M', '1989-08-28', 'romale@outlook.com', 'Lima', 986386250),
('P053', '12345678', 'Vásquez De La Cruz', 'Lucía Carla', 42064055, 'F', '1985-01-21', 'vasluc@ymail.com', 'Santa María del Mar', 969393749),
('P054', '12345678', 'Espinoza Gutiérrez', 'Javier Nilton', 58861406, 'M', '1989-04-01', 'espjav@ymail.com', 'Barranco', 921845792),
('P055', '12345678', 'Flores Mamani', 'Cristóbal Mason', 63206600, 'M', '1987-07-22', 'flocri@ymail.com', 'Miraflores', 941421953),
('P056', '12345678', 'Castro Pérez', 'Cristóbal Daniel', 52129059, 'M', '1985-09-28', 'cascri@outlook.com', 'Pucusana', 986120623),
('P057', '12345678', 'Gonzales Sánchez', 'Valentina Lucila', 56683013, 'F', '1984-11-29', 'gonval@ymail.com', 'Breña', 953703572),
('P058', '12345678', 'Castro Mamani', 'Hugo David', 45670569, 'M', '1981-01-08', 'cashug@ymail.com', 'Chaclacayo', 949092810),
('P059', '12345678', 'Gutiérrez Reyes', 'Martín Alexander', 43643885, 'M', '1984-08-07', 'gutmar@gmail.com', 'Comas', 938831633),
('P060', '12345678', 'Aguilar Quispe', 'Vicente Javier', 67399259, 'M', '1978-04-17', 'aguvic@outlook.com', 'Comas', 944022220),
('P061', '12345678', 'Sánchez Vargas', 'Michael Diego', 51106577, 'M', '1971-12-05', 'sanmic@gmail.com', 'Miraflores', 937616239),
('P062', '12345678', 'García Salazar', 'Alba Antonia', 54082828, 'F', '1990-01-29', 'garalb@gmail.com', 'San Juan de Lurigancho', 952387571),
('P063', '12345678', 'Gómez Martínez', 'William Daniel', 42178444, 'M', '1978-01-18', 'gomwil@gmail.com', 'Punta Negra', 972907019),
('P064', '12345678', 'Huamán Ramírez', 'Cristóbal Nilton', 51626252, 'M', '1972-04-01', 'huacri@ymail.com', 'El Agustino', 924542098),
('P065', '12345678', 'Gonzales Morales', 'Olivia Emilia', 64964806, 'F', '1990-07-20', 'gonoli@gmail.com', 'Lince', 975745740),
('P066', '12345678', 'Martínez Flores', 'Noah Nilton', 70721746, 'M', '1980-11-14', 'marnoa@ymail.com', 'Independencia', 987719697),
('P067', '12345678', 'Gutiérrez Martínez', 'Sara Paula', 47043198, 'F', '1993-04-24', 'gutsar@gmail.com', 'Cieneguilla', 949984745),
('P068', '12345678', 'Huamán Morales', 'Emily Alba', 61307536, 'F', '1991-03-12', 'huaemi@outlook.com', 'San Luis', 946430210),
('P069', '12345678', 'Aguilar Córdova', 'Lucila Madison', 61023647, 'F', '1970-07-02', 'aguluc@outlook.com', 'Ancón', 949919755),
('P070', '12345678', 'Aguilar Romero', 'Nilton Noah', 70216810, 'M', '1975-02-09', 'agunil@gmail.com', 'Pueblo Libre', 957899034),
('P071', '12345678', 'Espinoza Pérez', 'Abigail', 53257010, 'F', '1981-03-10', 'espabi@outlook.com', 'Villa María del Triunfo', 955520332),
('P072', '12345678', 'Rojas López', 'Vicente Matías', 42496401, 'M', '1981-09-18', 'rojvic@ymail.com', 'San Miguel', 973156107),
('P073', '12345678', 'Castillo Ramos', 'Mason Jacob', 70016243, 'M', '1990-06-10', 'casmas@gmail.com', 'Pueblo Libre', 929887252),
('P074', '12345678', 'Espinoza Flores', 'Olivia Emma', 63014084, 'F', '1971-07-31', 'espoli@ymail.com', 'Chaclacayo', 995379137),
('P075', '12345678', 'Martínez Huamán', 'Martín Jacob', 54760473, 'M', '1985-09-01', 'marmar@outlook.com', 'Barranco', 932164380),
('P076', '12345678', 'Vargas Ramírez', 'Antonella Julia', 69496161, 'F', '1979-12-27', 'varant@gmail.com', 'Santa Rosa', 962141196),
('P077', '12345678', 'Condori Córdova', 'Martina Florencia', 51824850, 'F', '1975-07-09', 'conmar@outlook.com', 'Miraflores', 973818793),
('P078', '12345678', 'Castro Paredes', 'Agustín Mario', 51891305, 'M', '1992-07-15', 'casagu@gmail.com', 'Magdalena del Mar', 924016273),
('P079', '12345678', 'Salazar Rodríguez', 'Catalina Elizabeth', 42488217, 'F', '1978-08-07', 'salcat@outlook.com', 'Lurigancho', 946500665),
('P080', '12345678', 'Ramos Romero', 'Aiden Martín', 61945082, 'M', '1980-12-25', 'ramaid@ymail.com', 'Jesús María', 961959132),
('P081', '12345678', 'Aguilar Gómez', 'Mia Abigail', 47084215, 'F', '1990-03-18', 'agumia@gmail.com', 'San Borja', 993434302),
('P082', '12345678', 'Quispe Cruz', 'Alexander Maximiliano', 67214936, 'M', '1989-10-26', 'quiale@ymail.com', 'Independencia', 926759727),
('P083', '12345678', 'López Chávez', 'Alejandro Álvaro', 63399210, 'M', '1984-08-05', 'lopale@ymail.com', 'San Miguel', 923815832),
('P084', '12345678', 'Córdova Pérez', 'Pablo Agustín', 64453313, 'M', '1973-09-17', 'corpab@gmail.com', 'Punta Negra', 920179130),
('P085', '12345678', 'Paredes Aguilar', 'Daniel Ethan', 53321716, 'M', '1971-08-03', 'pardan@gmail.com', 'Ancón', 980951339),
('P086', '12345678', 'Fernández Chávez', 'Daniela Emily', 62346822, 'F', '1988-06-15', 'ferdan@ymail.com', 'Santa Anita', 924383926),
('P087', '12345678', 'García Vásquez', 'Martina Antonella', 68743391, 'F', '1980-01-17', 'garmar@ymail.com', 'Santa María del Mar', 944734692),
('P088', '12345678', 'Medina García', 'Joaquín William', 64664229, 'M', '1987-05-10', 'medjoa@gmail.com', 'San Borja', 961911300),
('P089', '12345678', 'Martínez Silva', 'Fernanda Emilia', 52501080, 'F', '1976-05-03', 'marfer@ymail.com', 'Santa Rosa', 963704912),
('P090', '12345678', 'Vásquez López', 'Matías Sebastián', 61271242, 'M', '1993-04-05', 'vasmat@outlook.com', 'El Agustino', 971753961),
('P091', '12345678', 'Gómez Romero', 'Javier Agustín', 56498432, 'M', '1976-07-07', 'gomjav@gmail.com', 'Ancón', 940207715),
('P092', '12345678', 'Sánchez Paredes', 'Elizabeth Abigail', 51622485, 'F', '1987-12-23', 'saneli@outlook.com', 'Pachacámac', 920004429),
('P093', '12345678', 'Medina Ramírez', 'Pablo', 61053780, 'M', '1988-03-13', 'medpab@ymail.com', 'Carabayllo', 972312882),
('P094', '12345678', 'Rodríguez Castillo', 'Joaquín William', 51677897, 'M', '1977-04-13', 'rodjoa@gmail.com', 'Surquillo', 958119103),
('P095', '12345678', 'Condori Silva', 'Isabella Sofia', 63354403, 'F', '1970-06-01', 'conisa@gmail.com', 'Puente Piedra', 956523437),
('P096', '12345678', 'Sánchez Martínez', 'Sebastian Julio', 61652819, 'M', '1975-12-20', 'sanseb@ymail.com', 'La Victoria', 954464653),
('P097', '12345678', 'Silva Reyes', 'Daniel Sebastián', 42291570, 'M', '1991-11-28', 'sildan@outlook.com', 'San Luis', 949178995),
('P098', '12345678', 'Gonzales Pérez', 'Álvaro Tomás', 60408191, 'M', '1978-03-29', 'gonalv@gmail.com', 'San Isidro', 987903000),
('P099', '12345678', 'Castro Condori', 'Max', 53504238, 'M', '1989-09-19', 'casmax@ymail.com', 'Lince', 984042887),
('P100', '12345678', 'Rojas Pérez', 'Joaquín Sebastián', 54685056, 'M', '1982-03-25', 'rojjoa@outlook.com', 'Pachacámac', 995109806),
('P101', '12345678', 'Reyes Rodríguez', 'Abigail Madison', 52907242, 'F', '1978-02-17', 'reyabi@ymail.com', 'San Martín de Porres', 972831230),
('P102', '12345678', 'Silva Reyes', 'Daniel Sebastián', 43442456, 'M', '1992-07-20', 'sildan@ymail.com', 'Ate', 944809373),
('P103', '12345678', 'Córdova Salazar', 'Olivia Isabella', 69588506, 'F', '1971-02-26', 'coroli@ymail.com', 'Villa María del Triunfo', 966111936),
('P104', '12345678', 'Martínez Quispe', 'Antonella Sara', 68010399, 'F', '1992-01-01', 'marant@outlook.com', 'Pueblo Libre', 932792404),
('P105', '12345678', 'Chirinos Cornejo', 'Carlos Daniel', 47921826, 'M', '1977-08-15', 'chicar@outlook.com', 'Cieneguilla', 985976944),
('P106', '12345678', 'Medina Garcia', 'Vanessa Alva', 66106994, 'F', '1973-10-03', 'medvan@gmail.com', 'Chorrillos', 974150880),
('P107', '12345678', 'Trujillo Morón', 'Pamela Alejandra', 70182885, 'F', '1990-11-18', 'trupam@gmail.com', 'Puente Piedra', 978746783),
('P108', '12345678', 'Trujillo Soto', 'Celeste Julieta', 61364788, 'F', '1991-01-14', 'trucel@gmail.com', 'Cieneguilla', 954328665),
('P109', '12345678', 'Quispe López', 'Abigail María', 50156117, 'F', '1972-04-21', 'quiabi@outlook.com', 'Pueblo Libre', 991580902),
('P110', '12345678', 'Castillo García', 'Nilton Matías', 67200140, 'M', '1975-11-13', 'casnil@outlook.com', 'San Juan de Lurigancho', 943322837),
('P111', '12345678', 'Romero De La Cruz', 'Lucila Abigail', 53855189, 'F', '1988-07-06', 'romluc@gmail.com', 'Santa Anita', 950005762),
('P112', '12345678', 'Rojas Pérez', 'Joaquín Sebastián', 46495839, 'M', '1976-03-24', 'rojjoa@outlook.com', 'Punta Hermosa', 928150440),
('P113', '12345678', 'Reyes Rodríguez', 'Abigail Madison', 62527091, 'F', '1973-04-30', 'reyabi@outlook.com', 'Santa Rosa', 938122587),
('P114', '12345678', 'Paredes Mamani', 'Daniela Florencia', 66073844, 'F', '1992-08-30', 'pardan@gmail.com', 'San Luis', 973855324),
('P115', '12345678', 'García Fernández', 'Martina Alba', 48971549, 'F', '1970-12-18', 'garmar@gmail.com', 'La Molina', 930013512),
('P116', '12345678', 'Castillo Gómez', 'Emilia Alba', 45655656, 'F', '1978-09-08', 'casemi@outlook.com', 'Magdalena del Mar', 959665793),
('P117', '12345678', 'Reyes Flores', 'Elizabeth Lucía', 66238936, 'F', '1990-04-28', 'reyeli@outlook.com', 'Breña', 934488023),
('P118', '12345678', 'Aguilar Mendoza', 'Hugo Sebastián', 57492547, 'M', '1979-11-11', 'aguhug@outlook.com', 'San Juan de Miraflores', 928227209),
('P119', '12345678', 'Delgado Cornejo', 'Alexandra', 65096817, 'F', '1976-09-06', 'delale@outlook.com', 'Magdalena del Mar', 993439224),
('P120', '12345678', 'Condori Medina', 'Maximiliano Mario', 51175973, 'M', '1980-09-22', 'conmax@gmail.com', 'Jesús María', 990624689),
('P121', '12345678', 'Mendoza Quispe', 'Maximiliano Jacob', 67576502, 'M', '1988-10-28', 'menmax@gmail.com', 'Comas', 949092448),
('P122', '12345678', 'Quispe García', 'Álvaro William', 64158215, 'M', '1977-08-08', 'quialv@ymail.com', 'San Juan de Miraflores', 940159250),
('P123', '12345678', 'Castillo García', 'Nilton Matías', 68023166, 'M', '1984-09-21', 'casnil@ymail.com', 'Puente Piedra', 927540963),
('P124', '12345678', 'García Díaz', 'Julia Abigail', 45078702, 'F', '1974-06-24', 'garjul@ymail.com', 'San Juan de Lurigancho', 994941724),
('P125', '12345678', 'Huamán Romero', 'Alba Valentina', 48501384, 'F', '1974-01-01', 'huaalb@outlook.com', 'Lurigancho', 972546287),
('P126', '12345678', 'Hinostrosa Quispe', 'Franco Francisco', 45451730, 'M', '1992-07-21', 'hinfra@gmail.com', 'Punta Negra', 931749033),
('P127', '12345678', 'Ramos Castillo', 'Carla Catalina', 51155179, 'F', '1976-04-26', 'ramcar@gmail.com', 'Chaclacayo', 939778705),
('P128', '12345678', 'Romero Díaz', 'Jacob Álvaro', 67119445, 'M', '1975-10-22', 'romjac@outlook.com', 'San Juan de Lurigancho', 929750399),
('P129', '12345678', 'Paredes Mamani', 'Daniela Florencia', 70445238, 'F', '1979-08-29', 'pardan@gmail.com', 'Ancón', 960418523),
('P130', '12345678', 'García Fernández', 'Martina Alba', 70036604, 'F', '1977-07-20', 'garmar@outlook.com', 'Carabayllo', 980777246),
('P131', '12345678', 'Castillo Gómez', 'Emilia Alba', 69538695, 'F', '1971-03-20', 'casemi@gmail.com', 'Lurigancho', 933829605),
('P132', '12345678', 'Vargas Rojas', 'William Mason', 53054982, 'M', '1979-04-06', 'varwil@gmail.com', 'La Molina', 941594200),
('P133', '12345678', 'Quispe Reyes', 'Sara María', 57458172, 'F', '1976-03-22', 'quisar@ymail.com', 'Ancón', 944743238),
('P134', '12345678', 'Vargas Ramos', 'Joaquín David', 61389371, 'M', '1993-03-16', 'varjoa@ymail.com', 'Ancón', 959744312),
('P135', '12345678', 'Torres Gonzales', 'Martina Sara', 70259000, 'F', '1972-07-10', 'tormar@ymail.com', 'Lima', 968678918),
('P136', '12345678', 'Condori Medina', 'Maximiliano Mario', 54267505, 'M', '1981-10-08', 'conmax@outlook.com', 'Lurigancho', 981452588),
('P137', '12345678', 'De La Cruz Cruz', 'Cristóbal Michael', 42316956, 'M', '1971-08-13', 'decri@ymail.com', 'Independencia', 933194924),
('P138', '12345678', 'Mamani Pérez', 'Mia Florencia', 42953211, 'F', '1987-07-28', 'mammia@ymail.com', 'Lince', 924502956),
('P139', '12345678', 'Quispe Paredes', 'Emilia Sophia', 59253725, 'F', '1982-06-24', 'quiemi@gmail.com', 'Lurín', 955802046),
('P140', '12345678', 'Quispe Rojas', 'William Alberto', 57984598, 'M', '1986-08-31', 'quiwil@gmail.com', 'Punta Hermosa', 951131258),
('P141', '12345678', 'Martínez Vásquez', 'Tomás Diego', 44496455, 'M', '1971-04-12', 'martom@outlook.com', 'Lince', 926952580),
('P142', '12345678', 'Rojas Castro', 'Bruno Palomini', 59232153, 'M', '1977-01-25', 'rojbru@gmail.com', 'Punta Negra', 948053223),
('P143', '12345678', 'Espinoza Romero', 'Ethan Matías', 60490075, 'M', '1981-12-01', 'espeth@ymail.com', 'Ate', 989828383),
('P144', '12345678', 'Aldana Fernández', 'Tomás Lorenzo', 45326344, 'M', '1969-06-24', 'aldtom@gmail.com', 'La Molina', 955542853),
('P145', '12345678', 'Tello Chirinos', 'Paula', 57206766, 'F', '1982-06-16', 'telpau@ymail.com', 'San Miguel', 952784372),
('P146', '12345678', 'Huamán Ramírez', 'Alexander William', 64170115, 'M', '1987-10-01', 'huaale@ymail.com', 'San Miguel', 950466131),
('P147', '12345678', 'Medina De La Cruz', 'Alejandro Cristóbal', 66928910, 'M', '1976-06-18', 'medale@ymail.com', 'Chorrillos', 950465304),
('P148', '12345678', 'Albana Castro', 'Jose Enrique', 45390692, 'M', '1989-01-10', 'albjos@gmail.com', 'Comas', 985618543),
('P149', '12345678', 'Gutierrez Chirinos', 'Lucila', 68456384, 'F', '1971-06-20', 'gutluc@gmail.com', 'Pueblo Libre', 948668093),
('P150', '12345678', 'Chirinos Cornejo', 'Aracelly', 43430406, 'F', '1989-12-21', 'chiara@gmail.com', 'San Martín de Porres', 927056162),
('P151', '12345678', 'Paredes Córdova', 'Emma Elizabeth', 44571525, 'F', '1981-03-23', 'paremm@outlook.com', 'Barranco', 924546442),
('P152', '12345678', 'Soria Inga', 'Katherine Rocio', 48397248, 'F', '1991-01-31', 'sorkat@gmail.com', 'San Luis', 935630284),
('P153', '12345678', 'Gutierrez Castro', 'Veronica', 55971917, 'F', '1993-07-16', 'gutver@outlook.com', 'Punta Hermosa', 979477407),
('P154', '12345678', 'Orellana Paucar', 'Rony', 65060014, 'M', '1977-01-31', 'oreron@gmail.com', 'Santiago de Surco', 945936851),
('P155', '12345678', 'Castro Albana', 'Renzo', 70264980, 'M', '1988-02-07', 'casren@gmail.com', 'Lima', 965820593),
('P156', '12345678', 'Tello Condori', 'Renato', 47413046, 'M', '1979-07-09', 'telren@gmail.com', 'San Juan de Lurigancho', 941822012),
('P157', '12345678', 'Ruiz Ramírez', 'Antonella Florencia', 56359837, 'F', '1988-01-09', 'ruiant@outlook.com', 'Villa María del Triunfo', 966106559),
('P158', '12345678', 'Condori Condorcanqui', 'Cristian Daniel', 46256064, 'M', '1978-06-18', 'concri@gmail.com', 'Santa María del Mar', 954482685),
('P159', '12345678', 'Paucar Gutierrez', 'Milagros', 67018552, 'F', '1991-06-05', 'paumil@ymail.com', 'Pachacámac', 930235181),
('P160', '12345678', 'Mendiola Castro', 'Nelson', 43656048, 'M', '1986-04-02', 'mennel@gmail.com', 'Ate', 973971945),
('P161', '12345678', 'Ramírez Ocupa', 'Alexandra', 49615447, 'F', '1978-02-28', 'ramale@outlook.com', 'Comas', 956830734),
('P162', '12345678', 'Paredes Flores', 'Sofia Abigail', 68059983, 'F', '1988-07-14', 'parsof@gmail.com', 'Independencia', 929705331),
('P163', '12345678', 'Davila Herrera', 'Vanessa', 57767049, 'F', '1985-07-06', 'davvan@outlook.com', 'Punta Negra', 927642271),
('P164', '12345678', 'Calderón Puelles', 'Daniela', 63581740, 'F', '1979-04-20', 'caldan@outlook.com', 'Cieneguilla', 927729030),
('P165', '12345678', 'Flores Salazar', 'Alexander Álvaro', 44869997, 'M', '1977-12-25', 'floale@outlook.com', 'San Bartolo', 931884090),
('P166', '12345678', 'Fernández Pérez', 'Tomás Joaquín', 70399377, 'M', '1973-03-06', 'fertom@outlook.com', 'El Agustino', 970945854),
('P167', '12345678', 'Davila Castro', 'Junior', 47065780, 'M', '1979-04-22', 'davjun@outlook.com', 'Breña', 960368896),
('P168', '12345678', 'Sánchez Silva', 'Sebastián Cristóbal', 44631579, 'M', '1976-05-08', 'sanseb@ymail.com', 'San Luis', 968608240),
('P169', '12345678', 'Hinostroza Muelle', 'Jhonatan', 60985497, 'M', '1991-03-10', 'hinjho@gmail.com', 'Puente Piedra', 962629564),
('P170', '12345678', 'Castro Silva', 'Jennifer', 49158248, 'F', '1969-03-26', 'casjen@outlook.com', 'Pucusana', 935357145),
('P171', '12345678', 'Castro Rivera', 'Pablo Martín', 49241475, 'M', '1990-11-08', 'caspab@outlook.com', 'Barranco', 946777852),
('P172', '12345678', 'Córdova López', 'Diego Michael', 68589962, 'M', '1981-12-17', 'cordie@gmail.com', 'El Agustino', 920094931),
('P173', '12345678', 'Gómez Romero', 'Álvaro Alejandro', 68143358, 'M', '1992-04-04', 'gomalv@outlook.com', 'Punta Negra', 921823426),
('P174', '12345678', 'Vargas Condori', 'Vicente Álvaro', 44752317, 'M', '1980-06-20', 'varvic@gmail.com', 'Lurín', 936811921),
('P175', '12345678', 'Ruiz Gonzales', 'Álvaro Hugo', 69312916, 'M', '1988-07-08', 'ruialv@ymail.com', 'Los Olivos', 930884623),
('P176', '12345678', 'De La Cruz Cruz', 'Cristóbal Michael', 46429223, 'M', '1970-07-28', 'decri@ymail.com', 'San Isidro', 991783520),
('P177', '12345678', 'Martínez Vásquez', 'Tomás Diego', 60953851, 'M', '1972-05-05', 'martom@gmail.com', 'Santa Rosa', 982324760),
('P178', '12345678', 'Flores Gutiérrez', 'Cristóbal Ethan', 47835479, 'M', '1990-06-19', 'flocri@outlook.com', 'Rímac', 938784668),
('P179', '12345678', 'Castillo Quispe', 'Matías Pablo', 62227039, 'M', '1982-07-20', 'casmat@outlook.com', 'Lince', 994162538),
('P180', '12345678', 'Condori Martínez', 'Ethan Benjamín', 60392443, 'M', '1980-10-23', 'coneth@outlook.com', 'San Juan de Lurigancho', 952513475),
('P181', '12345678', 'Medina De La Cruz', 'Alejandro Cristóbal', 44219774, 'M', '1970-12-25', 'medale@ymail.com', 'Carabayllo', 987028523),
('P182', '12345678', 'Reyes Flores', 'Diego Daniel', 66264166, 'M', '1978-03-16', 'reydie@outlook.com', 'Santa Anita', 922556438),
('P183', '12345678', 'Aguilar Reyes', 'Ethan Martín', 47592418, 'M', '1976-08-26', 'agueth@ymail.com', 'Santiago de Surco', 944419173),
('P184', '12345678', 'Cruz Vargas', 'Michael Benjamín', 58408510, 'M', '1993-11-02', 'crumic@outlook.com', 'San Martín de Porres', 934927356),
('P185', '12345678', 'Vásquez Reyes', 'William Benjamín', 56683550, 'M', '1979-01-06', 'vaswil@outlook.com', 'Puente Piedra', 932413011),
('P186', '12345678', 'Huamán Silva', 'Maximiliano Hugo', 63126596, 'M', '1980-03-13', 'huamax@gmail.com', 'Santa Rosa', 945354726),
('P187', '12345678', 'Paredes Gonzales', 'Sebastián Tomás', 55861705, 'M', '1971-09-06', 'parseb@ymail.com', 'Lima', 983788670),
('P188', '12345678', 'Córdova Torres', 'Pablo Noah', 61560257, 'M', '1993-10-29', 'corpab@gmail.com', 'San Martín de Porres', 988395432),
('P189', '12345678', 'Rodríguez Córdova', 'Cristóbal Martín', 58041107, 'M', '1990-07-28', 'rodcri@gmail.com', 'San Borja', 977024843),
('P190', '12345678', 'Medina Ramírez', 'Ethan Aiden', 49583447, 'M', '1992-06-30', 'medeth@outlook.com', 'Independencia', 946900081),
('P191', '12345678', 'Fernández Pérez', 'Tomás Joaquín', 52875319, 'M', '1983-12-09', 'fertom@ymail.com', 'Ancón', 967787571),
('P192', '12345678', 'Sánchez Silva', 'Sebastián Cristóbal', 54783053, 'M', '1975-03-18', 'sanseb@gmail.com', 'San Miguel', 992639932),
('P193', '12345678', 'Martínez Gonzales', 'Pablo Noah', 44285396, 'M', '1992-09-05', 'marpab@outlook.com', 'San Luis', 934934770),
('P194', '12345678', 'Mendoza García', 'Joaquín Alexander', 46144837, 'M', '1988-11-11', 'menjoa@outlook.com', 'San Borja', 988953969),
('P195', '12345678', 'Gómez Romero', 'Álvaro Alejandro', 45719001, 'M', '1979-07-13', 'gomalv@ymail.com', 'San Isidro', 944379844),
('P196', '12345678', 'Vargas Condori', 'Pablo Álvaro', 62788782, 'M', '1974-05-28', 'varpab@ymail.com', 'Jesús María', 935193541),
('P197', '12345678', 'Quispe Castillo', 'Benjamín Hugo', 58398099, 'M', '1986-07-26', 'quiben@outlook.com', 'Pachacámac', 983555385),
('P198', '12345678', 'Ramos Martínez', 'Michael Martín', 55926194, 'M', '1970-10-13', 'rammic@outlook.com', 'Carabayllo', 989934025),
('P199', '12345678', 'Condori Martínez', 'Ethan Benjamín', 57917433, 'M', '1969-03-10', 'coneth@ymail.com', 'San Borja', 954275529),
('P200', '12345678', 'Aguilar Reyes', 'Ethan Martín', 53927281, 'M', '1988-06-26', 'agueth@ymail.com', 'Pachacámac', 993873345),
('P201', '12345678', 'Vásquez Reyes', 'William Benjamín', 46024179, 'M', '1982-10-05', 'vaswil@ymail.com', 'Barranco', 989178632),
('P202', '12345678', 'Huamán Silva', 'Maximiliano Hugo', 42631716, 'M', '1975-01-06', 'huamax@outlook.com', 'Surquillo', 934369748),
('P203', '12345678', 'Medina Ramírez', 'Ethan Aiden', 43678854, 'M', '1984-12-06', 'medeth@outlook.com', 'Breña', 944087468),
('P204', '12345678', 'Fernández Gonzales', 'Diego Noah', 48332117, 'M', '1991-08-25', 'ferdie@ymail.com', 'Breña', 986800664),
('P205', '12345678', 'Hoyos Paredes', 'Estefani', 48444596, 'F', '1983-04-06', 'hoyest@gmail.com', 'Rímac', 974754174),
('P206', '12345678', 'Mendoza Gutiérrez', 'Ethan Javier', 63096855, 'M', '1987-10-03', 'meneth@outlook.com', 'Villa El Salvador', 940822538),
('P207', '12345678', 'Mendoza García', 'Joaquín Alexander', 68326772, 'M', '1987-08-01', 'menjoa@ymail.com', 'Breña', 986213769),
('P208', '12345678', 'Quispe Castillo', 'Benjamín Hugo', 68290407, 'M', '1993-06-29', 'quiben@ymail.com', 'Barranco', 992643711),
('P209', '12345678', 'Vásquez Pérez', 'Tomás Pablo', 66664546, 'M', '1984-08-01', 'vastom@outlook.com', 'Carabayllo', 970241128),
('P210', '12345678', 'Silva Córdova', 'Alejandro Javier', 52386767, 'M', '1969-11-28', 'silale@gmail.com', 'Lurín', 936154901),
('P211', '12345678', 'Medina Torres', 'Alejandro Matías', 58521016, 'M', '1969-03-28', 'medale@ymail.com', 'Villa El Salvador', 938868882),
('P212', '12345678', 'Silva Chávez', 'Daniel Alejandro', 60615034, 'M', '1982-08-06', 'sildan@outlook.com', 'Punta Negra', 952987169),
('P213', '12345678', 'Vásquez Pérez', 'Tomás Pablo', 49825164, 'M', '1992-10-10', 'vastom@ymail.com', 'San Miguel', 949502195),
('P214', '12345678', 'Silva Córdova', 'Alejandro Javier', 53480567, 'M', '1972-09-05', 'silale@outlook.com', 'Ancón', 990221168),
('P215', '12345678', 'Salazar Castro', 'Joaquín Michael', 42538797, 'M', '1993-01-20', 'saljoa@gmail.com', 'La Molina', 921584235),
('P216', '12345678', 'Salazar Castro', 'Joaquín Michael', 43200240, 'M', '1986-08-09', 'saljoa@ymail.com', 'Pueblo Libre', 959690428),
('P217', '12345678', 'Huamán Díaz', 'Mario Joaquín', 43923362, 'M', '1972-06-16', 'huamar@outlook.com', 'Lurigancho', 935720506),
('P218', '12345678', 'Rivera Ruiz', 'Maximiliano Michael', 68925977, 'M', '1992-12-20', 'rivmax@outlook.com', 'Chaclacayo', 982710584),
('P219', '12345678', 'Ramos Gonzales', 'Hugo Benjamín', 54124474, 'M', '1972-02-06', 'ramhug@gmail.com', 'Santa Rosa', 982300940),
('P220', '12345678', 'Huamán Díaz', 'Mario Joaquín', 68799066, 'M', '1973-04-03', 'huamar@outlook.com', 'Punta Negra', 958459127),
('P221', '12345678', 'García Rodríguez', 'Matías Maximiliano', 69745998, 'M', '1976-11-23', 'garmat@outlook.com', 'San Luis', 969231650),
('P222', '12345678', 'Rivera Ruiz', 'Maximiliano Michael', 57428423, 'M', '1990-04-12', 'rivmax@gmail.com', 'Magdalena del Mar', 938384496),
('P223', '12345678', 'Quispe Condori', 'Ethan Hugo', 56673190, 'M', '1986-09-30', 'quieth@outlook.com', 'El Agustino', 947632439),
('P224', '12345678', 'Castillo Sánchez', 'Joaquín David', 53436215, 'M', '1970-12-26', 'casjoa@gmail.com', 'San Isidro', 936756729),
('P225', '12345678', 'Mendoza Chávez', 'Adrián Tomás', 51340901, 'M', '1972-03-05', 'menadr@gmail.com', 'Punta Negra', 966293691);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla sysdiagrams
--
-- Creación: 16-11-2024 a las 22:58:23
--

CREATE TABLE sysdiagrams (
  name varchar(160) NOT NULL,
  principal_id varchar(4) NOT NULL,
  diagram_id int(11) NOT NULL,
  version int(11) DEFAULT NULL,
  definition longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla usuario
--
-- Creación: 16-11-2024 a las 22:58:23
--

CREATE TABLE usuario (
  IdUsuario varchar(4) NOT NULL,
  Password varchar(8) NOT NULL,
  Rol varchar(75) NOT NULL,
  Apellidos varchar(75) NOT NULL,
  Nombres varchar(75) NOT NULL,
  Correo varchar(75) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla usuario
--

INSERT INTO usuario (IdUsuario, Password, Rol, Apellidos, Nombres, Correo) VALUES
('U001', '12345678', 'general', 'Condori Quispe', 'Martín Tomás', 'martin@gmail.com'),
('U002', '12345678', 'administrativo', 'Romero Castillo', 'Raúl Daniel', 'raul@gmail.com'),
('U003', '12345678', 'administrativo', 'Quispe Chirinos', 'Alejandro Michael', 'alejandro@gmail.com'),
('U004', '12345678', 'administrativo', 'Reyes Torres', 'Edgard Junior', 'edgard@gmail.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla cita
--
ALTER TABLE cita
  ADD PRIMARY KEY (IdCita),
  ADD KEY FK_Paciente (FK_Paciente) USING BTREE,
  ADD KEY FK_Especialidad (FK_Especialidad) USING BTREE,
  ADD KEY FK_Medico (FK_Medico) USING BTREE;

--
-- Indices de la tabla especialidad
--
ALTER TABLE especialidad
  ADD PRIMARY KEY (IdEspecialidad);

--
-- Indices de la tabla espmed
--
ALTER TABLE espmed
  ADD PRIMARY KEY (IdEspmed),
  ADD KEY FK_Especialidad (FK_Especialidad) USING BTREE,
  ADD KEY FK_Medico (FK_Medico) USING BTREE;

--
-- Indices de la tabla medico
--
ALTER TABLE medico
  ADD PRIMARY KEY (IdMedico);

--
-- Indices de la tabla paciente
--
ALTER TABLE paciente
  ADD PRIMARY KEY (IdPaciente);

--
-- Indices de la tabla sysdiagrams
--
ALTER TABLE sysdiagrams
  ADD PRIMARY KEY (diagram_id),
  ADD UNIQUE KEY principal_id (principal_id) USING BTREE;

--
-- Indices de la tabla usuario
--
ALTER TABLE usuario
  ADD PRIMARY KEY (IdUsuario);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla cita
--
ALTER TABLE cita
  ADD CONSTRAINT cita_ibfk_1 FOREIGN KEY (FK_Paciente) REFERENCES paciente (IdPaciente) ON UPDATE CASCADE,
  ADD CONSTRAINT cita_ibfk_2 FOREIGN KEY (FK_Especialidad) REFERENCES especialidad (IdEspecialidad) ON UPDATE CASCADE,
  ADD CONSTRAINT cita_ibfk_3 FOREIGN KEY (FK_Medico) REFERENCES medico (IdMedico) ON UPDATE CASCADE;

--
-- Filtros para la tabla espmed
--
ALTER TABLE espmed
  ADD CONSTRAINT espmed_ibfk_1 FOREIGN KEY (FK_Especialidad) REFERENCES especialidad (IdEspecialidad) ON UPDATE CASCADE,
  ADD CONSTRAINT espmed_ibfk_2 FOREIGN KEY (FK_Medico) REFERENCES medico (IdMedico) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
