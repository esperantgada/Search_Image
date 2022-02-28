package eg.esperantgada.searchimage.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import eg.esperantgada.searchimage.repository.ImageRepository

/**
 * Dagger depends on this application class to work
 */
@HiltAndroidApp
class ImageApplication : Application()