package com.burgerflip.game.screens.playscreen;

import com.burgerflip.game.Player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.burgerflip.game.Map.Route;
import com.burgerflip.game.Scenes.Hud;
import com.burgerflip.game.Scenes.MursManager;
import com.burgerflip.game.Sprites.Enemies.Enemy;
import com.burgerflip.game.Sprites.Enemies.Wave;
import com.burgerflip.game.Sprites.Enemies.Gobelin;
import com.burgerflip.game.Sprites.defenses.Archer;
import com.burgerflip.game.Sprites.defenses.ArcherEthere;
import com.burgerflip.game.Sprites.defenses.Canon;
import com.burgerflip.game.Sprites.defenses.CanonEthere;
import com.burgerflip.game.Sprites.defenses.Tourelle;
import com.burgerflip.game.TD;
import com.burgerflip.game.screens.gameover.GameOverScreen;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static java.lang.Integer.parseInt;

public class PlayScreen implements Screen {
    private TD game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TmxMapLoader mapLoader;
    private Wave wave;

    public TiledMap getMap() {
        return map;
    }

    // Coucou, c'est moi !

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Hud hud;
    private MursManager mursManager;
    private World world;
    private Box2DDebugRenderer b2dr;
    private ArrayList<Enemy> enemies;
    private ArrayList<Tourelle> tourelles;
    private LinkedList<Vector2> chemin;

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    private ArrayList<Route> routes;

    private Vector2 coordTemp;

    private static boolean jour;

    private float timeSinceLastEnemySpawn = 0f;
    private float enemySpawnInterval = 2f;


    //2d box

    public PlayScreen(TD game) throws Exception {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        enemies = new ArrayList<>();
        tourelles = new ArrayList<>();
        jour = true;
        coordTemp = new Vector2();
        hud = Hud.getInstance(game.getBatch());
        mursManager = MursManager.getInstance(game.getBatch());
        chemin = new LinkedList<>();
        routes = new ArrayList<>();
        wave = Wave.getInstance(this);
        creerRoutes();
    }

