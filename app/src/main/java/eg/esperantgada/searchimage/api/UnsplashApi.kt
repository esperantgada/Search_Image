package eg.esperantgada.searchimage.api

import eg.esperantgada.searchimage.util.Constant.Companion.AUTH
import eg.esperantgada.searchimage.util.Constant.Companion.ENDPOINT
import eg.esperantgada.searchimage.util.Constant.Companion.HEADER
import eg.esperantgada.searchimage.util.Constant.Companion.PAGE
import eg.esperantgada.searchimage.util.Constant.Companion.PER_PAGE
import eg.esperantgada.searchimage.util.Constant.Companion.QUERY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    @Headers(HEADER, AUTH)
    @GET(ENDPOINT)
    suspend fun searchImage(
        @Query(QUERY)
        query : String,

        @Query(PAGE)
        page : Int,

        @Query(PER_PAGE)
        perPage : Int
    ) : ApiResponse
}