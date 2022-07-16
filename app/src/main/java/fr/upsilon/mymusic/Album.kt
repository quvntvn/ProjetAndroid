package fr.upsilon.mymusic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.upsilon.mymusic.model.Network
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_album.*
import kotlinx.android.synthetic.main.titles_cell.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class Album : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        val albumImage = findViewById<ImageView>(R.id.albumImage)
        val backroundImage = findViewById<ImageView>(R.id.albumImageBackground)
        val artistName = findViewById<TextView>(R.id.artistName)
        val name = findViewById<TextView>(R.id.name)
        val note = findViewById<TextView>(R.id.note)
        val votes = findViewById<TextView>(R.id.votes)
        val description = findViewById<TextView>(R.id.description)
        val info = findViewById<TextView>(R.id.info)

        val backLogo = findViewById<ImageView>(R.id.backLogo)
        backLogo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        val favorisLogo = findViewById<ImageView>(R.id.favorisLogo)
        favorisLogo.setOnClickListener {
            favorisLogo.setImageResource(R.drawable.ic_like_on)
        }

        var annonceArray: MutableList<Title> = ArrayList()

        val album_id = intent.getStringExtra("id")

        GlobalScope.launch(Dispatchers.Default) {
            try {
                val dataRes = Network.api.getAlbumByIDDataAsync(album_id.toString()).await()
                withContext(Dispatchers.Main) {
                    dataRes.content.forEach {
                        artistName.text = it.strArtist.toString()
                        name.text = it.strAlbum.toString()
                        Picasso.get().load(it.strAlbumThumb.toString()).into(albumImage);
                        Picasso.get().load(it.strAlbumThumb.toString()).into(backroundImage);
                        note.text = it.intScore
                        if(it.intScoreVotes == "null"){
                            votes.text = "pas de votes"
                        }else{
                            votes.text = it.intScoreVotes + " votes"
                        }
                        description.text = it.strDescriptionEN

                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                }
            }
            try {
                val dataRes2 = Network.api.getTrackByAlbumIdDataAsync(album_id.toString()).await()
                withContext(Dispatchers.Main) {
                    dataRes2.content.forEach {
                        annonceArray.add(
                                Title(it.idTrack, it.strTrack)
                        )
                    }
                    list.layoutManager = LinearLayoutManager(this@Album)
                    list.adapter = TitleAdapter(
                            titles = annonceArray
                    )
                    info.text = annonceArray.count().toString() + " chansons"
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                }
            }
        }


    }
}

data class Title(
    val id: String? = null,
    val title: String? = null
) : java.io.Serializable {
}
class TitleAdapter(private val titles: List<Title>) :
    RecyclerView.Adapter<TitleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.titles_cell, parent, false
            )
        )
    }
    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.updateItem(
            position = position + 1,
            task = titles[position]
        )
    }

}



class TitleViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val titleTextView: TextView = v.titreCell
    val indexTextView: TextView = v.indexCell
    var titleID: String = ""



    init {
        titleTextView.setOnClickListener {
            val intent = Intent(v.context, MainActivity::class.java)
            GlobalScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    intent.putExtra("idTitle", titleID);
                    v.context.startActivity(intent)
                }
            }

        }
    }

    fun updateItem(position: Int, task: Title){
        titleTextView.text = task.title.toString()
        indexTextView.text = position.toString()

    }

}