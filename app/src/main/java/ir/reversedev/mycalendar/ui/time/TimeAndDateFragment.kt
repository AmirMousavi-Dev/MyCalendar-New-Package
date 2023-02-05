package ir.reversedev.mycalendar.ui.time

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import ir.reversedev.mycalendar.databinding.FragmentTimeAndDateBinding
import ir.reversedev.mycalendar.util.InternetChecker
import javax.inject.Inject

class TimeAndDateFragment() : DaggerFragment() {
    private lateinit var _binding: FragmentTimeAndDateBinding
    @Inject
    lateinit var viewModelFactory : TimeAndDateViewModel.Factory
    @Inject
    lateinit var internetChecker: InternetChecker
    @Inject
    lateinit var viewModel : TimeAndDateViewModel

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this , viewModelFactory) [TimeAndDateViewModel::class.java]
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimeAndDateBinding.inflate(inflater, container, false)
        return _binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMonasebat(view)
        viewModel.liveTime.observe(this.viewLifecycleOwner) {
            _binding.txtTimeTD.text = it
        }
        setDate()

    }

    private fun setDate () {
        val persian = viewModel.persianDate()
        val hijri = viewModel.hijriDate()
        val civil = viewModel.civilDate()
        with(_binding) {

            txtNumberShamsiTD.text = persian[0]
            txtMonthShamsiTD.text = persian[1]
            txtYearShamsiTD.text = persian[2]
            txtDayShamsiTD.text = persian[3]

            txtNumberGhamariTD.text = hijri[0]
            txtMonthGhamariTD.text = hijri[1]
            txtYearGhamariTD.text = hijri[2]
            txtDayGhamariTD.text = hijri[3]

            txtNumberMiladiTD.text = civil[0]
            txtMonthMiladiTD.text = civil[1]
            txtYearMiladiTD.text = civil[2]
            txtDayMiladiTD.text = civil[3]
        }
    }
    private fun showMonasebat (view: View) {
        if (internetChecker.checkForInternet()) {
            viewModel.getMonasebat()
            viewModel.monasebat.observe(this.viewLifecycleOwner) {
                it.ifNotHandled { monasebat ->
                    _binding.animMonasebat.visibility = View.GONE
                    _binding.txtTitleMonasebat.text = monasebat.occasion
                }
            }
        } else {
            Snackbar.make(requireContext(),view,"اینترنت شما متصل نیست", Snackbar.LENGTH_SHORT)
                .setAction("تلاش مجدد"){
                    showMonasebat(view)
                }
                .show()
        }
    }

}