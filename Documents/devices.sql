-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2020 at 09:37 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `devices`
--

-- --------------------------------------------------------

--
-- Table structure for table `devicecategory`
--

CREATE TABLE `devicecategory` (
  `Id` int(11) NOT NULL,
  `CategoryName` varchar(200) NOT NULL,
  `PictureURL` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `devicecategory`
--

INSERT INTO `devicecategory` (`Id`, `CategoryName`, `PictureURL`) VALUES
(1, 'Phone', 'https://upload-icon.s3.us-east-2.amazonaws.com/uploads/icons/png/14932821071536063625-512.png'),
(2, 'Laptop', 'https://upload-icon.s3.us-east-2.amazonaws.com/uploads/icons/png/16388271601537348218-512.png');

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE `devices` (
  `DeviceId` int(11) NOT NULL,
  `DeviceName` varchar(200) NOT NULL,
  `Price` int(15) NOT NULL,
  `PictureURL` varchar(200) NOT NULL,
  `Description` varchar(10000) NOT NULL,
  `CategoryId` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `devices`
--

INSERT INTO `devices` (`DeviceId`, `DeviceName`, `Price`, `PictureURL`, `Description`, `CategoryId`) VALUES
(1, 'iPhone 11 256GB', 25690000, 'https://cdn.tgdd.vn/Products/Images/42/210648/iphone-11-256gb-white-400x460.png', 'iPhone 11 256GB là chiếc máy có mức giá \"dễ chịu\" trong bộ 3 iPhone vừa được Apple giới thiệu và nếu bạn muốn được trải nghiệm những nâng cấp về camera mới hay hiệu năng hàng đầu mà lại không muốn bỏ ra quá nhiều tiền thì đây thực sự là lựa chọn hàng đầu dành cho bạn.\r\nThông số kĩ thuật:\r\nMàn hình:	IPS LCD, 6.1\", Liquid Retina\r\nHệ điều hành:	iOS 13\r\nCamera sau:	Chính 12 MP & Phụ 12 MP\r\nCamera trước:	12 MP\r\nCPU:	Apple A13 Bionic 6 nhân\r\nRAM:	4 GB\r\nBộ nhớ trong:	256 GB\r\nThẻ SIM:\r\n1 eSIM & 1 Nano SIM, Hỗ trợ 4G\r\nDung lượng pin:	3110 mAh, có sạc nhanh', 1),
(2, 'Samsung Galaxy Fold\r\n', 50000000, 'https://cdn.tgdd.vn/Products/Images/42/198158/samsung-galaxy-fold-black-400x460.png', 'Sau rất nhiều chờ đợi thì Samsung Galaxy Fold - chiếc smartphone màn hình gập đầu tiên của Samsung cũng đã chính thức trình làng với thiết kế mới lạ.\r\nThông số kỹ thuật:\r\nMàn hình:	Chính: Dynamic AMOLED, phụ: Super AMOLED, Chính 7.3\" & Phụ 4.6\", Quad HD (2K)\r\nHệ điều hành:	Android 9.0 (Pie)\r\nCamera sau:	Chính 12 MP & Phụ 12 MP, 16 MP\r\nCamera trước:	Trong: 10 MP, 8 MP; Ngoài: 10 MP\r\nCPU:	Snapdragon 855 8 nhân\r\nRAM:	12 GB\r\nBộ nhớ trong:	512 GB\r\nThẻ SIM:\r\n1 eSIM & 1 Nano SIM, Hỗ trợ 4G\r\nDung lượng pin:	4380 mAh, có sạc nhanh', 1),
(3, 'Laptop Macbook Pro Touch 16 inch 2019', 56990000, 'https://cdn.tgdd.vn/Products/Images/44/215941/macbook-pro-16-201926-macbookprotouch16inch-1-600x600.jpg', 'MacBook ProTouch 2019 i7 chiếc laptop cấu hình mạnh mẽ của Apple sẽ đem đến những trải nghiệm mà không phải chiếc laptop nào cũng có được. Thiết kế sang trọng tinh tế, cấu hình khủng, được trang bị card đồ họa và các công nghệ độc quyền của Apple sẽ đem lại nhiều thích thú cho người dùng.\r\nThông số kỹ thuật\r\nCPU:	Intel Core i7 Coffee Lake, 2.60 GHz\r\nRAM:	16 GB, DDR4 (1 khe), 2666 MHz\r\nỔ cứng:	SSD 512GB\r\nMàn hình:	16 inch, Retina (3072 x 1920)\r\nCard màn hình:	Card đồ họa rời, Radeon Pro 5300M, 4GB\r\nCổng kết nối:	4 x Thunderbolt 3 (USB-C)\r\nHệ điều hành:	Mac OS\r\nThiết kế:	Vỏ kim loại nguyên khối, PIN liền\r\nKích thước:	Dày 16.2 mm, 2.0 kg\r\nThời điểm ra mắt:	2019', 2),
(4, 'Laptop Apple MacBook Air 2019\r\n', 34990000, 'https://cdn.tgdd.vn/Products/Images/44/218493/apple-macbook-air-2019-i5-16ghz-8gb-256gb-mvfl2sa-600x600.jpg', 'Apple Macbook Air 2019 i5 (MVFL2SA/A) là phiên bản nâng cấp nhẹ so với MacBook Air 2018. Màn hình Retina nay được trang bị công nghệ Truetone hiện đại, có nhiều cải tiến trên bàn phím cánh bướm.\r\nThông số kỹ thuật\r\nCPU:	Intel Core i5 Coffee Lake, 1.60 GHz\r\nRAM:	8 GB, LPDDR3, 2133 MHz\r\nỔ cứng:	SSD: 256 GB\r\nMàn hình:	13.3 inch, Retina (2560 x 1600)\r\nCard màn hình:	Card đồ họa tích hợp, Intel UHD Graphics 617\r\nCổng kết nối:	2 x Thunderbolt 3 (USB-C)\r\nHệ điều hành:	Mac OS\r\nThiết kế:	Vỏ kim loại nguyên khối, PIN liền\r\nKích thước:	Dày 4.1 đến 15.6 mm, 1.25 kg\r\nThời điểm ra mắt:	2019', 2);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `OrderId` int(11) NOT NULL,
  `CustomerName` varchar(200) NOT NULL,
  `Phone` int(11) NOT NULL,
  `Email` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`OrderId`, `CustomerName`, `Phone`, `Email`) VALUES
(1, 'Tien', 36253713, 'tiennm1999@gmail.com'),
(2, 'Tien', 36253713, 'tiennm1999@gmail.com'),
(16, 'Tiennm', 12345678, 'tiennm1999@gmail.com'),
(17, 'Tiennm', 2147483647, 'tiennm1999@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `orderdetail`
--

CREATE TABLE `orderdetail` (
  `Id` int(11) NOT NULL,
  `OrderId` int(11) NOT NULL,
  `DeviceId` int(11) NOT NULL,
  `DeviceName` varchar(200) NOT NULL,
  `Price` int(11) NOT NULL,
  `DeviceQuantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orderdetail`
--

INSERT INTO `orderdetail` (`Id`, `OrderId`, `DeviceId`, `DeviceName`, `Price`, `DeviceQuantity`) VALUES
(5, 0, 1, 'iPhone 11 256GB', 25690000, 2),
(6, 0, 2, 'Samsung Galaxy Fold', 50000000, 4),
(7, 0, 4, 'Laptop Apple MacBook Air 2019\r\n', 34990000, 1),
(8, 0, 3, 'Laptop Macbook Pro Touch 16 inch 2019', 113980000, 2),
(9, 0, 1, 'iPhone 11 256GB', 25690000, 1),
(10, 0, 1, 'iPhone 11 256GB', 25690000, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `devicecategory`
--
ALTER TABLE `devicecategory`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`DeviceId`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`OrderId`);

--
-- Indexes for table `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `devicecategory`
--
ALTER TABLE `devicecategory`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `DeviceId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `OrderId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `orderdetail`
--
ALTER TABLE `orderdetail`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
