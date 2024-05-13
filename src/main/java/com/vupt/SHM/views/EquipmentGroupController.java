package com.vupt.SHM.views;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.AppConstants;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.services.EquipmentGroupService;
import com.vupt.SHM.views.component.DepartmentTitledPane;
import com.vupt.SHM.views.popup.EquipmentGroupEdit;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EquipmentGroupController {
    private String windowTitle = "Quản lý nhóm thiết bị";
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EquipmentGroupService equipmentGroupService;
    @FXML
    private ScrollPane equipmentGroupList;
    @FXML
    private VBox content;
    @FXML
    private ComboBox<DepartmentDTO> cbDepartment;

    public VBox getContent() {
        return content;
    }

    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(AccountController.class.getResource("/com.vupt.SHM.views/equipmentgroup.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        view.setId(AppConstants.MANAGE_EQUIPMENT_GROUP);
        EquipmentGroupController equipmentGroupController = loader.getController();
        primaryStage.setTitle(AppConstants.MANAGE_EQUIPMENT_GROUP);
        return view;
    }

    @FXML
    public void initialize() throws IOException {
        cbDepartment.setItems(FXCollections.observableList(departmentService.findAll()));
        reload();

    }

    @FXML
    public void createEquipmentGroupView() {
        EquipmentGroupEdit.loadView(null, this::save, getContent());
    }

    private void save(EquipmentGroupDTO equipmentGroupDTO) {
        equipmentGroupService.save(equipmentGroupDTO);
        reload();
    }

    public void removeEquipment(long equipmentId) {
        equipmentGroupService.removeEquipmentFromGroup(equipmentId);
    }


    public void reload() {
     reload(departmentService.findAll(),false);
    }
    public void reload(List<DepartmentDTO> departmentDTOList,boolean expanded){
        content.getChildren().clear();
        content.getChildren().addAll(generateContent(departmentDTOList,expanded));
    }

    private List<DepartmentTitledPane> generateContent(List<DepartmentDTO> departmentDTOList,boolean expanded) {
        List<DepartmentTitledPane> departmentTitledPaneList = new ArrayList<>();
        departmentDTOList.stream().forEach(e -> {
            List<EquipmentGroupDTO> equipmentGroupDTOList = equipmentGroupService.findALl().stream()
                    .filter(v -> v.getDepartmentId() == e.getId())
                    .collect(Collectors.toList());
            DepartmentTitledPane departmentTitledPane = new DepartmentTitledPane(this, e.getName(), equipmentGroupDTOList,expanded);
            departmentTitledPaneList.add(departmentTitledPane);
        });
        return departmentTitledPaneList;
    }

    @FXML
    private void search() {
        long departmentId=cbDepartment.getValue()!=null?cbDepartment.getValue().getId():0;
        DepartmentDTO departmentDTO=departmentService.getDTO(departmentId);
        reload(Arrays.asList(departmentDTO),true);
    }

    @FXML
    private void clear() {
    }

}
