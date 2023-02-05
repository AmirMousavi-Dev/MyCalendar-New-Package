package ir.reversedev.mycalendar.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerFragment
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.databinding.FragmentWeatherBinding
import ir.reversedev.mycalendar.model.data.ForecastEntity
import ir.reversedev.mycalendar.util.InternetChecker
import javax.inject.Inject

class WeatherFragment : DaggerFragment() {
    private lateinit var _binding: FragmentWeatherBinding
    @Inject
    lateinit var viewModel: WeatherViewModel
    @Inject
    lateinit var glide: RequestManager
    @Inject
    lateinit var internetChecker: InternetChecker


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return _binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (internetChecker.checkForInternet()) {
            with(_binding) {
                imgInternetWeather.visibility = View.GONE
                progressBarWeather.visibility = View.VISIBLE
                nesWeather.visibility = View.GONE
            }
            val toDay = viewModel.toDay()

            viewModel.getWeather()
            viewModel.favorite.observe(this.viewLifecycleOwner) { it ->
                it.ifNotHandled {
                    _binding.btnChooseCity.text = it.name
                }
            }
            viewModel.weather.observe(this.viewLifecycleOwner) {
                it.ifNotHandled { weatherEntity ->
                    //setImage(weatherEntity.weather[0].main, _binding.imgWeather)
                    setAnim(weatherEntity.weather[0].main, _binding.animWeather)
                    with(_binding) {
                        txtWeather.text = weatherEntity.weather[0].description
                        txtTemperature.text = weatherEntity.main.temp.toInt().toString()
                        txtHumidityWeather.text = weatherEntity.main.humidity.toString()
                        txtCloud.text = weatherEntity.clouds.all.toString()
                        txtRealFeel.text = weatherEntity.main.feels_like.toInt().toString()
                        txtWindSpeed.text = weatherEntity.wind.speed.toString()

                    }
                    if (weatherEntity.main.feels_like > 25.00) {
                        glide.load(R.drawable.ic_temperature_red).into(_binding.imgTempWeather)
                    } else {
                        glide.load(R.drawable.ic_temperature_blue).into(_binding.imgTempWeather)
                    }


                }
            }
            viewModel.forecast.observe(this.viewLifecycleOwner) {
                it.ifNotHandled {
                    with(_binding) {
                        when (toDay) {
                            "شنبه" -> {
                                txtNextTwoDay.text = "دوشنبه :"
                                txtNextThreeDays.text = "سه شنبه :"
                            }
                            "یکشنبه" -> {
                                txtNextTwoDay.text = "سه شنبه :"
                                txtNextThreeDays.text = "چهارشنبه :"
                            }
                            "دوشنبه" -> {
                                txtNextTwoDay.text = "چهارشنبه :"
                                txtNextThreeDays.text = "پنجشنبه :"
                            }
                            "سه شنبه" -> {
                                txtNextTwoDay.text = "پنجشنبه :"
                                txtNextThreeDays.text = "جمعه :"
                            }
                            "چهارشنبه" -> {
                                txtNextTwoDay.text = "جمعه :"
                                txtNextThreeDays.text = "شنبه :"
                            }
                            "پنجشنبه" -> {
                                txtNextTwoDay.text = "شنبه :"
                                txtNextThreeDays.text = "یکشنبه :"
                            }
                            else -> {
                                txtNextTwoDay.text = "یکشنبه :"
                                txtNextThreeDays.text = "دوشنبه :"
                            }
                        }
                        setImage(it.list[8].weather[0].main, _binding.imgFardaWeather)
                        setImage(it.list[16].weather[0].main, _binding.imgNextTwoDays)
                        setImage(it.list[24].weather[0].main, _binding.imgNextThreeDays)
                        txtFardaWeather.text = it.list[8].weather[0].description
                        txtNextTwoDayText.text = it.list[16].weather[0].description
                        txtNextThreeDaysText.text = it.list[24].weather[0].description


                        val listToDay = listOf(
                            it.list[0].main ,
                            it.list[1].main ,
                            it.list[2].main ,
                            it.list[3].main ,
                            it.list[4].main ,
                            it.list[5].main ,
                            it.list[6].main ,
                            it.list[7].main
                        )
                        val listFarda = listOf(
                            it.list[8].main ,
                            it.list[9].main ,
                            it.list[10].main ,
                            it.list[11].main ,
                            it.list[12].main ,
                            it.list[13].main ,
                            it.list[14].main ,
                            it.list[15].main
                        )
                        val listTwoDay = listOf(
                            it.list[16].main ,
                            it.list[17].main ,
                            it.list[18].main ,
                            it.list[19].main ,
                            it.list[20].main ,
                            it.list[21].main ,
                            it.list[22].main ,
                            it.list[23].main ,
                        )
                        val listThreeDay = listOf(
                            it.list[24].main ,
                            it.list[25].main ,
                            it.list[26].main ,
                            it.list[27].main ,
                            it.list[28].main ,
                            it.list[29].main ,
                            it.list[30].main ,
                            it.list[31].main ,
                        )
                        txtMaxTempWeather.text = findMaxTemp(listToDay)
                        txtMinTempWeather.text = findMinTemp(listToDay)

                        txtTempFardaUp.text = findMaxTemp(listFarda)
                        txtTempFardaDown.text = findMinTemp(listFarda)
                        txtTempNextTwoDaysUp.text = findMaxTemp(listTwoDay)
                        txtTempNextTwoDaysDown.text = findMinTemp(listTwoDay)
                        txtTempNextThreeDaysUp.text = findMaxTemp(listThreeDay)
                        txtTempNextThreeDaysDown.text = findMinTemp(listThreeDay)


                        _binding.progressBarWeather.visibility = View.GONE
                        _binding.nesWeather.visibility = View.VISIBLE
                    }
                }
            }

            _binding.btnChooseCity.setOnClickListener {
                findNavController().navigate(
                    WeatherFragmentDirections.actionWeatherFragmentToFavoriteDialog(
                        "weather"
                    )
                )
            }
        } else {
            _binding.imgInternetWeather.visibility = View.VISIBLE
            _binding.nesWeather.visibility = View.GONE
        }
    }

    private fun setImage(text: String, imageView: ImageView) {
        val time = viewModel.getTime().toInt()
        when (text) {
            "Haze" -> {
                if (time in 7..17) {
                    glide.load(R.drawable.ic_sun_cloud).into(imageView)
                } else {
                    glide.load(R.drawable.ic_moon_cloud).into(imageView)
                }
            }
            "Rain" -> {
                glide.load(R.drawable.ic_rain).into(imageView)
            }
            "Snow" -> {
                glide.load(R.drawable.ic_rain).into(imageView)
            }
            "Clouds" -> {
                glide.load(R.drawable.ic_cloud).into(imageView)
            }
            else -> {
                if (time in 7..17) {
                    glide.load(R.drawable.ic_sun).into(imageView)
                } else {
                    glide.load(R.drawable.ic_moon).into(imageView)
                }
            }
        }
    }
    private fun setAnim(text: String, lottie: LottieAnimationView) {
        val time = viewModel.getTime().toInt()
        when (text) {
            "Haze" -> {
                if (time in 7..17) {
                    lottie.setAnimation(R.raw.haze_sun)
                } else {
                    lottie.setAnimation(R.raw.haze_moon)
                }
            }
            "Rain" -> {
                if (time in 7..17) {
                    lottie.setAnimation(R.raw.day_rain)
                } else {
                    lottie.setAnimation(R.raw.night_rain)
                }
            }
            "Snow" -> {
                if (time in 7..17) {
                    lottie.setAnimation(R.raw.day_snow)
                } else {
                    lottie.setAnimation(R.raw.night_snow)
                }
            }
            "Clouds" -> {
                if (time in 7..17) {
                    lottie.setAnimation(R.raw.day_cloud)
                } else {
                    lottie.setAnimation(R.raw.night_cloud)
                }
            }
            else -> {
                if (time in 7..17) {
                    lottie.setAnimation(R.raw.sun)
                } else {
                    lottie.setAnimation(R.raw.moon)
                }
            }
        }
    }


    private fun findMaxTemp (list: List<ForecastEntity.Forecast.Main>) :String {
        val data = list.maxOf {
            it.temp_max
        }
        return data.toInt().toString()
    }
    private fun findMinTemp (list: List<ForecastEntity.Forecast.Main>) :String {
        val data = list.minOf {
            it.temp_min
        }
        return data.toInt().toString()
    }

}