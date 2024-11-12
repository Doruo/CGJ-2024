package com.burgerflip.game.Sprites.Enemies;

import com.burgerflip.game.screens.playscreen.PlayScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wave {
    private int waveNumber;
    private Random random;

    private boolean isEtherWave;

    private PlayScreen screen;
    private static Wave instance;

    public Wave(PlayScreen screen){
        waveNumber = 0;
        this.screen = screen;
        isEtherWave = false;
        random = new Random();
    }

    public List<Enemy> generateNextWave() throws Exception {

        determineIfEtherWave();
        List<Enemy> enemies = new ArrayList<>();
        waveNumber++;
        int enemyCount = waveNumber * 3;
        if(waveNumber==16) {
            System.out.println("Win !");
            enemies = null;
        } else {
            determineIfEtherWave();

            for (int i = 0; i < enemyCount; i++) {
                Enemy enemy = isEtherWave ? generateEtherEnemy() : generateRandomEnemy();
                enemies.add(enemy);
            }

            if (waveNumber == 5) {
                enemies.add(new Banshee(screen));
            }
        }

        return enemies;
    }

    private void determineIfEtherWave(){
        isEtherWave = !screen.estJour();
    }

    private Enemy generateRandomEnemy() throws Exception {
        int type;
        type =  random.nextInt(2);
        return type == 0 ? new Slime(screen) : new Gobelin(screen);
    }

    private Enemy generateEtherEnemy() throws Exception {
        int type = random.nextInt(4);
        switch (type) {
            case 0: return new Slime(screen);
            case 1: return new Gobelin(screen);
            case 2: return new GobelinEther(screen);
            default: return new SlimeEther(screen);
        }
    }

    public static synchronized Wave getInstance(PlayScreen screen){
        if(instance == null){
            instance = new Wave(screen);
        }
        return instance;
    }

}
