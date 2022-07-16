package fr.upsilon.mymusic.network

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// fonctionne
// requete get pour album by idArtist theaudiodb.com/api/v1/json/523532/album.php?m=2115888
// Return all Album details from artist name  theaudiodb.com/api/v1/json/523532/searchalbum.php?s=daft_punk
// Return Discography for an Artist with Album names and year only theaudiodb.com/api/v1/json/523532/discography.php?s=coldplay

// data Album
data class AlbumData(
        @SerializedName("album")
        val content: List<AlbumDataContent>,
)

data class AlbumDataNameYears(
        @SerializedName("album")
        val content: List<AlbumDataResume>,
)

// data pour l'écran classement onglet Titres
// url :
data class AlbumDataContent(
        val idArtist: String,
        val strAlbum: String,
        val strGenre: String,
        val strArtist:String,
        val intYearReleased: String,
        val strAlbumThumb: String,
        val intScore: String,
        val intScoreVotes: String,
        val strDescriptionEN: String

        /*val strTrackThumb: String,
        val idTrack: String,
        val idArtist: String,
        val idAlbum: String,*/
)

data class AlbumDataResume(
        val strAlbum: String,
        val intYearReleased: String,

        /*val strTrackThumb: String,
        val idTrack: String,
        val idArtist: String,
        val idAlbum: String,*/
)

interface APIAlbum {
    // get album par artiste
    @GET("album.php") //2115888({albumid}
    fun getAlbumByIDDataAsync(@Query("m")value:String): Deferred<AlbumData> // albumid:String

    @GET("discography.php") // coldplay({artistName}
    fun getAlbumNameYearsByArtisteDataAsync(@Query("s")value:String): Deferred<AlbumDataNameYears>

    @GET("searchalbum.php") // daft_punk{artistName}
    fun getAlbumByAtisteName(@Query("s")value:String): Deferred<AlbumData>
}


object NetworkAlbum {

    val api = Retrofit.Builder()
            .baseUrl("https://theaudiodb.com/api/v1/json/523532/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(APIAlbum::class.java)

}
