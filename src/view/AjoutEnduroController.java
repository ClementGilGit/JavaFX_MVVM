/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Enduro;

/**
 *
 * @author Utilisateur
 */
public class AjoutEnduroController implements Initializable {
    private Enduro enduro;
    
    @FXML
    private Button annuler;
    
    @FXML
    private Button valider;
    
    @FXML
    private TextField tfModele;
    
    @FXML
    private Label labelSound;
    
    @FXML
    private Label labelImage;
    
    @FXML
    private Button browseSound;
    
    @FXML
    private Button browseImage;
    
    @FXML
    private Button searchVideo;
    
    @FXML
    private Label labelVideo;
    
    @FXML
    public void chercherVideo(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner une Video");
        Stage mainStage=null;
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
             File source = new File(selectedFile.getAbsolutePath());
             File dest = new File("ressources/video/"+selectedFile.getName());
             source.renameTo(dest);
             labelVideo.setText(dest.getPath());
        }
    }
    
    @FXML
    public void chercherSound(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner un fichier audio");
        Stage mainStage=null;
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
             File source = new File(selectedFile.getAbsolutePath());
             File dest = new File("ressources/sound/"+selectedFile.getName());
             source.renameTo(dest);
             labelSound.setText(dest.getPath());
        }
    }
    
    @FXML
    public void chercherImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner une Image");
        Stage mainStage=null;
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) { 
             File source = new File(selectedFile.getAbsolutePath());
             File dest = new File("ressources/image/"+selectedFile.getName());
             source.renameTo(dest);
             labelImage.setText(dest.toURI().toString());
        }
    }
    
    @FXML
    public void annulerA(){
        Stage thisone = (Stage) valider.getScene().getWindow();
        thisone.close();
    }
    
    private void showAlert(String message) {
        Alert messageAlerte = new Alert(Alert.AlertType.ERROR);
        messageAlerte.setTitle("Erreur Remplissez les 4 champs");
        messageAlerte.setContentText(message);
        messageAlerte.setHeaderText(null);
        messageAlerte.showAndWait();
    }
    
    @FXML
    public void validerA() throws IOException{
        if(labelImage.getText() == "" || labelSound.getText()== "" || labelVideo.getText()== "" || tfModele.getText() == ""){
            showAlert("Remplir tous le champ et choisir les 3 fichiers avant d'ajouter");
        }else{
            enduro = new Enduro(tfModele.getText(),labelImage.getText(),labelSound.getText(),labelVideo.getText());
            System.out.println(enduro.getMarque()+" "+enduro.getPhoto()+" "+enduro.getSonMoteur()+" "+enduro.getVideo());
            Stage stage = (Stage) tfModele.getScene().getWindow();
            stage.close();
        }
    }
    
    public Enduro getEnduro(){
        return enduro;
    }

    private void creationEcouteur(){
        labelImage.textProperty().addListener((obs,old,newV)->{
           if(newV != null){
               valider.disableProperty().set(false);    
           }
        });
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creationEcouteur();
    }
    
}
