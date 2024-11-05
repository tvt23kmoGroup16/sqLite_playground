package com.example.sqlite_playground.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "UserDatabase.db"
        const val DATABASE_VERSION = 1 //version control
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
}