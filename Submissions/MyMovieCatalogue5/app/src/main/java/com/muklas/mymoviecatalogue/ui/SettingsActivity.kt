package com.muklas.mymoviecatalogue.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.muklas.mymoviecatalogue.alarm.AlarmReceiver
import com.muklas.mymoviecatalogue.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "SettingPref"
        private const val DAILY = "daily"
        private const val RELEASE = "release"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.settings)

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setSwitch()

        btnLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        swDaily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setDailyReminder(
                    this,
                    AlarmReceiver.TYPE_DAILY,
                    getString(R.string.daily_message)
                )
            } else {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_DAILY)
            }
            saveChange(DAILY, isChecked)
        }

        swRelease.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setReleaseReminder(this, AlarmReceiver.TYPE_RELEASE)
            } else {
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_RELEASE)
            }
            saveChange(RELEASE, isChecked)
        }
    }

    private fun setSwitch() {
        swDaily.isChecked = mSharedPreferences.getBoolean(DAILY, false)
        swRelease.isChecked = mSharedPreferences.getBoolean(RELEASE, false)
    }

    private fun saveChange(key: String, value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()

        return super.onOptionsItemSelected(item)
    }
}
