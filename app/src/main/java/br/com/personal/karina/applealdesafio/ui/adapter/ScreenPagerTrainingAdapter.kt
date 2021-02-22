package br.com.personal.karina.applealdesafio.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenPagerTrainingAdapter(fm: Fragment, private val  listFragments : List<Fragment>) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = listFragments.size

    override fun createFragment(position: Int): Fragment {
        return listFragments[position]
    }
}