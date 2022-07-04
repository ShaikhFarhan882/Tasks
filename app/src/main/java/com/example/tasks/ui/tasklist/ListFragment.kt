package com.example.tasks.ui.tasklist

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tasks.R
import com.example.tasks.databinding.FragmentListBinding
import com.example.tasks.viewmodel.TaskViewModel


class ListFragment : Fragment() {

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
        (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getResources().getColor(R.color.purple_700)));


        adapter = TaskAdapter(TaskClickListener { taskEntity ->
            findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(
                taskEntity))

        })


        viewModel.getAllTasks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }



        binding.apply {
            binding.recView.adapter = adapter

            fabAdd.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }

        // Inflate the layout for this fragment
        return binding.root


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.mymenu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> {
                viewModel.deleteAll()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}