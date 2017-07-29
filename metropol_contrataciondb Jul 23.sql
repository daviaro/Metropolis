-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-07-2017 a las 05:13:00
-- Versión del servidor: 10.1.16-MariaDB
-- Versión de PHP: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `metropol_contrataciondb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `acceso`
--

CREATE TABLE `acceso` (
  `idAcceso` int(11) NOT NULL,
  `usuario_idUsuario` int(11) DEFAULT NULL,
  `ip` varchar(39) NOT NULL,
  `fechaHora` date NOT NULL,
  `origen` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `busqueda`
--

CREATE TABLE `busqueda` (
  `idBusqueda` int(11) NOT NULL,
  `usuario_idUsuario` int(11) DEFAULT NULL,
  `valor` varchar(45) NOT NULL,
  `fechaHora` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `busqueda`
--

INSERT INTO `busqueda` (`idBusqueda`, `usuario_idUsuario`, `valor`, `fechaHora`) VALUES
(1, NULL, 'desarrollo', '2017-01-24'),
(8, NULL, 'pintu', '2017-01-24'),
(9, NULL, 'pintura', '2017-01-24'),
(10, NULL, 'softw', '2017-02-13'),
(11, NULL, 'software', '2017-02-13'),
(12, NULL, 'software', '2017-02-20'),
(13, NULL, 'software', '2017-03-01'),
(29, NULL, 'desarrollo', '2017-03-01'),
(34, NULL, 'desa', '2017-03-01'),
(35, NULL, 'des', '2017-03-01'),
(36, NULL, 'software', '2017-03-01'),
(37, NULL, 'desarrollo', '2017-06-24'),
(38, NULL, 'desa', '2017-07-04'),
(39, NULL, 'des', '2017-07-04'),
(40, NULL, 'repe', '2017-07-04'),
(41, NULL, 'repell', '2017-07-04'),
(42, NULL, 'rep', '2017-07-04'),
(43, NULL, 'rep', '2017-07-15');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `categoria_idCategoria` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `nombre`, `estado`, `categoria_idCategoria`) VALUES
(1, 'Sistemas', 1, NULL),
(2, 'Desarrollo Web', 1, 1),
(3, 'Diseño Base de Datos', 1, 1),
(4, 'Construcción', 1, NULL),
(5, 'Obra blanca', 1, 4),
(6, 'educacion', 1, NULL),
(7, 'ingles', 1, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comision`
--

