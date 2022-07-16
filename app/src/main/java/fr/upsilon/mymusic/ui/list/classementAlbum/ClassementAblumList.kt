package fr.upsilon.mymusic.ui.list.classementAlbum

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.upsilon.mymusic.Album
import fr.upsilon.mymusic.R
import fr.upsilon.mymusic.network.NetworkTopAlbum
import fr.upsilon.mymusic.network.TopAlbumDataContent
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.fragment_classement_album_list.view.*
import kotlinx.android.synthetic.main.titre_item_cell.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClassementAblumList : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_classement_album_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.classementAlbumList.layoutManager = LinearLayoutManager(requireContext())

        GlobalScope.launch(Dispatchers.Default) {
            val dataTopAlbum = NetworkTopAlbum.api.getTopAlbumDataAsync().await()
            print(dataTopAlbum)

            withContext(Dispatchers.Main) {
                // Donner les données à la liste
                view.classementAlbumList.adapter = TopAlbumAdapter(
                        albums = dataTopAlbum.content.sortedBy { it.intChartPlace.toInt() }
                )
            }
        }
    }
}

class TopAlbumAdapter(private val albums: List<TopAlbumDataContent>) :

        RecyclerView.Adapter<AlbumViewHolder>() {

    companion object {

        const val TYPE_TITLE = 0
        const val TYPE_ARTIST = 1
        const val TYPE_ALBUM = 2

    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.titre_item_cell, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.updateItem(
                oneAlbum = albums[position]
        )
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_TITLE
        } else if (position == 1) {
            return TYPE_ARTIST
        } else {
            return TYPE_ALBUM
        }
    }

}

// Une cellule
class AlbumViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var viewTitre: TextView = v.titreCellTitre
    val viewArtist: TextView = v.titreCellArtiste
    val viewThumbnail: ImageView = v.titreCellImage
    val viewCellNumber: TextView = v.titreCellNumber

    var albumId = ""
    init {
        viewTitre.setOnClickListener {
            val intent = Intent(v.context, Album::class.java)
            GlobalScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    intent.putExtra("id", albumId);
                    v.context.startActivity(intent)
                }
            }
        }
    }

    fun updateItem(oneAlbum: TopAlbumDataContent) {

        viewTitre.text = oneAlbum.strAlbum
        viewArtist.text = oneAlbum.strArtist
        viewCellNumber.text = oneAlbum.intChartPlace

        this.albumId = oneAlbum.idAlbum


        val transformation: Transformation = RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .cornerRadiusDp(5f)
                .oval(false)
                .build()

        Picasso.get().load(oneAlbum.strAlbumThumb   )
                .fit()
                .transform(transformation)
                .into(viewThumbnail)

    }

}
