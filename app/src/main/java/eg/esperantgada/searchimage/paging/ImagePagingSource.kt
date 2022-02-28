package eg.esperantgada.searchimage.paging

import androidx.paging.PagingSource
import eg.esperantgada.searchimage.api.UnsplashApi
import eg.esperantgada.searchimage.data.Photo
import eg.esperantgada.searchimage.util.Constant
import eg.esperantgada.searchimage.util.Constant.Companion.STARTING_INDEX
import retrofit2.HttpException
import java.io.IOException

/**
 * This class knows how to load images from the API
 * [STARTING_INDEX] represents the first page
 */

class ImagePagingSource(
    private val unsplashApi: UnsplashApi,
    private val query : String
) : PagingSource<Int, Photo>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key?: STARTING_INDEX

        return try {
            val response = unsplashApi.searchImage(query, position, params.loadSize)
            val photoList = response.results

            LoadResult.Page(
                data = photoList,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (photoList.isEmpty()) null else position + 1
            )
        }catch (exception : IOException){
            LoadResult.Error(exception)

        }catch (exception : HttpException){
            LoadResult.Error(exception)
        }
    }
}