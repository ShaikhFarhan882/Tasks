package com.example.tasks.ui.update

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tasks.R
import com.example.tasks.database.TaskEntity
import com.example.tasks.databinding.FragmentUpdateBinding
import com.example.tasks.viewmodel.TaskViewModel
import es.dmoral.toasty.Toasty


class UpdateFragment : Fragment() {

    private val viewModels: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentUpdateBinding.inflate(layoutInflater)

        val args = UpdateFragmentArgs.fromBundle(requireArguments())

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Update Tasks"

        binding.apply {
            taskTitleUpdate.setText(args.taskEntity.title)
            spinnerPriorityUpdate.setSelection(args.taskEntity.priority)

            UpdateTask.setOnClickListener {
                if (TextUtils.isEmpty(taskTitleUpdate.text)) {
                    Toasty.error(requireContext(), "Enter task name", Toasty.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val task_title = taskTitleUpdate.text.toString()
                val priority = spinnerPriorityUpdate.selectedItemPosition

                val taskDetails = TaskEntity(
                    args.taskEntity.id,
                    task_title.toString(),
                    priority,
                    args.taskEntity.timeStamp
                )

                viewModels.update(taskDetails)
                Toasty.success(requireContext(), "Successfully Updated", Toasty.LENGTH_SHORT).show()
                findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
                findNavController().popBackStack()


            }

            DeleteTask.setOnClickListener {
                val task_title = taskTitleUpdate.text.toString()
                val priority = spinnerPriorityUpdate.selectedItemPosition

                val taskDetails = TaskEntity(
                    args.taskEntity.id,
                    task_title.toString(),
                    priority,
                    args.taskEntity.timeStamp
                )

                viewModels.delete(taskDetails)
                Toasty.success(requireContext(), "Deleted Successfully", Toasty.LENGTH_SHORT).show()
                findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
                findNavController().popBackStack()

            }



        }



        // Inflate the layout for this fragment
        return binding.root
    }


}