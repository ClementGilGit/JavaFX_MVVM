/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import viewmodel.EnduroViewModel;

/**
 *
 * @author Utilisateur
 */
public class Concession implements Serializable{

    private List<Enduro> lesEnduros = new ArrayList<>();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String PROP_LESENDUROS = "lesEnduros";

     public Concession() {
        
     }
    
     /**Fonction de sauvegarde de la concession**/
    public void save(File fichier) throws IOException{
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier))) {
            oos.writeObject(this);
            this.getLesEnduros().forEach(enduro->System.out.println(enduro));
        }
    } 
    
 
    
    public List<Enduro>getLesEnduros() {
        return lesEnduros;
    }

    public void setLesEnduros(List<Enduro> lesEnduros) {
        List<Enduro> oldLesEnduros = this.lesEnduros;
        this.lesEnduros = lesEnduros;
        propertyChangeSupport.firePropertyChange(PROP_LESENDUROS, oldLesEnduros, lesEnduros);
    }

    public Enduro getLesEnduros(int index) {
        return this.lesEnduros.get(index);
    }

    public void setLesEnduros(int index, Enduro lesEnduros) {
        Enduro oldLesEnduros = this.lesEnduros.get(index);
        this.lesEnduros.set(index, lesEnduros);
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LESENDUROS, index, oldLesEnduros, lesEnduros);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    /**Supprime l'enduro sélectionne dans la listview**/
    public void supprimer(int index){
        Enduro oldLesEnduros =getLesEnduros(index);
        lesEnduros.remove(index);
        System.out.println("model supprimé");
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LESENDUROS+"SUPPRR", index, oldLesEnduros, null);
    }
    
    /**Ajoute un enduro **/
    public void addEnduro(Enduro e){
        lesEnduros.add(e);
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LESENDUROS, lesEnduros.size()-1, null, e);
    }
    
    /**Creer un SoundPlayer grâce au pattern Strategie**/
     public void jouerSonMoteur(int index, String file) {
        getLesEnduros(index).player = new SoundPlayer(file);
        getLesEnduros(index).player.play();
     }
     
     /**Stop le mediaplayer**/
     public void stop(int index){
         getLesEnduros(index).player.stop();
     }

     /**Creer un VideoPlayer grâce au pattern Strategie**/
    public void jouerVideo(int index, String file) {
        getLesEnduros(index).player = new VideoPlayer(file);
        getLesEnduros(index).player.play();
    }
    

}
