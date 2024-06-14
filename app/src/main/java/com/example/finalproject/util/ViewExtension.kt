package com.example.finalproject.util

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.ViewGroup


fun ViewGroup.shrink() {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f)

    ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        .setDuration(300)
        .start()
}

fun View.shrink() {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f)

    ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        .setDuration(300)
        .start()
}

fun ViewGroup.expand() {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)

    ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        .setDuration(300)
        .start()
}

fun View.expand() {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)

    ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
        .setDuration(300)
        .start()
}