package com.example.sqlite_playground.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.sqlite_playground.db.UserContract.UserEntry


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "UserDatabase.db"
        const val DATABASE_VERSION = 1 //version control
        const val TABLE_NAME = "UserTable"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_USERNAME = "name"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_LAST_LOGIN = "lastlogin"
        const val COLUMN_NAME_PASSWORD_HASH = "password"

        const val TAG = "DatabaseHelper"
    }

    //Creating the users table using SQL statements:
    private val SQL_CREATE_USERS_TABLE =
        "CREATE TABLE ${UserEntry.TABLE_NAME} (" +
                "${UserEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${UserEntry.COLUMN_NAME_USERNAME} TEXT NOT NULL," +
                "${UserEntry.COLUMN_NAME_EMAIL} TEXT NOT NULL UNIQUE," +
                "${UserEntry.COLUMN_NAME_LAST_LOGIN} TEXT NOT NULL UNIQUE," +
                "${UserEntry.COLUMN_NAME_PASSWORD_HASH} TEXT NOT NULL)"
    // Other tables here accordingly...


    // Deleting the users table
    private val SQL_DELETE_USERS_TABLE =
        "DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}"
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

    //   private const val TABLE_NAME = "UserTable"
    //   Getting Users from the table
    fun getAllUsers(): List<User> {
        val usersList = mutableListOf<User>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EMAIL))
                )
                usersList.add(user())
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return usersList
    }

    //Add user and first values
    fun addUser(name: String, id: String, email: String, password: String, lastlogin: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME_USERNAME, name)
        values.put(COLUMN_NAME_ID, id)
        values.put(COLUMN_NAME_EMAIL, email)
        values.put(COLUMN_NAME_PASSWORD_HASH, password)
        values.put(COLUMN_NAME_LAST_LOGIN, lastlogin)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    //SAVE PROGRESS?
    //fun saveProgress(score,level) {
    //    val db = this.writableDatabase
    //    val values = ContentValues()
    //    const val COLUMN_NAME_SCORE = "score" // The score achieved
    //    const val COLUMN_NAME_LEVEL = "level"
    //    db.insert(TABLE_NAME, null, values)
    //    db.close()
    //}

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



