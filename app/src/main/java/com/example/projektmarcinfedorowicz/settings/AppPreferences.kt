package com.example.projektmarcinfedorowicz.settings

import android.app.Application
import android.content.Context.MODE_PRIVATE

class AppPreferences(private var application: Application) {

    private val sharedPreferences = application.getSharedPreferences("app_preferences", MODE_PRIVATE)

    var invitationText: String
        get() {
            val storedInvitationText = sharedPreferences.getString("invitation_text", "")
            return if (storedInvitationText.isNullOrBlank()) {
                "Hello there!"
            } else {
                storedInvitationText
            }
        }
        set(value) {
            sharedPreferences.edit().putString("invitation_text", value).apply()
        }

    var profilePicture: Int
        get() {
            return sharedPreferences.getInt("profile_picture", 1)
        }
        set(value) {
            sharedPreferences.edit().putInt("profile_picture", value).apply()
        }

    var nickname: String
        get() {
            val storedNickname = sharedPreferences.getString("nickname", "")
            return if (storedNickname.isNullOrBlank()) {
                "User"
            } else {
                storedNickname
            }
        }
        set(value) {
            sharedPreferences.edit().putString("nickname", value).apply()
        }

    fun reset() {
        sharedPreferences.edit().clear().apply()
    }

    fun getFirstLine(): String {
        return sharedPreferences.getString("invitation_text", "") ?: ""
    }

    fun getSecondLine(): String {
        return sharedPreferences.getString("nickname", "") ?: ""
    }

    fun resetPicture() {
        sharedPreferences.edit().putInt("profile_picture", 1).apply()
    }
}