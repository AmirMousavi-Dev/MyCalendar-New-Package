package ir.reversedev.mycalendar.util


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.aminography.primecalendar.common.toPersian
import com.aminography.primecalendar.persian.PersianCalendar
import ir.reversedev.mycalendar.R
import java.util.*

class CalendarService : Service() {
    //    @Inject lateinit var persian: PersianCalendar
    private val content = persianCalendar().shortDateString + " " + persianCalendar().weekDayName
    private val title = persianCalendar().dayOfMonth.toString() + " " + persianCalendar().monthName
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(8910 , calendarNotification())


        return START_STICKY
    }

    private fun persianCalendar() : PersianCalendar {
        val calendar = Calendar.getInstance()
        val persian = calendar.toPersian()
        return persian
    }

    private fun calendarNotification(): Notification {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChanel =
                NotificationChannel("currentTime", "تاریخ روز", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChanel)
        }
        val notification = NotificationCompat.Builder(this, "currentTime")
            .setSmallIcon(R.drawable.ic_calendar)
            .setContentTitle(title)
            .setContentText(content)

            .build()

        return notification

    }
}