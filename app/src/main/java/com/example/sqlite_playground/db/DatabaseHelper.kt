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
    private val SQL_CREATE_USERS_TABLE =
        "CREATE TABLE ${UserContract.UserEntry.TABLE_NAME} (" +
                "${UserContract.UserEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${UserContract.UserEntry.COLUMN_NAME_USERNAME} TEXT NOT NULL," +
                "${UserContract.UserEntry.COLUMN_NAME_EMAIL} TEXT NOT NULL UNIQUE," +
                "${UserContract.UserEntry.COLUMN_NAME_PASSWORD_HASH} TEXT NOT NULL," +
                "${UserContract.UserEntry.COLUMN_NAME_LAST_LOGIN} DATETIME," +
                "${UserContract.UserEntry.COLUMN_NAME_SCORE} INTEGER," +
                "${UserContract.UserEntry.COLUMN_NAME_LEVEL} INTEGER)"

    // Other tables here accordingly...

    // Deleting the users table
    private val SQL_DELETE_USERS_TABLE =
        "DROP TABLE IF EXISTS ${UserContract.UserEntry.TABLE_NAME}"
    //Other deletions here...

    // Creating tables
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USERS_TABLE) // Creating the users table
    //other table creations here...
    }

    //Version control: upgrading tables
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_USERS_TABLE) // Deleting the old users table
        //other delete old versions here...
        onCreate(db) // Recreating all tables
    }

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
        val query = "SELECT MAX(${HighScoreContract.HighScoreEntry.COLUMN_NAME_SCORE}) AS HighScore " + //Select using MAX for highscore
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
}