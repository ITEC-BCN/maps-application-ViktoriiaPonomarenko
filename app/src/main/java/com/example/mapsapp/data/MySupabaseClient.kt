package com.example.mapsapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mapsapp.BuildConfig
import com.google.android.gms.maps.model.LatLng
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MySupabaseClient {
    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY
    lateinit var client: SupabaseClient
    lateinit var storage: Storage


    constructor() {
        client = createSupabaseClient(supabaseUrl = supabaseUrl, supabaseKey = supabaseKey) {
            install(Postgrest)
            install(Storage)
        }
        storage = client.storage
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images").upload(path = "image_${fechaHoraActual.format(formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) = "${this.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"


    suspend fun updateMarcador(id: String, titulo: String, descripcion: String, fotoUrl: String) {
        client.from("Marcador").update({
            set("titulo", titulo)
            set("descripcion", descripcion)
            set("foto", fotoUrl)
        }) {
            filter { eq("id", id) }
        }
    }



    //SQL operations

    suspend fun getAllMarcadores(): List<Marcador> {
        return client.from("Marcador").select().decodeList<Marcador>()
    }

    suspend fun getMarcador(id: String): Marcador{
        return client.from("Marcador").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Marcador>()
    }

    suspend fun insertMarcador(marcador: Marcador){
        client.from("Marcador").insert(marcador)
    }


    suspend fun deleteMarcador(id: String){
        client.from("Marcador").delete{ filter { eq("id", id) } }
    }

}
