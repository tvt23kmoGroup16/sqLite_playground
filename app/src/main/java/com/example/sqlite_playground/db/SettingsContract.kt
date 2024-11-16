package com.example.sqlite_playground.db

import android.provider.BaseColumns

object SettingsContract {
        object SettingsEntry : BaseColumns {
            const val TABLE_NAME = "settings"
            const val COLUMN_NAME_ID = BaseColumns._ID // Inherits ID, this always PK
            const val COLUMN_NAME_USER_ID = "user_id" // Foreign key referencing users
            const val COLUMN_NAME_MUSIC_ENABLED = "music_enabled" // Boolean for music settings
        }
}
