package com.aucto.core

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Rect
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

open class MaterialEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputEditText(context, attrs, defStyleAttr) {

    init {
        val drawable = compoundDrawables
        drawable.forEach {
            if (it != null) {
                val color = context.resources.getColor(R.color.body_text_color)
                it.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setKeyboardShown(context, focused)
    }
}

