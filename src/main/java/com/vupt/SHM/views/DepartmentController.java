package com.vupt.SHM.views;

import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.AppConstants;
import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.utils.DisplayMessage;
import com.vupt.SHM.views.common.CustomAlert;
import com.vupt.SHM.views.common.CustomNotification;
import com.vupt.SHM.views.component.DepartmentTypeListCell;
import com.vupt.SHM.views.popup.DepartmentEdit;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class DepartmentController {

    private String windowTitle = "Quản lý bộ phận";
    @Autowired
    DepartmentService departmentService;
    @Autowired
    ModelMapper modelMapper;
    @FXML
    Button btnSearch;
    @FXML
    TextField tfName;
    @FXML
    ComboBox<DepartmentType> cbDepartmentType;

    @FXML
    TableView<DepartmentDTO> tbDepartment;
    @FXML
    TableColumn<DepartmentDTO,Long> colId;
    @FXML
    TableColumn<DepartmentDTO,String> colName;
    @FXML
    TableColumn<DepartmentDTO,String> colType;
    @FXML
    TableColumn<DepartmentDTO,Boolean> colIsSuspended;
    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(DepartmentController.class.getResource("/com.vupt.SHM.views/department.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        view.setId(AppConstants.MANAGE_DEPARTMENT);

        DepartmentController departmentController=loader.getController();
        primaryStage.setTitle(AppConstants.MANAGE_DEPARTMENT);
        return view;
    }

    @FXML
    public void initialize() {
        cbDepartmentType.getItems().addAll(DepartmentType.values());
        cbDepartmentType.setCellFactory(param -> new DepartmentTypeListCell());
        cbDepartmentType.setButtonCell(new DepartmentTypeListCell());

        colId.setCellValueFactory(new PropertyValueFactory<DepartmentDTO, Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<DepartmentDTO, String>("name"));
        colType.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDepartmentType().getTitle()));
        //colIsSuspended.setCellValueFactory(new PropertyValueFactory<Category,Boolean>("isSuspended"));
        colIsSuspended.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isSuspended()));
        colIsSuspended.setCellFactory(CheckBoxTableCell.forTableColumn(colIsSuspended));


        tbDepartment.setItems(FXCollections.observableList(departmentService.findAll()));
    }


    private void save(DepartmentDTO DepartmentDTO) {

        departmentService.save(DepartmentDTO);
        reload();
    }
    private void reload() {
        tbDepartment.getItems().clear();
        tbDepartment.setItems(FXCollections.observableList(departmentService.findAll()));
    }
    private void reload(List<DepartmentDTO> departmentDTOList){
        tbDepartment.getItems().clear();
        tbDepartment.setItems(FXCollections.observableList(departmentDTOList));
    }
    @FXML
    public void createDepartmentView(){
        DepartmentEdit.loadView(null,this::save,tbDepartment);
    }
    @FXML
    public void updateDepartmentView(){
        DepartmentDTO selectedDepartmentDTO=tbDepartment.getSelectionModel().getSelectedItem();
        if (selectedDepartmentDTO != null) {
            DepartmentEdit.loadView(selectedDepartmentDTO,this::save,tbDepartment);
        }

    }
    @FXML
    private void deleteDepartment() {
        DepartmentDTO selectedDepartmentDTO = tbDepartment.getSelectionModel().getSelectedItem();

        if (selectedDepartmentDTO != null) {

            Optional<ButtonType> result = CustomAlert.AlertBuilder.builder(Alert.AlertType.WARNING)
                    .setHeaderText(null)
                    .setContentText(DisplayMessage.getWarningDeleteMessage(AppConstants.MENU_DEPARTMENT,selectedDepartmentDTO.getName()))
                    .setYesNoButtonTypes()
                    .build()
                    .showAndWait();
            if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                departmentService.softDelete(selectedDepartmentDTO.getId());
                reload();
                //show notification
                CustomNotification.createNotification("Trạng thái", "Xóa thành công", tbDepartment).showInformation();
            }

        }


    }
    @FXML
    private void search(){
        String departmentName=tfName.getText();
        DepartmentType departmentType=cbDepartmentType.getValue();
        List<DepartmentDTO> departmentDTOList = departmentService.search(departmentName,departmentType);
        reload(departmentDTOList);
    }
   @FXML
    private void clear(){
        tfName.setText("");
        cbDepartmentType.setValue(null);
   }
}
