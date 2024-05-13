package com.vupt.SHM.views.component;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.entity.EquipmentGroup;
import com.vupt.SHM.views.EquipmentGroupController;
import com.vupt.SHM.views.popup.EquipmentGroupSelect;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DepartmentTitledPane extends TitledPane{
    private VBox content;
    private List<EquipmentGroupTitledPane> equipmentGroupTitledPaneList;
    public DepartmentTitledPane(EquipmentGroupController equipmentGroupController, String title, List<EquipmentGroupDTO> listContent,boolean expanded){
        equipmentGroupTitledPaneList=new ArrayList<>();
        this.setText(title);
        this.setExpanded(expanded);
        content=new VBox();
        content.setSpacing(10);

        equipmentGroupTitledPaneList = new ArrayList<>();
       listContent.forEach(e->{
           EquipmentGroupTitledPane equipmentGroupTitledPane= new EquipmentGroupTitledPane(equipmentGroupController,e);
           equipmentGroupTitledPaneList.add(equipmentGroupTitledPane);
       });

        content.getChildren().addAll(equipmentGroupTitledPaneList);
        this.setContent(content);
    }
}
