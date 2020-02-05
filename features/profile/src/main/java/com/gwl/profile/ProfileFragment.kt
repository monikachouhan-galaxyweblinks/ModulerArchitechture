package com.gwl.profile

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

    override fun initObservers() {
        super.initObservers()
        setHasOptionsMenu(true)
    }
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater?.inflate(R.menu.menu_profile_setting, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_editprofile -> {
                if (item.title.toString() == getString(R.string.edit)) {
                    item.title = getString(R.string.save)
                    mViewModel.isEditable.set(true)
                } else {
                    mViewModel.updateUser()
                    item.title = getString(R.string.edit)
                    mViewModel.isEditable.set(false)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
