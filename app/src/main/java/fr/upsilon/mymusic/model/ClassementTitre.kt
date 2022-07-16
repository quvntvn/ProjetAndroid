package fr.upsilon.mymusic.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClassementTitre (
        val strAlbum: String,
        val strArtist: String,
        val intChartPlace: Int,
        val strAlbumThumb: String,
) : Parcelable

fun generateFakeTitre() = ClassementTitre(
        strAlbum = "Gucci Gang",
        strArtist = "Lil Pump",
        intChartPlace = 1,
        strAlbumThumb = "https://via.placeholder.com/150"
)
