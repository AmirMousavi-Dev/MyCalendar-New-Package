package ir.reversedev.mycalendar.ui.oghat.choosecity.state

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.reversedev.mycalendar.databinding.ItemChooseCityBinding

class StateAdapter (private val data : ArrayList<String> , private val chooseEvent: ChooseEvent) :
    RecyclerView.Adapter<StateAdapter.ChooseHolder>() {
    private lateinit var _binding : ItemChooseCityBinding
    inner class ChooseHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind (position: Int) {
            _binding.txtItemChoose.text = data[position]
            _binding.root.setOnClickListener {

                chooseEvent.clicked(data[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseHolder {
        val inflater = LayoutInflater.from(parent.context)
        _binding = ItemChooseCityBinding.inflate(inflater, parent , false)
        return ChooseHolder(_binding.root)
    }

    override fun onBindViewHolder(holder: ChooseHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData (newData :List<String>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    interface ChooseEvent {
        fun clicked (state: String)
    }
}