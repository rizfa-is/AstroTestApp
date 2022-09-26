package com.astro.test.rosyid.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.astro.test.rosyid.R

object Dialog {
    private var builder: AlertDialog.Builder? = null
    private var loading: AlertDialog? = null

    fun Context.dialog(message: String, action: () -> Unit) {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("Notice")
            setMessage(message)
            setCancelable(false)
            setPositiveButton("Yes") { _, _ ->
                action()
            }
            setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        }
        dialog.show()
    }

    fun Context.dialog2(message: String, action: (dialogInterface: DialogInterface) -> Unit) {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("Notice")
            setMessage(message)
            setCancelable(false)
            setPositiveButton("Yes") { dialogInterface, _ ->
                action(dialogInterface)
            }
        }
        dialog.show()
    }

    fun Context.dialogCheckInternet(closeAll: () -> Unit) {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.network_info))
            setMessage(getString(R.string.no_internet))
            setIcon(R.drawable.ic_no_internet)
            setCancelable(false)
            setPositiveButton("OK") { _, _ ->
                closeAll()
            }
        }
        dialog.show()
    }

    fun setLoading(activity: Activity) {
        val customView = activity.layoutInflater.inflate(R.layout.dialog_loading, null)

        if (builder == null && loading == null) {
            builder = AlertDialog.Builder(activity)
            builder?.setView(customView)
            builder?.setCancelable(false)

            loading = builder?.create()
            loading?.show()
        }
    }

    fun cancelLoading() {
        if (loading != null && loading?.isShowing == true) {
            loading = try {
                loading?.dismiss()
                null
            } catch (e: Exception) {
                null
            }
        }
        builder = null
    }
}