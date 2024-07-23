package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity {

    private TextView gamesPlayedTextView;
    private TextView winsTextView;
    private TextView lossesTextView;
    private TextView bestStreakTextView;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        gamesPlayedTextView = findViewById(R.id.games_played_text_view);
        winsTextView = findViewById(R.id.wins_text_view);
        lossesTextView = findViewById(R.id.losses_text_view);
        bestStreakTextView = findViewById(R.id.best_streak_text_view);
        playButton = findViewById(R.id.play_button);

    // The page will get the game statistics
        Intent intent = getIntent();
        int gamesPlayed = intent.getIntExtra("gamesPlayed", 0);
        int wins = intent.getIntExtra("wins", 0);
        int losses = intent.getIntExtra("losses", 0);
        int bestStreak = intent.getIntExtra("maxStreak", 0);

    // Then will display them
        gamesPlayedTextView.setText("Games Played: " + gamesPlayed);
        winsTextView.setText("Wins: " + wins);
        lossesTextView.setText("Losses: " + losses);
        bestStreakTextView.setText("Best Streak: " + bestStreak);

    // After clicking the Play button will be redirected to the Homepage and being able to play the game
        playButton.setOnClickListener(v -> {
            Intent playIntent = new Intent(StatisticsActivity.this, MainActivity.class);
            startActivity(playIntent);
        });
    }
}
