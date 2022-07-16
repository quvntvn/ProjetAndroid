package fr.upsilon.mymusic.ui.artisteDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.upsilon.mymusic.R


class ArtisteDetailFragment : Fragment(){
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artiste_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }
}