package ir.reversedev.mycalendar.ui.oghat.choosecity.state

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import ir.reversedev.mycalendar.databinding.FragmentChooseCityDialogBinding
import ir.reversedev.mycalendar.ui.oghat.OghatShareiViewModel
import javax.inject.Inject

class ChooseStateDialog : BottomSheetDialogFragment() ,StateAdapter.ChooseEvent {
    private lateinit var _binding: FragmentChooseCityDialogBinding

    lateinit var viewModel: OghatShareiViewModel
    private lateinit var adapter: StateAdapter
    @Inject
    lateinit var viewModelFactory: OghatShareiViewModel.Factory


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this.parentFragment as Fragment ,
            viewModelFactory
        ) [OghatShareiViewModel::class.java]

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChooseCityDialogBinding.inflate(inflater, container, false)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialAdapter()
        viewModel.getState()
        val data = viewModel.state
        data.observe(this.viewLifecycleOwner) {
            it.ifNotHandled {
                MainThread().run {
                    adapter.setData(it)
                    Log.v("test" , it.size.toString())
                    Log.v("test" , it.toString())

                }
            }
        }
    }

    private fun initialAdapter() {
        val data = arrayListOf<String>()
        adapter = StateAdapter(data,this)
        _binding.recyclerChooseCity.adapter = adapter
        _binding.recyclerChooseCity.layoutManager = LinearLayoutManager(context , RecyclerView.VERTICAL , false)
        _binding.recyclerChooseCity.recycledViewPool.setMaxRecycledViews(0,0)
    }

    override fun clicked(state: String) {
        findNavController().navigate(ChooseStateDialogDirections.actionWeatherFragmentToChooseCityDialog(state))

    }

}