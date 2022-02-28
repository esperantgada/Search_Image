package eg.esperantgada.searchimage.util


class Constant {
    companion object{
        private const val API_KEY = "k0tA-5R-tZAR7Z_sxd9lBSaxI7X-Dfn7rlFlXVZwccE"
        const val BASE_URL = "https://api.unsplash.com/"
        const val HEADER = "Accept-Version: v1"
        const val AUTH = "Authorization: Client-ID $API_KEY"
        const val ENDPOINT = "search/photos"
        const val QUERY = "query"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
        const val STARTING_INDEX = 1
        const val DEFAULT_QUERY = "cats"
        const val CURRENT_QUERY = "current_query"
    }
}