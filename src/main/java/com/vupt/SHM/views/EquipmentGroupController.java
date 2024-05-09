package com.vupt.SHM.views;

import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.services.EquipmentGroupService;
import com.vupt.SHM.views.component.DepartmentTitledPane;
import com.vupt.SHM.views.popup.EquipmentGroupEdit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EquipmentGroupController {
    private String windowTitle="Quản lý nhóm thiết bị";
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EquipmentGroupService equipmentGroupService;
    @FXML
    private ScrollPane equipmentGroupList;
    @FXML
    private VBox vbox;

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
   reload();

    }
    @FXML
    public void createEquipmentGroupView(){
        EquipmentGroupEdit.loadView(null,this::save,vbox);
    }
    private void save(EquipmentGroupDTO equipmentGroupDTO) {
        equipmentGroupService.save(equipmentGroupDTO);
        reload();
    }

    private Void reload() {
    vbox.getChildren().clear();
        departmentService.findAll().stream().forEach(e-> {
            List<EquipmentGroupDTO> equipmentGroupDTOList= equipmentGroupService.findALl().stream().filter(v->v.getDepartmentId() == e.getId()).collect(Collectors.toList());
            vbox.getChildren().add(new DepartmentTitledPane(e.getName(),equipmentGroupDTOList,this::reload));
        });
        return null;
    }

}
