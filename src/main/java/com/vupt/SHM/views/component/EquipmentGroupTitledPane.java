package com.vupt.SHM.views.component;

import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.entity.Equipment;
import com.vupt.SHM.views.EquipmentGroupController;
import com.vupt.SHM.views.SHMController;
import com.vupt.SHM.views.common.CustomAlert;
import com.vupt.SHM.views.common.CustomNotification;
import com.vupt.SHM.views.popup.EquipmentGroupSelect;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class EquipmentGroupTitledPane extends TitledPane {
    private EquipmentGroupDTO equipmentGroupDTO;
    private  EquipmentGroupController equipmentGroupController;
    HBox content;
   TableView<EquipmentDTO> tbEquipment;
   TableColumn<EquipmentDTO,String> colCategory;
   TableColumn<EquipmentDTO,String> colName;
   TableColumn<EquipmentDTO,String> colCode;
    public EquipmentGroupTitledPane(EquipmentGroupController equipmentGroupController, EquipmentGroupDTO equipmentGroupDTO) {

        this.equipmentGroupDTO=equipmentGroupDTO;
        this.equipmentGroupController=equipmentGroupController;
        this.setText(equipmentGroupDTO.getName());
        this.content=new HBox();
        tbEquipment=new TableView<>();

        tbEquipment.setPrefHeight(250);
        tbEquipment.setPrefWidth(500);
        TableColumn<EquipmentDTO, Integer> numberColumn = new TableColumn<>("STT");
        numberColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tbEquipment.getItems().indexOf(cellData.getValue()) + 1));
        numberColumn.setPrefWidth(50);

        colCategory=new TableColumn<>("Danh mục");
        colCategory.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("categoryName"));
        colCategory.setPrefWidth(150);

        colName=new TableColumn<>("Tên thiết bị");
        colName.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("name"));
        colName.setPrefWidth(150);

        colCode=new TableColumn<>("Code");
        colCode.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("code"));
        colCode.setPrefWidth(150);

        tbEquipment.getColumns().addAll(numberColumn,colCategory,colName,colCode);

        tbEquipment.setContextMenu(initContextMenu());
        //tbEquipment.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tbEquipment.setItems(FXCollections.observableList(equipmentGroupDTO.getEquipmentDTOList()));
      /*  tbEquipment.setItems(FXCollections.observableList(equipmentDTOList));*/
        content.getChildren().add(tbEquipment);
        this.setContent(content);
    }
    public ContextMenu initContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem itemAdd = new MenuItem("Thêm Thiết Bị");
        itemAdd.setOnAction(e-> EquipmentGroupSelect.loadView(equipmentGroupController,equipmentGroupDTO,tbEquipment));
        MenuItem itemRemove=new MenuItem("Xóa Thiết Bị");
        itemRemove.setOnAction(e-> {
                    EquipmentDTO selectedEquipmentDTO = tbEquipment.getSelectionModel().getSelectedItem();
                    if (selectedEquipmentDTO != null) {

                       Optional<ButtonType> result=  CustomAlert.AlertBuilder.builder(Alert.AlertType.WARNING)
                                .setHeaderText(null)
                                .setContentText("Bỏ thiết bị " + tbEquipment.getSelectionModel().getSelectedItem().getCode() + " ở " + equipmentGroupDTO.getName())
                                .setYesNoButtonTypes()
                                .build().showAndWait();
                       if(result.get().getButtonData()== ButtonBar.ButtonData.YES){
                           equipmentGroupController.removeEquipment(selectedEquipmentDTO.getId());

                           //show notification
                           CustomNotification.createNotification("Trạng thái","Xóa thành công",equipmentGroupController.getContent()).showInformation();

                           equipmentGroupController.reload();
                       }

                    }
                });
        contextMenu.getItems().addAll(itemAdd,itemRemove);
        return contextMenu;
    }
}
