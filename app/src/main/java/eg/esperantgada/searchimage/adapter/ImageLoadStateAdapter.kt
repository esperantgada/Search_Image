package eg.esperantgada.searchimage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import eg.esperantgada.searchimage.databinding.LoadImageStateFooterBinding

class ImageLoadStateAdapter(private val retryListener : () -> Unit) :
    LoadStateAdapter<ImageLoadStateAdapter.ImageLoadStateViewHolder>() {



    inner class ImageLoadStateViewHolder(
        private val binding: LoadImageStateFooterBinding): RecyclerView.ViewHolder(binding.root){


        //Sets clickListener on retry Button
        init {
            binding.retryButton.setOnClickListener {
                retryListener.invoke()
            }
        }

            fun bind(loadState: LoadState){
                binding.apply {
                    progressBar.isVisible = loadState is LoadState.Loading
                    retryButton.isVisible = loadState !is LoadState.Loading
                    loadStateTextView.isVisible = loadState !is LoadState.Loading
                }
            }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): ImageLoadStateViewHolder {

        val inflatedLayout = LoadImageStateFooterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageLoadStateViewHolder(inflatedLayout)
    }


    override fun onBindViewHolder(holder: ImageLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}