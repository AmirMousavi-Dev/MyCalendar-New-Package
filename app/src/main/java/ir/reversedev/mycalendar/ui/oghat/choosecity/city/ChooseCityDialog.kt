package ir.reversedev.mycalendar.ui.oghat.choosecity.city

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.databinding.FragmentChooseCityDialogBinding
import ir.reversedev.mycalendar.model.data.CitiesEntity
import ir.reversedev.mycalendar.model.data.FavoriteEntity
import ir.reversedev.mycalendar.ui.oghat.OghatShareiViewModel
import javax.inject.Inject

class ChooseCityDialog : BottomSheetDialogFragment() , CityAdapter.CityEvent {
    lateinit var _binding : FragmentChooseCityDialogBinding
    @Inject
    lateinit var viewModelFactory : OghatShareiViewModel.Factory
    lateinit var viewModel: OghatShareiViewModel
    lateinit var adapter :CityAdapter
    private val bundle by lazy {
        arguments ?: requireActivity().intent.extras!!
    }
    private val state by lazy {
        bundle.getString("state")
    }

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
        _binding = FragmentChooseCityDialogBinding.inflate(inflater , container , false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialAdapter()
        viewModel.getCities(state!!)
        viewModel.cities.observe(this.viewLifecycleOwner){
            it.ifNotHandled {
                adapter.setData(it)

            }
        }
    }

    private fun initialAdapter() {
        val data = arrayListOf<CitiesEntity.CitiesEntityItem>()
        adapter = CityAdapter(data , this)
        _binding.recyclerChooseCity.adapter = adapter
        _binding.recyclerChooseCity.layoutManager = LinearLayoutManager(context)
        _binding.recyclerChooseCity.recycledViewPool.setMaxRecycledViews(0,0)
    }

    override fun Clicked(city: CitiesEntity.CitiesEntityItem) {
        viewModel.onSelected()
        viewModel.insertFavorite(FavoriteEntity(city.City , city.lat , city.lon))
        findNavController().navigate(R.id.action_chooseCityDialog_to_oghatSharghiFragment)
    }

}