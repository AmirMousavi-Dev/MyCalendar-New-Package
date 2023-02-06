package ir.reversedev.mycalendar.ui.mainscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.android.support.DaggerAppCompatActivity
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.databinding.ActivityMainBinding
import ir.reversedev.mycalendar.util.CalendarService
import ir.reversedev.mycalendar.util.Constant.Companion.TAP_SELL_KEY
import ir.reversedev.mycalendar.util.Constant.Companion.ZONE_ID_REWARDED_VIDEO
import ir.reversedev.mycalendar.util.Constant.Companion.ZONE_ID_STANDARD_BANNER
import ir.tapsell.plus.*
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var mainScreenViewModel: MainScreenViewModel
    private lateinit var _binding: ActivityMainBinding
    private var showAdsCounter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startCalendarService()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initialTapSell()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            _binding.drawerLayoutMain,
            _binding.toolBarMain,
            R.string.open_drawer,
            R.string.close_drawer
        )
        _binding.drawerLayoutMain.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        _binding.navigationViewMain.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_aboutUs -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("bazaar://details?id=" + "com.example.mycalendar")
                    intent.setPackage("com.farsitel.bazaar")
                    startActivity(intent)
                }
                R.id.menu_ourWebsite -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data =
                        Uri.parse("bazaar://collection?slug=by_author&aid=reverse_developer")
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
                R.id.dateConverterFragment,
                R.id.weatherFragment,
                R.id.calendarFragment,
                R.id.oghatSharghiFragment,
                R.id.timeAndDateFragment -> {
                    _binding.toolBarMain.title = destination.label
                    showAdsCounter ++
                    if (showAdsCounter % 5 == 0) {
                        showInterstitialAd()
                    }
                    View.VISIBLE
                }
                else ->
                    View.GONE

            }
        }

        for (i in 0..5) {
            requestStandardBanner()
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

    private fun initialTapSell() {
        TapsellPlus.initialize(this, TAP_SELL_KEY,
            object : TapsellPlusInitListener {
                override fun onInitializeSuccess(adNetworks: AdNetworks) {
                    Log.d("onInitializeSuccess", adNetworks.name)


                }

                override fun onInitializeFailed(
                    adNetworks: AdNetworks,
                    adNetworkError: AdNetworkError
                ) {
                    Log.e(
                        "onInitializeFailed",
                        "ad network: " + adNetworks.name + ", error: " + adNetworkError.errorMessage
                    )
                }
            })
    }
    private fun requestStandardBanner() {
        TapsellPlus.requestStandardBannerAd(
            this, ZONE_ID_STANDARD_BANNER,
            TapsellPlusBannerType.BANNER_320x50,
            object : AdRequestCallback() {
                override fun response (tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.response(tapsellPlusAdModel)

                    //Ad is ready to show
                    //Put the ad's responseId to your responseId variable
                    val standardBannerResponseId = tapsellPlusAdModel.responseId
                    Log.d("responseTapSell" , "success")

                    TapsellPlus.showStandardBannerAd(this@MainActivity, standardBannerResponseId,
                        _binding.standardBanner,
                        object : AdShowListener() {
                            override fun onOpened(tapsellPlusAdModel: TapsellPlusAdModel) {
                                super.onOpened(tapsellPlusAdModel)
                            }

                            override fun onError(tapsellPlusErrorModel: TapsellPlusErrorModel) {
                                super.onError(tapsellPlusErrorModel)
                            }
                        })
                }

                override fun error(message: String?) {

                    Log.d("responseTapSell" , "fail")
                }
            })
    }
    private fun showInterstitialAd () {

        TapsellPlus.requestInterstitialAd(
            this,
            ZONE_ID_REWARDED_VIDEO,
            object : AdRequestCallback() {
                override fun response(tapsellPlusAdModel: TapsellPlusAdModel) {
                    super.response(tapsellPlusAdModel)

                    //Ad is ready to show
                    //Put the ad's responseId to your responseId variable
                    val rewardedResponseId = tapsellPlusAdModel.responseId

                    TapsellPlus.showInterstitialAd(this@MainActivity, rewardedResponseId,
                        object : AdShowListener() {
                            override fun onOpened(tapsellPlusAdModel: TapsellPlusAdModel) {
                                super.onOpened(tapsellPlusAdModel)
                            }
                            override fun onClosed(tapsellPlusAdModel: TapsellPlusAdModel) {
                                super.onClosed(tapsellPlusAdModel)
                            }
                            override fun onError(tapsellPlusErrorModel: TapsellPlusErrorModel) {
                                super.onError(tapsellPlusErrorModel)
                            }
                        })

                }

                override fun error(message: String) {}
            })



    }


}