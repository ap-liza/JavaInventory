package eappiah177.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import  javafx.stage.Stage;
import  javafx.event.ActionEvent;

//import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

class Userdata{
    String firstname = null;
    String lastname = null;
    String username = null;
    int id = 0;
}

public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordTextfield;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    public void loginButtonOnAction(ActionEvent event) throws IOException {

        if (usernameTextField.getText().isBlank() == false && enterPasswordTextfield.getText().isBlank() == false) {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB  = connectNow.getConnection();

            String verifyLogin = "SELECT * FROM user_account WHERE username =  '"
                    + usernameTextField.getText() + "'AND password = '"
                    + enterPasswordTextfield.getText() + "'";
            try{
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);
                ArrayList<Userdata> users = this.getData(queryResult);
                int len = users.size();
                if(len==1){
                        loginMessageLabel.setText("Login successful!!!");
                        System.out.println("Login successful");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
                        root = loader.load();
                        WelcomeController welcomeController = loader.getController();


                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                }else {
                    loginMessageLabel.setText("Invalid login, try again.");
                }
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }

        } else if (usernameTextField.getText().isBlank() == true && enterPasswordTextfield.getText().isBlank() == true) {
            loginMessageLabel.setText("Please enter username and password");
        }
        else{
            if (usernameTextField.getText().isBlank() == true){
                loginMessageLabel.setText("Please enter username");
            } else if (enterPasswordTextfield.getText().isBlank() == true) {
                loginMessageLabel.setText("Please enter password");
            }
        }
    }
    public void cancelButtonOnAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.WARNING,"Do you want to exit?",ButtonType.YES,ButtonType.NO);
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        if(ButtonType.YES.equals(result)){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();}
    }

    private ArrayList<Userdata> getData(ResultSet rs) throws Exception{
        ArrayList<Userdata> alldata = new ArrayList<>();
        try {

            Userdata user;
            while(rs.next()){
                    user = new Userdata();
                    user.firstname = rs.getString("firstname");
                    user.lastname = rs.getString("lastname");
                    user.username = rs.getString("username");
                    user.id = rs.getInt("account_id");
                    alldata.add(user);

            }

        }catch (Exception e){
            System.out.println("No no no Error");

        }
        return alldata;
    }



}