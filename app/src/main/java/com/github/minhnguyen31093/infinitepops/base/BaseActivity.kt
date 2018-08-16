package com.github.minhnguyen31093.infinitepops.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

abstract class BaseActivity : AppCompatActivity(), BaseView {

    var isTouchDisable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view != null && (view is AppCompatEditText || view is EditText)) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    view.clearFocus()
                    val imm = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
        return isTouchDisable || super.dispatchTouchEvent(event)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}