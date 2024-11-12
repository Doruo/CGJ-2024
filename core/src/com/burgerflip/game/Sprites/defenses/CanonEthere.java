package com.burgerflip.game.Sprites.defenses;

import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.Sprites.Enemies.Enemy;
import com.burgerflip.game.screens.playscreen.PlayScreen;

import java.util.ArrayList;

public class CanonEthere extends Tourelle{

    private final int damage = 25;
    private final int range = 6;
    private final int cost = 100;

    public CanonEthere(PlayScreen screen,float x, float y, float attackSpeed) {
        super(screen,new Texture("defense/tourelle/canonEthere.png"),new Texture("defense/tourelle/canonEthere2.png"),x, y, attackSpeed,"canonEthere");
    }

    public  void attack(ArrayList<Enemy> listeTarget){
    }

    @Override
    protected void attackCanonEthere(ArrayList<Enemy> listeTarget) {
        System.out.println("attackCanonEthere");
        for(Enemy e : listeTarget){
            e.subirDegats(getDamage());
        }
    }

    public ArrayList<Enemy> getTargetCanonEthere(){
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

    }



    @Override
    protected Enemy[] getMultiTarget() {
        return new Enemy[0];
    }

    @Override
    public  int getDamage() {
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
