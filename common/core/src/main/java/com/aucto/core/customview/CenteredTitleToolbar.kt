package com.aucto.core.customview

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class CenteredTitleToolbar : Toolbar {

    private var titleTextView: TextView? = null
    private var screenWidth = 0
    private var centerTitle = true

    private val screenSize: Point
        private get() {
            val wm =
                context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val screenSize = Point()
            display.getSize(screenSize)
            return screenSize
        }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        screenWidth = screenSize.x
        titleTextView = TextView(context)
        // titleTextView!!.setTextAppearance(context, R.style.ToolbarTitleText)
    }

    // ktlint-disable
    /* ktlint-disable number-of-args */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (centerTitle) {
            val location = IntArray(2)
            titleTextView!!.getLocationOnScreen(location)
            titleTextView!!.translationX =
                titleTextView!!.translationX + (-location[0] + screenWidth / 2 - titleTextView!!.width / 2)
        }
    }

    /* ktlint-enable number-of-args */
// ktlint-enable
    override fun setTitle(title: CharSequence) {
        removeView(titleTextView)
        addView(titleTextView)
        titleTextView!!.text = title
        requestLayout()
    }

    override fun setTitle(titleRes: Int) {
        removeView(titleTextView)
        addView(titleTextView)
        titleTextView!!.setText(titleRes)
        requestLayout()
    }

    fun setTitleCentered(centered: Boolean) {
        centerTitle = centered
        requestLayout()
    }
}
