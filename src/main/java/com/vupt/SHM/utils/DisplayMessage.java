package com.vupt.SHM.utils;

public class DisplayMessage {
    public static String getWarningDeleteMessage(String menu,String data){
        return String.format("Đồng ý xóa %s [%s]?",menu,data);
    }
    public static String getNotFoundMessage(String menu,String property,Object data){
        return String.format("Không tìm thấy %s với %s là %s",menu,property,data.toString());
    }
}
