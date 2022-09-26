package com.astro.test.rosyid.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Handler
import android.widget.ImageView
import android.widget.SearchView
import androidx.annotation.DrawableRes
import com.astro.test.rosyid.R
import com.astro.test.rosyid.utils.Dialog.dialogCheckInternet
import com.bumptech.glide.Glide

object AppUtils {

    fun searchByUsername(searchView: SearchView, action: (keyword: String?) -> Unit) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                action(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                action(p0)
                return false
            }
        })
    }

    fun Activity.connectionCheck(closeAll: () -> Unit) {
        val connectManager = this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val checkNetwork = connectManager.activeNetworkInfo

        if (checkNetwork == null || !checkNetwork.isConnected || !checkNetwork.isAvailable) {
            dialogCheckInternet(closeAll)
        }
    }

    fun showImage(
        context: Context,
        image: String,
        view: ImageView,
        @DrawableRes placeholder: Int = R.drawable.ic_avatar_en
    ){
        Glide.with(context)
            .load(image)
            .placeholder(placeholder)
            .into(view)
    }

    fun showImage(
        context: Context,
        bitmap: Bitmap,
        view: ImageView,
        @DrawableRes placeholder: Int = R.drawable.ic_avatar_en
    ) {
        Glide.with(context)
            .load(bitmap)
            .placeholder(placeholder)
            .into(view)
    }

    fun Context.delay(time: Long, action: () -> Unit) {
        Handler(mainLooper).postDelayed(
            { action() }, time
        )
    }
}