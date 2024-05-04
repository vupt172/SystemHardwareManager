package com.vupt.SHM.views.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javafx.application.Application.launch;

public class CustomAlert  {

    // Lớp con tĩnh AlertBuilder để xây dựng dialog alert
    public static class AlertBuilder {
        private Alert alert;

        // Khởi tạo với kiểu cảnh báo
        public AlertBuilder(Alert.AlertType alertType) {
            alert=new Alert(alertType);

        }

        public AlertBuilder setAlertType(Alert.AlertType alertType){
            alert.setAlertType(alertType);
            return this;
        }

        // Thiết lập tiêu đề
        public AlertBuilder setTitle(String title) {
            alert.setTitle(title);
            return this;
        }

        // Thiết lập Header Text
        public AlertBuilder setHeaderText(String headerText) {
            alert.setHeaderText(headerText);
            return this;
        }

        // Thiết lập nội dung
        public AlertBuilder setContentText(String contentText) {
            alert.setContentText(contentText);
            return this;
        }

        public AlertBuilder setButtonTypeList(HashMap<String,ButtonBar.ButtonData> buttonDataList){
            List<ButtonType> buttonTypeList= new ArrayList<>();
            for(String key : buttonDataList.keySet()){
                ButtonType buttonType=new ButtonType(key,buttonDataList.get(key));
                buttonTypeList.add(buttonType);
            }
            alert.getButtonTypes().setAll(buttonTypeList);
            return this;
        }
        public static AlertBuilder builder(Alert.AlertType alertType) {
            return new AlertBuilder(alertType);
        }

        // Xây dựng dialog alert
        public Alert build() {
            return alert;
        }
    }
}
