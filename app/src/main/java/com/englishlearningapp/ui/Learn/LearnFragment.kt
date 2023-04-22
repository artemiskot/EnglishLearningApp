package com.englishlearningapp.ui.Learn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.englishlearningapp.LearnModeActivity
import com.englishlearningapp.R
import com.englishlearningapp.data.*
import com.englishlearningapp.databinding.FragmentLearnBinding
import kotlinx.android.synthetic.main.fragment_learn.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LearnFragment : Fragment() {
    private var _binding: FragmentLearnBinding? = null
    private lateinit var moduleViewModel: ModuleViewModel
    private lateinit var moduleRepository: ModuleRepository
    private lateinit var moduleDao: ModuleDao
    private lateinit var emailId:String

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val db = MyDatabase.getInstance(requireContext())
        moduleDao = MyDatabase.getInstance(requireContext()).moduleDao()
        moduleRepository = ModuleRepository(moduleDao)
        moduleViewModel = ModuleViewModel(moduleRepository)
        return inflater.inflate(R.layout.fragment_learn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailId = arguments?.getString("email_id") ?: ""
        val adapter = ModuleListAdapter { moduleId ->
            // Navigate to the ModuleWordsActivity with moduleId as an extra
            val intent = Intent(requireContext(), LearnModeActivity::class.java)
            intent.putExtra("MODULE_ID", moduleId)
            intent.putExtra("email_id", emailId)
            startActivity(intent)
        }

        moduleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        moduleRecyclerView.adapter = adapter
        moduleViewModel.allModules.observe(viewLifecycleOwner) { modules ->
            adapter.submitList(modules)
        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}