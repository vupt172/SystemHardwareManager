package com.vupt.SHM.views;

import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.repositories.CategoryRepository;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SHMController {
    @FXML
    StackPane contentView;
    @FXML
    Menu menuWindows;
    @FXML
    Menu menuWelcome;
    List<Parent> listView = new ArrayList<>();
    @Autowired
    CategoryRepository categoryRepository;
    private int totalWindows=0;
    private IntegerProperty totalWindowsProperty=new SimpleIntegerProperty(0);

    public static void loadView() throws IOException {
        FXMLLoader loader = new FXMLLoader(SHMController.class.getResource("/com.vupt.SHM.views/SHM.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Stage stage = new Stage();
        Parent view = loader.load();
        stage.setScene(new Scene(view));
        stage.setTitle("Hệ thống quản lý thiết bị phần cứng");
        //stage.setMaximized(true);

        //stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }
    @FXML
    public void initialize(){
        menuWindows.textProperty().bind(totalWindowsProperty.asString());
     /*   System.out.println("aabb");
        String username = AuthenticationUtils.getUserDetails().getUsername();
        menuWelcome.setText("Welcome, "+username);*/
    }
    @FXML
    public void manageCategory() throws IOException {
        Parent view = CategoryController.loadView((Stage)contentView.getScene().getWindow());


      // menuWindows.getItems().add(new MenuItem(window));


        contentView.getChildren().add(view);
        totalWindowsProperty.set(++totalWindows);

    }
    @FXML
    public void manageAccount() throws IOException {
          Parent view= AccountController.loadView((Stage)contentView.getScene().getWindow()) ;

        // menuWindows.getItems().add(new MenuItem(window));
        contentView.getChildren().add(view);
        totalWindowsProperty.set(++totalWindows);
    }
    @FXML
    public void manageDepartment() throws IOException {
      Parent view =DepartmentController.loadView((Stage)contentView.getScene().getWindow());

      contentView.getChildren().add(view);
        totalWindowsProperty.set(++totalWindows);
    }
    @FXML
    public void manageEmployee() throws IOException {
       Parent view = EmployeeController.loadView((Stage)contentView.getScene().getWindow());
       contentView.getChildren().add(view);
        totalWindowsProperty.set(++totalWindows);
    }
    @FXML
    public void manageRepairter() throws IOException {
        Parent view = RepairterController.loadView((Stage)contentView.getScene().getWindow());
        contentView.getChildren().add(view);
        totalWindowsProperty.set(++totalWindows);
    }
    @FXML
    public void manageEquipment() throws IOException {
        Parent view = EquipmentController.loadView((Stage)contentView.getScene().getWindow());
        contentView.getChildren().add(view);
        totalWindowsProperty.set(++totalWindows);
    }
    @FXML
    public void manageEquipmentGroup() throws IOException {
        Parent view = EquipmentGroupController.loadView((Stage)contentView.getScene().getWindow());
        contentView.getChildren().add(view);
        totalWindowsProperty.set(++totalWindows);
    }
}
