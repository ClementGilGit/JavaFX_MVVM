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
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import launcher.Main;
import model.Enduro;
import viewmodel.ConcessionViewModel;
import viewmodel.EnduroViewModel;

/**
 *
 * @author Utilisateur
 */
public class MainWindowController implements Initializable{
    private ConcessionViewModel viewmodel;
    private int b =0;
    int selected =0;
    
    @FXML
    private ListView<EnduroViewModel> list;
    
    @FXML
    private Button button;
    
    @FXML
    private Button buttondelet;
    
    @FXML
    private Button buttonVideo;
    
    @FXML
    private TextField textFieldMarque;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private TextField search;
    
    @FXML
    private Button soundButton;
    
    @FXML
    private MenuItem itemSupprimer;
    
    @FXML
    private Button buttonBrowser;
    
    @FXML
    private MediaView mediaV;
    
    @FXML
    private MenuItem load;
    
    @FXML
    private MenuItem save;
    
    @FXML
    private ImageView imageIntro;
    
     private final FileChooser.ExtensionFilter exf = new FileChooser.ExtensionFilter("Fichier enduro", "*.enduro");
    
    @FXML
    public void browser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage mainStage=null;
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
             File source = new File(selectedFile.getAbsolutePath());
             File dest = new File("ressources/image/"+selectedFile.getName());
             source.renameTo(dest);
             list.getSelectionModel().getSelectedItem().setPhoto(dest.toURI().toString());
             imageView.setImage(new Image(list.getSelectionModel().getSelectedItem().getPhoto()));
             list.refresh();
        }
    }
    
    @FXML
    public void supprimer(){
       int index = list.getSelectionModel().getSelectedIndex();
       viewmodel.supprimerEnduro(index);
    }
    
    @FXML
    public void jouerSonMoteur(){
        if(b==1){
            viewmodel.stop(selected);
            b=0;
        }
        selected = list.getSelectionModel().getSelectedIndex();
        String file = list.getSelectionModel().selectedItemProperty().get().getSonMoteurVM();
        viewmodel.jouerSonMoteur(selected,file);
        b=1;

    }
    
    @FXML
    public void playVideo(){
        if(b==1){
            viewmodel.stop(selected);
            b=0;
        }
        selected = list.getSelectionModel().getSelectedIndex();
        String file = list.getSelectionModel().selectedItemProperty().get().getVideo();
        viewmodel.jouerVideo(selected, file);
        imageIntro.setVisible(false);
        mediaV.setVisible(true);
        viewmodel.getPlayer(selected);
        mediaV.setMediaPlayer(viewmodel.getPlayer(selected));
        b=1;
       
    }
    
    @FXML
    public void ajouterEnduro() throws IOException{
        //viewmodel.creerEnduro();
        Stage thisStage = (Stage) list.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjoutEnduro.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AjoutEnduroController ajoutf = loader.getController();
        Stage g = new Stage();
        g.initOwner(thisStage);
        g.initModality(Modality.WINDOW_MODAL);
        g.getIcons().add(new Image("/image/icon.png"));
        g.setTitle("Ajouter un Enduro");
        g.setScene(scene);
        g.showAndWait();
        Enduro eadd = ajoutf.getEnduro();
        if (eadd != null)
        {
            viewmodel.creerEnduroDonne(eadd);
        }
    }
    
    @FXML
    void clicLoad() throws IOException, ClassNotFoundException { 
        FileChooser fenetreChoixFichier = new FileChooser();
        fenetreChoixFichier.setTitle("Charger ue sauvegarde...");     
        fenetreChoixFichier.getExtensionFilters().add(exf);
        fenetreChoixFichier.setSelectedExtensionFilter(exf);
        File fichier = fenetreChoixFichier.showOpenDialog(list.getScene().getWindow());
        if (fichier != null) {
           load(fichier);
        }
        list.refresh();
    }
    
    private void load(File fichier) {
        try {
           list.itemsProperty().unbind();
            if (list.getItems() != null) {
                list.getItems().clear();
            }
            viewmodel.load(fichier);
            list.itemsProperty().bind(viewmodel.listProperty());
            list.getSelectionModel().select(0);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void clicSave() throws IOException {
        FileChooser fenetreChoixFichier = new FileChooser();
        fenetreChoixFichier.setTitle("Enregistrer la sauvegarde sous...");
        fenetreChoixFichier.getExtensionFilters().add(exf);
        fenetreChoixFichier.setSelectedExtensionFilter(exf);
        File fichier = fenetreChoixFichier.showSaveDialog(list.getScene().getWindow());
        if (fichier != null) {
            viewmodel.save(fichier);
        }
    }

    
    private void creationCellFactoryPourMorceaux() {
        list.setCellFactory(param -> new ListCell<EnduroViewModel>(){
            
            @Override
            protected void updateItem(EnduroViewModel item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    textProperty().bind(item.marqueProperty());
                    ImageView imv = new ImageView(item.getPhoto());
                    // 110 70
                    imv.setFitWidth(150);
                    imv.setFitHeight(84);
                    setGraphic(imv);
                } else {
                    textProperty().unbind();
                    setText("");
                    setGraphic(null);                    
                }
            }
            
        }
        );
    }
    
    private void creationEcouteurChangementSelection(){
        list.getSelectionModel().selectedItemProperty().addListener((obs,old,newV)->{
           if(old != null){
               textFieldMarque.textProperty().unbindBidirectional(old.marqueProperty());
               if(b==1){
                        //mp.stop();
                        int index = list.getSelectionModel().getSelectedIndex();
                        viewmodel.stop(selected);
                        b=0;
                    }
           }
           if(newV != null){
               textFieldMarque.textProperty().bindBidirectional(newV.marqueProperty());
               imageView.setImage(new Image(newV.getPhoto()));
               buttonVideo.disableProperty().set(false);
               soundButton.disableProperty().set(false);
               buttondelet.disableProperty().set(false);
               buttonBrowser.disableProperty().set(false);
               textFieldMarque.disableProperty().set(false);
               mediaV.setVisible(false);
               imageIntro.setVisible(true);
               imageIntro.setImage(new Image("/image/intro.png"));
              
           }
           if(newV == null){
               textFieldMarque.setText("");
               imageView.setImage(null);
               disableButtons();
           }
        });
    }
    
    /** Filtre la liste pour afficher uniquement les éléments commençant par la chaine saisie
     * 
     */
    public void creationEcouteurRecherche(){
	search.textProperty().addListener((obs,old,newV)-> {
		FilteredList<EnduroViewModel> f = viewmodel.lesEnduroFilter;
		if(newV == null || newV.length() == 0)
		{
			f.setPredicate(s-> true);
		}else{
			f.setPredicate(p -> p.getMarque().startsWith(newV));
		}
	});
    }
 
    /**
     * Désactive les buttons lorsqu'aucun élement est sélectionné au démarrage
     */
    public void disableButtons(){
        buttonVideo.disableProperty().set(true);
        soundButton.disableProperty().set(true);
        buttondelet.disableProperty().set(true);
        buttonBrowser.disableProperty().set(true);
        textFieldMarque.disableProperty().set(true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewmodel = new ConcessionViewModel();
        list.itemsProperty().bindBidirectional(viewmodel.listProperty());
        creationCellFactoryPourMorceaux();
        creationEcouteurChangementSelection();
        creationEcouteurRecherche();
        disableButtons();
        mediaV.setVisible(false);
        imageIntro.setVisible(true);
        imageIntro.setImage(new Image("/image/intro.png"));
    }
}
