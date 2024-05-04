package com.vupt.SHM.views.popup;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.views.common.CustomNotification;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;
@Controller
public class EquipmentGroupEdit {
    @Autowired
    DepartmentService departmentService;
    private Consumer<EquipmentGroupDTO> saveHandler;
    private EquipmentGroupDTO equipmentGroupDTO;
    private Object owner;
    @FXML
    private Label lbTitle;
    @FXML
    private Button btnSave;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private ComboBox<DepartmentDTO> cbDepartment;
    public static void loadView(Consumer<EquipmentGroupDTO> saveHandler, Object owner) {
        loadView(null, saveHandler, owner);
    }

    public static void loadView(EquipmentGroupDTO equipmentGroupDTO, Consumer<EquipmentGroupDTO> saveHandler, Object owner) {
        /* public static void loadView(Category category, Consumer<Category> saveHandler){*/
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(EquipmentGroupEdit.class.getResource("/com.vupt.SHM.views/popup/EquipmentGroupEdit.fxml"));
            loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
            stage.setScene(new Scene(loader.load()));

            //initialize form
            EquipmentGroupEdit equipmentGroupEdit = loader.getController();
            equipmentGroupEdit.init(equipmentGroupDTO,saveHandler,owner);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void init(EquipmentGroupDTO equipmentGroupDTO, Consumer<EquipmentGroupDTO> saveHandler,Object owner) {
        this.saveHandler=saveHandler;
        this.owner=owner;

        this.tfId.setEditable(false);

        cbDepartment.getItems().addAll(departmentService.findAll());



        if(equipmentGroupDTO == null){
            this.equipmentGroupDTO=new EquipmentGroupDTO();
            this.lbTitle.setText("Thêm thiết bị mới");

        }
        else {
            this.equipmentGroupDTO=equipmentGroupDTO;
            this.lbTitle.setText("Cập nhật thiết bị");

        }
    }


    @FXML
    private void save() {
       equipmentGroupDTO.setName(tfName.getText());
       equipmentGroupDTO.setDepartmentId(cbDepartment.getValue().getId());
       saveHandler.accept(equipmentGroupDTO);
        //show notification
        CustomNotification.createNotification("Trạng thái","Lưu thành công",owner).showInformation();

        close();
    }
    @FXML
    public void close() {
        btnSave.getScene().getWindow().hide();
    }
}
