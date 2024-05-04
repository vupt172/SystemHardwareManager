package com.vupt.SHM.views;

import com.vupt.SHM.SHMApplication;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.security.UserDetailsServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    @FXML
    Button loginBtn;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label message;


    public static void loadView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com.vupt.SHM.views/login.fxml"));
        loader.setControllerFactory(SHMApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        stage.setScene(new Scene(view));
        stage.initStyle(StageStyle.UNDECORATED);
        LoginController controller = loader.getController();
        //controller.attachEvent();

        stage.show();
    }

    @FXML
    public void login() {
        try {
            if (username.getText().trim().equals(""))
                message.setText("Tài khoản không được để trống");
            else if (password.getText().trim().equals(""))
                message.setText("Mật khẩu không được để trống");
            else {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username.getText(), password.getText())
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                SHMController.loadView();
                close();
            }

        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            message.setText("Tài khoản hoặc mật khẩu không chính xác!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void close() {
        loginBtn.getScene().getWindow().hide();
    }
}
