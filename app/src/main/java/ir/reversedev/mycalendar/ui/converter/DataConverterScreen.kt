package ir.reversedev.mycalendar.ui.converter

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import com.aminography.primecalendar.common.toCivil
import com.aminography.primecalendar.common.toHijri
import com.gmail.hamedvakhide.compose_jalali_datepicker.JalaliDatePickerDialog
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.ui.ui.component.CustomCard
import ir.reversedev.mycalendar.ui.ui.component.CustomOutlinedButton
import ir.reversedev.mycalendar.ui.ui.theme.cardDateTextColor
import ir.reversedev.mycalendar.ui.ui.theme.textColor
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun DateConverterScreen(
    viewModel: DateConverterViewModel,

    ) {
    val solar = remember {
        mutableStateOf(viewModel.persianDate())
    }

    val lunar = remember {
        mutableStateOf(viewModel.hijriDate())
    }
    val gregorian = remember {
        mutableStateOf(viewModel.civilDate())
    }
    var differenceText by remember {
        mutableStateOf("امروز")
    }

    var openDatePicker = remember { mutableStateOf(false) }
    JalaliDatePickerDialog(
        openDialog = openDatePicker,
        onSelectDay = {},
        onConfirm = {
            val selectedDate = it.toGregorian()
            solar.value = listOf(
                it.day.toString(),
                it.monthString,
                it.year.toString(),
                it.dayOfWeekString
            )
            gregorian.value = listOf(
                selectedDate.toCivil().dayOfMonth.toString(),
                selectedDate.toCivil().monthName,
                selectedDate.toCivil().year.toString(),
                selectedDate.toCivil().weekDayName
            )
            lunar.value = listOf(
                selectedDate.toHijri().dayOfMonth.toString(),
                selectedDate.toHijri().monthName,
                selectedDate.toHijri().year.toString(),
                selectedDate.toHijri().weekDayName
            )


            val currentTime = Calendar.getInstance().time
            val mDateFormat = SimpleDateFormat("MM/dd/yyyy")
            val date = mDateFormat.format(currentTime)
            val newDate = mDateFormat.parse(date)
            if (newDate.time < it.toGregorian().toCivil().timeInMillis) {
                val mDifference =
                    kotlin.math.abs(newDate.time - it.toGregorian().toCivil().timeInMillis)
                val difference = mDifference / (24 * 60 * 60 * 1000)
                if (difference.toInt() == 0) {
                    differenceText = "امروز"
                } else {
                    differenceText = "$difference روز بعد "
                }
            } else {
                val mDifference =
                    kotlin.math.abs(it.toGregorian().toCivil().timeInMillis - newDate.time)
                Log.v("Amiremoon", mDifference.toString())
                val difference = mDifference / (24 * 60 * 60 * 1000)

                differenceText = "$difference روز قبل "

            }


        }
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomOutlinedButton(text = solar.value[3].setCurrentDate(
                solar.value[0],
                solar.value[1],
                solar.value[2]
            ) , icon = R.drawable.ic_select_time , modifier =
            Modifier.
            fillMaxWidth()
                .padding(8.dp)) {
                openDatePicker.value = true
            }

            Row() {
                CardDate(
                    title = stringResource(id = R.string.title_date_solar),
                    cardDetail = solar,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                CardDate(
                    title = stringResource(id = R.string.title_date_gregorian),
                    cardDetail = gregorian,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                CardDate(
                    title = stringResource(id = R.string.title_date_lunar),
                    cardDetail = lunar,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }
            CustomCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.title_time_interval),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.weight(3f),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.textColor
                    )
                    Text(
                        text = differenceText,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.cardDateTextColor,
                        modifier = Modifier.weight(7f),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
        }
    }


}

@Composable
fun CardDate(
    title: String,
    cardDetail: State<List<String>>,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title , style = MaterialTheme.typography.titleMedium
            , fontWeight = FontWeight.Bold ,
        color = MaterialTheme.colorScheme.textColor)
        CustomCard(
            modifier = Modifier
                .height(250.dp)
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = cardDetail.value[0],
                    color = MaterialTheme.colorScheme.textColor)
                Text(
                    text = cardDetail.value[1],
                    color = MaterialTheme.colorScheme.cardDateTextColor
                )
                Text(text = cardDetail.value[2],
                    color = MaterialTheme.colorScheme.textColor)
                Text(
                    text = cardDetail.value[3],
                    color = MaterialTheme.colorScheme.cardDateTextColor
                )
            }
        }
    }
}

private val setCurrentDate: String.(String, String, String) -> String = { day, month, year ->
    "$this  $day  $month  $year"
}