package com.example.tasks.ui.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tasks.R
import com.example.tasks.database.TaskEntity
import com.example.tasks.databinding.FragmentAddBinding
import com.example.tasks.viewmodel.TaskViewModel
import es.dmoral.toasty.Toasty


class AddFragment : Fragment() {

    private val viewModel : TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAddBinding.inflate(inflater)
        // Inflate the layout for this fragment

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Add Task"

        val myAdapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.priority_list)
            )

        binding.apply {
            spinnerPriority.adapter = myAdapter
            AddTask.setOnClickListener {
                if(TextUtils.isEmpty(taskTitle.text)){
                    Toasty.error(requireContext(),"Enter task name",Toasty.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val task_title = taskTitle.text.toString()
                val priority = spinnerPriority.selectedItemPosition

                val taskDetails = TaskEntity(
                    0,
                    task_title,
                    priority,
                    System.currentTimeMillis()
                )

                viewModel.insert(taskDetails)

                Toasty.success(requireContext(),"Successfully Added",Toasty.LENGTH_SHORT).show()
                findNavController().navigate(AddFragmentDirections.actionAddFragmentToListFragment())
                findNavController().popBackStack()

            }
        }

        return binding.root
    }

}