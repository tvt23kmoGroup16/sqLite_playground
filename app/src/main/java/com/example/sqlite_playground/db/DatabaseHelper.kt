package com.example.sqlite_playground.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "UserDatabase.db"
        const val DATABASE_VERSION = 1 //version control
        const val TAG = "DatabaseHelper"
    }

    //Creating the users table using SQL statements:
    private val sqlCreateUsersTable =
        "CREATE TABLE ${UserContract.UserEntry.TABLE_NAME} (" +
                "${UserContract.UserEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${UserContract.UserEntry.COLUMN_NAME_USERNAME} TEXT NOT NULL," +
                "${UserContract.UserEntry.COLUMN_NAME_EMAIL} TEXT NOT NULL UNIQUE," +
                "${UserContract.UserEntry.COLUMN_NAME_PASSWORD_HASH} TEXT NOT NULL," +
                "${UserContract.UserEntry.COLUMN_NAME_LAST_LOGIN} DATETIME," +
                "${UserContract.UserEntry.COLUMN_NAME_SCORE} INTEGER," +
                "${UserContract.UserEntry.COLUMN_NAME_LEVEL} INTEGER)"

    // Other tables here accordingly

    private val sqlCreateLevelsTable =
        "CREATE TABLE ${LevelContract.LevelEntry.TABLE_NAME} (" +
                "${LevelContract.LevelEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${LevelContract.LevelEntry.COLUMN_NAME_LEVEL_NUMBER} INTEGER NOT NULL," +
                "${LevelContract.LevelEntry.COLUMN_NAME_LEVEL_NAME} TEXT NOT NULL UNIQUE," +
                "${LevelContract.LevelEntry.COLUMN_NAME_DIFFICULTY} INTEGER NOT NULL," +
                "${LevelContract.LevelEntry.COLUMN_NAME_REQUIRED_SCORE} INTEGER NOT NULL)"

    private val sqlCreateSettingsTable =
        "CREATE TABLE ${SettingsContract.SettingsEntry.TABLE_NAME} (" +
                "${SettingsContract.SettingsEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${SettingsContract.SettingsEntry.COLUMN_NAME_USER_ID} INTEGER NOT NULL," +
                "${SettingsContract.SettingsEntry.COLUMN_NAME_MUSIC_ENABLED} INTEGER NOT NULL," +
                "FOREIGN KEY (${SettingsContract.SettingsEntry.COLUMN_NAME_USER_ID}) " +
                "REFERENCES ${UserContract.UserEntry.TABLE_NAME} (${UserContract.UserEntry.COLUMN_NAME_ID}))"

    private val sqlCreateHighScoresTable =
        "CREATE TABLE ${HighScoreContract.HighScoreEntry.TABLE_NAME} (" +
                "${HighScoreContract.HighScoreEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${HighScoreContract.HighScoreEntry.COLUMN_NAME_USER_ID} INTEGER NOT NULL," +
                "${HighScoreContract.HighScoreEntry.COLUMN_NAME_SCORE} INTEGER NOT NULL," +
                "${HighScoreContract.HighScoreEntry.COLUMN_NAME_LEVEL} INTEGER NOT NULL," +
                "${HighScoreContract.HighScoreEntry.COLUMN_NAME_DATE} DATETIME NOT NULL," +
                "FOREIGN KEY (${HighScoreContract.HighScoreEntry.COLUMN_NAME_USER_ID}) " +
                "REFERENCES ${UserContract.UserEntry.TABLE_NAME} (${UserContract.UserEntry.COLUMN_NAME_ID}))"


    // Deleting the users table
    private val sqlDeleteUsersTable =
        "DROP TABLE IF EXISTS ${UserContract.UserEntry.TABLE_NAME}"

    //Other deletions here...
    private val sqlDeleteLevelsTable =
        "DROP TABLE IF EXISTS ${LevelContract.LevelEntry.TABLE_NAME}"
    private val sqlDeleteSettingsTable =
        "DROP TABLE IF EXISTS ${SettingsContract.SettingsEntry.TABLE_NAME}"
    private val sqlDeleteHighScoresTable =
        "DROP TABLE IF EXISTS ${HighScoreContract.HighScoreEntry.TABLE_NAME}"

    data class User(
        val id: Long,
        val username: String,
        val email: String,
        val passwordHash: String,
        val lastLogin: String?,
        val score: Int?,
        val level: Long
    )

    data class Level(
        val id: Long,
        val levelNumber: Int,
        val levelName: String,
        val difficulty: Int,
        val requiredScore: Int
    )

    // Creating tables
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sqlCreateUsersTable) // Creating the users table
        // other table creations here..
        db.execSQL(sqlCreateLevelsTable) // Creating the levels table
        db.execSQL(sqlCreateSettingsTable) // Creating the settings table
        db.execSQL(sqlCreateHighScoresTable) // Creating the highScores table
    }

    //Version control: upgrading tables
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(sqlDeleteUsersTable) // Deleting the old users table
        //other delete old versions here...
        db.execSQL(sqlDeleteLevelsTable)
        db.execSQL(sqlDeleteSettingsTable)
        db.execSQL(sqlDeleteHighScoresTable)

        onCreate(db) // Recreating all tables
    }

    /*deletes databases but does not reset auto-increments,
    if you want to delete auto-increments, drop tables and recreate them and do it inside this function*/
    fun resetDatabase() {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            //Delete rows
            db.delete("Users", null, null)
            db.delete("Levels", null, null)
            db.delete("HighScores", null, null)
            db.delete("Settings", null, null)

            db.setTransactionSuccessful() //Commit if all succeeded
            Log.d(TAG, "Database reset: Data cleared")
        } catch (e: Exception) {
            Log.e(TAG, "Error resetting database: ", e)
        } finally {
            db.endTransaction() //Commit or rollback
            db.close()
        }
    }

    fun getUserScore(userId: Long): Int? {
        val db = this.readableDatabase
        var highScore: Int? = null
        val query =
            "SELECT MAX(${HighScoreContract.HighScoreEntry.COLUMN_NAME_SCORE}) AS HighScore " + //Select using MAX for highScore
                    "FROM ${HighScoreContract.HighScoreEntry.TABLE_NAME} " +
                    "WHERE ${HighScoreContract.HighScoreEntry.COLUMN_NAME_USER_ID} = ?"

        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        try {
            if (cursor.moveToFirst()) { //Check for any results
                highScore = cursor.getInt(cursor.getColumnIndexOrThrow("HighScore"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving high score: ", e)
        } finally {
            //Close cursor and db to free resources
            cursor.close()
            db.close()
        }
        return highScore //Return high score if found, else return null
    }

    fun getUsersByLevel(levelId: Long): List<User> {
        val db = this.readableDatabase
        val userList = mutableListOf<User>()
        val query =
            "SELECT * FROM ${UserContract.UserEntry.TABLE_NAME} " +
                    "WHERE ${UserContract.UserEntry.COLUMN_NAME_LEVEL} = ?"

        val cursor = db.rawQuery(query, arrayOf(levelId.toString()))
        try {
            if (cursor.moveToFirst()) {
                do {
                    val user = User( //Create a User object for each row found
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_ID)),
                        username = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_USERNAME)),
                        email = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_EMAIL)),
                        passwordHash = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_PASSWORD_HASH)),
                        lastLogin = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_LAST_LOGIN)),
                        score = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_SCORE)),
                        level = cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_LEVEL))
                    )
                    userList.add(user)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving users by level: ", e)
        } finally {
            cursor.close()
            db.close()
        }
        return userList //Return list of users associated with the given level
    }

    fun getLevelsByScore(minScore: Int): List<Level> {
        val db = this.readableDatabase
        val levelList = mutableListOf<Level>()
        val query =
            "SELECT * FROM ${LevelContract.LevelEntry.TABLE_NAME} " +
                    "WHERE ${LevelContract.LevelEntry.COLUMN_NAME_REQUIRED_SCORE} > ?"

        val cursor = db.rawQuery(query, arrayOf(minScore.toString()))
        try {
            if (cursor.moveToFirst()) {
                do {
                    val level = Level( //Create a Level object for each row found
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(LevelContract.LevelEntry.COLUMN_NAME_ID)),
                        levelNumber = cursor.getInt(cursor.getColumnIndexOrThrow(LevelContract.LevelEntry.COLUMN_NAME_LEVEL_NUMBER)),
                        levelName = cursor.getString(cursor.getColumnIndexOrThrow(LevelContract.LevelEntry.COLUMN_NAME_LEVEL_NAME)),
                        difficulty = cursor.getInt(cursor.getColumnIndexOrThrow(LevelContract.LevelEntry.COLUMN_NAME_DIFFICULTY)),
                        requiredScore = cursor.getInt(cursor.getColumnIndexOrThrow(LevelContract.LevelEntry.COLUMN_NAME_REQUIRED_SCORE))
                    )
                    levelList.add(level)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error retrieving levels by score: ", e)
        } finally {
            cursor.close()
            db.close()
        }
        return levelList //Return list of levels that require a greater score than the provided minimum score
    }

}

