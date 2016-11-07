/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author Utilisateur
 */
public class Enduro implements Serializable{
    
    private String marque;
    private String sonMoteur;   
    private String photo;
    private String video;
    public transient Player player;
    public static final String PROP_VIDEO = "video";
    public static final String PROP_PHOTO = "photo";
    public static final String PROP_MARQUE = "marque";
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Enduro(String marque, String photo, String sonMoteur, String video) {
        setMarque(marque);
        setPhoto(photo);
        setSonMoteur(sonMoteur);
        setVideo(video);
    }
    
    public Enduro(){
        
    }

    /**Gestion de la vidéo**/
    public String getVideo() {return video;}
    public void setVideo(String video) {
        String oldVideo = this.video;
        this.video = video;
        propertyChangeSupport.firePropertyChange(PROP_VIDEO, oldVideo, video);
    }

    /**Gestion de l'audio**/
    public String getSonMoteur() {return sonMoteur;}
    public void setSonMoteur(String sonMoteur) {this.sonMoteur = sonMoteur;}
    
    /**Gestion de l'image**/
    public String getPhoto() {return photo;}
    public void setPhoto(String photo) {
        String oldPhoto = this.photo;
        this.photo = photo;
        propertyChangeSupport.firePropertyChange(PROP_PHOTO, oldPhoto, photo);
    }

    /**Gestion du nom de la moto**/
    public String getMarque() {return marque;}
    public void setMarque(String marque) {
        String oldMarque = this.marque;
        this.marque = marque;
        propertyChangeSupport.firePropertyChange(PROP_MARQUE, oldMarque, marque);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    
}
