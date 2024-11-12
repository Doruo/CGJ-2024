package com.burgerflip.game.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.burgerflip.game.Scenes.MursManager;
import com.burgerflip.game.screens.playscreen.PlayScreen;


public class Route {
    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    private final float posX;
    private final float posY;

    public boolean isWalled() {
        return IsWalled;
    }

    private boolean IsWalled;
    private int wallHP;
    private PlayScreen screen;

    public boolean isEther() {
        return IsEther;
    }

    private boolean IsEther;

    public Route(float posX, float posY, PlayScreen screen) {
        this.posX = posX;
        this.posY = posY;
        IsWalled = false;
        IsEther = false;
        wallHP = 0;
        this.screen = screen;
        creerBindings();
    }

    public void placeAWall(String cheminImage, boolean isEthere) {
        IsWalled = true;
        wallHP = 1000;
        Texture texture = new Texture(Gdx.files.internal(cheminImage));
        Image image = new Image(texture);
        image.setPosition(posX, posY);
        MursManager.stage.addActor(image);
        screen.getHud().buyWall(isEthere);
    }

    public void takeDmg(int dmg) {
        if (IsWalled) {
            wallHP -= dmg;
            if (wallHP <= 0) {
                destroyeTheWall();
            }
        }
    }

    public void destroyeTheWall() {
        Array<Actor> actors = screen.getMursManager().stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof Image && actor.getX() == posX && actor.getY() == posY) {
                actor.remove();
                IsWalled = false;
            }
        }
    }

    public void creerBindings() {
        screen.getMursManager().stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TiledMapTileLayer layer = (TiledMapTileLayer) screen.getMap().getLayers().get("Chemin");
                float height = layer.getTileHeight();
                float width = layer.getTileWidth();
                if (x>= posX - width/1.75 && x<= posX + width/1.75 && y>= posY - height/1.75 && y<= posY + height/1.75) {
                    //On peut modifier le coût d'un mur ici
                    if(!IsWalled && screen.getHud().getGoldNumber()>=10 && screen.getMursManager().isBaseWallActive()) {
                        placeAWall("defense/murs/murX.png", false);
                        IsEther = false;
                        screen.getHud().buyANormalWall();
                    } else if(screen.getMursManager().isEtherWallActive() && screen.getHud().getEtherNumber()>=10 && !IsWalled) {
                        placeAWall("defense/murs/murEthereeX.png", true);
                        IsEther = true;
                        screen.getHud().buyAnEtherWall();
                    }
                }
            }
        });
    }
}
