package dev.eagelware.phonex

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.eagleware.Phonex

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver{
        return AndroidSqliteDriver(Phonex.Schema, context, "Qlin.db")
    }
}