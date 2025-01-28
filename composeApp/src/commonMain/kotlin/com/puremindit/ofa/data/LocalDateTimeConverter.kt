package com.puremindit.ofa.data

import androidx.room.TypeConverter
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

object LocalDateTimeConverter {
    @TypeConverter
    fun toDate(dateString: String?): LocalDateTime? {
        return if (dateString.isNullOrEmpty()) {
            LocalDateTime.now()
        } else {
            LocalDateTime.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDateTime?): String? {
        return date?.toString()
    }

}
object LocalDateConverter {
    @TypeConverter
    fun toDate(dateString: String?): LocalDate{
        return if (dateString.isNullOrEmpty()) {
            LocalDate.now()
        } else {
            LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.toString()
    }

}
