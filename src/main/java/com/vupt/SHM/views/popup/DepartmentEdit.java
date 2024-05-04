package com.vupt.SHM.views.popup;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.views.common.CustomNotification;
import com.vupt.SHM.views.component.DepartmentTypeListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;
@Controller
public class DepartmentEdit {
    @Autowired
    PasswordEncoder passwordEncoder;
    private Consumer<DepartmentDTO> saveHandler;
    private DepartmentDTO departmentDTO;
    private Object owner;
    @FXML
    Label lbTitle;
    @FXML
    TextField tfId;
    @FXML
    TextField tfName;
    @FXML
    ComboBox<DepartmentType> cbTypeList;
    @FXML
    CheckBox cbIsSuspended;
    @FXML
    Label lbMessage;
    @FXML
    javafx.scene.control.Button btnSave;
    



    public static void loadView(Consumer<DepartmentDTO> saveHandler, Object owner){
        loadView(null,saveHandler,owner);
    }
    public static void loadView(DepartmentDTO departmentDTO,Consumer<DepartmentDTO> saveHandler,Object owner){
        /* public static void loadView(Category category, Consumer<Category> saveHandler){*/
        try{
            Stage stage=new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(DepartmentEdit.class.getResource("/com.vupt.SHM.views/popup/DepartmentEdit.fxml"));
            loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
            stage.setScene(new Scene(loader.load()));

            //initialize form
            DepartmentEdit departmentEdit=loader.getController();
            departmentEdit.init(departmentDTO,saveHandler,owner);

            stage.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void init(DepartmentDTO departmentDTO, Consumer<DepartmentDTO> saveHandler,Object owner) {
        this.saveHandler=saveHandler;
        this.owner=owner;
        this.tfId.setEditable(false);
        cbTypeList.getItems().addAll(DepartmentType.values());
        cbTypeList.setCellFactory(param -> new DepartmentTypeListCell());
        cbTypeList.setButtonCell(new DepartmentTypeListCell());

        if(departmentDTO == null){
            this.departmentDTO=new DepartmentDTO();
            this.lbTitle.setText("Thêm bộ phận mới");
            this.cbIsSuspended.setDisable(true);
        }
        else {
            this.departmentDTO=departmentDTO;
            this.lbTitle.setText("Cập nhật bộ phận");
            this.tfId.setText(String.valueOf(departmentDTO.getId()));
            this.tfName.setText(departmentDTO.getName());
            this.cbTypeList.setValue(departmentDTO.getDepartmentType());
            this.cbIsSuspended.setSelected(departmentDTO.isSuspended());
        }
    }
    @FXML
    private void save(){
        departmentDTO.setName(tfName.getText());
        departmentDTO.setDepartmentType(cbTypeList.getValue());
        departmentDTO.setSuspended(cbIsSuspended.isSelected());

        saveHandler.accept(departmentDTO);

        //show notification
        CustomNotification.createNotification("Trạng thái","Lưu thành công",owner).showInformation();

        close();
    }
    @FXML
    public void close(){
        btnSave.getScene().getWindow().hide();
    }


}
