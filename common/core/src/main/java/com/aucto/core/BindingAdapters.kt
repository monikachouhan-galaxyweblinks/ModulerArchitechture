package com.aucto.core

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

// region - Public function
@BindingAdapter("visibleIf")
fun View.showLoader(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("customcolor")
fun TextView.setColor(color: Int) {
    this.setTextColor(color)
}

@BindingAdapter("favTint")
fun MaterialTextView.setTint(isFav: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val colorRes: Int =if (isFav) R.color.red else R.color.icon_tint_icon
        this.compoundDrawableTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    }
}
/*
@BindingAdapter("onCheckedChange")
fun CheckBox.OnCheckedChange(: Int) {
    this.setTextColor(color)
}
*/

@BindingAdapter("error")
fun TextInputLayout.setError(name: String?) {
    error = name?.capitalize() ?: ""
}

@BindingAdapter(value = ["adapter", "list"], requireAll = false)
fun <T> RecyclerView.setAdapter(adapter: BaseAdapter<T>?, list: MutableList<T>?) {
    setAdapter(adapter)
    adapter?.setData(list)
    Log.d("setAdapter", "====adapter  == $adapter")
}

@BindingAdapter(value = ["pageAdapter", "pageList"], requireAll = false)
fun <T> ViewPager.setAdapter(adapter: BasePagerAdapter<T>?, list: MutableList<T>?) {
    this.adapter = adapter
    adapter?.setData(list)
}

@BindingAdapter("styledText")
fun TextView.setStyledText(text: String?) {
    val value = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(text)
    }
    setText(value)
}

@SuppressLint("DefaultLocale")
@BindingAdapter("textCapitalize")
fun TextView.capitalizeText(name: String?) {
    text = name?.capitalize() ?: ""
}

@SuppressLint("DefaultLocale")
@BindingAdapter("textStyleItalic")
fun TextView.makeItalic(isItalic: Boolean?) {
    if (isItalic == true) setTypeface(this.typeface, Typeface.ITALIC)
    else setTypeface(this.typeface, Typeface.NORMAL)
}

@BindingAdapter("loadDrawable")
fun ImageView.setImageDrawable(drawable: Drawable?) {
    drawable?.also { this.setImageDrawable(drawable) }
}
// endregion

