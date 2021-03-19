package com.maciel.murillo.musales.presentation.ad_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.maciel.murillo.musales.R
import com.maciel.murillo.musales.core.helper.EventObserver
import com.maciel.murillo.musales.databinding.FragmentAdDetailsBinding
import com.maciel.murillo.musales.domain.model.Ad
import com.synnapps.carouselview.CarouselView
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdDetailFragment : Fragment() {

    private val adDetailViewModel: AdDetailViewModel by viewModel()

    private val arguments: AdDetailFragmentArgs by navArgs()
    private val ad: Ad by lazy { arguments.ad }

    private lateinit var carouselView: CarouselView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentAdDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = adDetailViewModel
            setUpView(this)
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adDetailViewModel.onCreatedScreen(ad)

        setUpShowPhoneObserver()

        setUpCarouselView()
    }

    private fun setUpView(binding: FragmentAdDetailsBinding) = with(binding) {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        this@AdDetailFragment.carouselView = carrousel
    }

    private fun setUpShowPhoneObserver() {
        adDetailViewModel.showPhone.observe(viewLifecycleOwner, EventObserver {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", ad.phone, null))
            startActivity(intent)
        })
    }

    private fun setUpCarouselView() = with(carouselView) {
        setImageListener { position, imageView ->
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            Glide.with(imageView.context)
                .load(ad.images[position])
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.standard)
                .into(imageView)
        }
        setImageClickListener { position ->
            ImageDetailsDialog.show(childFragmentManager, ad.images[position])
        }
        pageCount = ad.images.size
    }
}