    public void update(float dt) throws Exception {
        handleInput(dt);
        camera.update();
        renderer.setView(camera);
        hud.update();
        for (Enemy enemy : enemies) {
            if(enemy.isDead()){
                enemies.remove(enemy);
                if(enemy.isEthere()){
                    Integer nombre = parseInt(new StringBuilder(String.valueOf(hud.getLabelNbEther())).toString());
                    nombre = nombre + enemy.getEnemysRessource();
                    hud.getLabelNbGold().setText(nombre);
                }
            }
        }

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            try {
                enemy.update(dt);
            } catch (Exception e) {
                Player player = Player.getInstance(100, 100, 0);
                player.setVie(player.getVie() - enemy.getEnemysDmg());
                iterator.remove();
            }
        }
        for (Tourelle tourelle : tourelles) {
            tourelle.update(dt);
        }
        hud.stage.draw();
        MursManager.update();
        if(Player.getInstance(100, 100, 0) .getVie() <= 0) {
            game.setScreen(new GameOverScreen(game));
        }
    }

    private void handleInput(float dt) throws Exception {
        Player player = Player.getInstance(hud.getGoldNumber(), hud.getVie(), hud.getEtherNumber());
        if(Gdx.input.isKeyJustPressed(62)){
            enemies.add(new Gobelin(this));
        }
        if(Gdx.input.isButtonJustPressed(0)){
            if(tourelleClicked()){
                boolean tourellePresente = false;
                String type = "";
                int index = 0;
                for (Tourelle tourelle : tourelles) {
                    if(tourelle.getPosition().x == coordTemp.x && tourelle.getPosition().y == coordTemp.y){
                        tourellePresente = true;
                        if (tourelle instanceof Canon){
                            type = "canon";
                            index = tourelles.indexOf(tourelle);
                        }
                        break;
                    }
                }
                if(!tourellePresente && player.getGold()>15){
                    tourelles.add(new Canon(this, coordTemp.x, coordTemp.y, 1));
                    player.setGold(player.getGold() - 15);
                }
                if (tourellePresente && type.equals("canon") && player.getEther()>50){
                    tourelles.remove(index);
                    tourelles.add(new CanonEthere(this, coordTemp.x, coordTemp.y, 0.5f));
                    player.setEther(player.getEther() - 50);
                }
            }
        }
        if(Gdx.input.isButtonJustPressed(1)){
            if(tourelleClicked()){
                boolean tourellePresente = false;
                String type = "";
                int index = 0;
                for (Tourelle tourelle : tourelles) {
                    if(tourelle.getPosition().x == coordTemp.x && tourelle.getPosition().y == coordTemp.y){
                        tourellePresente = true;
                        if (tourelle instanceof Archer){
                            type = "archer";
                            index = tourelles.indexOf(tourelle);
                        }

                        break;

                    }
                }
                if (!tourellePresente && player.getGold()>10 ){
                    tourelles.add(new Archer(this, coordTemp.x, coordTemp.y, 3));
                    player.setGold(player.getGold() - 10);
                }
                if (tourellePresente && type.equals("archer") && player.getEther()>65){
                    tourelles.remove(index);
                    tourelles.add(new ArcherEthere(this, coordTemp.x, coordTemp.y, 4));
                    player.setEther(player.getEther() - 65);
                }
            }
        }
        hud = Hud.getInstance(game.getBatch());
        mursManager = MursManager.getInstance(game.getBatch());
    }

    @Override
    public void show() {

    }

    public void switchDayNight() {
        jour = !jour;
        getHud().switchDayNight();

        if (!jour) {
            try {
                final List<Enemy> nextWave = wave.generateNextWave();
                final int totalEnemies = nextWave.size();
                if (nextWave == null) {
                    System.out.println("Victoire");
                } else {
                    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    int delay = 0;

                    for (final Enemy enemy : nextWave) {
                        executorService.schedule(new Runnable() {
                            public void run() {
                                enemies.add(enemy);
                                // Vérifier si tous les ennemis sont apparus
                                if (enemies.size() >= totalEnemies) {
                                    // Tous les ennemis sont apparus, planifier une tâche pour votre méthode
                                    executorService.schedule(new Runnable() {
                                        public void run() {
                                            boolean boucle = true;
                                            while (!enemies.isEmpty()) {
                                                System.out.println("aa");
                                            }
                                            switchDayNight();
                                        }
                                    }, 0, TimeUnit.SECONDS);
                                }
                            }
                        }, delay, TimeUnit.SECONDS);
                        delay++;
                    }
                }
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public boolean estJour() {
        return jour;
    }


    @Override
    public void render(float v) {
        timeSinceLastEnemySpawn += v;
        try {
            update(v);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        renderer.render();
        game.getBatch().begin();
            for (Enemy enemy : enemies) {
                    enemy.draw(game.getBatch());
            }

        for (Tourelle tourelle : tourelles) {
                tourelle.draw(game.getBatch());
            }
            game.getBatch().end();
            hud.stage.draw();
            MursManager.stage.draw();
    }

    public Vector2 getSpawnPoint() throws Exception {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Spawn");
        return getPoint(layer);
    }

    public Vector2 getArrivee() throws Exception {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Arrivee");
        return getPoint(layer);
    }

    public Vector2 getPoint(TiledMapTileLayer layer) throws Exception{
        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) return new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight());
            }
        }
        throw new Exception("Aucun points définie pour la couche: " + layer.getName());
    }

    public LinkedList<Vector2> getChemin() throws Exception {
        chemin = new LinkedList<>();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Chemin");
        boolean directionIsHorizontal = true;
        Vector2 previousPoint = getSpawnPoint();
        chemin.add(previousPoint);

        while(previousPoint != null && !previousPoint.equals(getArrivee())){ // tant que le prochain point n'est pas l'arrivée ou qu'il n'est pas nul
            Vector2 nextPoint = getPointLePlusProche(layer, directionIsHorizontal); // on récupère le prochain point
            chemin.add(nextPoint); // on l'ajoute au chemin
            previousPoint = nextPoint; // on le définit comme le point précédent
            directionIsHorizontal = !directionIsHorizontal; // on change de direction (horizontal -> vertical ou vertical -> horizontal)
        }
        return chemin;
    }

    private ArrayList<Vector2> getPoints(TiledMapTileLayer layer){
        ArrayList<Vector2> points = new ArrayList<>();
        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null && !chemin.contains(new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight()))) points.add(new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight()));
            }
        }
        return points;
    }

    private void creerRoutes() {
        TiledMapTileLayer layer = (TiledMapTileLayer) getMap().getLayers().get("Route");
        ArrayList<Vector2> liste = getPoints(layer);
        for (Vector2 v2 : liste) {
            routes.add(new Route(v2.x, v2.y, this));
        }
    }

    private Vector2 getPointLePlusProche(TiledMapTileLayer layer, boolean direction) {
        Vector2 pointLePlusProche = null;
        float distanceMin = Float.MAX_VALUE;
        for (Vector2 point : getPoints(layer)) {
            if (direction) {
                if (point.y == chemin.getLast().y && point.x != chemin.getLast().x) {
                    float distance = chemin.getLast().dst(point);
                    if (distance < distanceMin) {
                        distanceMin = distance;
                        pointLePlusProche = point;
                    }
                }
            } else {
                if (point.x == chemin.getLast().x && point.y != chemin.getLast().y) {
                    float distance = chemin.getLast().dst(point);
                    if (distance < distanceMin) {
                        distanceMin = distance;
                        pointLePlusProche = point;
                    }
                }
            }
        }
        return pointLePlusProche;
    }

    public boolean tourelleClicked() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Tourelles");

        int mouseX = Gdx.input.getX();
        int mouseY = (1079-Gdx.input.getY());

        ArrayList<Vector2> points = getPoints(layer);

        for (Vector2 point : points) {
            if (mouseX >= point.x && mouseX <= point.x + layer.getTileWidth() && mouseY >= point.y && mouseY <= point.y + layer.getTileHeight()) {
                coordTemp.x = point.x;
                coordTemp.y = point.y;


                return true;
            }
        }
        return false;

    }
    public static boolean getJour(){
         return jour;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Hud getHud() {
        return hud;
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public MursManager getMursManager() { return mursManager; }
}
