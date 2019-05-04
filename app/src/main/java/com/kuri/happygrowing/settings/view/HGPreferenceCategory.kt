package com.kuri.happygrowing.settings.view

import android.content.Context
import androidx.preference.PreferenceCategory
import android.util.AttributeSet
import android.widget.TextView
import androidx.preference.PreferenceViewHolder
import com.kuri.happygrowing.R

class HGPreferenceCategory : PreferenceCategory {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context:Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val titleView = holder?.findViewById(android.R.id.title) as TextView
        titleView?.setTextColor(context.getColor(R.color.colorPrimaryDark))
    }
}