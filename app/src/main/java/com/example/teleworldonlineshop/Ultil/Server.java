package com.example.teleworldonlineshop.Ultil;

public class Server {
    public static String localhost ="192.168.43.214";
    public static String categoryLink ="http://" + localhost + "/server/getcategory.php";
    public static String latestProductLink = "http://" + localhost +"/server/getlatestproduct.php";
    public static String phoneLink = "http://" + localhost +"/server/getproduct.php?page=";
    public static String orderLink = "http://" +localhost +"/server/customerinfo.php";
    public static String orderdetailLink = "http://" +localhost +"/server/orderdetail.php";
    public static String loginLink = "http://" +localhost +"/server/login.php";
    public static String insertDeviceLink = "http://" +localhost +"/server/insertdevice.php";
    public static String deleteDeviceLink = "http://" +localhost +"/server/deletedevice.php";
    public static String modifyDeviceLink = "http://" +localhost +"/server/modifydevice.php";
    public static String orderdataLink = "http://" +localhost +"/server/orderdata.php";
    public static String getOrderDetaislLink = "http://" +localhost +"/server/getorderdetails.php";
}
