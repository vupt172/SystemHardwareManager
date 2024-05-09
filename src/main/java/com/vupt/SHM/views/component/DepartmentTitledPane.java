package com.vupt.SHM.views.component;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.entity.EquipmentGroup;
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
    public DepartmentTitledPane(String title, List<EquipmentGroupDTO> listContent, Supplier<Void> reload){
        equipmentGroupTitledPaneList=new ArrayList<>();
        this.setText(title);
        content=new VBox();
        content.setSpacing(10);
       equipmentGroupTitledPaneList= listContent.stream()
               .map(e->new EquipmentGroupTitledPane(e,reload))
               .collect(Collectors.toList());


        content.getChildren().addAll(equipmentGroupTitledPaneList);
        this.setContent(content);
    }
}
