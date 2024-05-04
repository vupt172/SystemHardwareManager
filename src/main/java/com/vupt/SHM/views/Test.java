package com.vupt.SHM.views;

import com.vupt.SHM.SHMApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.springframework.stereotype.Controller;

import javax.management.Notification;
import java.io.IOException;
@Controller
public class Test {
    public static void loadView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com.vupt.SHM.views/test.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        stage.setScene(new Scene(view));

        //controller.attachEvent();

        stage.show();
    }
    @FXML
    public void clickEvent(){
        Notifications notifications=Notifications.create();

        notifications.text("Hello world");
        notifications.position(Pos.TOP_RIGHT);
        notifications.title("Success message");
        notifications.hideAfter(Duration.seconds(5));
        notifications.showInformation();
        //notifications.show();
    }
}
