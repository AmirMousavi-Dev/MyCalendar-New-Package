package ir.reversedev.mycalendar.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.reversedev.mycalendar.databinding.ItemNoteBinding
import ir.reversedev.mycalendar.model.data.NoteEntity

class NoteAdapter(private val data: ArrayList<NoteEntity>, private val noteEvent: NoteEvent) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    private lateinit var _binding: ItemNoteBinding

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(noteEntity: NoteEntity , position: Int) {
            val title = noteEntity.title
            val detail = noteEntity.detail
            val year = noteEntity.year
            val month = noteEntity.month
            val day = noteEntity.day
            with(_binding) {
                titleItemNote.text = title
                txtDateItemNote.text = year.toString().setDate(month , day)
                imgDeleteNote.setOnClickListener { noteEvent.deleteClicked(noteEntity , position) }
                imgEditNote.setOnClickListener { noteEvent.editClicked(noteEntity) }
            }

        }
    }
    fun delete (noteEntity: NoteEntity ,position: Int) {
        data.remove(noteEntity)
        notifyItemRemoved(position)
    }

    fun setData (newData : List<NoteEntity>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        _binding = ItemNoteBinding.inflate(inflater, parent, false)
        return NoteHolder(_binding.root)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(data[position] , position)
    }

    override fun getItemCount(): Int = data.size

    interface NoteEvent {
        fun editClicked(noteEntity: NoteEntity)
        fun deleteClicked(noteEntity: NoteEntity, position: Int)
    }
    private val setDate : String.(Int , Int) -> String = {month , day ->
        this +"/"+month+"/"+day
    }
}