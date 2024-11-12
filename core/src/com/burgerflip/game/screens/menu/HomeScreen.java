package com.burgerflip.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.burgerflip.game.TD;


public class HomeScreen extends ScreenAdapter {

    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;
    private Viewport viewport;
    private Table tableMenu;
    private Label labelGameName;
    private Button playButton;

    public HomeScreen() {
        stage = new Stage();
        viewport = new ExtendViewport(1280, 720);
        tableMenu = new Table();
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        labelGameName = new Label("LE CRYSTAL DU POUVOIR", labelStyle);
        labelGameName.setSize(256, 256);
        tableMenu.add(labelGameName);
        tableMenu.row();
        tableMenu.center().center();
        tableMenu.setFillParent(true);
        playButton = addButton("menu/playButton.png");
        tableMenu.add(playButton);
        tableMenu.row();
        Button quitButton = addButton("menu/quitButton.png");
        tableMenu.add(quitButton);
        Gdx.input.setInputProcessor(stage);

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(tableMenu);

    }
    public Button getPlayButton() {
            return playButton;
        }


    public Button addButton(String texturePath) {
        Button button = new Button();
        button.setStyle(new Button.ButtonStyle());
        button.setTouchable(Touchable.enabled);
        button.setSize(256, 256);
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        button.getStyle().up = new TextureRegionDrawable(texture);
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
    }
}
