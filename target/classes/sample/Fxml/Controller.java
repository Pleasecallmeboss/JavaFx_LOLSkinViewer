package sample.Fxml;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Controller{

    @FXML
    private ImageView ImageBack;

    @FXML
    private ImageView ImaegCycle;

    @FXML
    private JFXPasswordField TextPassWord;

    @FXML
    private JFXTextField TextField;

    @FXML
    private JFXButton BtnLogUp;

    @FXML
    private JFXButton BtnLog;

    @FXML
    void OnClickLogUp(ActionEvent event) {

    }

    @FXML
    void OnClickLogin(ActionEvent event) {

    }

    @FXML
    private void initialize() {
        Image Image = new Image("./Back.jpg");
        ImageBack.setImage(Image);

    }

}
