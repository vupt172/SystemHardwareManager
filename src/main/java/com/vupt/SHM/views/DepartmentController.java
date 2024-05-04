package com.vupt.SHM.views;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.views.popup.DepartmentEdit;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    ModelMapper modelMapper;
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
        String window = "Quản lý tổ chức";
        primaryStage.setTitle(window);
        return view;
    }

    @FXML
    public void initialize() {


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
    @FXML
    public void createDepartmentView(){
        DepartmentEdit.loadView(null,this::save,tbDepartment);
    }

}
