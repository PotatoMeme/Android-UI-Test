package com.potatomeme.screen.planfit.presentation.on_board

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.potatomeme.screen.planfit.R
import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val viewModel: PlanfitSplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.isLoginFlow.collect { loginEvent: LoginEvent ->
                    when (loginEvent) {
                        is LoginEvent.Login -> {
                            when (loginEvent.loginType) {
                                PlanfitLoginType.None -> {
                                    Log.d(TAG, "onViewCreated PlanfitLoginType.None")
                                    view.findNavController().navigate(
                                        R.id.action_splashFragment_to_signupSigninFragment
                                    )
                                }

                                PlanfitLoginType.KakaoTalk, PlanfitLoginType.Google, PlanfitLoginType.Facebook -> {
                                    Log.d(TAG, "onViewCreated PlanfitLoginType.KakaoTalk, PlanfitLoginType.Google, PlanfitLoginType.Facebook")
                                    binding.textView.text =
                                        "로그인이 되었습니다. ${loginEvent.loginType}"
                                }
                            }
                        }

                        LoginEvent.UnChecked -> {
                            Log.d(TAG, "onViewCreated LoginEvent.UnChecked")
                            binding.textView.text =
                                "로그인 확인중입니다."
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkLoginState()
    }

    companion object{
        private const val TAG = "SplashFragment"
    }
}