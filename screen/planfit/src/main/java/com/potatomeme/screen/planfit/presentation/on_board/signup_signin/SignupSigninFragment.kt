package com.potatomeme.screen.planfit.presentation.on_board.signup_signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.databinding.FragmentSignupSigninBinding
import com.potatomeme.screen.planfit.domain.usecase.SetPlanfitLoginTypeUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignupSigninFragment : Fragment() {
    private lateinit var binding: FragmentSignupSigninBinding

    @Inject
    lateinit var setPlanfitLoginTypeUseCase: SetPlanfitLoginTypeUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignupSigninBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnKakaoLogin.setOnClickListener {
            Log.d(TAG, "onViewCreated: btnKakaoLogin clicked")
            lifecycleScope.launch {
                setPlanfitLoginTypeUseCase(PlanfitLoginType.KakaoTalk)
                view.findNavController().popBackStack()
            }
        }
        binding.btnFacebookLogin.setOnClickListener {
            Log.d(TAG, "onViewCreated: btnFacebookLogin clicked")
            lifecycleScope.launch {
                setPlanfitLoginTypeUseCase(PlanfitLoginType.Facebook)
                view.findNavController().popBackStack()
            }
        }
        binding.btnGoogleLogin.setOnClickListener {
            Log.d(TAG, "onViewCreated: btnGoogleLogin clicked")
            lifecycleScope.launch {
                view.findNavController().popBackStack()
                setPlanfitLoginTypeUseCase(PlanfitLoginType.Google)
            }
        }
    }

    companion object{
        private const val TAG = "SignupSigninFragment"
    }
}