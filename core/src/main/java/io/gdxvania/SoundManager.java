package io.gdxvania;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;

import io.gdxvania.utils.ESounds;

public class SoundManager {
	
	private static SoundManager instance;
    private ObjectMap<String, Music> musics;
    private ObjectMap<String, Sound> sounds;    
    private Music currentMusic;
    private float volume = 1.0f;
    private boolean audioUnlocked = false;

    private SoundManager() {
        musics = new ObjectMap<>();
        sounds = new ObjectMap<>();
        
        loadMusic(ESounds.TitleScreen, "Vampire_Killer.mp3");
        loadMusic(ESounds.TutorialScreen, "Prologue.mp3");
        loadMusic(ESounds.Game, "Vampire_Killer.mp3");
        loadMusic(ESounds.Boss, "Heart_of_Fire.mp3");
        loadMusic(ESounds.VictoryScreen, "All_Clear.mp3");
        loadMusic(ESounds.GameOverScreen, "Game_Over.mp3");
        loadSound(ESounds.PlayerHit, "damage.mp3");
        loadSound(ESounds.WhipHit, "whip-hit.mp3");

    }
    
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    public void unlockAudio() {
        if (!audioUnlocked) {
            try {
                Music unlockSound = Gdx.audio.newMusic(Gdx.files.internal("block-breaking.mp3"));
                unlockSound.setVolume(0);
                unlockSound.play();
                unlockSound.setOnCompletionListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {
                        music.dispose();
                    }
                });
                audioUnlocked = true;
                Gdx.app.log("SoundManager", "Audio context unlocked");
            } catch (Exception e) {
                Gdx.app.log("SoundManager", "Error unloacking audio: " + e.getMessage());
            }
        }
    }
    
    private void loadMusic(ESounds key, String filePath) {
        load(key, filePath, true);
    }
    
    private void loadSound(ESounds key, String filePath) {
        load(key, filePath, false);
    }

    private void load(ESounds key, String filePath, boolean isMusic) {        
        if (isMusic) {
        	Music music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        	musics.put(key.toString(), music);
		} else {
	        Sound sound = Gdx.audio.newSound(Gdx.files.internal(filePath));
			sounds.put(key.toString(), sound);
		}
        
    }
    
    private void playMusic(ESounds key) {
        Music music = musics.get(key.toString());
        if (music != null) {
            if (currentMusic != null && currentMusic.isPlaying()) {
                currentMusic.stop();
            }
            currentMusic = music;
            currentMusic.setLooping(true);
            currentMusic.setVolume(volume);
            currentMusic.play();
        } else {
        	Gdx.app.error("SoundManager", "Cannot find music with key: " + key);
        }
    }
    
    private long playSound(ESounds key) {
        Sound sound = sounds.get(key.toString());
        if (sound != null) {
            return sound.play(volume);
        } else {
        	Gdx.app.error("SoundManager", "Cannot find sound with key: " + key);
            return -1;
        }
    }

    public void play(ESounds key, boolean isMusic) {
    	if (isMusic) {
			playMusic(key);
		} else {
			playSound(key);
		}
    }    
    
    private void stopMusic(ESounds key) {
        Music music = musics.get(key.toString());
        if (music != null && music.isPlaying()) {
            music.stop();
            if (currentMusic == music) {
                currentMusic = null;
            }
        }
    }
    
    private void stopSound(ESounds key) {
        Sound sound = sounds.get(key.toString());
        if (sound != null) {
            sound.stop();
        }
    }
    
    public void stop(ESounds key, boolean isMusic) {
        if (isMusic) {
			stopMusic(key);
		} else {
			stopSound(key);
		}
    }
    
    private void stopAllMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
            currentMusic = null;
        }
    }

    private void stopAllSounds() {
        for (Sound sound : sounds.values()) {
            sound.stop();
        }
    }

    public void stopAll() {
    	stopAllSounds();
        stopAllMusic();        
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
    
    public void dispose() {
        for (Music music : musics.values()) {
            music.dispose();
        }
        musics.clear();
        if (currentMusic != null) {
            currentMusic.dispose();
            currentMusic = null;
        }
        for (Sound sound : sounds.values()) {
            sound.dispose();
        }
        sounds.clear();
    }
}