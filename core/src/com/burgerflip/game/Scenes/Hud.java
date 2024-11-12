package com.burgerflip.game.Scenes;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.burgerflip.game.Player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.burgerflip.game.TD;
import com.burgerflip.game.screens.playscreen.PlayScreen;

import javax.swing.text.html.ImageView;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Hud {
    public Stage stage;
    private Viewport viewport;

    public String getDayTime() {
        return dayTime;
    }

    private String dayTime;

    public Integer getEtherNumber() {
        return etherNumber;
    }

    public Integer getVie() {
        return vie;
    }

    public void acheterTourEther() {
        etherNumber -= 30;
    }

    public void acheterCanonEther() {
        etherNumber -= 20;
    }

    public void buyAnEtherWall() {
        etherNumber -= 10;
    }

    private Integer etherNumber;
    private Integer vie;

    public Integer getGoldNumber() {
        return goldNumber;
    }

    public void acheterTour() {
        goldNumber -= 10;
    }

    public void acheterCanon() {
        goldNumber -= 15;
    }

    public void buyANormalWall() {
        goldNumber -= 10;
    }

    private Integer goldNumber;
    private static Hud instance;

    Texture menuTexture;

    Texture soleilTexture;
    Texture luneTexture;

    Label timeLabel; // Temps
    Label etherLabel; // Ether
    Label hPLabel; // HP
    Label goldLabel; // GOLD
    Texture goldTexture;
    Texture etherTexture;
    Label labelDayOrNight; // JOUR/Nuit
    Label labelNbEther;
    Label labelNbHP;
    Label labelNbGold;
    Label labelLancement;

    Image imageDayNight;

    private ImageButton buttonMenu;

    private Sound sound;
    private float volume;
    private Player player;

    private Hud(final SpriteBatch sb) {
        player = Player.getInstance(20, 15, 0);
        dayTime = PlayScreen.getJour() ? "JOUR" : "NUIT";
        etherNumber = player.getEther();
        vie = player.getVie();
        goldNumber = player.getGold();

        viewport = new FitViewport(TD.V_WIDTH, TD.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        /* TABLE */

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        /* VOLUME */
        volume = 0;

        /* TEXTURES */

        menuTexture = new Texture(Gdx.files.internal("menu/gear.png"));

        goldTexture = new Texture("Ressource/gold.png");
        etherTexture = new Texture("Ressource/ether.png");
        soleilTexture = new Texture("decor/Soleil/soleil.png");
        luneTexture = new Texture("decor/Lune/lunedether.png");

        /* IMAGES */
        imageDayNight = new Image(soleilTexture);

        /* BUTTON */

        buttonMenu = new ImageButton(new TextureRegionDrawable(new TextureRegion (menuTexture))); //Set the button u

        /* LABEL */

        LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);

        timeLabel = new Label("TIME", labelStyle);
        goldLabel = new Label("GOLD", labelStyle);
        etherLabel = new Label("ETHER", labelStyle);
        hPLabel = new Label("PV", labelStyle);
        labelDayOrNight = new Label(String.format(dayTime), labelStyle);
        labelNbGold = new Label(String.format(goldNumber.toString()), labelStyle);
        labelNbEther = new Label(String.format(etherNumber.toString()), labelStyle);
        labelNbHP = new Label(String.format(vie.toString()), labelStyle);

        /* AJOUT ELEM TABLE */

        Image imageGold = new Image(goldTexture);
        Image imageEther = new Image(etherTexture);
        stage.addActor(imageEther);
        stage.addActor(imageGold);

        table.add(timeLabel).expandX().padTop(10);
        table.add(imageGold).expandX().padTop(10);
        table.add(imageEther).expandX().padTop(10);
        table.add(hPLabel).expandX().padTop(10);
        table.add(buttonMenu).expandX().padTop(10);
        table.row();
        table.add(imageDayNight).expandX();
        table.add(labelNbGold).expandX();
        table.add(labelNbEther).expandX();
        table.add(labelNbHP).expandX();

        Table tableLancement = new Table();
        tableLancement.center().bottom();
        tableLancement.setFillParent(true);

        labelLancement = new Label("Appuyez sur ENTRÉE lorsque vos défenses sont prêtes ! ", labelStyle);
        labelLancement.setFontScale(3f);

        tableLancement.add(labelLancement).fill();

        stage.addActor(table);
        stage.addActor(tableLancement);

        // Musique d'intro
        jouerMusic(0);

        player.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                if (event.getPropertyName().equals("vie")) {
                    Integer newHp = (Integer) event.getNewValue();
                    labelNbHP.setText(newHp.toString());
                } else if (event.getPropertyName().equals("gold")) {
                    Integer newGold = (Integer) event.getNewValue();
                    labelNbGold.setText(newGold.toString());
                } else if (event.getPropertyName().equals("ether")) {
                    Integer newEther = (Integer) event.getNewValue();
                    labelNbEther.setText(newEther.toString());
                }
            }
        });
    }

    public synchronized static Hud getInstance(SpriteBatch sb) {
        if (instance == null) {
            instance = new Hud(sb);
        }
        return instance;
    }


    public Label getLabelNbGold(){
        return labelNbGold;
    }

    public Label getLabelNbEther(){
        return labelNbEther;
    }

    public void switchDayNight() {
        sound.stop();
        if (dayTime.equals("JOUR")) {
            dayTime = "NUIT";
            labelLancement.setVisible(false);

            imageDayNight.setDrawable(new TextureRegionDrawable(new TextureRegion(luneTexture)));
            jouerMusic(5);
        } else {
            dayTime = "JOUR";
            labelLancement.setVisible(true);
            imageDayNight.setDrawable(new TextureRegionDrawable(new TextureRegion(soleilTexture)));

            jouerMusic(1);
        }
    }

    public void update() {
        labelDayOrNight.setText(dayTime);
        labelNbGold.setText(goldNumber.toString());
        labelNbEther.setText(etherNumber.toString());
        etherNumber = player.getEther();
        vie = player.getVie();
        goldNumber = player.getGold();
    }

    public void buyWall(boolean isEthere) {
        if(isEthere) {
            etherNumber -= 10;
        } else {
            goldNumber -= 10;
        }
    }

    /* - MUSIQUES -
     *
     * INTRO - 0
     * DAY 1 - 1
     * DAY 2 - 2
     * TIME SWITCH - 3
     * NIGHT 1 - 4
     * NIGHT 2 - 5
     *
     * */
    public void jouerMusic (int val){

        if (sound != null) sound.stop();

        sound = null;

        if (val == 0){
            sound = Gdx.audio.newSound(Gdx.files.internal("music/intro.mp3"));
            sound.play();
            volume = 0.45F;
        }
        else if (val == 1){
            sound = Gdx.audio.newSound(Gdx.files.internal("music/day1.mp3"));
            sound.play();
            volume = 0.7F;
        }
        else if (val == 3){
            sound = Gdx.audio.newSound(Gdx.files.internal("music/myst4.mp3"));
            sound.play();
            volume = 5.2F;
        }
        else if (val == 4 ){
            sound = Gdx.audio.newSound(Gdx.files.internal("music/night1.mp3"));
            sound.play();
            volume = 0.3F;
        }
        else if (val == 5){
            sound = Gdx.audio.newSound(Gdx.files.internal("music/night2.mp3"));
            sound.play();
            volume = 2.0F;
        }
        if (sound != null) sound.setVolume(0,volume);
    }
}
