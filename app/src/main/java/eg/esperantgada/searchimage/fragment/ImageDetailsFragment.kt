package eg.esperantgada.searchimage.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import eg.esperantgada.searchimage.R
import eg.esperantgada.searchimage.databinding.FragmentImageDetailsBinding


class ImageDetailsFragment : Fragment() {

    //Allows to get arguments sent from ImagesFragments
    private val navigationArgs : ImageDetailsFragmentArgs by navArgs()

    private var _binding : FragmentImageDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImageDetailsBinding.inflate(inflater, container, false)

        /**
         * Binds Views with photos details
         */
        binding.apply {
            //Retrieve photos from navigation
            val photos = navigationArgs.photos

            Glide.with(this@ImageDetailsFragment)
                .load(photos.urls.regular)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        progressBar.isVisible = false

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        progressBar.isVisible = false
                        ownerTextView.isVisible = true
                        imageDescriptionTextView.isVisible = photos.description != null

                        return false
                    }

                })
                .into(imageDetailedView)

            imageDescriptionTextView.text = photos.description

            //Converts attribution Url in Uri
            val uri = Uri.parse(photos.user.urlAttribution)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            //Sets ClickListener on User's name and sends the link to a browser by intent
            ownerTextView.apply {
                text = getString(R.string.photo_owned_by, photos.user.name)
                setOnClickListener {
                    context.startActivity(intent)
                }

                //The name appears like a link
                paint.isUnderlineText = true
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}