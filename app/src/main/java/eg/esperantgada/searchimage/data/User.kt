package eg.esperantgada.searchimage.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name : String,
    val username : String
): Parcelable{
    val urlAttribution get() = "https://unsplash.com/$username?utm=_source=SearchImage&utm_medium=referral"
}
