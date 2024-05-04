package com.vupt.SHM.views.popup;

import com.vupt.SHM.DTO.AccountDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.entity.Account;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.views.common.CustomNotification;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;
@Controller
public class AccountEdit {
    @Autowired
    PasswordEncoder passwordEncoder;
    private Consumer<AccountDTO> saveHandler;
    private AccountDTO accountDTO;
    private Object owner;
    @FXML
    Label lbTitle;
    @FXML
    TextField tfId;
    @FXML
    TextField tfUsername;
    @FXML
    TextField tfFullName;
    @FXML
    PasswordField tfPassword;
    @FXML
    CheckBox cbIsSuspended;
    @FXML
    Label lbMessage;
    @FXML
    javafx.scene.control.Button btnSave;

    public static void loadView(Consumer<AccountDTO> saveHandler, Object owner){
        loadView(null,saveHandler,owner);
    }
    public static void loadView(AccountDTO accountDTO,Consumer<AccountDTO> saveHandler,Object owner){
        /* public static void loadView(Category category, Consumer<Category> saveHandler){*/
        try{
            Stage stage=new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(AccountEdit.class.getResource("/com.vupt.SHM.views/popup/AccountEdit.fxml"));
            loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
            stage.setScene(new Scene(loader.load()));

            //initialize form
            AccountEdit accountEdit=loader.getController();
            accountEdit.init(accountDTO,saveHandler,owner);

            stage.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void init(AccountDTO AccountDTO, Consumer<AccountDTO> saveHandler,Object owner) {
        this.saveHandler=saveHandler;
        this.owner=owner;
        this.tfId.setEditable(false);
        if(AccountDTO == null){
            this.accountDTO=new AccountDTO();
            this.lbTitle.setText("Thêm tài khoản mới");
            this.cbIsSuspended.setDisable(true);
        }
        else {
            this.accountDTO=AccountDTO;
            this.lbTitle.setText("Cập nhật tài khoản");
            this.tfId.setText(String.valueOf(AccountDTO.getId()));
            this.tfUsername.setText(AccountDTO.getUsername());
            this.tfFullName.setText(AccountDTO.getFullName());
            this.tfPassword.setText(AccountDTO.getPassword());
            this.cbIsSuspended.setSelected(AccountDTO.isSuspended());

            this.tfUsername.setEditable(true);
            this.tfPassword.setEditable(true);
        }
    }
    @FXML
    private void save(){
        accountDTO.setUsername(tfUsername.getText());
        accountDTO.setFullName(tfFullName.getText());
        accountDTO.setPassword(passwordEncoder.encode(tfPassword.getText()));
        accountDTO.setSuspended(cbIsSuspended.isSelected());

        saveHandler.accept(accountDTO);

        //show notification
        CustomNotification.createNotification("Trạng thái","Lưu thành công",owner).showInformation();

        close();
    }
    @FXML
    private void close(){
        btnSave.getScene().getWindow().hide();
    }
}
