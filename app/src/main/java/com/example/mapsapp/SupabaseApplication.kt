package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient

class SupabaseApplication: Application() {
    companion object{
        lateinit var supabase: MySupabaseClient
      //  lateinit var supabase: SupabaseManager
    }
    override fun onCreate() {
        super.onCreate()
      //  supabase = SupabaseManager()
        supabase = MySupabaseClient()
    }
}
