package com.burgerflip.game.Sprites.defenses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.burgerflip.game.Sprites.Enemies.Enemy;
import com.burgerflip.game.screens.playscreen.PlayScreen;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Tourelle extends Sprite {

    protected PlayScreen screen;
    protected Texture texture;
    protected Vector2 position;

    protected float cooldownTime; // Durée du cooldown
    protected float timeSinceLastAttack; // Temps écoulé depuis la dernière attaque

    private TextureRegion spriteOne;
    private TextureRegion spriteTwo;
    private boolean isSpriteOne;
    private int taille;
    private String type;

    public Tourelle(PlayScreen screen, Texture texture1,Texture texture2, float x, float y, float attackSpeed,String type) {
        super(texture1);
        this.screen = screen;
        this.texture = texture1;
        this.position = new Vector2(x, y);
        this.cooldownTime = (float) 1 / attackSpeed;
        this.timeSinceLastAttack = 0;
        setPosition(position.x, position.y);
        this.type = type;
        if(type.equals("archer")||type.equals("archerEthere")){
            taille=128;
        }else {
            taille=64;
        }
        spriteOne = new TextureRegion(texture1, 0, 0, 64, taille);
        spriteTwo = new TextureRegion(texture2, 0, 0, 64, taille);
        isSpriteOne = true;
    }

    public void attack(Enemy target){
        target.subirDegats(getDamage());
    }
    public boolean enemysInRange(){

        if(screen.getEnemies().size() > 0) {
            for (int i = 0; i < screen.getEnemies().size(); i++) {
                if (screen.getEnemies().get(i).getPosition().dst(getPosition()) <= getRange()*64) {

                    return true;
                }
            }
        }
        return false;
    }
    public Enemy getTarget(){
        if(screen.getEnemies().size() > 0) {
            for (int i = 0; i < screen.getEnemies().size(); i++) {
                if (screen.getEnemies().get(i).getPosition().dst(getPosition()) <= getRange()*64) {

                    return screen.getEnemies().get(i);
                }
            }
        }
        return null;
    }

    public void update(float deltaTime) {
        timeSinceLastAttack += deltaTime; // Augmenter le temps écoulé depuis la dernière attaque

        if(enemysInRange()) {
            if (type.equals("archerEthere")) {

                ArrayList<Enemy> listeTarget = getTargetCanonEthere();
                if (!(listeTarget.isEmpty()) && timeSinceLastAttack >= cooldownTime) { // Vérifier le cooldown

                    isSpriteOne = !isSpriteOne;
                    attackArcherEthere(listeTarget);
                    timeSinceLastAttack = 0; // Réinitialiser le temps écoulé depuis la dernière attaque
                }
            }else if (type.equals("canonEthere")) {
                ArrayList<Enemy> listeTarget = getTargetCanonEthere();
                if (!(listeTarget.isEmpty()) && timeSinceLastAttack >= cooldownTime) { // Vérifier le cooldown

                    isSpriteOne = !isSpriteOne;
                    attackCanonEthere(getTargetCanonEthere());
                    timeSinceLastAttack = 0; // Réinitialiser le temps écoulé depuis la dernière attaque
                }
            }else {
                Enemy target = getTarget();
                if (target != null && timeSinceLastAttack >= cooldownTime) { // Vérifier le cooldown

                    isSpriteOne = !isSpriteOne;
                    attack(target);

                    timeSinceLastAttack = 0; // Réinitialiser le temps écoulé depuis la dernière attaque
                }
            }
        }else {
            isSpriteOne = true;
        }

    }
    protected abstract void attackCanonEthere(ArrayList<Enemy> tab);
    protected abstract ArrayList<Enemy> getTargetCanonEthere();



    protected abstract void attackArcherEthere(ArrayList<Enemy> listeTarget);

    protected abstract Enemy[] getMultiTarget();

    @Override
    public void draw(Batch batch) {
        // Draw the appropriate sprite
        if(isSpriteOne) {
            batch.draw(spriteOne, position.x, position.y);
        } else {
            batch.draw(spriteTwo, position.x, position.y);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    abstract public int getDamage();
    abstract public int getRange();
    abstract public int getCost();
}