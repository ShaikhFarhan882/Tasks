package com.example.tasks.ui.tasklist

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tasks.R
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.utils.EmptyStateObserver
import com.example.tasks.utils.MyPreferences
import com.example.tasks.viewmodel.TaskViewModel
import es.dmoral.toasty.Toasty
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


        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Task List"


        adapter = TaskAdapter(TaskClickListener { taskEntity ->
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToUpdateFragment(
                    taskEntity
                )
            )
        })


        viewModel.getAllTasks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }



        binding.apply {

            recView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            /*   recView.layoutManager = LinearLayoutManager(layoutInflater.context)*/

            binding.recView.adapter = adapter

            val emptyStateObserver = EmptyStateObserver(recView, binding.emptyStateRecyclerView)
            adapter.registerAdapterDataObserver(emptyStateObserver)

            recView.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 440
            }

            //Floating action button states
            recView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && !fabAdd.isExtended
                        && recyclerView.computeVerticalScrollOffset() == 0
                    ) {
                        fabAdd.extend()
                    }
                    super.onScrollStateChanged(recyclerView, newState)
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy != 0 && fabAdd.isExtended) {
                        fabAdd.shrink()
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })

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
                    .observe(viewLifecycleOwner) { tasks ->
                        adapter.submitList(tasks)
                        Toasty.normal(requireContext(), "High to Low", Toasty.LENGTH_SHORT).show()
                    }
            }

            R.id.default_view -> {
                viewModel.getAllTasks.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }

            R.id.settings -> {
                chooseThemeDialog()
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


    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.current_theme))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(requireContext()).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(requireContext()).darkMode = 0
                    Toast.makeText(requireContext(), "Light Theme Applied", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(requireContext()).darkMode = 1
                    Toast.makeText(requireContext(), "Dark Theme Applied", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(requireContext()).darkMode = 2
                    Toast.makeText(requireContext(), "System Default Theme Applied ", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }

            }
        }
        val dialog = builder.create()
        dialog.show()
    }



}