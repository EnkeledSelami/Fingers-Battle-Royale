package com.example.app1;

public class GameSubject {
}

/**
 * Rock, Paper, Scissors Game (I called it Fingers Battle Royale and even created a logo)
 *
 * Description:
 * Rock, Paper, Scissors is a classic game brought to life in this Android application. The game allows players to compete
 * against the computer by selecting one of three hand gestures: rock, paper, or scissors.
 *
 * Classes:
 * 1. MainActivity.java:
 *    - Description: This class serves as the homepage of the game. It provides the user interface for playing Rock, Paper, Scissors
 *      and managing game statistics.
 *    - Features:
 *      - Allows users to select their hand gesture (rock, paper, or scissors).
 *      - Determines the outcome of each round by comparing the user's choice with the computer's choice.
 *      - Keeps track of game statistics including current streak, maximum streak, total games played, wins, and losses.
 *      - Provides options to navigate to the settings page and view game statistics.
 *
 * 2. SettingsActivity.java:
 *    - Description: This class represents the settings page of the game where users can customize their gameplay experience.
 *    - Features:
 *      - Enables users to toggle background music on/off and choose between different difficulty levels (easy, medium, hard).
 *      - Persists user settings using SharedPreferences to ensure preferences are retained across app sessions such as Home (MainActivity) and Statistics.
 *      - Utilizes MediaPlayer for background music playback with options for starting, stopping, and releasing audio resources.
 *
 * 3. StatisticsActivity.java:
 *    - Description: This class displays the game statistics, providing users with insights into their performance.
 *    - Features:
 *      - Retrieves game statistics passed from MainActivity via Intent and displays them on the statistics page.
 *      - Presents statistics such as total games played, wins, losses, and best streak in a clear and concise format.
 *      - Allows users to return to the homepage and continue playing the game via the Play button.
 *
 * About the design of the game I tried to learn Unity and make this game 3D but I had a limited free time because at that time I was also working on my internship project
 * and couldn't keep up with the same intense on both projects.
 * For now this game might be 2D but someday I might revisit the idea of developing it in Unity and transforming it into a 3D experience.With more free time and a deeper
 * understanding of Unity.
 */
