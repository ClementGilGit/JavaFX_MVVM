/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.Serializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Cette classe s'occupe de la gestion de l'audio via le pattern strategie
 */
public class SoundPlayer extends Player implements Serializable{
    public SoundPlayer(String chemin) {
        Media media = new Media(new File(chemin).toURI().toString());
        mediaplayer = new MediaPlayer(media);
    }

    public void play(){
        mediaplayer.play();
    }
    
    public void stop(){
        mediaplayer.stop();
    }
    
}
