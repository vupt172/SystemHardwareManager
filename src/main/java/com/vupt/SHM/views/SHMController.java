package com.vupt.SHM.views;

import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.AppConstants;
import com.vupt.SHM.repositories.CategoryRepository;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

@Controller
public class SHMController {
    private Stage stage;
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
        stage.setMaximized(true);

        SHMController shmController=loader.getController();
        shmController.stage=stage;
        //stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }
    @FXML
    public void initialize(){
/*
        this.stage= (Stage) contentView.getScene().getWindow();
*/
/*
        menuWindows.textProperty().bind(Bindings.concat("Cửa sổ (",totalWindowsProperty,")"));
*/

    }
    @FXML
    public void manageCategory() throws IOException {
        Optional<Parent> oldView = listView.stream().filter(parent -> parent.getId().equals(AppConstants.MANAGE_CATEGORY)).findFirst();
        if(oldView.isPresent()){
            contentView.getChildren().remove(oldView.get());
            contentView.getChildren().add(oldView.get());
        }
        else {
            Parent view = CategoryController.loadView((Stage)contentView.getScene().getWindow());
            listView.add(view);

            contentView.getChildren().add(view);

        }

    }
    @FXML
    public void manageAccount() throws IOException {
        Optional<Parent> oldView = listView.stream().filter(parent -> parent.getId().equals(AppConstants.MANAGE_ACCOUNT)).findFirst();
        if(oldView.isPresent()){
            contentView.getChildren().remove(oldView.get());
            contentView.getChildren().add(oldView.get());
            this.stage.setTitle(AppConstants.MANAGE_ACCOUNT);
        }
        else {
            Parent view = AccountController.loadView((Stage)contentView.getScene().getWindow());
            listView.add(view);

            contentView.getChildren().add(view);

        }
    }
    @FXML
    public void manageDepartment() throws IOException {
        Optional<Parent> oldView = listView.stream().filter(parent -> parent.getId().equals(AppConstants.MANAGE_DEPARTMENT)).findFirst();
        if(oldView.isPresent()){
            contentView.getChildren().remove(oldView.get());
            contentView.getChildren().add(oldView.get());
            this.stage.setTitle(AppConstants.MANAGE_DEPARTMENT);
        }
        else {
            Parent view = DepartmentController.loadView((Stage)contentView.getScene().getWindow());

            listView.add(view);

            contentView.getChildren().add(view);

        }
    }
    @FXML
    public void manageEmployee() throws IOException {
        Optional<Parent> oldView = listView.stream().filter(parent -> parent.getId().equals(AppConstants.MANAGE_EMPLOYEE)).findFirst();
        if(oldView.isPresent()){
            contentView.getChildren().remove(oldView.get());
            contentView.getChildren().add(oldView.get());
            this.stage.setTitle(AppConstants.MANAGE_EMPLOYEE);
        }
        else {
            Parent view = EmployeeController.loadView((Stage)contentView.getScene().getWindow());

            MenuItem menuItem=new MenuItem(AppConstants.MANAGE_EMPLOYEE);
            menuItem.setId(AppConstants.MANAGE_EMPLOYEE);

            listView.add(view);


            contentView.getChildren().add(view);

        }
    }
    @FXML
    public void manageRepairter() throws IOException {
        Parent view = RepairterController.loadView((Stage)contentView.getScene().getWindow());
        contentView.getChildren().add(view);
        totalWindowsProperty.set(++totalWindows);
    }
    @FXML
    public void manageEquipment() throws IOException {
        Optional<Parent> oldView = listView.stream().filter(parent -> parent.getId().equals(AppConstants.MANAGE_EQUIPMENT)).findFirst();
        if(oldView.isPresent()){
            contentView.getChildren().remove(oldView.get());
            contentView.getChildren().add(oldView.get());
            this.stage.setTitle(AppConstants.MANAGE_EQUIPMENT);
        }
        else {
            Parent view = EquipmentController.loadView((Stage)contentView.getScene().getWindow());


            listView.add(view);


            contentView.getChildren().add(view);

        }
    }
    @FXML
    public void manageEquipmentGroup() throws IOException {
        Optional<Parent> oldView = listView.stream().filter(parent -> parent.getId().equals(AppConstants.MANAGE_EQUIPMENT_GROUP)).findFirst();
        if(oldView.isPresent()){
            contentView.getChildren().remove(oldView.get());
            contentView.getChildren().add(oldView.get());
            this.stage.setTitle(AppConstants.MANAGE_EQUIPMENT_GROUP);
        }
        else {
            Parent view = EquipmentGroupController.loadView((Stage)contentView.getScene().getWindow());

            listView.add(view);


            contentView.getChildren().add(view);

        }
    }
}
