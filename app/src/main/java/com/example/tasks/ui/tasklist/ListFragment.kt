package com.example.tasks.ui.tasklist

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tasks.R
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.viewmodel.TaskViewModel
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.elevation.SurfaceColors.SURFACE_2
import es.dmoral.toasty.Toasty
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel: TaskViewModel by viewModels()

    private lateinit var adapter: TaskAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Task Lists"



     /*   (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getResources().getColor(R.color.purple_700)));*/


        adapter = TaskAdapter(TaskClickListener { taskEntity ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(
                taskEntity))
        })


        viewModel.getAllTasks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }



        binding.apply {

            recView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            binding.recView.adapter = adapter

            recView.itemAnimator = LandingAnimator().apply {
                addDuration = 440
            }

            fabAdd.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }

        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return binding.root


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.mymenu, menu)

        val search = menu.findItem(R.id.search_bar)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> {
                deleteAllTasks()
            }

            R.id.sort_by_priority -> {
                viewModel.sortByPriority
                    .observe(viewLifecycleOwner, { tasks ->
                        adapter.submitList(tasks)
                        Toasty.normal(requireContext(), "High to Low", Toasty.LENGTH_SHORT).show()
                    })
            }

            R.id.default_view -> {
                viewModel.getAllTasks.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {/*
        if(query!=null){
            searchThroughDatabase(query)
        }*/
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {

        if (query!!.isEmpty()) {
            viewModel.getAllTasks.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        } else {
            searchThroughDatabase(query)
        }


        return true
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery: String = query
        searchQuery = "%$searchQuery%"

        viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, { tasks ->
            adapter.submitList(tasks)
        })

    }


    fun deleteAllTasks() {
        android.app.AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_delete)
            .setTitle("Clear All?")
            .setMessage("Are you sure you want to delete all tasks?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which -> viewModel.deleteAll() }
            )
            .setNegativeButton("No", null)
            .show()
    }


}