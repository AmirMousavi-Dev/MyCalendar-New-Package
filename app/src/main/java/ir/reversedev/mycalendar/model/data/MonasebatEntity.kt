package ir.reversedev.mycalendar.model.data

class MonasebatEntity : ArrayList<MonasebatEntity.MonasebatEntityItem>(){
    data class MonasebatEntityItem(
        val occasion: String
    )
}