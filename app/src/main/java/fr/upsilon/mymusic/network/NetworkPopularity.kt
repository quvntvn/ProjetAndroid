package fr.upsilon.mymusic.network

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// fonctionne
// /*Return The top 10 Most Loved tracks for an Artist Name *Patreon ONLY*
//theaudiodb.com/api/v1/json/523532/track-top10.php?s=coldplay/

// data Album
data class ArtistTopTrackData(
        @SerializedName("track")
        val content: List<ArtistTopTrackContent>,
)
// data pour l'écran classement onglet Titres
// url :
data class ArtistTopTrackContent(
        val idTrack: String,
        val idAlbum:String,
        val idArtist: String,
        val strTrack: String,
        val strAlbum: String,
        val strArtist: String,
)

interface APIArtistToptrack {
    // get album par artiste
    @GET("track-top10.php") //coldplay({artistid}
    fun getArtistsTopByNameDataAsync(@Query("s")value:String): Deferred<ArtistTopTrackData> // albumId:String


}


object NetworkArtistToptrack {

    val api = Retrofit.Builder()
            .baseUrl("https://theaudiodb.com/api/v1/json/523532/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(APIArtistToptrack::class.java)

}