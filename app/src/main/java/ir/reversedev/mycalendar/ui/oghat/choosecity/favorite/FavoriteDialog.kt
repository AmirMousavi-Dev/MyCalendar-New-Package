package ir.reversedev.mycalendar.ui.oghat.choosecity.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.databinding.FavoriteDialogBinding
import ir.reversedev.mycalendar.model.data.FavoriteEntity
import ir.reversedev.mycalendar.ui.oghat.OghatShareiViewModel
import javax.inject.Inject

class FavoriteDialog : DialogFragment() , FavoriteAdapter.FavoriteEvent {
    private lateinit var _binding: FavoriteDialogBinding
    @Inject
    lateinit var viewModelFactory: OghatShareiViewModel.Factory
    lateinit var viewModel: OghatShareiViewModel
    private lateinit var adapter : FavoriteAdapter

    private val bundle by lazy {
        arguments ?: requireActivity().intent.extras!!
    }
    private val where by lazy {
        bundle.getString("where")
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
        )[OghatShareiViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FavoriteDialogBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialAdapter()
        viewModel.getAllFavorite()
        viewModel.favorites.observe(this.viewLifecycleOwner) {
            it.ifNotHandled { favorites ->
                adapter.setData(favorites)
            }
        }
    }

    override fun clicked(name: String) {
        viewModel.onSelected()
        viewModel.selected(name)
        if (where == "weather")
            findNavController().navigate(R.id.action_chooseCityDialog_to_weatherFragment)
        else if (where == "oghat")
            findNavController().navigate(R.id.action_favoriteDialog_to_oghatSharghiFragment)
    }

    override fun delete(favorite: FavoriteEntity, position: Int) {
        if (favorite.selected) {
            Toast.makeText(context, "شهر انتخاب شده را نمیتوان حذف کرد", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.deleteFavorite(favorite.name)
            adapter.delete(favorite , position)
        }
    }

    fun initialAdapter () {
        val data = arrayListOf<FavoriteEntity>()
        adapter = FavoriteAdapter(data , this)
        _binding.recyclerFavorite.adapter = adapter
        _binding.recyclerFavorite.layoutManager = LinearLayoutManager(context , RecyclerView.VERTICAL , false)
        _binding.recyclerFavorite.recycledViewPool.setMaxRecycledViews(0,0)
    }


}