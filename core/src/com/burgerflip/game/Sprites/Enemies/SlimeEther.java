package com.burgerflip.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.burgerflip.game.screens.playscreen.PlayScreen;

public class SlimeEther extends Enemy {
    private float slimesHP = 15;
    private int slimesDmg = 1;

    private int slimesEther = 5;

    public SlimeEther(PlayScreen screen) throws Exception {
        super(screen, true, new Texture("mobs/slime/ether/slimeEther1.png"));
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
        return slimesEther;
    }
}
