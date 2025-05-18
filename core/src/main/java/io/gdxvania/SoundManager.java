package io.gdxvania;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ObjectMap;

import io.gdxvania.utils.ESounds;

public class SoundManager {
	
	private static SoundManager instance;
    private ObjectMap<String, Music> musics;
    private Music currentMusic;
    private float volume = 1.0f;

    private SoundManager() {
        musics = new ObjectMap<>();
        load(ESounds.TitleScreen, "Vampire_Killer.mp3");
        load(ESounds.TutorialScreen, "Prologue.mp3");
        load(ESounds.Game, "Vampire_Killer.mp3");
        load(ESounds.Boss, "Heart_of_Fire.mp3");
        load(ESounds.VictoryScreen, "All_Clear.mp3");
        load(ESounds.GameOverScreen, "Game_Over.mp3");
        load(ESounds.PlayerHit, "damage.mp3");
        load(ESounds.WhipHit, "whip-hit.mp3");

    }
    
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void load(ESounds key, String filePath) {
        Music music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        musics.put(key.toString(), music);
    }

    public void play(ESounds key, boolean looping) {
        Music music = musics.get(key.toString());
        if (music != null) {
            if (currentMusic != null && currentMusic.isPlaying()) {
                currentMusic.stop();
            }
            currentMusic = music;
            currentMusic.setLooping(looping);
            currentMusic.setVolume(volume);
            currentMusic.play();
        } else {
            Gdx.app.error("MusicManager", "Cannot found music with key: " + key);
        }
    }

    public void stop(ESounds key) {
        Music music = musics.get(key.toString());
        if (music != null && music.isPlaying()) {
            music.stop();
            if (currentMusic == music) {
                currentMusic = null;
            }
        }
    }

    public void stopAll() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
            currentMusic = null;
        }
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0, Math.min(1, volume));
        if (currentMusic != null) {
            currentMusic.setVolume(this.volume);
        }
    }

    public float getVolume() {
        return volume;
    }

    public boolean isPlaying(ESounds key) {
        Music music = musics.get(key.toString());
        return music != null && music.isPlaying();
    }

    public void dispose() {
        for (Music music : musics.values()) {
            music.dispose();
        }
        musics.clear();
        currentMusic = null;
    }
}