package com.example.mapsapp.data

import com.google.android.gms.maps.model.LatLng
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from


class MySupabaseClient() {
    lateinit var client: SupabaseClient
    constructor(supabaseUrl: String, supabaseKey: String): this(){
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
        }
    }


    //SQL operations

    suspend fun getAllMarcadors(): List<Marcador> {
        return client.from("Marcador").select().decodeList<Marcador>()
    }

    suspend fun getMarcador(id: String): Marcador{
        return client.from("Student").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Marcador>()
    }

    suspend fun insertMarcador(marcador: Marcador){
        client.from("Marcador").insert(marcador)
    }
    suspend fun updateMarcador(id: String, titulo: String, descripcion: String, foto: String, coordenadas: LatLng){
        client.from("Marcador").update({
            set("titulo", titulo)
            set("descripcion", descripcion)
            set("foto", foto)
            set("coordenadas", coordenadas)
        }) { filter { eq("id", id) } }
    }
    suspend fun deleteMarcador(id: String){
        client.from("Marcador").delete{ filter { eq("id", id) } }
    }

}
