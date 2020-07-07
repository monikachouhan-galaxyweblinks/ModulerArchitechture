/*
 * Copyright (c) 2018 Nam Nguyen, nam@aucto.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gwl.playerfeed.common

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

/**
 * @author eneim (2018/01/26).
 */

fun AppCompatTextView.htmlText(html: String) {
    @Suppress("DEPRECATION")
    val spanned = if (VERSION.SDK_INT >= VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(html)
    }

    val spannable = SpannableStringBuilder(spanned)
    replaceBulletSpans(spannable)
    this.text = spannable
}

fun replaceBulletSpans(spannable: Spannable) {
    val quoteSpans = spannable.getSpans(0, spannable.length, BulletSpan::class.java)
    quoteSpans.forEach {
        val start = spannable.getSpanStart(it)
        val end = spannable.getSpanEnd(it)
        val flags = spannable.getSpanFlags(it)
        spannable.removeSpan(it)
        spannable.setSpan(BulletSpan(12), start, end, flags)
    }
}

@BindingAdapter("visibleIf")
fun View.show(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}


fun AppCompatActivity.setupToolbarWithTitle(
    toolbar: Toolbar,
    darkIcons: Boolean = true,
    enableUpButton: Boolean = true,
    title: String? = null
) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(enableUpButton)
    /* val indicator =
         if (darkIcons) R.drawable.ic_arrow_back_black_24dp else R.drawable.ic_arrow_back_black_24dp
     supportActionBar?.setHomeAsUpIndicator(indicator)*/
    supportActionBar?.title = title ?: ""
}