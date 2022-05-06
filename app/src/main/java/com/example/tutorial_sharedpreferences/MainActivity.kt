package com.example.tutorial_sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tutorial_sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

    private val preferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == TEXT_VALUE)
            binding.tvCurrentValue.text = preferences.getString(key, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val value = preferences.getString(TEXT_VALUE, "")
        with(binding) {
            edValue.setText(value)
            tvCurrentValue.text = value
            btnSave.setOnClickListener{
                val newValue = edValue.text.toString()
                preferences.edit()
                    .putString(TEXT_VALUE, newValue)
                    .apply()
            }
        }
        preferences.registerOnSharedPreferenceChangeListener(preferencesListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.unregisterOnSharedPreferenceChangeListener(preferencesListener)
    }
}