package com.vupt.SHM.views.popup;

import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.services.CategoryService;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.services.EmployeeService;
import com.vupt.SHM.views.common.CustomNotification;
import com.vupt.SHM.views.component.EquipmentStatusListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.function.Consumer;

@Controller
public class EquipmentEdit {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    private Consumer<EquipmentDTO> saveHandler;
    private EquipmentDTO equipmentDTO;
    private Object owner;
    @FXML
    Label lbTitle;

    @FXML
    TextField tfId;
    @FXML
    TextField tfName;
    @FXML
    TextField tfCode;
    @FXML
    DatePicker dpReceivedDate;
    @FXML
    ComboBox<EquipmentStatus> cbStatus;
    @FXML
    ComboBox<CategoryDTO> cbCategory;
    @FXML
    ComboBox<EmployeeDTO> cbManager;
    @FXML
    ComboBox<DepartmentDTO> cbDepartment;
    @FXML
    Label lbMessage;
    @FXML
    javafx.scene.control.Button btnSave;


    public static void loadView(Consumer<EquipmentDTO> saveHandler, Object owner) {
        loadView(null, saveHandler, owner);
    }

    public static void loadView(EquipmentDTO equipmentDTO, Consumer<EquipmentDTO> saveHandler, Object owner) {
        /* public static void loadView(Category category, Consumer<Category> saveHandler){*/
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(EquipmentEdit.class.getResource("/com.vupt.SHM.views/popup/EquipmentEdit.fxml"));
            loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
            stage.setScene(new Scene(loader.load()));

            //initialize form
            EquipmentEdit equipmentEdit = loader.getController();
            equipmentEdit.init(equipmentDTO, saveHandler, owner);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(EquipmentDTO equipmentDTO, Consumer<EquipmentDTO> saveHandler, Object owner) {
        this.saveHandler = saveHandler;
        this.owner = owner;

        this.tfId.setEditable(false);
        this.tfCode.setEditable(false);


        cbStatus.getItems().addAll(EquipmentStatus.values());
        cbStatus.setCellFactory(param -> new EquipmentStatusListCell());
        cbStatus.setButtonCell(new EquipmentStatusListCell());

        cbCategory.getItems().addAll(categoryService.findAllIfNotSuspended());
        cbManager.getItems().addAll(employeeService.findByIsManager());
        cbDepartment.getItems().addAll(departmentService.findAll());


        if (equipmentDTO == null) {
            this.equipmentDTO = new EquipmentDTO();
            this.lbTitle.setText("Thêm thiết bị mới");

        } else {
            this.equipmentDTO = equipmentDTO;
            this.lbTitle.setText("Cập nhật thiết bị");
            setFormValueFromDTO(equipmentDTO);
        }
    }

    private boolean checkValidateForm() {
        if (tfName.getText().trim().isEmpty()) {
            lbMessage.setText("Tên thiết bị không được để trống");
            return false;
        }
        if (dpReceivedDate.getValue() == null) {
            lbMessage.setText("Ngày nhận không được để trống");
            return false;
        }
        if (cbStatus.getValue() == null) {
            lbMessage.setText("Trạng thái không được để trống");
            return false;
        }
        if (cbCategory.getValue() == null) {
            lbMessage.setText("Danh mục không đươc để trống");
            return false;
        }
        if (cbManager.getValue() == null) {
            lbMessage.setText("Người quản lý không được để trống");
            return false;
        }
        if (cbDepartment.getValue() == null) {
            lbMessage.setText("Địa điểm không được để trống");
            return false;
        }
        return true;
    }

    private EquipmentDTO setDTOFromFormValue() {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setId(Integer.valueOf(tfId.getText()));
        equipmentDTO.setName(tfName.getText());
        equipmentDTO.setCode(tfCode.getText());
        equipmentDTO.setReceivedDate(Date.valueOf(dpReceivedDate.getValue()));
        equipmentDTO.setStatus(cbStatus.getValue());

        equipmentDTO.setCategoryId(cbCategory.getValue().getId());
        equipmentDTO.setDepartmentId(cbDepartment.getValue().getId());
        equipmentDTO.setManagerId(cbManager.getValue().getId());
        return equipmentDTO;
    }
    private void setFormValueFromDTO(EquipmentDTO equipmentDTO){
        tfId.setText(String.valueOf(equipmentDTO.getId()));
        tfName.setText(equipmentDTO.getName());
        tfCode.setText(equipmentDTO.getCode());
        dpReceivedDate.setValue(equipmentDTO.getReceivedDate().toLocalDate());
        cbStatus.setValue(equipmentDTO.getStatus());
        cbCategory.setValue(categoryService.getDTO(equipmentDTO.getCategoryId()));
        cbDepartment.setValue(departmentService.getDTO(equipmentDTO.getDepartmentId()));
        cbManager.setValue(employeeService.getDTO(equipmentDTO.getManagerId()));

    }


    @FXML
    private void save() {
        if (!checkValidateForm()) return;
        this.equipmentDTO = setDTOFromFormValue();
        saveHandler.accept(equipmentDTO);

        //show notification
        CustomNotification.createNotification("Trạng thái", "Lưu thành công", owner).showInformation();

        close();
    }

    @FXML
    public void close() {
        btnSave.getScene().getWindow().hide();
    }
}
