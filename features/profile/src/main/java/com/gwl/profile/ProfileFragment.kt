package com.gwl.profile

import android.os.Bundle
import com.example.profile.BR
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.gwl.core.BaseFragment
import com.gwl.core.initViewModel
import com.gwl.model.User

private const val ARG_PARAM = "data"

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(ARG_PARAM)
                ?: throw IllegalArgumentException("User should not be null.")
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun getViewModel(): ProfileViewModel {
        return initViewModel { ProfileViewModel(user) }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) =
            ProfileFragment().apply {
                arguments = Bundle().apply { putParcelable(ARG_PARAM, user) }
            }
    }
}
