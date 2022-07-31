package com.berkayyetis.campbooktesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.berkayyetis.campbooktesting.adapter.CampRecyclerAdapter
import com.berkayyetis.campbooktesting.adapter.ImageRecyclerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class CampFragmentFactory
@Inject constructor(private val glide: RequestManager , val campRecyclerAdapter: CampRecyclerAdapter, val imageRecyclerAdapter: ImageRecyclerAdapter)
    : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            CampFragment::class.java.name -> CampFragment(campRecyclerAdapter)
            CampDetailFragment::class.java.name -> CampDetailFragment(glide)
            else -> return super.instantiate(classLoader, className)
        }
    }
}