package com.aprianto.core.utils

import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog
import java.text.NumberFormat
import java.util.*

object UIHelper {
    fun showDialog(
        context: Context,
        title: String?,
        message: String?,
        style: Int = SweetAlertDialog.SUCCESS_TYPE
    ) {
        SweetAlertDialog(context, style).apply {
            title?.let { titleText = it }
            message?.let { contentText = it }
            setCancelable(false)
            show()
        }
    }

    fun castCustomRupiah(value: Int): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(value).toString()
    }


}