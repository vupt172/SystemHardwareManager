package com.vupt.SHM;

import com.vupt.SHM.audit.AuditorAwareImpl;
import com.vupt.SHM.modelmapper.configuration.EmployeeMap;
import com.vupt.SHM.modelmapper.configuration.EquipmentMap;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
@Configuration
public class AppConfiguration {
    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.addMappings(new EmployeeMap()) ;
        modelMapper.addMappings(new EquipmentMap());
        return modelMapper;
    }
}
