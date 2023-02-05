package ir.reversedev.mycalendar.ui.oghat.choosecity.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.reversedev.mycalendar.databinding.ItemChooseCityBinding
import ir.reversedev.mycalendar.model.data.CitiesEntity

class CityAdapter
    (private val data: ArrayList<CitiesEntity.CitiesEntityItem>,
     private val cityEvent: CityEvent
) :
    RecyclerView.Adapter<CityAdapter.CityHolder>() {
    private lateinit var _binding: ItemChooseCityBinding

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: CitiesEntity.CitiesEntityItem) {
            _binding.txtItemChoose.text = city.City
            _binding.root.setOnClickListener { cityEvent.Clicked(city) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val inflater = LayoutInflater.from(parent.context)
        _binding = ItemChooseCityBinding.inflate(inflater, parent, false)
        return CityHolder(_binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData: List<CitiesEntity.CitiesEntityItem>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    interface CityEvent {
        fun Clicked(city: CitiesEntity.CitiesEntityItem)
    }
}