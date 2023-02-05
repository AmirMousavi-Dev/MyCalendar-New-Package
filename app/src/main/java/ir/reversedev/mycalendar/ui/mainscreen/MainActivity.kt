package ir.reversedev.mycalendar.ui.mainscreen

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.android.support.DaggerAppCompatActivity
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.databinding.ActivityMainBinding
import ir.reversedev.mycalendar.util.CalendarService
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mainScreenViewModel: MainScreenViewModel

    private lateinit var _binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startCalendarService()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this ,
            _binding.drawerLayoutMain ,
            _binding.toolBarMain ,
            R.string.open_drawer ,
            R.string.close_drawer
        )
        _binding.drawerLayoutMain.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        _binding.navigationViewMain.setNavigationItemSelectedListener {
            when(it.itemId)  {
                R.id.menu_aboutUs -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("bazaar://details?id=" + "com.example.mycalendar")
                    intent.setPackage("com.farsitel.bazaar")
                    startActivity(intent)
                }
                R.id.menu_ourWebsite -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("bazaar://collection?slug=by_author&aid=reverse_developer")
                    intent.setPackage("com.farsitel.bazaar")
                    startActivity(intent)
                }
                R.id.menu_rattingUs -> {
                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.data = Uri.parse("bazaar://details?id=" + "com.example.mycalendar")
                    intent.setPackage("com.farsitel.bazaar")
                    startActivity(intent)
                }
            }
            true
        }
        obtainNavHost()

        NavigationUI.setupWithNavController(
            _binding.bottomNavigationView,
            obtainNavHost().navController
        )

        obtainNavHost().navController.addOnDestinationChangedListener { _, destination, _ ->
            _binding.bottomNavigationView.visibility = when (destination.id) {
                R.id.dateConverterFragment ,
                R.id.weatherFragment,
                R.id.calendarFragment,
                R.id.oghatSharghiFragment,
                R.id.timeAndDateFragment ->
                {
                    _binding.toolBarMain.title = destination.label
                    View.VISIBLE
                }
                else ->
                    View.GONE

            }
        }


        mainScreenViewModel.insertCities()

    }


    private fun obtainNavHost(): NavHostFragment {
        return supportFragmentManager.findFragmentByTag("CalendarNavHost") as? NavHostFragment
            ?: NavHostFragment.create(R.navigation.nav_main).also {
                supportFragmentManager.beginTransaction()
                    .add(_binding.fragmentContainerView.id, it, "CalendarNavHost")
                    .commitNow()

            }
    }
    private fun startCalendarService() {
        val intent = Intent(this , CalendarService::class.java)
        startService(intent)
    }

}