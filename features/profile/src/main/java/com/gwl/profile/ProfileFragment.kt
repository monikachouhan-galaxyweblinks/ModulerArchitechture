package com.gwl.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.profile.BR
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.gwl.core.*
import com.gwl.core.Common.Companion.CAMERA
import com.gwl.core.FilesUtil.getRealPathFromGalleryUri
import com.gwl.core.FilesUtil.getRealPathFromURI
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File

private const val ARG_PARAM = "data"

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    var mPickProfilePictureClick: Boolean = false

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun getViewModel(): ProfileViewModel {
        return initViewModel { ProfileViewModel() }
    }

    override fun initObservers() {
        super.initObservers()
        if (!checkStorageAndCameraPermission()) requestPermission()
        setHasOptionsMenu(true)
        mViewModel.mPickProfilePicture.observe {
            mPickProfilePictureClick = it
            if (checkStorageAndCameraPermission()) requestPermission() else showPictureDialog()
        }
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

    fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(activity, R.style.AlertDialogTheme)
        pictureDialog.setTitle(getString(R.string.select_action))
        val pictureDialogItems = arrayOf(
            getString(R.string.select_photo_from_gallery),
            getString(R.string.select_photo_from_camera)
        )
        pictureDialog.setItems(pictureDialogItems) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
            }
        }
        val dialog = pictureDialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.background_light)
        // dialog.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.rounded_white_bg))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Common.PERMISSION_REQ_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    if (mPickProfilePictureClick)
                        showPictureDialog()
                }
                return
            }
        }
    }

    private fun choosePhotoFromGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, Common.GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            activity?.also {
                if (requestCode == Common.GALLERY) {
                    data?.data?.also { uri ->
                        try {
                            val finalFile = File(it.getRealPathFromGalleryUri(uri))
                            profileImage.loadImage(finalFile.absolutePath)
                            // mViewModel.setProfileImagePath(finalFile.absolutePath)
                        } catch (e: Exception) {
                            mDataBinding.root.showSnackbar(StringUtil.getString(R.string.unable_to_upload_profile_picture))
                        }
                    }
                } else if (requestCode == CAMERA) {
                    val thumbnail = data?.extras?.get("data") as Bitmap
                    val tempUri = FilesUtil.getImageUri(it, thumbnail)
                    val finalFile = File(it.getRealPathFromURI(tempUri))
                    profileImage.loadImage(it.getRealPathFromURI(tempUri))
                }
            }
        }
    }
}
