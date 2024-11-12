package com.burgerflip.game.Sprites.defenses;

import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.Sprites.Enemies.Enemy;
import com.burgerflip.game.screens.playscreen.PlayScreen;

import java.util.ArrayList;

public class Archer extends Tourelle{




    private final int damage = 2;
    private final int range = 6;
    private final int cost = 10;

    public Archer(PlayScreen screen,float x, float y, float attackSpeed) {
        super(screen,new Texture("defense/tourelle/tourArcher.png"),new Texture("defense/tourelle/tourArcher2.png"),x, y, attackSpeed,"archer");
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
