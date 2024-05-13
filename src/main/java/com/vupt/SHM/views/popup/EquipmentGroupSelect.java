package com.vupt.SHM.views.popup;

import com.vupt.SHM.DTO.*;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.entity.Equipment;
import com.vupt.SHM.entity.EquipmentGroup;
import com.vupt.SHM.services.CategoryService;
import com.vupt.SHM.services.DepartmentService;
import com.vupt.SHM.services.EquipmentGroupService;
import com.vupt.SHM.services.EquipmentService;
import com.vupt.SHM.utils.FxUtilTest;
import com.vupt.SHM.views.EquipmentGroupController;
import com.vupt.SHM.views.common.CustomNotification;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Controller
public class EquipmentGroupSelect {
    private Object owner;
    private EquipmentGroupSelectDTO equipmentGroupSelectDTO;
    private EquipmentGroupDTO equipmentGroupDTO;
    private EquipmentGroupController equipmentGroupController;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EquipmentGroupService equipmentGroupService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    CategoryService categoryService;
    @FXML
    Label lbTitle;
    @FXML
    Button btnSave;
    @FXML
    Button btnSelect;
    @FXML
    ComboBox<DepartmentDTO> cbDepartment;
    @FXML
    ComboBox<EquipmentGroupDTO> cbEquipmentGroup;
    @FXML
    ComboBox<CategoryDTO> cbCategory;
    @FXML
    ComboBox<EquipmentDTO> cbEquipment;

    public static void loadView(EquipmentGroupController equipmentGroupController, EquipmentGroupDTO equipmentGroupDTO, Object owner) {
        loadView(equipmentGroupController, equipmentGroupDTO, null, owner);

    }

    public static void loadView(EquipmentGroupController equipmentGroupController, EquipmentGroupDTO equipmentGroupDTO, EquipmentGroupSelectDTO equipmentGroupSelectDTO, Object owner) {
        /* public static void loadView(Category category, Consumer<Category> saveHandler){*/
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader(EquipmentGroupSelect.class.getResource("/com.vupt.SHM.views/popup/EquipmentGroupSelect.fxml"));
            loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
            stage.setScene(new Scene(loader.load()));

            //initialize form
            EquipmentGroupSelect equipmentGroupSelect = loader.getController();
            equipmentGroupSelect.init(equipmentGroupController, equipmentGroupDTO, equipmentGroupSelectDTO, owner);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(EquipmentGroupController equipmentGroupController, EquipmentGroupDTO equipmentGroupDTO, EquipmentGroupSelectDTO equipmentGroupSelectDTO, Object owner) {
        this.owner = owner;
        this.equipmentGroupController = equipmentGroupController;
        this.equipmentGroupDTO = equipmentGroupDTO;
        this.cbDepartment.setEditable(false);
        this.cbEquipmentGroup.setEditable(false);

        this.cbDepartment.setValue(departmentService.getDTO(equipmentGroupDTO.getDepartmentId()));
        this.cbEquipmentGroup.setValue(equipmentGroupService.getDTO(equipmentGroupDTO.getId()));

        this.cbCategory.getItems().addAll(categoryService.findAllIfNotSuspended());
        this.cbCategory.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectEquipmentsByCategory();
        });
        if (equipmentGroupSelectDTO == null) {
            this.lbTitle.setText("Chọn thiết bị vào nhóm");
        }
    }

    @FXML
    public void save() {
        equipmentGroupService.addEquipment(equipmentGroupDTO, cbEquipment.getValue().getId());

        //show notification
        CustomNotification.createNotification("Trạng thái", "Lưu thành công", owner).showInformation();
        equipmentGroupController.reload();
        close();
    }

    @FXML
    public void close() {
        btnSave.getScene().getWindow().hide();
    }

    public void selectEquipmentsByCategory() {
        List<EquipmentDTO> equipmentDTOList = equipmentService.selectEquipment(cbDepartment.getValue().getId(), cbCategory.getValue().getId());

        cbEquipment.getItems().clear();
        cbEquipment.getItems().addAll(equipmentDTOList);
        //FxUtilTest.autoCompleteComboBoxPlus(cbEquipment,(typedText, equipmentDTO)->equipmentDTO.toString().contains(typedText));

    }

}