CREATE TABLE `comision` (
  `idcomision` int(11) NOT NULL,
  `valor` decimal(3,3) NOT NULL,
  `fecha_creacion` date NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `comision`
--

INSERT INTO `comision` (`idcomision`, `valor`, `fecha_creacion`, `estado`) VALUES
(1, '0.055', '2016-06-01', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contrato`
--

CREATE TABLE `contrato` (
  `idContrato` int(11) NOT NULL,
  `usuario_idUsuarioModificador` int(11) DEFAULT NULL,
  `fecha` date NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `calificacion` int(11) DEFAULT NULL,
  `comentario` varchar(45) DEFAULT NULL,
  `valor_total` int(11) NOT NULL,
  `cotizacion_idCotizacion` int(11) NOT NULL,
  `liquidacion_idliquidacion` int(11) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `contrato`
--

INSERT INTO `contrato` (`idContrato`, `usuario_idUsuarioModificador`, `fecha`, `direccion`, `calificacion`, `comentario`, `valor_total`, `cotizacion_idCotizacion`, `liquidacion_idliquidacion`, `estado`) VALUES
(1, NULL, '2016-11-29', 'prueba de descripción de trabajo', 3, NULL, 1600000, 1, NULL, 1),
(2, NULL, '2016-11-29', 'prueba de descripción de trabajo', 3, NULL, 1600000, 1, NULL, 1),
(3, NULL, '2016-11-29', 'Prueba ingreso de contrato', 5, NULL, 1125000, 5, 5, 1),
(4, NULL, '2016-10-29', 'prueba 2', 4, NULL, 160000, 6, 5, 1),
(5, 1, '2017-01-19', 'calle escondida', 3, 'me gusto', 2000000, 1, 5, 1),
(6, 2, '2017-02-17', 'carrera con calle', 5, NULL, 200000, 3, 5, 1),
(7, NULL, '2017-06-09', 'Prueba Junio 6', NULL, NULL, 140000, 13, NULL, 1),
(8, NULL, '2017-06-09', 'Prueba Junio 6', NULL, NULL, 140000, 13, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cotizacion`
--

CREATE TABLE `cotizacion` (
  `idCotizacion` int(11) NOT NULL,
  `valorPresupuesto` int(11) NOT NULL,
  `unidadesTrabajo` int(11) NOT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `fechaTrabajo` date NOT NULL,
  `fechaSolicitud` date NOT NULL,
  `Respuesta` tinyint(1) DEFAULT NULL,
  `valorContrapropuesta` int(11) DEFAULT NULL,
  `fechaContraPropuestaRespuesta` date DEFAULT NULL,
  `fechaRespuesta` date DEFAULT NULL,
  `fechaEstricta` date DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `oferta_idOferta` int(11) NOT NULL,
  `usuario_idUsuarioContratador` int(11) NOT NULL,
  `comision_idcomision` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cotizacion`
--

INSERT INTO `cotizacion` (`idCotizacion`, `valorPresupuesto`, `unidadesTrabajo`, `descripcion`, `fechaTrabajo`, `fechaSolicitud`, `Respuesta`, `valorContrapropuesta`, `fechaContraPropuestaRespuesta`, `fechaRespuesta`, `fechaEstricta`, `estado`, `oferta_idOferta`, `usuario_idUsuarioContratador`, `comision_idcomision`) VALUES
(1, 80000, 20, 'prueba de descripción de trabajo', '2016-08-24', '2016-08-17', 1, NULL, NULL, '2016-08-17', NULL, 0, 1, 1, 1),
(2, 400000, 20, 'software', '2016-10-26', '2016-10-26', 1, NULL, NULL, '2016-12-22', NULL, 0, 1, 1, 1),
(3, 1000000, 100, 'Prueba contraoferta', '2016-08-24', '2016-11-23', 1, 85000, NULL, '2017-01-24', NULL, 0, 1, 1, 1),
(4, 70000, 20, 'trabajo', '2016-11-24', '2016-11-23', 1, 75000, '2016-11-25', '2016-11-23', NULL, 0, 1, 1, 1),
(5, 75000, 15, 'Prueba ingreso de contrato', '2016-11-30', '2016-11-29', 1, NULL, NULL, '2016-11-29', NULL, 0, 1, 1, 1),
(6, 80000, 2, 'prueba 2', '2016-11-30', '2016-11-29', 1, NULL, NULL, '2016-11-29', NULL, 0, 1, 1, 1),
(7, 80000, 12, 'enero prueba', '2017-01-19', '2017-01-05', 1, NULL, NULL, '2017-01-05', NULL, 0, 1, 1, 1),
(8, 20000, 6, 'mucho repello', '2017-03-30', '2017-03-29', 1, NULL, NULL, '2017-03-29', NULL, 0, 2, 2, 1),
(9, 20000, 5, 'mas repello', '2017-03-30', '2017-03-29', 1, NULL, NULL, '2017-03-29', NULL, 0, 2, 2, 1),
(10, 60000, 3, 'Trabajo de repello', '2017-05-10', '2017-05-08', 0, NULL, NULL, NULL, NULL, 0, 2, 2, 1),
(11, 20000, 2, 'algo', '2017-05-11', '2017-05-09', 0, NULL, NULL, NULL, NULL, 0, 2, 2, 1),
(12, 50000, 3, 'Quiero una página bien cool', '2017-06-07', '2017-06-05', 1, 65000, '2017-06-07', '2017-06-05', NULL, 0, 1, 3, 1),
(13, 70000, 2, 'Prueba Junio 6', '2017-06-23', '2017-06-09', 1, NULL, NULL, '2017-06-09', NULL, 0, 1, 1, 1),
(14, 60000, 2, 'PRUEBA RECHAZAR', '2017-06-13', '2017-06-11', NULL, NULL, NULL, NULL, NULL, 0, 1, 1, 1),
(15, 80000, 40, 'PRUEBA 2 RECHAZO', '2017-06-13', '2017-06-11', 1, 81000, '2017-06-23', '2017-06-11', NULL, 0, 1, 1, 1),
(16, 19000, 40, 'trabajo de repello prueba pago', '2017-06-22', '2017-06-21', 1, NULL, NULL, '2017-06-21', NULL, 0, 2, 2, 1),
(17, 66000, 20, 'Prueba de Propuestas', '2017-06-28', '2017-06-27', 0, NULL, NULL, NULL, NULL, 0, 1, 1, 1),
(18, 80000, 120, 'Prueba  - Oferta sin respuesta', '2017-07-22', '2017-07-16', 1, 70000, '2017-07-29', '2017-07-20', NULL, 0, 1, 3, 1),
(19, 70000, 120, 'prueba - propuesta sin respuesta', '2017-07-22', '2017-07-16', 1, 65000, '2017-07-28', '2017-07-20', NULL, 0, 1, 3, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `etiqueta`
--

CREATE TABLE `etiqueta` (
  `idEtiqueta` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `categoria_idCategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `etiqueta`
--

INSERT INTO `etiqueta` (`idEtiqueta`, `nombre`, `estado`, `categoria_idCategoria`) VALUES
(2, 'Java', 1, 2),
(3, 'SQL', 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagenes`
--

CREATE TABLE `imagenes` (
  `idImagenes` int(11) NOT NULL,
  `url` varchar(100) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `portafolio_idPortafolio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jornada`
--

CREATE TABLE `jornada` (
  `idJornada` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `jornada`
--

INSERT INTO `jornada` (`idJornada`, `nombre`) VALUES
(1, 'Diurna'),
(2, 'Nocturna'),
(3, 'Tiempo Completo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `liquidacion`
--

CREATE TABLE `liquidacion` (
  `idliquidacion` int(11) NOT NULL,
  `comision` int(11) NOT NULL,
  `subtotal` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `pago` tinyint(1) NOT NULL DEFAULT '0',
  `fecha_pago` date DEFAULT NULL,
  `fecha_liquidacion` date NOT NULL,
  `usuario_idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `liquidacion`
--

INSERT INTO `liquidacion` (`idliquidacion`, `comision`, `subtotal`, `total`, `pago`, `fecha_pago`, `fecha_liquidacion`, `usuario_idUsuario`) VALUES
(5, 70675, 1214325, 1285000, 0, NULL, '2018-06-01', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medicion_trabajo`
--

CREATE TABLE `medicion_trabajo` (
  `idMedicionTrabajo` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `medicion_trabajo`
--

INSERT INTO `medicion_trabajo` (`idMedicionTrabajo`, `nombre`, `estado`) VALUES
(1, 'Hora', 1),
(2, 'Complejidad', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oferta`
--

CREATE TABLE `oferta` (
  `idOferta` int(11) NOT NULL,
  `costo` int(11) NOT NULL,
  `fecha_creacion` date NOT NULL,
  `fecha_ultima_actualizacion` date DEFAULT NULL,
  `fecha_limite` date DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `usuario_idUsuarioOferta` int(11) NOT NULL,
  `trabajo_idTrabajo` int(11) NOT NULL,
  `Jornada_idJornada` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `oferta`
--

INSERT INTO `oferta` (`idOferta`, `costo`, `fecha_creacion`, `fecha_ultima_actualizacion`, `fecha_limite`, `estado`, `usuario_idUsuarioOferta`, `trabajo_idTrabajo`, `Jornada_idJornada`) VALUES
(1, 80000, '2016-08-17', NULL, '2017-10-31', 1, 2, 1, 2),
(2, 20000, '2017-01-05', NULL, NULL, 1, 1, 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `valor_pago` int(11) NOT NULL,
  `numero_autorizacion` varchar(45) NOT NULL,
  `respuesta_del_servicio_web` varchar(100) NOT NULL,
  `fecha_creacion` date NOT NULL,
  `fecha_ultima_actualizacion` date DEFAULT NULL,
  `contrato_idContrato` int(11) NOT NULL,
  `tipo_pago_idTipoPago` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`idPago`, `fecha`, `valor_pago`, `numero_autorizacion`, `respuesta_del_servicio_web`, `fecha_creacion`, `fecha_ultima_actualizacion`, `contrato_idContrato`, `tipo_pago_idTipoPago`) VALUES
(1, '2018-05-11', 160000, '00000000', '4-APPROVED', '2016-11-29', NULL, 4, 2),
(2, '2017-09-06', 140000, '00000000', '4-APPROVED', '2017-06-09', NULL, 7, 2),
(3, '2017-09-06', 140000, '00000000', '4-APPROVED', '2017-06-09', NULL, 8, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `portafolio`
--

CREATE TABLE `portafolio` (
  `idPortafolio` int(11) NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `fecha_creacion` date NOT NULL,
  `fecha_ultima_actualizacion` date DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `oferta_idOferta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`idRol`, `nombre`, `estado`) VALUES
(1, 'Administrador', 1),
(2, 'Empleado', 1),
(3, 'Usuario', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol_usuario`
--

CREATE TABLE `rol_usuario` (
  `usuario_idUsuario` int(11) NOT NULL,
  `rol_idRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `rol_usuario`
--

INSERT INTO `rol_usuario` (`usuario_idUsuario`, `rol_idRol`) VALUES
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 3),
(4, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_pago`
--

CREATE TABLE `tipo_pago` (
  `idTipoPago` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipo_pago`
--

INSERT INTO `tipo_pago` (`idTipoPago`, `nombre`, `estado`) VALUES
(1, 'PSE', 1),
(2, 'CREDIT_CARD', 1),
(3, 'CASH', 1),
(4, 'REFERENCED', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo`
--

CREATE TABLE `trabajo` (
  `idTrabajo` int(11) NOT NULL,
  `titulo` varchar(45) NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `fecha_creacion` date NOT NULL,
  `fecha_ultima_actualizacion` date DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `categoria_idCategoria` int(11) NOT NULL,
  `medicion_trabajo_idMedicionTrabajo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `trabajo`
--

INSERT INTO `trabajo` (`idTrabajo`, `titulo`, `descripcion`, `fecha_creacion`, `fecha_ultima_actualizacion`, `estado`, `categoria_idCategoria`, `medicion_trabajo_idMedicionTrabajo`) VALUES
(1, 'Desarrollo de Software', 'Desarrolla paginas web', '2016-05-01', NULL, 1, 2, 1),
(2, 'Repello', 'Repello obra blanca', '2017-01-05', NULL, 1, 5, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubicacion`
--

CREATE TABLE `ubicacion` (
  `idUbicacion` int(11) NOT NULL,
  `barrio` varchar(45) NOT NULL,
  `latitud` float DEFAULT NULL,
  `longitud` float DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ubicacion`
--

INSERT INTO `ubicacion` (`idUbicacion`, `barrio`, `latitud`, `longitud`, `estado`) VALUES
(1, 'Melendez', NULL, NULL, 1),
(2, 'Caney', NULL, NULL, 1),
(3, 'El Ingenio', NULL, NULL, 1),
(4, 'La Playa', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `email` varchar(45) NOT NULL,
  `emailAlterno` varchar(45) DEFAULT NULL,
  `emailAlterno2` varchar(45) DEFAULT NULL,
  `contrasena` varchar(45) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `documento_identidad` int(11) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `foto` varchar(100) NOT NULL,
  `perfil` varchar(300) DEFAULT NULL,
  `experiencia` varchar(300) DEFAULT NULL,
  `profesion` varchar(300) DEFAULT NULL,
  `fecha_creacion` date NOT NULL,
  `fecha_actualizacion` date DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT '1',
  `ubicacion_idUbicacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `email`, `emailAlterno`, `emailAlterno2`, `contrasena`, `nombres`, `apellidos`, `documento_identidad`, `telefono`, `direccion`, `foto`, `perfil`, `experiencia`, `profesion`, `fecha_creacion`, `fecha_actualizacion`, `estado`, `ubicacion_idUbicacion`) VALUES
(1, 'andresfvalenciar123@gmail.com', '', NULL, 'andres123', 'Andres Felipe', 'Valencia Rivera', 1113664970, '3175211450', 'Cra 66#4-70', '', '', 'Mucha experiencia', '', '2016-07-04', NULL, 1, 1),
(2, 'jotamarios@gmail.com', '', '', '192837465', 'Jorge mario', 'Serrate', 1130586245, '3245313', 'Cra 67# 3C-15 Apto 502', '/images/Profile/1130586245.jpg', 'Ingeniero de sistemas con 9 años de experiencia en análisis, diseño y desarrollo de aplicaciones. Gestión de proyectos, coordinación de equipos de trabajo. Conocimientos en html, java, .net, php, sql, sql server, mysql.', 'He trabajado en muchos proyectos de implementación de portales web, sitios de comercio electrónico, seguridad web, aplicaciones móviles, de escritorio, servicios web, integración de aplicaciones mediante FTP, ODBC, SOAP, Rest, et.', 'Ingeniero informático de la universidad autónoma de occidente, magister de ciencias de la computación.', '2016-08-01', '2017-06-05', 1, 1),
(3, 'pepito@gmail.com', '', '', 'pepito123', 'pepito', 'perez', 12345678, '2343221', 'calle 23 # 34-12', '', NULL, NULL, NULL, '2016-10-18', NULL, 1, 3),
(4, 'ingrid.paola.rios@gmail.com', 'email@gmail.com', '', '12345678', 'ingrid', 'rios', 1032417110, '3302323', 'cra 32 # 12-22', '', NULL, NULL, NULL, '2016-12-04', NULL, 1, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `acceso`
--
ALTER TABLE `acceso`
  ADD PRIMARY KEY (`idAcceso`),
  ADD KEY `fk_acceso_usuario1_idx` (`usuario_idUsuario`);

--
-- Indices de la tabla `busqueda`
--
ALTER TABLE `busqueda`
  ADD PRIMARY KEY (`idBusqueda`),
  ADD KEY `fk_busqueda_usuario1_idx` (`usuario_idUsuario`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`),
  ADD KEY `fk_categoria_categoria1_idx` (`categoria_idCategoria`);

--
-- Indices de la tabla `comision`
--
ALTER TABLE `comision`
  ADD PRIMARY KEY (`idcomision`);

--
-- Indices de la tabla `contrato`
--
ALTER TABLE `contrato`
  ADD PRIMARY KEY (`idContrato`),
  ADD KEY `fk_contrato_usuario2_idx` (`usuario_idUsuarioModificador`),
  ADD KEY `fk_contrato_cotizacion1_idx` (`cotizacion_idCotizacion`),
  ADD KEY `fk_contrato_liquidacion1_idx` (`liquidacion_idliquidacion`);

--
-- Indices de la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  ADD PRIMARY KEY (`idCotizacion`),
  ADD KEY `fk_cotizacion_oferta1_idx` (`oferta_idOferta`),
  ADD KEY `fk_cotizacion_usuario1_idx` (`usuario_idUsuarioContratador`),
  ADD KEY `fk_cotizacion_comision1_idx` (`comision_idcomision`);

--
-- Indices de la tabla `etiqueta`
--
ALTER TABLE `etiqueta`
  ADD PRIMARY KEY (`idEtiqueta`),
  ADD KEY `fk_etiqueta_categoria1_idx` (`categoria_idCategoria`);

--
-- Indices de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD PRIMARY KEY (`idImagenes`),
  ADD KEY `fk_Imagenes_portafolio1_idx` (`portafolio_idPortafolio`);

--
-- Indices de la tabla `jornada`
--
ALTER TABLE `jornada`
  ADD PRIMARY KEY (`idJornada`);

--
-- Indices de la tabla `liquidacion`
--
ALTER TABLE `liquidacion`
  ADD PRIMARY KEY (`idliquidacion`),
  ADD KEY `fk_liquidacion_usuario1_idx` (`usuario_idUsuario`);

--
-- Indices de la tabla `medicion_trabajo`
--
ALTER TABLE `medicion_trabajo`
  ADD PRIMARY KEY (`idMedicionTrabajo`);

--
-- Indices de la tabla `oferta`
--
ALTER TABLE `oferta`
  ADD PRIMARY KEY (`idOferta`),
  ADD KEY `fk_oferta_usuario1_idx` (`usuario_idUsuarioOferta`),
  ADD KEY `fk_oferta_trabajo1_idx` (`trabajo_idTrabajo`),
  ADD KEY `fk_oferta_Jornada1_idx` (`Jornada_idJornada`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`idPago`),
  ADD KEY `fk_pago_contrato1_idx` (`contrato_idContrato`),
  ADD KEY `fk_pago_tipo_pago1_idx` (`tipo_pago_idTipoPago`);

--
-- Indices de la tabla `portafolio`
--
ALTER TABLE `portafolio`
  ADD PRIMARY KEY (`idPortafolio`),
  ADD KEY `fk_portafolio_oferta1_idx` (`oferta_idOferta`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`idRol`);

--
-- Indices de la tabla `rol_usuario`
--
ALTER TABLE `rol_usuario`
  ADD PRIMARY KEY (`usuario_idUsuario`,`rol_idRol`),
  ADD KEY `fk_rol_usuario_usuario1_idx` (`usuario_idUsuario`),
  ADD KEY `fk_rol_usuario_rol1_idx` (`rol_idRol`);

--
-- Indices de la tabla `tipo_pago`
--
ALTER TABLE `tipo_pago`
  ADD PRIMARY KEY (`idTipoPago`);

--
-- Indices de la tabla `trabajo`
--
ALTER TABLE `trabajo`
  ADD PRIMARY KEY (`idTrabajo`),
  ADD KEY `fk_trabajo_categoria1_idx` (`categoria_idCategoria`),
  ADD KEY `fk_trabajo_medicion_trabajo1_idx` (`medicion_trabajo_idMedicionTrabajo`);

--
-- Indices de la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  ADD PRIMARY KEY (`idUbicacion`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`),
  ADD UNIQUE KEY `documento_identidad` (`documento_identidad`),
  ADD KEY `fk_usuario_ubicacion1_idx` (`ubicacion_idUbicacion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `acceso`
--
ALTER TABLE `acceso`
  MODIFY `idAcceso` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `busqueda`
--
ALTER TABLE `busqueda`
  MODIFY `idBusqueda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `comision`
--
ALTER TABLE `comision`
  MODIFY `idcomision` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `contrato`
--
ALTER TABLE `contrato`
  MODIFY `idContrato` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  MODIFY `idCotizacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT de la tabla `etiqueta`
--
ALTER TABLE `etiqueta`
  MODIFY `idEtiqueta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  MODIFY `idImagenes` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `jornada`
--
ALTER TABLE `jornada`
  MODIFY `idJornada` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `liquidacion`
--
ALTER TABLE `liquidacion`
  MODIFY `idliquidacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `medicion_trabajo`
--
ALTER TABLE `medicion_trabajo`
  MODIFY `idMedicionTrabajo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `oferta`
--
ALTER TABLE `oferta`
  MODIFY `idOferta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `portafolio`
--
ALTER TABLE `portafolio`
  MODIFY `idPortafolio` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `idRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `tipo_pago`
--
ALTER TABLE `tipo_pago`
  MODIFY `idTipoPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `trabajo`
--
ALTER TABLE `trabajo`
  MODIFY `idTrabajo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `ubicacion`
--
ALTER TABLE `ubicacion`
  MODIFY `idUbicacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `acceso`
--
ALTER TABLE `acceso`
  ADD CONSTRAINT `fk_acceso_usuario1` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `busqueda`
--
ALTER TABLE `busqueda`
  ADD CONSTRAINT `fk_busqueda_usuario1` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD CONSTRAINT `fk_categoria_categoria1` FOREIGN KEY (`categoria_idCategoria`) REFERENCES `categoria` (`idCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `contrato`
--
ALTER TABLE `contrato`
  ADD CONSTRAINT `fk_contrato_cotizacion1` FOREIGN KEY (`cotizacion_idCotizacion`) REFERENCES `cotizacion` (`idCotizacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_contrato_liquidacion1` FOREIGN KEY (`liquidacion_idliquidacion`) REFERENCES `liquidacion` (`idliquidacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_contrato_usuario2` FOREIGN KEY (`usuario_idUsuarioModificador`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  ADD CONSTRAINT `fk_cotizacion_comision1` FOREIGN KEY (`comision_idcomision`) REFERENCES `comision` (`idcomision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cotizacion_oferta1` FOREIGN KEY (`oferta_idOferta`) REFERENCES `oferta` (`idOferta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cotizacion_usuario1` FOREIGN KEY (`usuario_idUsuarioContratador`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `etiqueta`
--
ALTER TABLE `etiqueta`
  ADD CONSTRAINT `fk_etiqueta_categoria1` FOREIGN KEY (`categoria_idCategoria`) REFERENCES `categoria` (`idCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD CONSTRAINT `fk_Imagenes_portafolio1` FOREIGN KEY (`portafolio_idPortafolio`) REFERENCES `portafolio` (`idPortafolio`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `liquidacion`
--
ALTER TABLE `liquidacion`
  ADD CONSTRAINT `fk_liquidacion_usuario1` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `oferta`
--
ALTER TABLE `oferta`
  ADD CONSTRAINT `fk_oferta_Jornada1` FOREIGN KEY (`Jornada_idJornada`) REFERENCES `jornada` (`idJornada`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_oferta_trabajo1` FOREIGN KEY (`trabajo_idTrabajo`) REFERENCES `trabajo` (`idTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_oferta_usuario1` FOREIGN KEY (`usuario_idUsuarioOferta`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `fk_pago_contrato1` FOREIGN KEY (`contrato_idContrato`) REFERENCES `contrato` (`idContrato`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_pago_tipo_pago1` FOREIGN KEY (`tipo_pago_idTipoPago`) REFERENCES `tipo_pago` (`idTipoPago`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `portafolio`
--
ALTER TABLE `portafolio`
  ADD CONSTRAINT `fk_portafolio_oferta1` FOREIGN KEY (`oferta_idOferta`) REFERENCES `oferta` (`idOferta`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `rol_usuario`
--
ALTER TABLE `rol_usuario`
  ADD CONSTRAINT `fk_rol_usuario_rol1` FOREIGN KEY (`rol_idRol`) REFERENCES `rol` (`idRol`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_rol_usuario_usuario1` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `trabajo`
--
ALTER TABLE `trabajo`
  ADD CONSTRAINT `fk_trabajo_categoria1` FOREIGN KEY (`categoria_idCategoria`) REFERENCES `categoria` (`idCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_trabajo_medicion_trabajo1` FOREIGN KEY (`medicion_trabajo_idMedicionTrabajo`) REFERENCES `medicion_trabajo` (`idMedicionTrabajo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_ubicacion1` FOREIGN KEY (`ubicacion_idUbicacion`) REFERENCES `ubicacion` (`idUbicacion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
