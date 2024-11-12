package com.burgerflip.game.Sprites.defenses;

import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.Sprites.Enemies.Enemy;
import com.burgerflip.game.screens.playscreen.PlayScreen;

import java.util.ArrayList;

public class ArcherEthere extends Tourelle{

        private final int damage = 5;
        private final int range = 6;
        private final int cost = 100;

        public ArcherEthere(PlayScreen screen,float x, float y, float attackSpeed) {
            super(screen,new Texture("defense/tourelle/tourArcherEthere.png"),new Texture("defense/tourelle/tourArcherEthere2.png"),x, y, attackSpeed,"archerEthere");
        }



    @Override
    protected void attackCanonEthere(ArrayList<Enemy> tab) {
    }

    @Override
    protected ArrayList<Enemy> getTargetCanonEthere() {
        ArrayList<Enemy> liste = new ArrayList<Enemy>();
        if(screen.getEnemies().size() > 0) {
            for (int i = 0; i < screen.getEnemies().size(); i++) {
                if (screen.getEnemies().get(i).getPosition().dst(getPosition()) <= getRange()*64) {
                    liste.add(screen.getEnemies().get(i));
                }
            }
        }
        return liste;
    }

    @Override
    protected void attackArcherEthere(ArrayList<Enemy> listeTarget) {
        System.out.println("attackArcherEthere");
        listeTarget.get(0).subirDegats(getDamage());
        if (listeTarget.size()>=2){
            listeTarget.get(1).subirDegats(getDamage()*0.8f);
            if(listeTarget.size()>=3){
                listeTarget.get(2).subirDegats(getDamage()*0.6f);
                if(listeTarget.size()>=4){
                    listeTarget.get(3).subirDegats(getDamage()*0.4f);
                    if(listeTarget.size()>=5){
                        listeTarget.get(4).subirDegats(getDamage()*0.2f);
                    }
                }
            }
        }
    }

    public Enemy[] getMultiTarget(){
            Enemy[] tab = new Enemy[5];
            if(screen.getEnemies().size() > 0) {
                for (int i = 0; i < screen.getEnemies().size(); i++) {
                    if (screen.getEnemies().get(i).getPosition().dst(getPosition()) <= getRange()*64) {

                        if(i<5){
                            tab[i] = screen.getEnemies().get(i);
                        }
                    }
                }
            }
            for (int i = 0; i < tab.length; i++) {
                if(tab[i] == null){
                    tab[i] = null;
                }
            }
            return null;
        }
        @Override
        public int getDamage() {
            return damage;
        }

        @Override
        public int getRange() {
            return range;
        }

        @Override
        public int getCost() {
            return cost;
        }
}
