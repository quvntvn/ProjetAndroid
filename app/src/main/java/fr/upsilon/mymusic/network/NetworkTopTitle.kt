package fr.upsilon.mymusic.network

import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Endpoint GET FranceLiveGlobalData trending
// Base URL = https://coronavirusapi-france.vercel.app/  https://theaudiodb.com/
// {"FranceGlobalLiveData":[{"code":"FRA","nom":"France","date":"2021-07-20","hospitalises":6912,"reanimation":876,"nouvellesHospitalisations":327,"nouvellesReanimations":61,"deces":85020,"gueris":388118,"source":{"nom":"OpenCOVID19-fr"},"sourceType":"opencovid19-fr"}]}

// data list pour l'écran classement onglet Titres
data class TopTitleSongData(
        @SerializedName("trending")
        val content: List<TopTitleDataContent>,
)
// data pour l'écran classement onglet Titres
// url : https://theaudiodb.com/api/v1/json/523532/trending.php?country=us&type=itunes&format=singles
data class TopTitleDataContent(
        val idTrend: String,
        val intChartPlace: String,
        val strArtist: String,
        val strAlbum: String,
        val strTrackThumb: String,
        val strTrack: String,
)

interface APITitreClassement {
    // get pour l'écran classement onglet Titres
    @GET("trending.php?country=us&type=itunes&format=singles")
    fun getTopTitleDataAsync(): Deferred<TopTitleSongData>
}


object NetworkTopTitle {

    val api = Retrofit.Builder()
            .baseUrl("https://theaudiodb.com/api/v1/json/523532/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(APITitreClassement::class.java)

}
