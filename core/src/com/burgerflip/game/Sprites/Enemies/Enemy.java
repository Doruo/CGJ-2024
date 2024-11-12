package com.burgerflip.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.burgerflip.game.Map.Route;
import com.burgerflip.game.Player.Player;
import com.burgerflip.game.screens.playscreen.PlayScreen;
import java.util.List;

public abstract class Enemy extends Sprite {
    protected PlayScreen screen;

    protected boolean isEthere;

    protected Texture texture;

    //2d box
    protected BodyDef bodyDef;
    List<Vector2> waypoints;
    int currentWaypointIndex = 0;
    Vector2 position;
    float speed = 80.0f;
    private int drop;

    public Enemy(PlayScreen screen,  boolean ethere, Texture texture) throws Exception {
        super(texture);
        this.screen = screen;
        this.texture = texture;
        setPosition(screen.getSpawnPoint().x, screen.getSpawnPoint().y);
        isEthere = ethere;
        bodyDef = new BodyDef();
        waypoints = screen.getChemin();
        this.position = new Vector2(screen.getSpawnPoint().x, screen.getSpawnPoint().y);
    }

    public void update(float deltaTime) throws Exception {
        Route routeSousEnnemie = null;
        for(Route route : screen.getRoutes()) {
            if ((route.getPosX() == position.x && position.y <=route.getPosY()+32 && position.y >=route.getPosY()-32) || route.getPosY() == position.y && position.x <=route.getPosX()+32 && position.x <=route.getPosX()-32) {
                routeSousEnnemie = route;
                break;
            }
        }
        if (routeSousEnnemie!=null && routeSousEnnemie.isWalled() && ((routeSousEnnemie.isEther() && this.isEthere) || (!routeSousEnnemie.isEther() && !this.isEthere))) {
            routeSousEnnemie.takeDmg(this.getEnemysDmg());
        } else {
            Vector2 target = waypoints.get(currentWaypointIndex);
            Vector2 direction = new Vector2(target).sub(position).nor();
            position.mulAdd(direction, speed * deltaTime);
            setPosition(position.x, position.y);
            // Check if we reached the waypoint
            if (position.dst(target) <= speed * deltaTime) {
                position.set(target);
                currentWaypointIndex++;
                if (currentWaypointIndex >= waypoints.size()) {
                    currentWaypointIndex = 0;
                }
            }
            if (position.x == screen.getArrivee().x && position.y == screen.getArrivee().y) {
                throw new Exception();
            }
        }
    }

    public void subirDegats(float degats){
        setEnemysHP(getEnemysHP() - degats);
        if(getEnemysHP() <= 0){
            Player player = Player.getInstance(100, 100, 0);
            if (this.isEthere) player.setEther(player.getEther() + this.getEnemysRessource());
             else player.setGold(player.getGold() + this.getEnemysRessource());
            screen.getEnemies().remove(this);
        }
    }

    public boolean isDead(){
        return(getEnemysHP() <= 0);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isEthere() {
        return isEthere;
    }

    public abstract float getEnemysHP();
    protected abstract void setEnemysHP(float hp);
    public abstract Integer getEnemysDmg();
    public abstract Integer getEnemysRessource();
}
