package com.vupt.SHM.views;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.entity.Employee;
import com.vupt.SHM.services.EmployeeService;
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
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @FXML
    TableView<EmployeeDTO> tbEmployee;
    @FXML
    TableColumn<EmployeeDTO,Long> colId;
    @FXML
    TableColumn<EmployeeDTO,String> colName;
    @FXML
    TableColumn<EmployeeDTO,String> colUsername;
    @FXML
    TableColumn<EmployeeDTO,String> colDepartment;

    private String windowTitle="Quản lý nhân viên";


    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(EmployeeController.class.getResource("/com.vupt.SHM.views/employee.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();

        EmployeeController employeeController=loader.getController();
        primaryStage.setTitle(employeeController.windowTitle);
        return view;
    }


    @FXML
    public void initialize() {


        colId.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("fullName"));
        colUsername.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("username"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("departmentName"));


        tbEmployee.setItems(FXCollections.observableList(employeeService.findALl()));
    }
    @FXML
    public void createEmployeeView(){

    }

}
