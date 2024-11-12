package com.burgerflip.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.screens.playscreen.PlayScreen;

public class Slime extends Enemy {
    private float slimesHP = 5;
    private int slimesDmg = 1;

    private int slimesGold = 5;

    public Slime(PlayScreen screen) throws Exception {
        super(screen, false, new Texture("mobs/slime/base/slime3.png"));
    }


    @Override
    public float getEnemysHP() {
        return slimesHP;
    }

    @Override
    protected void setEnemysHP(float hp) {
        slimesHP = hp;
    }

    @Override
    public Integer getEnemysDmg() {
        return slimesDmg;
    }

    @Override
    public Integer getEnemysRessource() {
        return slimesGold;
    }
}
