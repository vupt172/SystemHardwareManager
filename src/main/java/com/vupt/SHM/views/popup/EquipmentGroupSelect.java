package com.vupt.SHM.views.popup;

import com.vupt.SHM.DTO.*;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.entity.EquipmentGroup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class EquipmentGroupSelect {
    private Object owner;
    private EquipmentGroupSelectDTO equipmentGroupSelectDTO;
    @FXML
    Button btnSave;
    @FXML
    Button btnSelect;
    @FXML
    TextField tfDepartment;
    @FXML
    TextField tfEquipmentGroup;
    @FXML
    ComboBox<CategoryDTO> cbCategory;
    @FXML
    ComboBox<EquipmentDTO> cbEquipment;
    public static void loadView(long departmentId,long equipmentGroupId, Object owner) {
        loadView(departmentId,equipmentGroupId,null, owner);
    }

    public static void loadView(long departmentId,long equipmentGroupId,EquipmentGroupSelectDTO equipmentGroupSelectDTO, Object owner) {
        /* public static void loadView(Category category, Consumer<Category> saveHandler){*/
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(EquipmentGroupSelect.class.getResource("/com.vupt.SHM.views/popup/EquipmentGroupSelect.fxml"));
            loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
            stage.setScene(new Scene(loader.load()));

            //initialize form
            EquipmentGroupSelect equipmentGroupSelect = loader.getController();
            equipmentGroupSelect.init(departmentId,equipmentGroupId,equipmentGroupSelectDTO,owner);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void init(long departmentId,long equipmentGroupId,EquipmentGroupSelectDTO equipmentGroupSelectDTO,Object owner){
        this.owner=owner;
        this.tfDepartment.setEditable(false);
        this.tfEquipmentGroup.setEditable(false);

        this.tfDepartment.setText(String.valueOf(departmentId));
        this.tfEquipmentGroup.setText(String.valueOf(equipmentGroupId));
        if(equipmentGroupSelectDTO==null){

        }
    }
    @FXML
    public void save(){}
    @FXML
    public void close(){
           btnSave.getScene().getWindow().hide();
    }
    @FXML
    public void selectCategory(){

    }
}
