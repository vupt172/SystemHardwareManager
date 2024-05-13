package com.vupt.SHM.views;

import com.vupt.SHM.DTO.*;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.AppConstants;
import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.services.CategoryService;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.services.EmployeeService;
import com.vupt.SHM.services.EquipmentService;
import com.vupt.SHM.utils.DateTimeUtils;
import com.vupt.SHM.utils.DisplayMessage;
import com.vupt.SHM.views.common.CustomAlert;
import com.vupt.SHM.views.common.CustomNotification;
import com.vupt.SHM.views.component.DepartmentTypeListCell;
import com.vupt.SHM.views.component.EquipmentStatusListCell;
import com.vupt.SHM.views.popup.CategoryEdit;
import com.vupt.SHM.views.popup.EquipmentEdit;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class EquipmentController {
    private String windowTitle="Quản lý thiết bị phần cứng";

    @Autowired
    EquipmentService equipmentService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
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

    //footer
    @FXML
    TextField tfName;
    @FXML
    TextField tfCode;

    @FXML
    ComboBox<EquipmentStatus> cbStatus;
   @FXML
    ComboBox<CategoryDTO> cbCategory;
    @FXML
    ComboBox<EmployeeDTO> cbManager;
    @FXML
    ComboBox<DepartmentDTO> cbDepartment;
    @FXML
    Button btnSearch;



    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(AccountController.class.getResource("/com.vupt.SHM.views/equipment.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        view.setId(AppConstants.MANAGE_EQUIPMENT);

        EquipmentController equipmentController=loader.getController();
        primaryStage.setTitle(AppConstants.MANAGE_EQUIPMENT);
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

        //footer
        cbStatus.getItems().addAll(EquipmentStatus.values());
        cbStatus.setCellFactory(param -> new EquipmentStatusListCell());
        cbStatus.setButtonCell(new EquipmentStatusListCell());

        cbCategory.setItems((FXCollections.observableList(categoryService.findAllIfNotSuspended())));
        cbDepartment.setItems(FXCollections.observableList(departmentService.findAll()));
        cbManager.setItems(FXCollections.observableList(employeeService.findByIsManager()));
    }


    private void save(EquipmentDTO equipmentDTO) {
        equipmentService.save(equipmentDTO);
        reload();
    }

    private void reload() {
        tbEquipment.getItems().clear();
        tbEquipment.setItems(FXCollections.observableList(equipmentService.findAll()));
    }
    private void reload(List<EquipmentDTO> equipmentDTOList){
        tbEquipment.getItems().clear();
        tbEquipment.setItems(FXCollections.observableList(equipmentDTOList));
    }
    @FXML
    public void createEquipmentView(){
        EquipmentEdit.loadView(this::save, tbEquipment);
    }
    @FXML
    public void updateEquipmentView(){
        EquipmentDTO selectedEquipmentDTO= tbEquipment.getSelectionModel().getSelectedItem();
        if(selectedEquipmentDTO!=null){
            EquipmentEdit.loadView(selectedEquipmentDTO,this::save,tbEquipment);
        }
    }
    @FXML
    public void deleteEquipment(){
        EquipmentDTO selectEquipmentDTO = tbEquipment.getSelectionModel().getSelectedItem();
        if(selectEquipmentDTO!=null){
            Optional<ButtonType> result = CustomAlert.AlertBuilder.builder(Alert.AlertType.WARNING)
                    .setHeaderText(null)
                    .setContentText(DisplayMessage.getWarningDeleteMessage(AppConstants.MENU_EQUIPMENT,selectEquipmentDTO.getCode()))
                    .setYesNoButtonTypes()
                    .build()
                    .showAndWait();

            if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                equipmentService.softDelete(selectEquipmentDTO.getId());
                reload();
                //show notification
                CustomNotification.createNotification("Trạng thái", "Xóa thành công", tbEquipment).showInformation();

            }
        }
    }
    @FXML
    public void search(){
        String name=tfName.getText();
        String code=tfCode.getText();
        long categoryId= cbCategory.getValue()!=null?cbCategory.getValue().getId():0;
        long departmentId=cbDepartment.getValue()!=null?cbDepartment.getValue().getId():0;
        long managerId=cbManager.getValue()!=null?cbManager.getValue().getId():0;
        EquipmentStatus  status=cbStatus.getValue();
    List<EquipmentDTO> equipmentDTOList=equipmentService.search(name,code,status,categoryId,managerId,departmentId);
    reload(equipmentDTOList);
    }
    @FXML
    public void clear(){
   tfName.setText("");
   tfCode.setText("");
   cbStatus.setValue(null);
   cbCategory.setValue(null);
   cbDepartment.setValue(null);
   cbManager.setValue(null);
    }
}
