package com.vupt.SHM.views;

import com.vupt.SHM.DTO.AccountDTO;
import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.services.EquipmentService;
import com.vupt.SHM.utils.DateTimeUtils;
import com.vupt.SHM.views.popup.CategoryEdit;
import com.vupt.SHM.views.popup.EquipmentEdit;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Controller
public class EquipmentController {
    private String windowTitle="Quản lý thiết bị phần cứng";
    @Autowired
    EquipmentService equipmentService;
    @FXML
    TableView<EquipmentDTO> tbEquipment;
    @FXML
    TableColumn<EquipmentDTO,Long> colId;
    @FXML
    TableColumn<EquipmentDTO,String> colName;
    @FXML
    TableColumn<EquipmentDTO,String> colCode;
    @FXML
    TableColumn<EquipmentDTO, String> colReceivedDate;
    @FXML
    TableColumn<EquipmentDTO,String> colCategory;
    @FXML
    TableColumn<EquipmentDTO, String> colStatus;
    @FXML
    TableColumn<EquipmentDTO,String> colManager;
    @FXML
    TableColumn<EquipmentDTO,String> colDepartment;
    @FXML
    TableColumn<EquipmentDTO,String> colNote;
    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(AccountController.class.getResource("/com.vupt.SHM.views/equipment.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        EquipmentController equipmentController=loader.getController();
        primaryStage.setTitle(equipmentController.windowTitle);
        return view;
    }
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<EquipmentDTO, Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("name"));
        colCode.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("code"));
        colReceivedDate.setCellValueFactory(param -> new SimpleStringProperty(DateTimeUtils.format(param.getValue().getReceivedDate())));

        colCategory.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("categoryName"));
        colStatus.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus().getTitle()));
        colManager.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("managerName"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("departmentName"));
        colNote.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("note"));
        List<EquipmentDTO> equipmentDTOList=equipmentService.findAll();
        tbEquipment.getItems().clear();
        tbEquipment.setItems(FXCollections.observableList(equipmentService.findAll()));

    }


    private void save(EquipmentDTO equipmentDTO) {
        equipmentService.save(equipmentDTO);
        reload();
    }

    private void reload() {
        tbEquipment.getItems().clear();
        tbEquipment.setItems(FXCollections.observableList(equipmentService.findAll()));
    }
    @FXML
    public void createEquipmentView(){
        EquipmentEdit.loadView(this::save, tbEquipment);
    }
}
