package fr.upsilon.mymusic.model


import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
        val strDescriptionEN: String,

        /*val strTrackThumb: String,
        val idTrack: String,
        val idArtist: String,
        val idAlbum: String,*/

    //nesteadedScrollview
)

data class AlbumDataResume(
        val strAlbum: String,
        val intYearReleased: String,

        /*val strTrackThumb: String,
        val idTrack: String,
        val idArtist: String,
        val idAlbum: String,*/
)

// list des track pour ecran de l'album
data class TrackSongData(
        @SerializedName("track")
        val content: List<TrackDataContent>,
)
// data pour l'écran classement onglet Titres
data class TrackDataContent(
        val idTrack: String,
        val strTrack: String,
        val intTrackNumber: String,

        /*val strTrackThumb: String,
        val idTrack: String,
        val idArtist: String,
        val idAlbum: String,*/
)

interface API {
    // get album par artiste
    @GET("album.php") //2115888({albumid}
    fun getAlbumByIDDataAsync(@Query("m")value:String): Deferred<AlbumData> // albumid:String

    // get pour l'écran classement onglet Titres
    @GET("track.php") //2115888{albumID}
    fun getTrackByAlbumIdDataAsync(@Query("m")value:String): Deferred<TrackSongData>


}

object Network {
    val api = Retrofit.Builder()
        .baseUrl("https://www.theaudiodb.com/api/v1/json/523532/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(API::class.java)
}

