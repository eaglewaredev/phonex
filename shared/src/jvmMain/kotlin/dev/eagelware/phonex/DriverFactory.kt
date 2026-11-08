package dev.eagelware.phonex

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.eagleware.Phonex

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:Qlin.db")
        Phonex.Schema.create(driver)
        return driver
    }
}