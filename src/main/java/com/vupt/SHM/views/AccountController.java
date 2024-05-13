package com.vupt.SHM.views;

import com.vupt.SHM.DTO.AccountDTO;
import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.AppConstants;
import com.vupt.SHM.entity.Account;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.services.AccountService;
import com.vupt.SHM.views.popup.AccountEdit;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ModelMapper modelMapper;
    @FXML
    TableView<AccountDTO> tbAccount;
    @FXML
    private TableColumn<AccountDTO, Long> colId;
    @FXML
    private TableColumn<AccountDTO, String> colUsername;
    @FXML
    private TableColumn<AccountDTO, String> colFullName;
    @FXML
    private TableColumn<AccountDTO, Boolean> colIsSuspended;

    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(AccountController.class.getResource("/com.vupt.SHM.views/account.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        view.setId(AppConstants.MANAGE_ACCOUNT);

        primaryStage.setTitle(AppConstants.MANAGE_ACCOUNT);
        return view;
    }
    @FXML
    public void initialize() {


        colId.setCellValueFactory(new PropertyValueFactory<AccountDTO, Long>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<AccountDTO, String>("username"));
        colFullName.setCellValueFactory(new PropertyValueFactory<AccountDTO, String>("fullName"));
        //colIsSuspended.setCellValueFactory(new PropertyValueFactory<Category,Boolean>("isSuspended"));
        colIsSuspended.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isSuspended()));
        colIsSuspended.setCellFactory(CheckBoxTableCell.forTableColumn(colIsSuspended));


        tbAccount.setItems(FXCollections.observableList(accountService.findAll()));
    }
    private void save(AccountDTO AccountDTO) {

        accountService.save(AccountDTO);
        reload();
    }

    private void reload() {
        tbAccount.getItems().clear();
        tbAccount.setItems(FXCollections.observableList(accountService.findAll()));
    }
    @FXML
    public void createAccountView(){
        AccountEdit.loadView(this::save,tbAccount);
    }

}
