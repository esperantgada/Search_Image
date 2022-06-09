package eg.esperantgada.searchimage.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import eg.esperantgada.searchimage.R
import eg.esperantgada.searchimage.adapter.ImageLoadStateAdapter
import eg.esperantgada.searchimage.adapter.ImagePagingAdapter
import eg.esperantgada.searchimage.data.Photo
import eg.esperantgada.searchimage.databinding.FragmentImagesBinding
import eg.esperantgada.searchimage.model.ImageViewModel

@AndroidEntryPoint
class ImagesFragment : Fragment(), ImagePagingAdapter.OnItemClickedListener {

    private val viewModel : ImageViewModel by viewModels()

    private var _binding : FragmentImagesBinding? = null
    private val binding get() = _binding!!

    /**
     * We pass the fragment itself in the adapter after setting OnClickListener on it
     */
    private val adapter = ImagePagingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Handles [header] and [footer] behavior in the recyclerView when loading images
         */
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ImageLoadStateAdapter{adapter.retry()},
                footer = ImageLoadStateAdapter{adapter.retry()}
            )

            //Sets clickListener on retry Button if there is error or the recyclerView is invisible
            retryButton.setOnClickListener {
                adapter.retry()
            }
        }

        //Observes photoList in the ViewModel class and updates UI accordingly
        viewModel.photosList.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        /**
         * Handles [recyclerView] [retryButton]... visibility depending on the loading state
         */
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
                resultStatusTextView.isVisible = loadState.source.refresh is LoadState.Error

                //If the recyclerView is empty, sets its visibility to false
                if (loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1){

                    recyclerView.isVisible = false
                    emptyTextStatus.isVisible = true

                }else{
                    emptyTextStatus.isVisible = false
                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        //Inflates the layout
        inflater.inflate(R.menu.images_menu, menu)

        //Gets reference to the view
        val search = menu.findItem(R.id.search_image)

        //Casts view as SearchView
        val searchView = search.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{

            //Takes a query from the user and submits it to the adapter by calling setQuery method from the ViewModel
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.setQuery(query)

                    //Removes keyboard after submitting a query
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }




    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    /**
     * Implements the [onItemClicked] method from the [interface] which is created in [ImagePagingAdapter]
     * by setting navigation from [ImagesFragment] to [ImageDetailsFragment]
     */
    override fun onItemClicked(photo: Photo) {
        val action = ImagesFragmentDirections.actionImagesFragmentToImageDetailsFragment(photo)
        findNavController().navigate(action)
    }
}