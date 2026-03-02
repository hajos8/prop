-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2026. Feb 28. 17:00
-- Kiszolgáló verziója: 10.4.32-MariaDB
-- PHP verzió: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `propaganda`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `kihelyezesek`
--

CREATE TABLE `kihelyezesek` (
  `szelessegi` float NOT NULL,
  `hosszusagi` float NOT NULL,
  `plakat_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `kihelyezesek`
--

INSERT INTO `kihelyezesek` (`szelessegi`, `hosszusagi`, `plakat_id`) VALUES
(46.253, 20.1482, 4),
(46.253, 20.1482, 5),
(46.8964, 19.6897, 1),
(46.8964, 19.6897, 2),
(46.8964, 19.6897, 3);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `plakatok`
--

CREATE TABLE `plakatok` (
  `id` int(10) UNSIGNED NOT NULL,
  `part` varchar(255) DEFAULT 'FIDESZ',
  `felirat` varchar(255) DEFAULT NULL,
  `kep` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `plakatok`
--

INSERT INTO `plakatok` (`id`, `part`, `felirat`, `kep`) VALUES
(1, 'FIDESZ', 'Együtt a háborúért. Több fegyver. Adóemelés. Sorkatonaság.', '/images/haboruert.jpg'),
(2, 'FIDESZ', 'Voks 2025. Ne hagyjuk, hogy a fejünk felett döntsenek!', '/images/voks2025.jpg'),
(3, 'FIDESZ', 'A brüsszeli szankciók tönkretesznek minket!', '/images/szankciok.jpg'),
(4, 'Momentum', 'Folytatják? A kormányváltás rajtad múlik!', '/images/folytatjak.jpg'),
(5, 'Momentum', 'A te pénzedből hazudnak neked. Váltsunk kormányt április 12-én!', '/images/hazudnak.jpg');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `utcak`
--

CREATE TABLE `utcak` (
  `szelessegi` float NOT NULL,
  `hosszusagi` float NOT NULL,
  `iranyitoszam` int(10) UNSIGNED DEFAULT 1000,
  `varos` varchar(255) DEFAULT 'Budapest',
  `nev` varchar(255) DEFAULT NULL,
  `jelleg` varchar(255) DEFAULT 'utca',
  `kep` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- A tábla adatainak kiíratása `utcak`
--

INSERT INTO `utcak` (`szelessegi`, `hosszusagi`, `iranyitoszam`, `varos`, `nev`, `jelleg`, `kep`) VALUES
(46.253, 20.1482, 6724, 'Szeged', 'Kossuth Lajos', 'sugárút', '/images/szeged-kossuth.jpeg'),
(46.8964, 19.6897, 6000, 'Kecskemét', 'Bethlen', 'körút', '/images/kecskemet-bethlen.jpg');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `kihelyezesek`
--
ALTER TABLE `kihelyezesek`
  ADD PRIMARY KEY (`szelessegi`,`hosszusagi`,`plakat_id`),
  ADD KEY `szelessegi` (`szelessegi`,`hosszusagi`),
  ADD KEY `plakat_id` (`plakat_id`);

--
-- A tábla indexei `plakatok`
--
ALTER TABLE `plakatok`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `utcak`
--
ALTER TABLE `utcak`
  ADD PRIMARY KEY (`szelessegi`,`hosszusagi`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `plakatok`
--
ALTER TABLE `plakatok`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `kihelyezesek`
--
ALTER TABLE `kihelyezesek`
  ADD CONSTRAINT `fk_kihelyezesek_utcak` FOREIGN KEY (`szelessegi`,`hosszusagi`) REFERENCES `utcak` (`szelessegi`, `hosszusagi`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `kihelyezesek_ibfk_1` FOREIGN KEY (`plakat_id`) REFERENCES `plakatok` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
