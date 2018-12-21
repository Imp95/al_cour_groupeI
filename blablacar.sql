-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 21, 2018 at 09:29 AM
-- Server version: 5.7.24-0ubuntu0.18.04.1
-- PHP Version: 7.2.10-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blablacar`
--

-- --------------------------------------------------------

--
-- Table structure for table `ad`
--

CREATE TABLE `ad` (
  `id` int(11) NOT NULL,
  `client_id` int(11) DEFAULT NULL,
  `departure_date` date NOT NULL,
  `arrival_date` date NOT NULL,
  `bagage` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `payment` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `departure_address` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `arrival_address` longtext COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ad`
--

INSERT INTO `ad` (`id`, `client_id`, `departure_date`, `arrival_date`, `bagage`, `payment`, `status`, `departure_address`, `arrival_address`) VALUES
(941, 1091, '2018-01-13', '2018-01-14', '15x15x15', 50, 0, '3 rue swag 03333 Magic Town', '3 rue nulle 09333 Meat City'),
(942, 1092, '2018-01-18', '2018-01-19', '15x15x30', 50, 0, '3 rue swag 03333 Ville', '3 rue nulle 09333 Villeuuu'),
(943, 1091, '2018-01-22', '2018-01-24', '15x15x74', 45, 0, '3 rue swag 03333 Ville', '3 rue nulle 09333 Meat City');

-- --------------------------------------------------------

--
-- Table structure for table `contract`
--

CREATE TABLE `contract` (
  `id` int(11) NOT NULL,
  `offer_id` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `deposit_accused` int(11) DEFAULT NULL,
  `acknowledgement` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `offer`
--

CREATE TABLE `offer` (
  `id` int(11) NOT NULL,
  `carrier_id` int(11) DEFAULT NULL,
  `ad_id` int(11) DEFAULT NULL,
  `proposed_date` date NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `firstname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birthday` date NOT NULL,
  `phone_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `name`, `firstname`, `birthday`, `phone_number`, `amount`) VALUES
(1091, 'example1@email.com', 'azerty', 'Doe', 'John', '1980-01-20', '0122334455', 0),
(1092, 'example2@email.com', 'password', 'Bon', 'Jean', '1977-03-18', '0322334459', 50);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ad`
--
ALTER TABLE `ad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_77E0ED5819EB6921` (`client_id`);

--
-- Indexes for table `contract`
--
ALTER TABLE `contract`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_E98F285953C674EE` (`offer_id`);

--
-- Indexes for table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_29D6873E21DFC797` (`carrier_id`),
  ADD KEY `IDX_29D6873E4F34D596` (`ad_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_8D93D649E7927C74` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ad`
--
ALTER TABLE `ad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=944;
--
-- AUTO_INCREMENT for table `contract`
--
ALTER TABLE `contract`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `offer`
--
ALTER TABLE `offer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1093;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `ad`
--
ALTER TABLE `ad`
  ADD CONSTRAINT `FK_77E0ED5819EB6921` FOREIGN KEY (`client_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `contract`
--
ALTER TABLE `contract`
  ADD CONSTRAINT `FK_E98F285953C674EE` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`);

--
-- Constraints for table `offer`
--
ALTER TABLE `offer`
  ADD CONSTRAINT `FK_29D6873E21DFC797` FOREIGN KEY (`carrier_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_29D6873E4F34D596` FOREIGN KEY (`ad_id`) REFERENCES `ad` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
