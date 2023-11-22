package com.example.navigationpracticakt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.navigationpracticakt.databinding.FragmentTabBinding
import com.example.navigationpracticakt.fragments.TabA
import com.example.navigationpracticakt.fragments.TabB
import com.example.navigationpracticakt.fragments.TabC
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Tab : Fragment() {
    private lateinit var binding : FragmentTabBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //To change to the specific fragment from the adapter
        binding.viewPager.adapter = object : FragmentStateAdapter(this@Tab) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> TabA()
                    1 -> TabB()
                    2 -> TabC()
                    else -> TabA()
                }
            }

        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "TAB A"
                1 -> "TAB B"
                2 -> "TAB C"
                else -> "TAB A"
            }
        }.attach()
    }
}