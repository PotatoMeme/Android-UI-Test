package com.potatomeme.screen.planfit.presentation.on_board.select_info

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.potatomeme.screen.planfit.R
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoBodyInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectInfoBodyInfoFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoBodyInfoBinding
    private val viewModel: SelectInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSelectinfoBodyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.infoBirth.collect { birth ->
                        if (birth.isNotEmpty()) binding.tvDate.text = birth
                    }
                }
                launch {
                    viewModel.infoWeight.collect { weight ->
                        if (weight.isNotEmpty()) binding.etWeight.setText(weight)
                    }
                }
                launch {
                    viewModel.infoHeight.collect { height ->
                        if (height.isNotEmpty()) binding.etHeight.setText(height)
                    }
                }
            }
        }

        binding.frameDatePicker.setOnClickListener {
            val onConfirm = { year: Int, month: Int, day: Int ->
                binding.tvDate.text = "%d.%02d.%02d".format(year, month + 1, day)
            }
            val (year, month, day) = binding.tvDate.text.toString().split(".")
                .mapNotNull { it.toIntOrNull() }.takeIf { it.size == 3 }
                ?: listOf(2000, 0, 1)
            BirthPickerBottomSheet(year, month - 1, day, onConfirm).apply {
                setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            }.show(childFragmentManager, BirthPickerBottomSheet.TAG)
        }

        addTextWatcher(binding.etHeight, 100, 250, "100cm ~ 250cm 범위의 값을 입력하세요.")
        addTextWatcher(binding.etWeight, 30, 200, "30kg ~ 200kg 범위의 값을 입력하세요.")


        binding.root.setOnTouchListener { _, _ ->
            if (activity != null && activity?.currentFocus != null) {
                hideKeyboard()
            }
            return@setOnTouchListener false
        }

        binding.btnNext.setOnClickListener {
            if (binding.tvDate.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "생년월일을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val date = binding.tvDate.text.toString()
            val height = binding.etHeight.text.toString().toIntOrNull()
            val weight = binding.etWeight.text.toString().toIntOrNull()

            if (height == null || height < 100 || height > 250) {
                binding.etHeight.error = "정상 범위 내의 키를 입력하세요."
                return@setOnClickListener
            }

            if (weight == null || weight < 30 || weight > 200) {
                binding.etWeight.error = "정상 범위 내의 몸무게를 입력하세요."
                return@setOnClickListener
            }
            //TODO: 다음 화면으로 이동
            viewModel.setBodyInfo(date, weight.toString(), height.toString())
            findNavController().navigate(R.id.action_selectInfoBodyInfoFragment_to_selectInfoRouteFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setState(SelectInfoState.STATE_BODY_INFO)
    }

    private fun addTextWatcher(editText: EditText, min: Int, max: Int, errorMsg: String) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val value = s.toString().toIntOrNull()
                editText.error =
                    if (value != null && (value < min || value > max)) errorMsg else null
            }
        })
    }

    private fun hideKeyboard() {
        val inputManager = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}