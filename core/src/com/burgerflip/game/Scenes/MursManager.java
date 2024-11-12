package com.burgerflip.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.burgerflip.game.TD;

public class MursManager {
    public static Stage stage;
    private Viewport viewport;
    private Button baseWallButton;
    private Button etherWallButton;
    private static MursManager instance;
    private static Table wallTable;
    private static final String PATH_TEXTURE_WALL_BASE = "defense/murs/murY.png";
    private static final String PATH_TEXTURE_ETHER_WALL = "defense/murs/murEthereeY.png";
    private static final String PATH_TEXTURE_ACTIVE_WALL_BASE = "defense/murs/murX.png";
    private static final String PATH_TEXTURE_ACTIVE_ETHER_WALL = "defense/murs/murEthereeX.png";
    private static final int BUTTON_SIZE = 100;
    private boolean isBaseWallActive;
    private boolean isEtherWallActive;

    private MursManager(SpriteBatch sb) {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, TD.V_WIDTH, TD.V_HEIGHT);
        viewport = new FitViewport(TD.V_WIDTH, TD.V_HEIGHT, camera);

        stage = new Stage(viewport, sb);
        createButtons();
        createTable();
        createBindings();
    }

    private void createButtons() {
        baseWallButton = createButton(PATH_TEXTURE_WALL_BASE);
        etherWallButton = createButton(PATH_TEXTURE_ETHER_WALL);
    }

    private void createTable() {
        wallTable = new Table();
        wallTable.bottom().right();
        wallTable.setFillParent(true);
        wallTable.add(baseWallButton).size(BUTTON_SIZE, BUTTON_SIZE).fill();
        wallTable.add(etherWallButton).size(BUTTON_SIZE, BUTTON_SIZE).fill();
        wallTable.setTouchable(Touchable.childrenOnly);
        stage.addActor(wallTable);
    }

    private void createBindings() {
        baseWallButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleBaseWall();
            }
        });
        etherWallButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleEtherWall();
            }
        });
    }

    private Button createButton(String texturePath) {
        Button button = new Button();
        button.setStyle(new ButtonStyle());
        button.setTouchable(Touchable.enabled);
        button.setSize(BUTTON_SIZE, BUTTON_SIZE);
        applyTextureToButton(texturePath, button);
        return button;
    }

    private void applyTextureToButton(String texturePath, Button button) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        Drawable drawable = new TextureRegionDrawable(texture);
        button.getStyle().up = drawable;
    }

    private void toggleBaseWall() {
        if(isBaseWallActive) {
            isBaseWallActive = false;
            applyTextureToButton(PATH_TEXTURE_WALL_BASE, baseWallButton);
        } else {
            isBaseWallActive = true;
            if(isEtherWallActive) {
                isEtherWallActive = false;
                applyTextureToButton(PATH_TEXTURE_ETHER_WALL, etherWallButton);
            }
            applyTextureToButton(PATH_TEXTURE_ACTIVE_WALL_BASE, baseWallButton);
        }
    }

    private void toggleEtherWall() {
        if(isEtherWallActive) {
            isEtherWallActive = false;
            applyTextureToButton(PATH_TEXTURE_ETHER_WALL, etherWallButton);
        } else {
            isEtherWallActive = true;
            if(isBaseWallActive) {
                isBaseWallActive = false;
                applyTextureToButton(PATH_TEXTURE_WALL_BASE, baseWallButton);
            }
            applyTextureToButton(PATH_TEXTURE_ACTIVE_ETHER_WALL, etherWallButton);
        }
    }

    public synchronized static MursManager getInstance(SpriteBatch sb) {
        if (instance == null) {
            instance = new MursManager(sb);
        }
        return instance;
    }

    public boolean isBaseWallActive() {
        return isBaseWallActive;
    }

    public boolean isEtherWallActive() {
        return isEtherWallActive;
    }

    public static void update() {
        wallTable.bottom().right();
    }
}