# SQLite Playground

## Overview

The **SQLite Playground** is an Android application designed to demonstrate the use of SQLite databases in a simple gaming context. This project showcases how to create, read, update, and delete (CRUD) operations on multiple tables within an SQLite database, making it a useful resource for learning about database management in Android development.

## Features

- User authentication with secure password storage (optional).
- Ability to track user scores and levels.
- High scores leaderboard to display top players.
- Support for multiple tables including users, levels, and high scores.

## Getting Started

### Prerequisites

- Android Studio (version 4.1 or later)
- Basic understanding of Kotlin and Android development

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/tvt23kmoGroup16/sqLite_playground.git
   cd sqLite_playground

2. **Open the Project in Android Studio**

    ```bash 
   Launch Android Studio and open the cloned project.

3. **Sync Gradle**

    ```bash
   Click on "Sync Now" when prompted to ensure all dependencies are properly set up.

4. **Run the Application**

     ```bash  
        Connect your Android device or start an emulator.
        Click on the Run button in Android Studio to build and run the app.

Usage

    On launching the app, users can create an account by providing a username and password.
    Users can log in to view their scores and levels.
    The high scores leaderboard can be accessed to see the top players in the game.


Database Schema

The database consists of the following tables:

    Users
        user_name: Name of the user, or username in the game.
        email: User's email address.
        password_hash: Hashed password for authentication.
        last_login: Timestamp of the last login.
        score: Total score of the user.
        level: Current level of the user.

    Levels
        level_number: Unique identifier for the level.
        level_name: Name of the level.
        difficulty: Difficulty rating of the level.
        required_score: Score needed to unlock level.

    HighScores
        user_id: Reference to the user's ID.
        score: High score achieved by the user.
        level: Level associated with the high score.
        timestamp: Date and time when the score was achieved.

    Settings
        user_id: Reference to the user's ID.
        music_enabled: Boolean for Music Settings.
     

Contributing

Contributions are welcome! Please feel free to submit a pull request or create an issue for any enhancements or bug fixes.

    Fork the repository.
    Create your feature branch (git checkout -b feature/YourFeature).
    Commit your changes (git commit -m 'Add some feature').
    Push to the branch (git push origin feature/YourFeature).
    Open a pull request.


License

This project is licensed under the GNU General License - see the LICENSE file for details.


Acknowledgments

    SQLite for the lightweight database implementation.
    Android Developers for the comprehensive documentation and resources.
    Oulu University of Appiled Sciences for supporting this project.




    
