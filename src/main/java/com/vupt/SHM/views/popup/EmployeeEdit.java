package com.vupt.SHM.views.popup;

import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.services.EmployeeService;
import com.vupt.SHM.views.common.CustomNotification;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class EmployeeEdit {

    private Consumer<EmployeeDTO> saveHandler;
    private Object owner;
    private EmployeeDTO employeeDTO;
    @Autowired
    DepartmentService departmentService;
    @FXML
    Label lbTitle;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfContact;
    @FXML
    private CheckBox cbIsManager;
    @FXML
    private Label lbMessage;
    @FXML
    private ComboBox<DepartmentDTO> cbDepartment;
    @FXML
    private Button btnSave;


    public static void loadView(Consumer<EmployeeDTO> saveHandler, Object owner) {
        loadView(null, saveHandler, owner);
    }

    public static void loadView(EmployeeDTO employeeDTO, Consumer<EmployeeDTO> saveHandler, Object owner) {
        /* public static void loadView(Category category, Consumer<Category> saveHandler){*/
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(EmployeeEdit.class.getResource("/com.vupt.SHM.views/popup/EmployeeEdit.fxml"));
            loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
            stage.setScene(new Scene(loader.load()));

            //initialize form
            EmployeeEdit employeeEdit = loader.getController();
            employeeEdit.init(employeeDTO, saveHandler, owner);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(EmployeeDTO employeeDTO, Consumer<EmployeeDTO> saveHandler, Object owner) {
        this.saveHandler = saveHandler;
        this.owner = owner;
        this.tfId.setEditable(false);
        this.cbDepartment.setItems(FXCollections.observableList(departmentService.findAll()));
        if (employeeDTO == null) {
            lbTitle.setText("Thêm nhân viên mới");
            this.employeeDTO = new EmployeeDTO();

        } else {
            lbTitle.setText("Cập nhật nhân viên");
            this.employeeDTO=employeeDTO;

            tfId.setText(String.valueOf(employeeDTO.getId()));
            tfName.setText(employeeDTO.getFullName());
            tfUsername.setText(employeeDTO.getUsername());
            cbDepartment.setValue(departmentService.getDTO(employeeDTO.getDepartmentId()));
            tfContact.setText(employeeDTO.getContact());
            cbIsManager.setSelected(employeeDTO.isManager());
        }
    }

    @FXML
    private void save() {
        if (tfName.getText().trim().isEmpty()) {
            lbMessage.setText("Tên nhân viên không được để trống");
            return;
        }

        if (cbDepartment.getValue() == null) {
            lbMessage.setText("Bộ phận không được để trống");
            return;
        }
        try {
            employeeDTO.setFullName(tfName.getText());
            employeeDTO.setUsername(tfUsername.getText());
            employeeDTO.setDepartmentId(cbDepartment.getValue().getId());
            employeeDTO.setContact(tfContact.getText());
            employeeDTO.setManager(cbIsManager.isSelected());
            saveHandler.accept(employeeDTO);

            //show notification
            CustomNotification.createNotification("Trạng thái", "Lưu thành công", owner).showInformation();

            close();
        } catch (AppException e) {
            lbMessage.setText(e.getMessage());
        }

    }

    @FXML
    private void close() {
        btnSave.getScene().getWindow().hide();
    }
}
