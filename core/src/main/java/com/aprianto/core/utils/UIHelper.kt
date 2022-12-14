package com.aprianto.core.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aprianto.core.R
import java.time.Duration

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