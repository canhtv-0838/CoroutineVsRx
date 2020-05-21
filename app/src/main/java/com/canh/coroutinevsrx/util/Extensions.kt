package com.canh.coroutinevsrx.util

import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun log(msg: String) = Log.d("canh123", "[${Thread.currentThread().name}] $msg")

fun RecyclerView.decorationView() =
    DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)

@BindingAdapter("clickSafe")
fun View.setClickSafe(listener: View.OnClickListener?) {
    val thresholdClickTime = 500
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < thresholdClickTime) {
                return
            }
            listener?.onClick(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
