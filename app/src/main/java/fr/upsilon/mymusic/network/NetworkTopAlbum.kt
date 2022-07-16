package fr.upsilon.mymusic.network

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// fonctionne

// data list pour l'écran classement onglet Albums
data class TopAlbumData(
        @SerializedName("trending")
        val content: List<TopAlbumDataContent>,
)

// data pour l'écran classement onglet Albums
// url : https://theaudiodb.com/api/v1/json/523532/trending.php?country=us&type=itunes&format=albums
data class TopAlbumDataContent(
        val intChartPlace: String,
        val strArtist: String,
        val idAlbum: String,
        val strAlbum: String,
        val strAlbumThumb: String,
        /*val idAlbum: String,*/
)

interface APIAlbumClassement {
    // get pour l'écran classement onglet Albums
    @GET("trending.php?country=us&type=itunes&format=albums")
    fun getTopAlbumDataAsync(): Deferred<TopAlbumData>
}


object NetworkTopAlbum{

    val api = Retrofit.Builder()
            .baseUrl("https://theaudiodb.com/api/v1/json/523532/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(APIAlbumClassement::class.java)

}
