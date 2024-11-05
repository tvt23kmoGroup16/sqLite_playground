package com.example.sqlite_playground.db

import android.provider.BaseColumns

object UserContract {

    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_ID = BaseColumns._ID //Inherits ID
        const val COLUMN_NAME_USERNAME = "user_name"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_PASSWORD_HASH = "password_hash"
        const val COLUMN_NAME_LAST_LOGIN = "last_login"
        const val COLUMN_NAME_SCORE = "score"
        const val COLUMN_NAME_LEVEL = "level"
    }
}