package com.astro.test.rosyid.utils

import com.astro.test.rosyid.db.network.model.Developer

interface OnClickListener {
    fun onChecked(developer: Developer)
    fun onUnchecked(developer: Developer)
}