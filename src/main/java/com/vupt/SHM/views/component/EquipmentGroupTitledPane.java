package com.vupt.SHM.views.component;

import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.views.SHMController;
import com.vupt.SHM.views.popup.EquipmentGroupSelect;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public class EquipmentGroupTitledPane extends TitledPane {
   TableView<EquipmentDTO> tbEquipment;
   TableColumn<EquipmentDTO,String> colCategory;
   TableColumn<EquipmentDTO,String> colName;
   TableColumn<EquipmentDTO,String> colCode;
    public EquipmentGroupTitledPane(EquipmentGroupDTO equipmentGroupDTO, Supplier<Void> reload) {

        this.setText(equipmentGroupDTO.getName());
        tbEquipment=new TableView<>();
        tbEquipment.setMinHeight(200);
        colCategory=new TableColumn<>("Danh mục");
        colCategory.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("categoryName"));
        colName=new TableColumn<>("Tên thiết bị");
        colName.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("name"));
        colCode=new TableColumn<>("Code");
        colCode.setCellValueFactory(new PropertyValueFactory<EquipmentDTO,String>("code"));

        tbEquipment.getColumns().addAll(colCategory,colName,colCode);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Thêm Thiết Bị");
        item1.setOnAction(e-> EquipmentGroupSelect.loadView(equipmentGroupDTO,tbEquipment,reload));
        MenuItem item2=new MenuItem("Xóa Thiết Bị");
        contextMenu.getItems().addAll(item1,item2);


        tbEquipment.setContextMenu(contextMenu);
        tbEquipment.setItems(FXCollections.observableList(equipmentGroupDTO.getEquipmentDTOList()));
      /*  tbEquipment.setItems(FXCollections.observableList(equipmentDTOList));*/
        this.setContent(tbEquipment);
    }
}
