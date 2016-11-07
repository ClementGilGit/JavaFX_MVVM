/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Enduro;

/**
 *
 * @author Utilisateur
 */
public class EnduroViewModel implements PropertyChangeListener {

    private Enduro model ;
    private final StringProperty marque = new SimpleStringProperty();
    private final StringProperty video = new SimpleStringProperty();
    private final StringProperty photo = new SimpleStringProperty();
    private final StringProperty sonMoteurVM = new SimpleStringProperty();

    public EnduroViewModel(Enduro enduro) {
        model = enduro;
        setMarque(enduro.getMarque());
        setPhoto(enduro.getPhoto());
        setSonMoteurVM(enduro.getSonMoteur());
        setVideo(enduro.getVideo());
        model.addPropertyChangeListener(this);
    
        marque.addListener((obs,old,newV)->{model.setMarque(newV);});
        photo.addListener((obs,old,newV)->model.setPhoto(newV));
        sonMoteurVM.addListener((obs,old,newV)->model.setSonMoteur(newV));
        video.addListener((obs,old,newV)->model.setVideo(newV));
    }

    public String getVideo() {return video.get();}
    public void setVideo(String value) {video.set(value); }
    public StringProperty videoProperty() {return video;}
    
    public String getSonMoteurVM() {return sonMoteurVM.get();}
    public void setSonMoteurVM(String value) {sonMoteurVM.set(value);}
    public StringProperty sonMoteurVMProperty() {return sonMoteurVM;}   
    
    public String getPhoto() {return photo.get();}
    public void setPhoto(String value) {photo.set(value);}
    public StringProperty photoProperty() {return photo;}
        
    public String getMarque() {return marque.get();}
    public void setMarque(String value) {marque.set(value);}
    public StringProperty marqueProperty() {return marque;}
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("j'ai changé");
    }
    
    
}
