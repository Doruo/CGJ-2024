package com.burgerflip.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.screens.playscreen.PlayScreen;

public class GobelinEther extends Enemy {

    //Il faut changer les valeurs ici selon ce qu'on veut, les 10 c'est juste pour l'exemple
    private float gobelinsHP = 15;
    private int gobelinsDmg = 3;

    private int govlinEther = 20;

    //--------------------------------------------------------------------------------------
    public GobelinEther(PlayScreen screen) throws Exception {
        super(screen, true, new Texture("mobs/Goblin/ethere/goblinEthere.png"));
    }

    @Override
    public float getEnemysHP() {
        return gobelinsHP;
    }

    @Override
    public void setEnemysHP(float hp) {
        gobelinsHP = hp;
    }


    @Override
    public Integer getEnemysDmg() {
        return gobelinsDmg;
    }

    @Override
    public Integer getEnemysRessource() {
        return govlinEther;
    }


}
