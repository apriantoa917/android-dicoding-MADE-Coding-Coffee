package com.aprianto.core.utils

import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog

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


}