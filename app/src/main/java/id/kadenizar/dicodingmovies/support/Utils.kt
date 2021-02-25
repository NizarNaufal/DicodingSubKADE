package id.kadenizar.dicodingmovies.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import id.kadenizar.dicodingmovies.R

fun Activity.showToast(message: String?, isLongDuration: Boolean = false) {
    runOnUiThread {
        val duration = if (isLongDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(this, message, duration).show()
    }
}
fun showSnackBar(context: Context, view: View, message: String?, runnable: Runnable) {
    val safeMessage = message ?: context.getString(R.string.unknown_error)
    Snackbar.make(view, safeMessage, Snackbar.LENGTH_INDEFINITE)
        .setAction(context.getText(R.string.retry)) {
            runnable.run()
        }
        .apply {
            anchorView = view
        }
        .show()
}
fun <C> Activity.showActivity(classDestination: Class<C>, isFinish: Boolean = false) {
    startActivity(Intent(this, classDestination))
    if (isFinish) {
        finish()
    }
}