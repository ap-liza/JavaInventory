package eappiah177.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WelcomeController implements Initializable {
    @FXML
    private Button signoutButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button viewaddgoodsButton;
    @FXML
    private Button viewvendorsButton;
    @FXML
    private Button viewissuegoodsButton;
    @FXML
    private Button exitButton;
    @FXML
    private StackPane stackPaneView;

    static int a  = 30;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void signoutButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root = loader.load();


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        System.out.println("Sign-out successful");
    }
    public void  exitButtonOnAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.WARNING,"Do you want to exit?", ButtonType.YES,ButtonType.NO);
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        if(ButtonType.YES.equals(result)){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            stackPaneView.getChildren().removeAll();
            stackPaneView.getChildren().setAll(root);

            dashboardButton.setStyle("-fx-background-color:  #4c214b;\n" +
                    "-fx-border:solid;\n" +
                    "-fx-border-width:0px 0px 0px 3px;\n" +
                    "-fx-border-color: #9a1d5f;");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public void viewaddgoodsButtonOnAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("add-goods.fxml"));
        stackPaneView.getChildren().removeAll();
        stackPaneView.getChildren().setAll(root);
        viewaddgoodsButton.setStyle("-fx-background-color:  #4c214b;\n" +
                "-fx-border:solid;\n" +
                "-fx-border-width:0px 0px 0px 3px;\n" +
                "-fx-border-color: #9a1d5f;");
        dashboardButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
        viewvendorsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
        viewissuegoodsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
    }
    public void dashboardButtonOnAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        stackPaneView.getChildren().removeAll();
        stackPaneView.getChildren().setAll(root);

        dashboardButton.setStyle("-fx-background-color:  #4c214b;\n" +
                "-fx-border:solid;\n" +
                "-fx-border-width:0px 0px 0px 3px;\n" +
                "-fx-border-color: #9a1d5f;");
        viewaddgoodsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
        viewvendorsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
        viewissuegoodsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
    }
    public void viewvendorsButtonOnAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("vendors-view.fxml"));
        stackPaneView.getChildren().removeAll();
        stackPaneView.getChildren().setAll(root);

        viewvendorsButton.setStyle("-fx-background-color:  #4c214b;\n" +
                "-fx-border:solid;\n" +
                "-fx-border-width:0px 0px 0px 3px;\n" +
                "-fx-border-color: #9a1d5f;");
        dashboardButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
        viewaddgoodsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
        viewissuegoodsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
    }
    public void viewissuegoodsButtonOnAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("issue-goods.fxml"));
        stackPaneView.getChildren().removeAll();
        stackPaneView.getChildren().setAll(root);

        viewissuegoodsButton.setStyle("-fx-background-color:  #4c214b;\n" +
                "-fx-border:solid;\n" +
                "-fx-border-width:0px 0px 0px 3px;\n" +
                "-fx-border-color: #9a1d5f;");
        viewvendorsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
        dashboardButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
        viewaddgoodsButton.setStyle("-fx-background-color: #140620;\n" +
                "-fx-background-radius:0px 5px 5px 0px;\n" +
                "-fx-border-radius:0px;");
    }
}
