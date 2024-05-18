package com.example.threadssocialmediaapp.views.dialogs

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.threadssocialmediaapp.R
import com.example.threadssocialmediaapp.databinding.DialogFullPictureBinding

class FullPictureDialog(
    val imageURL: String
) : DialogFragment() {
    lateinit var binding: DialogFullPictureBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.rounded_corner_bg)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_full_picture,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(binding.root).load(imageURL)
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(Resources.getSystem().displayMetrics.widthPixels)
            .into(binding.imageToDisplay)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}