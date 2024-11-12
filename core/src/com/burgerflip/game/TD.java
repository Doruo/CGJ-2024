package com.burgerflip.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.burgerflip.game.Fonctions.LancementRound;
import com.burgerflip.game.Scenes.MursManager;
import com.burgerflip.game.screens.menu.HomeScreen;
import com.burgerflip.game.screens.playscreen.PlayScreen;

public class TD extends Game {
	public static final int V_WIDTH = 1920;
	public static final int V_HEIGHT = 1088;
	private SpriteBatch batch;
	private static InputProcessor lancementRound;
	private PlayScreen playScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		playScreen = null;
		HomeScreen homeScreen = new HomeScreen();
		try {
			playScreen = new PlayScreen(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


		setScreen(homeScreen);

		homeScreen.getPlayButton().addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				lancementRound = new LancementRound(playScreen);
				InputMultiplexer inputMultiplexer = new InputMultiplexer();
				inputMultiplexer.addProcessor(lancementRound);
				inputMultiplexer.addProcessor(MursManager.stage);
				Gdx.input.setInputProcessor(null);
				Gdx.input.setInputProcessor(inputMultiplexer);
				setScreen(playScreen);
			}
		});
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public SpriteBatch getBatch(){
		return batch;
	}

}