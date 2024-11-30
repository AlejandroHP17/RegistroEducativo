package com.mx.liftechnology.registroeducativo.main.viewextensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mx.liftechnology.registroeducativo.R

const val getTime = 4000L

/** Toast generic to success
 * @author pelkidev
 * @since 1.0.0
 */
fun showCustomToastSuccess(activity: Activity, message: String) {
    showCustomToast(activity, message, R.color.color_success)
}

/** Toast generic to error
 * @author pelkidev
 * @since 1.0.0
 */
fun showCustomToastFailed(activity: Activity, message: String) {
    showCustomToast(activity, message, R.color.color_error_toast)
}

/** Toast generic to warnings
 * @author pelkidev
 * @since 1.0.0
 */
fun showCustomToastWarning(activity: Activity, message: String) {
    showCustomToast(activity, message, R.color.color_warning)
}


@SuppressLint("InflateParams")
private fun showCustomToast(activity: Activity, message: String, @ColorRes backgroundColor: Int) {
    /** Views */
    val inflater = activity.layoutInflater
    val layout = inflater.inflate(R.layout.custom_toast, null)
    val tvMessage: TextView = layout.findViewById(R.id.tv_mensaje)
    val rootLayout: ConstraintLayout = layout.findViewById(R.id.custom_toast)
    val ivClose: ImageView = layout.findViewById(R.id.iv_close)

    /** Assign data */
    tvMessage.text = message
    rootLayout.setBackgroundColor(ContextCompat.getColor(activity, backgroundColor))

    /** Create the dialog */
    val dialog = Dialog(activity)
    dialog.setContentView(layout)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL)
    dialog.setCancelable(false)

    /** Configrute the close, by time or click */
    val handler = Handler(Looper.getMainLooper())
    val runnable = Runnable { dialog.dismiss() }
    handler.postDelayed(runnable, getTime)

    ivClose.setOnClickListener {
        dialog.dismiss()
        handler.removeCallbacks(runnable)
    }

    dialog.show()
}
