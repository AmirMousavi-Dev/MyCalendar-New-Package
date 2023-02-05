package ir.reversedev.mycalendar.ui.oghat.choosecity.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.reversedev.mycalendar.databinding.ItemFavoriteBinding
import ir.reversedev.mycalendar.model.data.FavoriteEntity

class FavoriteAdapter(private val data: ArrayList<FavoriteEntity>, private val favoriteEvent: FavoriteEvent) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    private lateinit var _binding : ItemFavoriteBinding
    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind (favorite : FavoriteEntity, position: Int) {
            _binding.txtNameFavorite.text = favorite.name
            _binding.imgDeleteFavorite.setOnClickListener {
                favoriteEvent.delete(favorite ,position )
            }
            _binding.root.setOnClickListener {
                favoriteEvent.clicked(favorite.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val inflater = LayoutInflater.from(parent.context)
        _binding = ItemFavoriteBinding.inflate(inflater , parent , false)
        return FavoriteHolder(_binding.root)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bind(data[position] , position)
    }

    override fun getItemCount(): Int = data.size

    fun setData (newData :List<FavoriteEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    fun delete (favorite: FavoriteEntity, position: Int) {
        data.remove(favorite)
        notifyItemRemoved(position)
    }

    interface FavoriteEvent {
        fun clicked (name :String)
        fun delete (favorite: FavoriteEntity, position: Int)
    }
}