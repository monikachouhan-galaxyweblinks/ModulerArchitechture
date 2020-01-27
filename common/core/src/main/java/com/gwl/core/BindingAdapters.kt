package com.gwl.core

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

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


@BindingAdapter("error")
fun TextInputLayout.setError(name: String?) {
    error = name?.capitalize() ?: ""
}

@BindingAdapter(value = ["adapter", "list"], requireAll = false)
fun <T> RecyclerView.setAdapter(adapter: BaseAdapter<T>?, list: MutableList<T>?) {
    setAdapter(adapter)
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

@SuppressLint("Defaul" +
        "tLocale")
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

