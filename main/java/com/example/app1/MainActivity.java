package com.example.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private TextView streakTextView;
    private int currentStreak = 0;
    private int maxStreak = 0;
    private int gamesPlayed = 0;
    private int wins = 0;
    private int losses = 0;

    private static final String PREFS_NAME = "GameStats";
    private static final String KEY_CURRENT_STREAK = "currentStreak";
    private static final String KEY_MAX_STREAK = "maxStreak";
    private static final String KEY_GAMES_PLAYED = "gamesPlayed";
    private static final String KEY_WINS = "wins";
    private static final String KEY_LOSSES = "losses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resultTextView = findViewById(R.id.result_text_view);
        streakTextView = findViewById(R.id.streak_text_view);

        Button rockButton = findViewById(R.id.rock_button);
        Button paperButton = findViewById(R.id.paper_button);
        Button scissorsButton = findViewById(R.id.scissors_button);

        rockButton.setOnClickListener(v -> play("rock"));
        paperButton.setOnClickListener(v -> play("paper"));
        scissorsButton.setOnClickListener(v -> play("scissors"));

        loadStatistics();
        updateStreakTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            // You will be redirected to Settings page after clicking Settings icon
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_statistics) {
            // You will be redirected to Statistics page after clicking Statistics icon
            saveStatistics();
            showStatisticsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void play(String userChoice) {
        String[] options = {"rock", "paper", "scissors"};
        Random random = new Random();
        int randomIndex = random.nextInt(3);
        String computerChoice = options[randomIndex];

        String resultMessage = "You chose: " + userChoice + "\nComputer chose: ";

        String computerEmoji = "";
        switch (computerChoice) {
            case "rock":
                computerEmoji = "\uD83E\uDD18"; // This is supposed to be Rock emoji
                break;
            case "paper":
                computerEmoji = "\uD83D\uDD90"; // This is supposed to be Paper emoji
                break;
            case "scissors":
                computerEmoji = "\u270C"; // This is supposed to be Scissors emoji
                break;
        }

        resultMessage += computerChoice + " " + computerEmoji + "\n";
    // Same choice with computer will produce a tie
        if (userChoice.equals(computerChoice)) {
            resultMessage += "It's a tie!";
            resetStreak();
        } else if ((userChoice.equals("rock") && computerChoice.equals("scissors")) ||
                (userChoice.equals("paper") && computerChoice.equals("rock")) ||
                (userChoice.equals("scissors") && computerChoice.equals("paper"))) {
            resultMessage += "You win!";
            incrementStreak();
            wins++;
        } else {
            resultMessage += "Computer wins!";
            resetStreak();
            losses++;
        }
    // I produced the defeat options based on your choice,so the remaning option it means the win result

        gamesPlayed++;

        // You can see your streak result
        resultTextView.setText(resultMessage);
        updateStreakTextView();

        // For checking if you have surpased the streak record or not
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }
    }

    private void updateStreakTextView() {
        streakTextView.setText("Current Streak: " + currentStreak + "\nMax Streak: " + maxStreak);
    }

    private void resetStreak() {
        currentStreak = 0;
    }

    private void incrementStreak() {
        currentStreak++;
    }

// Below the player will get the Record Message every time he surpass the Max Streak
    private void displayNewRecordMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("🎉 New Record! 🎉")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveStatistics() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_CURRENT_STREAK, currentStreak);
        editor.putInt(KEY_MAX_STREAK, maxStreak);
        editor.putInt(KEY_GAMES_PLAYED, gamesPlayed);
        editor.putInt(KEY_WINS, wins);
        editor.putInt(KEY_LOSSES, losses);
        editor.apply();
    }

    private void loadStatistics() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentStreak = sharedPreferences.getInt(KEY_CURRENT_STREAK, 0);
        maxStreak = sharedPreferences.getInt(KEY_MAX_STREAK, 0);
        gamesPlayed = sharedPreferences.getInt(KEY_GAMES_PLAYED, 0);
        wins = sharedPreferences.getInt(KEY_WINS, 0);
        losses = sharedPreferences.getInt(KEY_LOSSES, 0);
    }

// I have made the program count the game statistics and to store/update them in the page Statistics
    private void showStatisticsActivity() {
        Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
        intent.putExtra("gamesPlayed", gamesPlayed);
        intent.putExtra("wins", wins);
        intent.putExtra("losses", losses);
        intent.putExtra("maxStreak", maxStreak);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SettingsActivity.releaseMediaPlayer();
    }
}
