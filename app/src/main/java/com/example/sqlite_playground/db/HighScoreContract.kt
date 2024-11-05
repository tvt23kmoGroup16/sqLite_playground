package com.example.sqlite_playground.db

import android.provider.BaseColumns

object HighScoreContract {
    object HighScoreEntry : BaseColumns {
        const val TABLE_NAME = "high_scores"
        const val COLUMN_NAME_ID = BaseColumns._ID // Inherits ID, PK
        const val COLUMN_NAME_USER_ID = "user_id" // Foreign key referencing users
        const val COLUMN_NAME_SCORE = "score" // The score achieved
        const val COLUMN_NAME_LEVEL = "level" // Level of achieved score
        const val COLUMN_NAME_DATE = "date" // Date of score
    }
}