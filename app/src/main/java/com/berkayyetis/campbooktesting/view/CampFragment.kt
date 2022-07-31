package com.berkayyetis.campbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berkayyetis.campbooktesting.R
import com.berkayyetis.campbooktesting.adapter.CampRecyclerAdapter
import com.berkayyetis.campbooktesting.databinding.FragmentCampsBinding
import com.berkayyetis.campbooktesting.viewmodel.CampViewModel
import kotlinx.android.synthetic.main.fragment_camps.*
import javax.inject.Inject

class CampFragment @Inject constructor(val campRecyclerAdapter: CampRecyclerAdapter)
    : Fragment(R.layout.fragment_camps) {

    private var fragmentCampsBinding :FragmentCampsBinding? = null
    lateinit var viewModel : CampViewModel

    private val swipeCallBack = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true //Do nothing
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val selectedCamp = campRecyclerAdapter.camps[viewHolder.layoutPosition]
            viewModel.deleteCamp(selectedCamp)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCampsBinding.bind(view)
        fragmentCampsBinding = binding
        viewModel= ViewModelProvider(requireActivity()).get(CampViewModel::class.java)
        subscribeToObservers()

        binding.recyclerViewCamp.adapter = campRecyclerAdapter
        binding.recyclerViewCamp.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewCamp)

        binding.fab.setOnClickListener {
           findNavController().navigate(CampFragmentDirections.actionCampFragmentToCampDetailFragment())
        }
    }

    private fun subscribeToObservers(){
        viewModel.campList.observe(viewLifecycleOwner, Observer {
            campRecyclerAdapter.camps = it
        })
    }


}