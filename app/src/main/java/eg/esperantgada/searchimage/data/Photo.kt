package eg.esperantgada.searchimage.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id : String,
    val description : String?,
    val user: User,
    val urls: PhotoUrl
): Parcelable