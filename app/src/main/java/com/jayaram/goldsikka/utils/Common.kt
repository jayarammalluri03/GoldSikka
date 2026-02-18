package com.jayaram.goldsikka.utils

object Common {


    fun getDate(date: Long): String {
        val sdf = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
        val formattedDate = sdf.format(java.util.Date(date))
        return formattedDate
    }
}