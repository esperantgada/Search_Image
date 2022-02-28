package eg.esperantgada.searchimage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import eg.esperantgada.searchimage.R
import eg.esperantgada.searchimage.data.Photo
import eg.esperantgada.searchimage.databinding.ApiPhotosItemBinding

class ImagePagingAdapter(private val itemListener: OnItemClickedListener) :
    PagingDataAdapter<Photo, ImagePagingAdapter.ImagePagingViewHolder>(DiffCallback) {

   inner class ImagePagingViewHolder(
        private val binding: ApiPhotosItemBinding): RecyclerView.ViewHolder(binding.root){

       /**
        * Sets ClickListener on photos
        */
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition

                //Checks if the position or the Item index is null so that the app doesn't crash
                if (position != RecyclerView.NO_POSITION){

                    //Gets item or photo by its position in the recyclerView
                    val item = getItem(position)

                    //If item is not null, sets ClickListener on it
                    if (item != null){
                        itemListener.onItemClicked(item)
                    }
                }
            }
        }



        //Use Glide library to load photos from the API and bind views with photos details as needed
            fun bind(photo: Photo){
                binding.apply {
                    Glide.with(itemView)
                        .load(photo.urls.regular)
                        .circleCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_error)
                        .into(apiImages)

                    userNameText.text = photo.user.username
                }
            }

    }


    /**
     * Interface that helps to set ClickListener on item. It will be implemented in the fragment
     */
    interface OnItemClickedListener{
        fun onItemClicked(photo: Photo)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ImagePagingAdapter.ImagePagingViewHolder {
        val inflatedLayout = ApiPhotosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImagePagingViewHolder(inflatedLayout)
    }


    override fun onBindViewHolder(holder: ImagePagingAdapter.ImagePagingViewHolder, position: Int) {
        val currentPhoto = getItem(position)

        if (currentPhoto != null){
            holder.bind(currentPhoto)
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Photo>(){
        override fun areItemsTheSame(
            oldItem: Photo,
            newItem: Photo): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: Photo,
            newItem: Photo): Boolean = oldItem == newItem

    }
}