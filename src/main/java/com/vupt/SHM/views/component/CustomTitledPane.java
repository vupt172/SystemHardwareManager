package com.vupt.SHM.views.component;

import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.views.SHMController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;


import java.io.IOException;

public class CustomTitledPane extends TitledPane {
    public CustomTitledPane(String title, String contentText) {
        this.setText(title);
      //  this.set
        Label contentLabel = new Label(contentText);
        VBox content = new VBox();
        content.setPrefHeight(500);
        content.getChildren().add(contentLabel);

        this.setContent(content);
    }
}
