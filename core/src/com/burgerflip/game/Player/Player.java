package com.burgerflip.game.Player;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Player {
    private int gold;
    private int vie;
    private int ether;
    private static Player instance;
    private PropertyChangeSupport support;

    private Player(int gold, int vie, int ether) {
        this.gold = gold;
        this.vie = vie;
        this.ether = ether;
        support = new PropertyChangeSupport(this);
    }

    public int getGold() {
        return gold;
    }


    public int getVie() {
        return vie;
    }

    public void setVie(int hps) {
        int ancienneVie = this.vie;
        this.vie = hps;
        support.firePropertyChange("vie", ancienneVie, hps);
    }

    public void setGold(int gold) {
        int oldGold = this.gold;
        this.gold = gold;
        this.support.firePropertyChange("gold", oldGold, gold);
    }

    public void setEther(int ether) {
        int oldEther = this.ether;
        this.ether = ether;
        this.support.firePropertyChange("ether", oldEther, ether);
    }

    public int getEther() {
        return ether;
    }


    public static synchronized Player getInstance(int gold, int vie, int ether){
        if(instance == null){
            instance = new Player(gold, vie, ether);
        }
        return instance;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);

    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
