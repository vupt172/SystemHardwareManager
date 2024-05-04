package com.vupt.SHM.views;

import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.services.EquipmentGroupService;
import com.vupt.SHM.views.component.CustomTitledPane;
import com.vupt.SHM.views.popup.EquipmentGroupEdit;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.io.IOException;

@Controller
public class EquipmentGroupController {
    private String windowTitle="Quản lý nhóm thiết bị";
    @Autowired
    DepartmentService departmentService;
    @FXML
    private ScrollPane equipmentGroupList;
    @FXML
    private VBox vbox;
    @Autowired
    EquipmentGroupService equipmentGroupService;
    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(AccountController.class.getResource("/com.vupt.SHM.views/equipmentgroup.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        EquipmentGroupController equipmentGroupController=loader.getController();
        primaryStage.setTitle(equipmentGroupController.windowTitle);
        return view;
    }

    @FXML
    public void initialize() throws IOException {
        departmentService.findAll().stream().forEach(e-> vbox.getChildren().add(new CustomTitledPane(e.getName(),e.getDepartmentType().getTitle())));

    }
    @FXML
    public void createEquipmentGroupView(){
        EquipmentGroupEdit.loadView(null,this::save,equipmentGroupList);
    }
    private void save(EquipmentGroupDTO equipmentGroupDTO) {
        equipmentGroupService.save(equipmentGroupDTO);
        reload();
    }

    private void reload() {
   /*     tbEquipment.getItems().clear();
        tbEquipment.setItems(FXCollections.observableList(equipmentService.findAll()));*/
    }

    private class EquipmentGroupTitledPane extends TitledPane {

     public EquipmentGroupTitledPane(){

     }
    }

}
