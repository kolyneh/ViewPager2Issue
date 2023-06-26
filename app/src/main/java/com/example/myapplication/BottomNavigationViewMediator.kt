package com.example.myapplication

import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationViewMediator(
    private val bottomNavigationView: BottomNavigationView,
    private val viewPager: ViewPager2,
    private val smoothScroll: Boolean = true,
    private val config: ((BottomNavigationView, ViewPager2) -> Unit)? = null
) {

    var attached: Boolean = false
        private set

    private val pagerChangeCallback by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                try {
                    bottomNavigationView.menu.getItem(position)?.isChecked = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun attach() {
        check(!attached) { "TabLayoutMediator is already attached" }
        config?.invoke(bottomNavigationView, viewPager)
        viewPager.registerOnPageChangeCallback(pagerChangeCallback)
        bottomNavigationView.setOnItemSelectedListener { item ->
            val index = bottomNavigationView.menu.children.indexOfFirst { item == it }
            viewPager.setCurrentItem(index, smoothScroll)
            return@setOnItemSelectedListener true
        }
        attached = true
    }

    fun detach() {
        viewPager.unregisterOnPageChangeCallback(pagerChangeCallback)
        attached = false
    }

}