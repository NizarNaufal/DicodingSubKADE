package id.kadenizar.dicodingmovies.support

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

class ViewPager(context: Context, attrs: AttributeSet) :
    androidx.viewpager.widget.ViewPager(context, attrs) {
    private var swipeEnabled: Boolean = false

    init {
        this.swipeEnabled = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.swipeEnabled) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.swipeEnabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    fun disableSwipePaging(){
        this.swipeEnabled = false
    }
}