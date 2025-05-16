package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient
import com.example.mapsapp.data.SupabaseManager

class SupabaseApplication: Application() {
    companion object{
        lateinit var database: MySupabaseClient
        lateinit var supabase: SupabaseManager
    }
    override fun onCreate() {
        super.onCreate()
        supabase = SupabaseManager()
        database = MySupabaseClient()
    }
}
