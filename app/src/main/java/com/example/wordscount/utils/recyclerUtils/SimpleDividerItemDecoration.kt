package com.example.wordscount.utils.recyclerUtils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.wordscount.R

class SimpleDividerItemDecoration() : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null

    constructor(context: Context) : this() {
        mDivider = ContextCompat.getDrawable(context, R.drawable.shape_line_divider)
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left: Int = parent.paddingLeft
        val right: Int = parent.width - parent.paddingRight

        for (child in parent.children) {
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams

            val top: Int = child.bottom + params.bottomMargin
            val bottom: Int = top + mDivider!!.intrinsicHeight

            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }

}