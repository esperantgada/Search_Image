@file:Suppress("KDocUnresolvedReference")

package eg.esperantgada.searchimage.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import eg.esperantgada.searchimage.api.UnsplashApi
import eg.esperantgada.searchimage.paging.ImagePagingSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * We injected the [unsplashApi] instance we created in [AppModule] in the repository
 */

@Singleton
class ImageRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    //This method will get result from the ImagePagingSource by following this configuration
    fun getSearchResult(query : String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            ImagePagingSource(unsplashApi, query)
        }
    ).liveData
}