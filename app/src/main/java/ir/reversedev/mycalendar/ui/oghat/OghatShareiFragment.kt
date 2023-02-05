package ir.reversedev.mycalendar.ui.oghat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.tianma8023.model.Time
import com.github.tianma8023.ssv.SunriseSunsetView
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.databinding.FragmentOghatShareiBinding
import ir.reversedev.mycalendar.util.InternetChecker
import javax.inject.Inject

class OghatShareiFragment : DaggerFragment() {
    private lateinit var _binding: FragmentOghatShareiBinding
    private lateinit var viewModel: OghatShareiViewModel

    @Inject
    lateinit var viewModelFactory: OghatShareiViewModel.Factory

    @Inject
    lateinit var internetChecker: InternetChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[OghatShareiViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOghatShareiBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (internetChecker.checkForInternet()) {
            val mSunriseSunsetView = _binding.ssv as SunriseSunsetView

            _binding.imgInternetOghat.visibility = View.GONE
            with(_binding) {

                progressBarOghat.visibility = View.VISIBLE
                btnChooseCity.visibility = View.GONE
                fabAddCity.visibility = View.GONE
                cvOghatShargi.visibility = View.GONE
                cvSSV.visibility = View.GONE
            }
            Log.v("test", "test")



            viewModel.getOghat()
            viewModel.favorite.observe(this.viewLifecycleOwner) {
                it.ifNotHandled { favorite ->
                    _binding.btnChooseCity.text = favorite.name
                }
            }
            viewModel.oghat.observe(this.viewLifecycleOwner) { event ->
                event.ifNotHandled { oghatEntity ->
                    with(_binding) {
                        azanSobh.text = oghatEntity.result.azan_sobh
                        toluAftab.text = oghatEntity.result.tolu_aftab
                        azanZohr.text = oghatEntity.result.azan_zohr
                        ghorubAftab.text = oghatEntity.result.ghorub_aftab
                        azanMaghreb.text = oghatEntity.result.azan_maghreb
                        nimeshab.text = oghatEntity.result.nimeshab
                        val hourSunrise = oghatEntity.result.tolu_aftab.substring(0, 2)
                        val minSunrise = oghatEntity.result.tolu_aftab.substring(3, 5)
                        val hourSunset = oghatEntity.result.ghorub_aftab.substring(0, 2)
                        val minSunset = oghatEntity.result.ghorub_aftab.substring(3, 5)
                        mSunriseSunsetView.setSunriseTime(Time(hourSunrise.toInt(), minSunrise.toInt()))
                        mSunriseSunsetView.setSunsetTime(Time(hourSunset.toInt(), minSunset.toInt()))
                        mSunriseSunsetView.startAnimate()

                        progressBarOghat.visibility = View.GONE
                        btnChooseCity.visibility = View.VISIBLE
                        fabAddCity.visibility = View.VISIBLE
                        cvOghatShargi.visibility = View.VISIBLE
                        cvSSV.visibility = View.VISIBLE

                    }
                }
            }

            _binding.fabAddCity.setOnClickListener {
                findNavController().navigate(R.id.action_oghatSharghiFragment_to_chooseStateDialog)
            }
            _binding.btnChooseCity.setOnClickListener {
                findNavController().navigate(OghatShareiFragmentDirections.actionOghatSharghiFragmentToFavoriteDialog("oghat"))}
        } else {
            with(_binding){
                imgInternetOghat.visibility = View.VISIBLE
                progressBarOghat.visibility = View.GONE
                btnChooseCity.visibility = View.GONE
                fabAddCity.visibility = View.GONE
                cvOghatShargi.visibility = View.GONE
                cvSSV.visibility = View.GONE
            }
            Snackbar.make(requireContext(), view, "اینترنت شما متصل نیست !!!", Snackbar.LENGTH_LONG)
                .show()
        }

    }


}