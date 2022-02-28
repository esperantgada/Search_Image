package eg.esperantgada.searchimage.dependencyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eg.esperantgada.searchimage.api.UnsplashApi
import eg.esperantgada.searchimage.util.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * We created an instance of [Retrofit] and [UnsplashApi] that will be injected in the corresponding class
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit = Retrofit
        .Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) : UnsplashApi = retrofit.create(UnsplashApi::class.java)
}