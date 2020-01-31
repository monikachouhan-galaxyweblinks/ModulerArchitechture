package com.gwl.profile

import com.example.profile.BR
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.gwl.core.BaseFragment
import com.gwl.core.initViewModel

private const val ARG_PARAM = "data"

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun getViewModel(): ProfileViewModel {
        return initViewModel { ProfileViewModel() }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    /* companion object {
         @JvmStatic
         fun newInstance(user: User) =
             ProfileFragment().apply {
                 arguments = Bundle().apply { putParcelable(ARG_PARAM, user) }
             }
     }*/
}
