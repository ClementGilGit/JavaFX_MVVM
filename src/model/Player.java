/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javafx.application.Application;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Utilisateur
 */
public abstract class Player implements Serializable{
    MediaPlayer mediaplayer = null;
    
    public void play(){
   
    }
    
    public void stop()
    {
       
    }
    
    
    public MediaPlayer getMediaPlayer(){
        return mediaplayer;
    }

}
