package ir.reversedev.mycalendar.ui.calendar.note

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.databinding.NoteDialogBinding
import ir.reversedev.mycalendar.model.data.NoteEntity
import ir.reversedev.mycalendar.ui.calendar.CalendarViewModel
import javax.inject.Inject

class NoteDialog : DialogFragment() {
    private lateinit var _binding: NoteDialogBinding

    @Inject
    lateinit var viewModelFactory: CalendarViewModel.Factory
    private lateinit var viewModel: CalendarViewModel
    private val bundle by lazy {
        arguments ?: requireActivity()
            .intent.extras!!
    }
    private val year by lazy {
        bundle.getInt("year")
    }
    private val month by lazy {
        bundle.getInt("month")
    }
    private val day by lazy {
        bundle.getInt("day")
    }
    private val note by lazy {
        bundle.getParcelable("note") as NoteEntity?
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this.parentFragment as Fragment,
            viewModelFactory
        )[CalendarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = NoteDialogBinding.inflate(inflater, container, false)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true

        _binding.edtTitleNote.requestFocus()
        if (note != null) {
            val title = note!!.title
            val detail = note!!.detail
            val id = note!!.id
            with(_binding) {
                edtTitleNote.setText(title)
                edtDetailNote.setText(detail)
                btnConfirm.setOnClickListener {
                    Log.v("test" , id.toString())
                    if (edtTitleNote.text != null &&edtDetailNote.text != null) {
                        val newTitle = edtTitleNote.text.toString()
                        val newDetail = edtDetailNote.text.toString()
                        viewModel.updateNote(newTitle, newDetail, id!!)
                        findNavController().navigate(R.id.action_note_dialog_to_calendarFragment)
                        //findNavController().navigate(R.id.action_note_dialog_to_calendarFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "لطفا مقادیر را کامل وارد کنید",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        } else {
            _binding.btnConfirm.setOnClickListener {
                val title = _binding.edtTitleNote.text.toString()
                val detail = _binding.edtDetailNote.text.toString()
                if (title.isNotEmpty() && detail.isNotEmpty()) {

                    viewModel.insertNote(title, detail, year, month, day)
                    findNavController().navigate(R.id.action_note_dialog_to_calendarFragment)
                    //findNavController().navigate(R.id.action_note_dialog_to_calendarFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "لطفا مقادیر را کامل وارد کنید",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
        _binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_note_dialog_to_calendarFragment)
        }
    }

}