/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.media.MediaPlayer;
import model.Concession;
import model.Enduro;
import model.Player;
import model.SoundPlayer;
import model.VideoPlayer;

/**
 *
 * @author Utilisateur
 */
public class ConcessionViewModel implements PropertyChangeListener{

    private Concession model = new Concession();
    private final ObservableList<EnduroViewModel> lesObservablesEnduros = FXCollections.observableArrayList();
       public FilteredList<EnduroViewModel> lesEnduroFilter = new FilteredList<>(lesObservablesEnduros,s -> true);
       private final ListProperty<EnduroViewModel> lesEndurosVM = new SimpleListProperty<>(lesEnduroFilter);
    //private final ListProperty<EnduroViewModel> lesEndurosVM = new SimpleListProperty<>(lesObservablesEnduros);
    

    public ObservableList getList() {
        return lesEndurosVM.get();
    }

    public void setList(ObservableList value) {
        lesEndurosVM.set(value);
    }

    public ListProperty listProperty() {
        return lesEndurosVM;
    }
    
    public ConcessionViewModel() {
        model.addPropertyChangeListener(this); 
        model.addEnduro(new Enduro("Beta 250 RR Factory 2014", "/image/beta250.jpg","ressources/sound/yam.mp3","ressources/video/betaok.mp4"));
        model.addEnduro(new Enduro("KTM 300 EXC 2017", "/image/300exc.jpg","ressources/sound/300exc.mp3","ressources/video/300exc.mp4"));
        model.addEnduro(new Enduro("KTM 250 EXC 2015", "/image/250exc.jpg","ressources/sound/ktm250exc.mp3","ressources/video/250exc.mp4"));
        model.addEnduro(new Enduro("Yamaha 125 YZ-E 2014", "/image/125yz.jpg","ressources/sound/125yz.mp3","ressources/video/125yz.mp4"));
        model.addEnduro(new Enduro("Yamaha 250 YZ-E 2014", "/image/250yze.jpg","ressources/sound/250yz.mp3","ressources/video/250yz.mp4"));          
    }
    
    public void load(File fichier) throws IOException, ClassNotFoundException {
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(fichier))) {
            model.removePropertyChangeListener(this);
            model = (Concession) oos.readObject();
            model.addPropertyChangeListener(this);
            model.getLesEnduros().forEach(uneEnduro -> lesObservablesEnduros.add(new EnduroViewModel(uneEnduro)));
        } catch (Exception e) {
            model.addPropertyChangeListener(this);
            throw e;
        }
    }

    public void save(File fichier) throws IOException {
       
        model.save(fichier);
    }
    
    public void creerEnduro(){
        model.addEnduro(new Enduro("KTM","/image/default.jpg","ressources/sound/yam.mp3",""));
    }
    
    public void creerEnduroDonne(Enduro e){
        model.addEnduro(e);
    }
    
    public void supprimerEnduro(int index){
       model.supprimer(index);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Concession.PROP_LESENDUROS)){
            System.out.println("Enduro ajoutée");
            lesObservablesEnduros.add(((IndexedPropertyChangeEvent)evt).getIndex(), new EnduroViewModel((Enduro)evt.getNewValue()));
        }
        if(evt.getPropertyName().equals(Concession.PROP_LESENDUROS+"SUPPRR")){
            lesObservablesEnduros.remove(((IndexedPropertyChangeEvent)evt).getIndex());
        }
    }

    public void stop(int index){
        model.stop(index);
    }
    
    public void jouerSonMoteur(int index, String file) {
        model.jouerSonMoteur(index, file);
    }
    
    public void jouerVideo(int index, String file) {
        model.jouerVideo(index,file);
    }

   public MediaPlayer getPlayer(int selected) {
        return model.getLesEnduros(selected).player.getMediaPlayer();
    }
}
