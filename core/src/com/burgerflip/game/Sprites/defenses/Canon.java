package com.burgerflip.game.Sprites.defenses;

import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.Sprites.Enemies.Enemy;
import com.burgerflip.game.screens.playscreen.PlayScreen;
import com.burgerflip.game.Sprites.defenses.Tourelle;

import java.util.ArrayList;

public class Canon extends Tourelle {

    private final int damage = 5;
    private final int range = 4;

    private final int cost = 10;

    public Canon(PlayScreen screen,float x, float y, float attackSpeed) {

         super(screen, new Texture("defense/tourelle/canon.png"),new Texture("defense/tourelle/canon2.png"), x, y,attackSpeed,"canon" );
    }


    @Override
    protected void attackCanonEthere(ArrayList<Enemy> tab) {

    }

    @Override
    protected ArrayList<Enemy> getTargetCanonEthere() {
        return null;
    }

    @Override
    protected void attackArcherEthere(ArrayList<Enemy> listeTarget) {

    }



    @Override
    protected Enemy[] getMultiTarget() {
        return new Enemy[0];
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getCost() {
        return cost;
    }
}
