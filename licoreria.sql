-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-05-2021 a las 04:34:11
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `licoreria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `numTrabajador` varchar(3) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `contrasena` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `tipo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`numTrabajador`, `nombre`, `apellidos`, `contrasena`, `tipo`) VALUES
('001', 'Karla', 'Guevara', 'adm', 1),
('002', 'Nadia', 'Bravo', 'adm', 1);

-- --------------------------------------------------------

CREATE TABLE `proveedor` (
 `nombre` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
 `empresa` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
 `telefono` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
 `direccion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
 `idProveedor` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
 `borrado` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `nombre` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `marca` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `precio` float NOT NULL,
  `cantidad` int(3) NOT NULL,
  `idProveedor` int(3) NOT NULL,
  `borrado` tinyint(1) DEFAULT 0,
  FOREIGN KEY (`idProveedor`) REFERENCES `proveedor`(`idProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `producto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `id` int(11) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `total` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `venta`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta_producto`
--

CREATE TABLE `venta_producto` (
  `id_venta` int(11) DEFAULT NULL,
  `id_producto` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `subtotal` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `venta_producto`
--

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`numTrabajador`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `venta_producto`
--
ALTER TABLE `venta_producto`
  ADD KEY `fk_venta` (`id_venta`),
  ADD KEY `fk_producto` (`id_producto`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `venta`
--

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `venta_producto`
--
ALTER TABLE `venta_producto`
  ADD CONSTRAINT `fk_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`nombre`),
  ADD CONSTRAINT `fk_venta` FOREIGN KEY (`id_venta`) REFERENCES `venta` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
