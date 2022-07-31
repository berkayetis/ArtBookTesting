package com.berkayyetis.campbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.berkayyetis.campbooktesting.R
import com.berkayyetis.campbooktesting.databinding.FragmentCampDetailsBinding
import com.berkayyetis.campbooktesting.util.Status
import com.berkayyetis.campbooktesting.viewmodel.CampViewModel
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class CampDetailFragment @Inject constructor(private val glide: RequestManager)  : Fragment(R.layout.fragment_camp_details) {

    private var fragmentCampDetailsBinding : FragmentCampDetailsBinding? = null
    lateinit var viewModel : CampViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCampDetailsBinding.bind(view)
        fragmentCampDetailsBinding = binding

        viewModel = ViewModelProvider(requireActivity())[CampViewModel::class.java]
        subscribeToObservers()

        binding.imageView.setOnClickListener {
            findNavController().navigate(CampDetailFragmentDirections.actionCampDetailFragmentToImageApiFragment())
        }
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.button.setOnClickListener {
            viewModel.makeCamp(binding.campNameTextView.text.toString(),binding.artistTextView.text.toString(),binding.yearTextView.text.toString())

        }
    }

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url->
                fragmentCampDetailsBinding?.let {
                    glide.load(url).into(it.imageView)
                }
        })
        viewModel.insertCampMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    Toast.makeText(requireContext(),"SUCCESS",Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertCampMsg()
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message?: "Error", Toast.LENGTH_LONG).show()
                }
                Status.LOADING->{

                }
            }
        })
    }

    override fun onDestroyView() {
        fragmentCampDetailsBinding = null
        super.onDestroyView()
    }
}