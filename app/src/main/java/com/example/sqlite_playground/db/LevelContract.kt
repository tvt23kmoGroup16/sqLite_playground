package com.example.sqlite_playground.db

import android.provider.BaseColumns

object LevelContract {
    object LevelEntry : BaseColumns {
        const val TABLE_NAME = "levels"
        const val COLUMN_NAME_ID = BaseColumns._ID // Inherits ID
        const val COLUMN_NAME_LEVEL_NUMBER = "level_number" // e.g., 1, 2, 3, ...
        const val COLUMN_NAME_LEVEL_NAME = "level_name" // Name of the level, for example "Easter Garden"
        const val COLUMN_NAME_DIFFICULTY = "difficulty" // Difficulty rating
        const val COLUMN_NAME_REQUIRED_SCORE = "required_score" // Score needed to unlock level
    }
}