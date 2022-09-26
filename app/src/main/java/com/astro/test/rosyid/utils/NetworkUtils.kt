package com.astro.test.rosyid.utils

import android.app.Activity
import com.astro.test.rosyid.utils.Dialog.cancelLoading
import com.astro.test.rosyid.utils.Dialog.dialog2
import com.astro.test.rosyid.utils.Dialog.setLoading
import com.telkom.privyidtest.utils.Resource
import com.telkom.privyidtest.utils.Status

object NetworkUtils {
    fun <T> Activity.populateState(
        data: Resource<T>,
        onSuccess:() -> Unit,
        onFailed:() -> Unit = {},
        onEmpty:() -> Unit = {}) {
        when(data.status) {
            Status.LOADING -> setLoading(this)
            Status.EMPTY -> {
                cancelLoading()
                onEmpty()
            }
            Status.ERROR -> {
                cancelLoading()
                dialog2(
                    message = data.message!!,
                    action = { dialog ->
                        dialog.dismiss()
                    }
                )
                onFailed()
            }
            Status.SUCCESS -> {
                cancelLoading()
                onSuccess()
            }
        }
    }
}