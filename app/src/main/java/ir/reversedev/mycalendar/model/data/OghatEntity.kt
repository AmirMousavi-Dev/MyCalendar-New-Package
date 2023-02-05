package ir.reversedev.mycalendar.model.data

data class OghatEntity(
    val ok: Boolean,
    val result: Result
) {
    data class Result(
        val azan_maghreb: String,
        val azan_sobh: String,
        val azan_zohr: String,
        val day: Int,
        val ghorub_aftab: String,
        val month: Int,
        val nimeshab: String,
        val tolu_aftab: String
    )
}