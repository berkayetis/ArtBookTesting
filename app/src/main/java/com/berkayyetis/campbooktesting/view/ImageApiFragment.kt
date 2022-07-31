package com.berkayyetis.campbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.berkayyetis.campbooktesting.R
import com.berkayyetis.campbooktesting.adapter.ImageRecyclerAdapter
import com.berkayyetis.campbooktesting.databinding.FragmentImageApiBinding
import com.berkayyetis.campbooktesting.roomdb.Camp
import com.berkayyetis.campbooktesting.util.Status
import com.berkayyetis.campbooktesting.viewmodel.CampViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(val imageRecyclerAdapter : ImageRecyclerAdapter) :
    Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: CampViewModel
    private var fragmentBinding : FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CampViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding
        var job : Job? = null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObservers()



        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        imageRecyclerAdapter.setOnItemClickListener {
            //findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }

    fun subscribeToObservers(){
        println("sub observe caliisiyor")
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    val urls = it.data?.hits?.map {
                       it-> it.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }
                Status.LOADING->{
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        })
    }
}