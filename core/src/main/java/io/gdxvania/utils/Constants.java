package io.gdxvania.utils;

import com.badlogic.gdx.Input;

public class Constants {
    public static final float PLAYER_SPEED = 150f;
    public static final int PLAYER_HEALTH = 5;
    public static final float BLINK_INTERVAL = 0.5f;
    public static final float GRAVITY = -500f;
    public static final float JUMP_FORCE = 250f;
    
    public static final float ATTACK_DURATION = 0.25f;
    public static final float DAMAGE_COOLDOWN = 5f;

    public static final float ENEMY_SPEED = 100f;
    public static final float KNIFE_SPEED_MULTIPLIER = 300f;
    public static final float ENEMY_SPAWN_RIGHT_RATE = 0.66f;
    public static final int SCORE_SPAWN_BOSS = 1000;

    public static final float GROUND_LEVEL = 7f;
    public static final int SCREEN_WIDTH = 480;
    public static final int SCREEN_HEIGHT = 480;
    
    public static final int LEFT_DIRECTION_KEY = Input.Keys.LEFT;
    public static final int RIGHT_DIRECTION_KEY = Input.Keys.RIGHT;
    public static final int WHIP_KEY = Input.Keys.X;
    public static final int KNIFE_KEY = Input.Keys.C;
    public static final int JUMP_KEY = Input.Keys.SPACE;
}
