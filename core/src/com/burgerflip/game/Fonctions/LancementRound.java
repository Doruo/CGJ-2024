package com.burgerflip.game.Fonctions;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.burgerflip.game.screens.playscreen.PlayScreen;

public class LancementRound implements InputProcessor {
    private PlayScreen screen;
    public LancementRound(PlayScreen screen) {
        this.screen = screen;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.ENTER) {
            if (screen.getHud().getDayTime().equals("JOUR")) {
                screen.switchDayNight();
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
