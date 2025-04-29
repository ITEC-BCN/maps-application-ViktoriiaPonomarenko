package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient


class MyApp: Application() {
    companion object {
        lateinit var database: MySupabaseClient
    }
    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = "https://ymqpurclsevkorrvtglt.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InltcXB1cmNsc2V2a29ycnZ0Z2x0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDU5MjM2MzcsImV4cCI6MjA2MTQ5OTYzN30.WeFYjM1yCmeU0dr1jDFTq8t28MfRb6DH_BjKktSW4bM"
        )
    }
}
