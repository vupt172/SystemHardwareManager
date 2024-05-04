package com.vupt.SHM;

import com.vupt.SHM.views.LoginController;
import com.vupt.SHM.views.SHMController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SHMApplication extends Application {
    private static ConfigurableApplicationContext applicationContext;
    @Override
    public void init() throws Exception {
        this.applicationContext= SpringApplication.run(SHMApplication.class);
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
    //  Test.loadView(primaryStage);
      //  LoginController.loadView(primaryStage);
        SHMController.loadView();
    }


    public static void main(String[] args) {
        launch(args);
    }
    public static ConfigurableApplicationContext getApplicationContext(){
        return applicationContext;
    }




}