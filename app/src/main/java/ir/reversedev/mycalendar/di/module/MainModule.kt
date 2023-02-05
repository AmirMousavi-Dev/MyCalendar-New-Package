package ir.reversedev.mycalendar.di.module

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ir.reversedev.mycalendar.model.apiservice.MonasebatApiService
import ir.reversedev.mycalendar.model.apiservice.OghatApiService
import ir.reversedev.mycalendar.model.apiservice.WeatherApiService
import ir.reversedev.mycalendar.model.dao.CityDao
import ir.reversedev.mycalendar.model.dao.FavoriteDao
import ir.reversedev.mycalendar.model.dao.NoteDao
import ir.reversedev.mycalendar.model.database.MyDataBase
import ir.reversedev.mycalendar.viewmodel.MainRepository
import javax.inject.Singleton

@Module
class MainModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): MyDataBase {
        return MyDataBase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideCityDao(myDataBase: MyDataBase): CityDao {
        return myDataBase.cityDao
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(myDataBase: MyDataBase): FavoriteDao {
        return myDataBase.favoriteDao
    }
    @Singleton
    @Provides
    fun provideNoteDao (myDataBase: MyDataBase) : NoteDao {
        return myDataBase.noteDao
    }

    @Singleton
    @Provides
    fun provideMainRepository(
        weatherApiService: WeatherApiService,
        oghatApiService: OghatApiService,
        monasebatApiService: MonasebatApiService,
        cityDao: CityDao,
        favoriteDao: FavoriteDao,
        noteDao: NoteDao
    ): MainRepository {
        return MainRepository(weatherApiService, oghatApiService,monasebatApiService, cityDao, favoriteDao , noteDao)
    }

    @Singleton
    @Provides
    fun provideRequestManager(context: Context): RequestManager {
        return Glide.with(context)
    }

//    @Singleton
//    @Provides
//    fun ProvidePersianCalendar() :PersianCalendar{
//            val calendar = Calendar.getInstance()
//            val persian = calendar.toPersian()
//            return persian
//    }



}