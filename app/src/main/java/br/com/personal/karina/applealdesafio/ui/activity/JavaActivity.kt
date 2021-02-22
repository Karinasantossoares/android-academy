package br.com.personal.karina.applealdesafio.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import br.com.personal.karina.applealdesafio.R
import br.com.personal.karina.applealdesafio.ui.adapter.ScreenPagerTrainingAdapter

class JavaActivity : AppCompatActivity() {
    var myViewPager2: ViewPager2? = null
    var MyAdapter: ScreenPagerTrainingAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        myViewPager2!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        myViewPager2!!.adapter = MyAdapter
        myViewPager2!!.offscreenPageLimit = 3
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
        myViewPager2!!.setPageTransformer { page: View, position: Float ->
            val myOffset = position * -(2 * pageOffset + pageMargin)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }
    }
}