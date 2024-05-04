package com.vupt.SHM.views;

import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.services.CategoryService;
import com.vupt.SHM.views.common.CustomNotification;
import com.vupt.SHM.views.popup.CategoryEdit;
import com.vupt.SHM.views.common.CustomAlert;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ModelMapper modelMapper;
    @FXML
    TableView<CategoryDTO> tbCategory;

    @FXML
    private TableColumn<CategoryDTO, Long> colId;
    @FXML
    private TableColumn<CategoryDTO, String> colName;
    @FXML
    private TableColumn<CategoryDTO, String> colCode;
    @FXML
    private TableColumn<CategoryDTO, Boolean> colIsSuspended;



    public static Parent loadView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(CategoryController.class.getResource("/com.vupt.SHM.views/category.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        String window = "Quản lý danh mục";
        primaryStage.setTitle(window);
        return view;
    }

    @FXML
    public void initialize() {


        colId.setCellValueFactory(new PropertyValueFactory<CategoryDTO, Long>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<CategoryDTO, String>("name"));
        colCode.setCellValueFactory(new PropertyValueFactory<CategoryDTO, String>("code"));
        //colIsSuspended.setCellValueFactory(new PropertyValueFactory<Category,Boolean>("isSuspended"));
        colIsSuspended.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isSuspended()));
        colIsSuspended.setCellFactory(CheckBoxTableCell.forTableColumn(colIsSuspended));


        tbCategory.setItems(FXCollections.observableList(categoryService.findAll()));
    }

    @FXML
    public void createCategoryView() {

        CategoryEdit.loadView(this::save, tbCategory);
    }

    private void save(CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        reload();
    }

    private void reload() {
        tbCategory.getItems().clear();
        tbCategory.setItems(FXCollections.observableList(categoryService.findAll()));
    }

    @FXML
    private void updateCategoryView() {
        CategoryDTO selectedCategoryDTO = tbCategory.getSelectionModel().getSelectedItem();

        if (selectedCategoryDTO == null) {
            CustomAlert.AlertBuilder.builder(Alert.AlertType.WARNING)
                    .setTitle("Thông báo")
                    .setHeaderText("Cập nhật danh mục")
                    .setContentText("Không có danh mục được chọn để cập nhật!")
                    .build()
                    .show();
            return;
        }
        CategoryEdit.loadView(selectedCategoryDTO, this::save, tbCategory);

    }

    @FXML
    private void deleteCategory() {
        CategoryDTO selectedCategoryDTO = tbCategory.getSelectionModel().getSelectedItem();

        if (selectedCategoryDTO == null) {

            CustomAlert.AlertBuilder.builder(Alert.AlertType.WARNING)
                    .setTitle("Thông báo")
                    .setHeaderText("Xóa danh mục")
                    .setContentText("Không có danh mục được chọn để xóa!")
                    .build()
                    .show();
            return;
        }
        categoryService.softDelete(selectedCategoryDTO.getId());

        reload();

        //show notification
        CustomNotification.createNotification("Trạng thái", "Xóa thành công", tbCategory).showInformation();

    }

}
