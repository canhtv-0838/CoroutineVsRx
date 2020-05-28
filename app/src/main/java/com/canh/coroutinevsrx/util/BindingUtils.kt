package com.canh.coroutinevsrx.util

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bindImageUrl")
fun ImageView.bindImageUrl(url: String?) {
    url?.let {
        Glide.with(this)
            .load(StringUtils.getSmallImage(it))
            .into(this)
    }
}

@BindingAdapter("bindRating")
fun RatingBar.bindRating(voteAverage: Double) {
    if (voteAverage != 0.0) {
        this.rating = (voteAverage * (this.numStars) / 10).toFloat()
    }
}

@BindingAdapter("bindDate")
fun TextView.formatDate(date: String?) {
    if (date.isNullOrBlank()) {
        this.text = date
        return
    }
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("dd-MM-yyyy")
    date.let {
        val dateOuput: Date? = inputFormat.parse(it)
        dateOuput?.let { date ->
            val outputFormatString = outputFormat.format(date)
            this.text = outputFormatString
        }
    }
}