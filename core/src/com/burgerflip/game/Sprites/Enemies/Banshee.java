package com.burgerflip.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.screens.playscreen.PlayScreen;

public class Banshee extends Enemy {
    private float bansheesHP = 300;
    private int bansheesDmg = 15;

    private int bansheesEther = 500;

    public Banshee(PlayScreen screen) throws Exception {
        super(screen, true, new Texture("mobs/Banshee/ethere/bansheeEthere.png"));
    }

    @Override
    public float getEnemysHP() {
        return bansheesHP;
    }


    @Override
    protected void setEnemysHP(float hp) {
        bansheesHP = hp;
    }

    @Override
    public Integer getEnemysDmg() {
        return bansheesDmg;
    }

    @Override
    public Integer getEnemysRessource(){
        return bansheesEther;
    }

}
