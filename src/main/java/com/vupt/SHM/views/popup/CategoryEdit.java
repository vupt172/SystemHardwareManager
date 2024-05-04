package com.vupt.SHM.views.popup;

import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.views.CategoryController;
import com.vupt.SHM.views.common.CustomNotification;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



import java.util.function.Consumer;
@Controller
public class CategoryEdit {

    private Consumer<CategoryDTO> saveHandler;
    private CategoryDTO categoryDTO;
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
    CheckBox cbIsSuspended;
    @FXML
    Label lbMessage;
    @FXML
    javafx.scene.control.Button btnSave;
    public static void loadView(Consumer<CategoryDTO> saveHandler, Object owner){
        loadView(null,saveHandler,owner);
    }
    public static void loadView(CategoryDTO categoryDTO,Consumer<CategoryDTO> saveHandler,Object owner){
   /* public static void loadView(Category category, Consumer<Category> saveHandler){*/
        try{
            Stage stage=new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(CategoryEdit.class.getResource("/com.vupt.SHM.views/popup/CategoryEdit.fxml"));
            stage.setScene(new Scene(loader.load()));

            //initialize form
            CategoryEdit categoryEdit=loader.getController();
            categoryEdit.init(categoryDTO,saveHandler,owner);

            stage.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void init(CategoryDTO categoryDTO, Consumer<CategoryDTO> saveHandler,Object owner) {
        this.saveHandler=saveHandler;
        this.owner=owner;
        this.tfId.setEditable(false);
        if(categoryDTO == null){
            this.categoryDTO=new CategoryDTO();
            this.lbTitle.setText("Thêm danh mục mới");
            this.cbIsSuspended.setDisable(true);
        }
        else {
            this.categoryDTO=categoryDTO;
            this.lbTitle.setText("Cập nhật danh mục");
            this.tfId.setText(String.valueOf(categoryDTO.getId()));
            this.tfName.setText(categoryDTO.getName());
            this.tfCode.setText(categoryDTO.getCode());
            this.cbIsSuspended.setSelected(categoryDTO.isSuspended());
        }
    }
    @FXML
    private void save(){
        try{
            if(tfName.getText().trim().isEmpty()){
                lbMessage.setText("Tên danh mục không được trống");
                return;
            }
            else if(tfCode.getText().trim().isEmpty()){
                lbMessage.setText("Code không được trống");
                return;
            }


            categoryDTO.setName(tfName.getText());
            categoryDTO.setCode(tfCode.getText());
            categoryDTO.setSuspended(cbIsSuspended.isSelected());
            saveHandler.accept(categoryDTO);

            //show notification
            CustomNotification.createNotification("Trạng thái","Lưu thành công",owner).showInformation();

            close();
        }
        catch (AppException e){
            lbMessage.setText(e.getMessage());
        }

    }
    @FXML
    private void close(){
        btnSave.getScene().getWindow().hide();
    }

}
