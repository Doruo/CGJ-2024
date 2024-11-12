package com.burgerflip.game.Sprites.Enemies;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.screens.playscreen.PlayScreen;

public class Gobelin extends Enemy {

    //Il faut changer les valeurs ici selon ce qu'on veut, les 10 c'est juste pour l'exemple
    private float gobelinsHP = 15;
    private int gobelinsDmg = 3;

    private int goblinGold = 20;


    //--------------------------------------------------------------------------------------
    public Gobelin(PlayScreen screen) throws Exception {
        super(screen, false, new Texture("mobs/Goblin/base/gobelin.png"));
    }

    @Override
    public  float getEnemysHP() {
        return gobelinsHP;
    }

    @Override
    protected void setEnemysHP(float hp) {
        gobelinsHP = hp;
    }

    @Override
    public Integer getEnemysDmg() {
        return gobelinsDmg;
    }

    @Override
    public Integer getEnemysRessource() {
        return goblinGold;
    }


}
