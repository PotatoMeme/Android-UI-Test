package com.potatomeme.screen.planfit.presentation.on_board

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatomeme.screen.planfit.R
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoGymBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SelectInfoGymFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoGymBinding
    private val viewModel: SelectInfoViewModel by activityViewModels()
    private val adapter: GymListAdapter = GymListAdapter { gym ->
        val modal = GymCheckBottomSheet(gym) { gym ->
            viewModel.setSelectedGym(gym)
            findNavController().navigate(R.id.action_selectInfoGymFragment_to_selectInfoTimesFragment)
        }
        modal.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
        modal.show(childFragmentManager, GymCheckBottomSheet.TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSelectinfoGymBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.requestGymList("")

        binding.rvGym.apply {
            adapter = this@SelectInfoGymFragment.adapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.infoGymList.collect {
                    adapter.submitList(it)
                }
            }
        }

        //ivClear 관련
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.ivClear.visibility =
                    if (p0.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.ivClear.setOnClickListener {
            binding.etSearch.text.clear()
        }


        //엔터키 상호작용
        binding.etSearch.setOnKeyListener { v, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.requestGymList((v as EditText).text.toString())
                hideKeyboard()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        //EditText 사용중 다른곳 누르면 키보드 내리기
        binding.root.setOnTouchListener { _, _ ->
            if (activity != null && activity?.currentFocus != null) {
                hideKeyboard()
            }
            return@setOnTouchListener false
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setState(SelectInfoState.STATE_GYM)
    }

    private fun hideKeyboard() {
        val inputManager =
            activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}