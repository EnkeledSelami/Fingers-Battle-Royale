package com.example.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "App1Prefs";
    private static final String KEY_BACKGROUND_MUSIC = "background_music";
    private static final String KEY_DIFFICULTY = "difficulty";

    private Switch backgroundSoundSwitch;
    private RadioGroup difficultyRadioGroup;
    private static MediaPlayer mediaPlayer;
    private boolean isBackgroundMusicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backgroundSoundSwitch = findViewById(R.id.background_sound_switch);
        Button playButton = findViewById(R.id.play_button);
        difficultyRadioGroup = findViewById(R.id.difficulty_radio_group);

        // Load the saved settings
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isBackgroundMusicOn = settings.getBoolean(KEY_BACKGROUND_MUSIC, false);
        String difficulty = settings.getString(KEY_DIFFICULTY, "easy");

        // I have created a difficulty level even if it doesn't matter to this game just practicing my skills
        backgroundSoundSwitch.setChecked(isBackgroundMusicOn);
        if ("easy".equals(difficulty)) {
            difficultyRadioGroup.check(R.id.radio_easy);
        } else if ("medium".equals(difficulty)) {
            difficultyRadioGroup.check(R.id.radio_medium);
        } else if ("hard".equals(difficulty)) {
            difficultyRadioGroup.check(R.id.radio_hard);
        }

        // Play the sound if the Background Audio is on
        if (isBackgroundMusicOn) {
            startBackgroundMusic();
        }

        backgroundSoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startBackgroundMusic();
                } else {
                    stopBackgroundMusic();
                }
                // Save the Audio status
                saveSettings(KEY_BACKGROUND_MUSIC, isChecked);
            }
        });

        difficultyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String difficulty = "easy";
                if (checkedId == R.id.radio_medium) {
                    difficulty = "medium";
                } else if (checkedId == R.id.radio_hard) {
                    difficulty = "hard";
                }
                // Save the Difficulty level
                saveSettings(KEY_DIFFICULTY, difficulty);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Once you click the button Play the game will start with the selected ettings (Audio and Difficulty)
                startGame();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isBackgroundMusicOn = settings.getBoolean(KEY_BACKGROUND_MUSIC, false);

        // Set the switch based on the saved settings
        backgroundSoundSwitch.setChecked(isBackgroundMusicOn);

        // Switch Button will start/stop the Audio
        if (isBackgroundMusicOn) {
            startBackgroundMusic();
        } else {
            stopBackgroundMusic();
        }
    }

    private void startBackgroundMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.peaceful_music);
            mediaPlayer.setLooping(true);
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void stopBackgroundMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void startGame() {
        // Start the game activity with the selected settings
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void saveSettings(String key, boolean value) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void saveSettings(String key, String value) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The Audio will continue till you close the Application
    }

// Making sure the music is playing or not
    public static void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
