package com.vupt.SHM.views;

import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.DTO.RepairterDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.services.EmployeeService;
import com.vupt.SHM.services.RepairterService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class RepairterController {
    @Autowired
    RepairterService repairterService;
    @FXML
    TableView<RepairterDTO> tbRepairter;
    @FXML
    TableColumn<RepairterDTO, Long> colId;
    @FXML
    TableColumn<RepairterDTO, String> colName;
    @FXML
    TableColumn<RepairterDTO, String> colCompany;
    @FXML
    TableColumn<RepairterDTO, String> colPhone;
    @FXML
    TableColumn<RepairterDTO, String> colNote;
    private String windowTitle = "Quản lý đơn vị sửa chữa";


    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(RepairterController.class.getResource("/com.vupt.SHM.views/repairter.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();

        RepairterController repairterController = loader.getController();
        primaryStage.setTitle(repairterController.windowTitle);
        return view;
    }


    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<RepairterDTO, Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<RepairterDTO, String>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<RepairterDTO, String>("company"));
        colPhone.setCellValueFactory(new PropertyValueFactory<RepairterDTO, String>("phone"));
        colNote.setCellValueFactory(new PropertyValueFactory<RepairterDTO, String>("note"));

        tbRepairter.setItems(FXCollections.observableList(repairterService.findALl()));

    }

    @FXML
    public void createRepairterView() {

    }

}
