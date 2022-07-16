package fr.upsilon.mymusic.network

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// fonctionne
// requete get pour artist by :
// idArtist, return details artist by id theaudiodb.com/api/v1/json/523532/artist.php?i=112024
// Return Artist details from artist name theaudiodb.com/api/v1/json/523532/search.php?s=coldplay


// data artist
data class ArtistData(
        @SerializedName("artists")
        val content: List<ArtistDataContent>,
)
// data pour l'écran classement onglet Titres
// url :
data class ArtistDataContent(
        val idArtist: String,
        val strArtist:String,
        val strGenre: String,
        val strBiographyEN: String,
        val strCountry: String,
        val strArtistThumb: String,


        /*val strTrackThumb: String,
        val idTrack: String,
        val idArtist: String,
        val idAlbum: String,*/
)

interface APIArtists {
    // get album par artiste
    @GET("artist.php") //112024({artistid}
    fun getArtistsByIDDataAsync(@Query("i")value:String): Deferred<ArtistData> // albumId:String

    @GET("search.php") //coldplay({artistName}
    fun getArtistsDetailsByArtistNameDataAsync(@Query("s")value:String): Deferred<ArtistData> // artistName:String

}


object NetworkArtist {

    val api = Retrofit.Builder()
            .baseUrl("https://theaudiodb.com/api/v1/json/523532/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(APIArtists::class.java)

}
