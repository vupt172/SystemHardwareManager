package com.vupt.SHM.views;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.AppConstants;
import com.vupt.SHM.entity.Employee;
import com.vupt.SHM.services.EmployeeService;
import com.vupt.SHM.utils.DisplayMessage;
import com.vupt.SHM.views.common.CustomAlert;
import com.vupt.SHM.views.common.CustomNotification;
import com.vupt.SHM.views.popup.EmployeeEdit;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdk.internal.instrumentation.TypeMapping;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @FXML
    TableView<EmployeeDTO> tbEmployee;
    @FXML
    TableColumn<EmployeeDTO, Long> colId;
    @FXML
    TableColumn<EmployeeDTO, String> colName;
    @FXML
    TableColumn<EmployeeDTO, String> colUsername;
    @FXML
    TableColumn<EmployeeDTO, String> colDepartment;
    @FXML
    TableColumn<EmployeeDTO,String> colContact;
    @FXML
    TableColumn<EmployeeDTO,Boolean> colIsManager;

    private String windowTitle = "Quản lý nhân viên";


    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(EmployeeController.class.getResource("/com.vupt.SHM.views/employee.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        view.setId(AppConstants.MANAGE_EMPLOYEE);

        EmployeeController employeeController = loader.getController();
        primaryStage.setTitle(AppConstants.MANAGE_EMPLOYEE);
        return view;
    }


    @FXML
    public void initialize() {


        colId.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("fullName"));
        colUsername.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("username"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<EmployeeDTO, String>("departmentName"));
        colContact.setCellValueFactory(new PropertyValueFactory<EmployeeDTO,String>("contact"));
        colIsManager.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isManager()));
        colIsManager.setCellFactory(CheckBoxTableCell.forTableColumn(colIsManager));
        tbEmployee.setItems(FXCollections.observableList(employeeService.findALl()));
    }

    private void save(EmployeeDTO employeeDTO) {

        employeeService.save(employeeDTO);
        reload();
    }

    private void reload() {
        tbEmployee.getItems().clear();
        tbEmployee.setItems(FXCollections.observableList(employeeService.findALl()));
    }

    @FXML
    public void createEmployeeView() {
        System.out.println("Create employee");
        EmployeeEdit.loadView(this::save, tbEmployee);
    }

    @FXML
    public void updateEmployeeView() {
        EmployeeDTO selectedEmployeeDTO = tbEmployee.getSelectionModel().getSelectedItem();
        if (selectedEmployeeDTO != null) {
            EmployeeEdit.loadView(selectedEmployeeDTO, this::save, tbEmployee);
        }

    }

    @FXML
    public void deleteEmployee() {
        EmployeeDTO selectedEmployeeDTO = tbEmployee.getSelectionModel().getSelectedItem();
        if (selectedEmployeeDTO != null) {

            Optional<ButtonType> result = CustomAlert.AlertBuilder.builder(Alert.AlertType.WARNING)
                    .setHeaderText(null)
                    .setContentText(DisplayMessage.getWarningDeleteMessage(AppConstants.MENU_EMPLOYEE,selectedEmployeeDTO.getFullName()))
                    .setYesNoButtonTypes()
                    .build()
                    .showAndWait();

            if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                employeeService.softDelete(selectedEmployeeDTO.getId());
                reload();
                //show notification
                CustomNotification.createNotification("Trạng thái", "Xóa thành công", tbEmployee).showInformation();
            }


        }

    }

}
