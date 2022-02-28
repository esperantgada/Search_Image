package eg.esperantgada.searchimage.api

import eg.esperantgada.searchimage.data.Photo

//Holds a list of photos
data class ApiResponse(val results : List<Photo>)