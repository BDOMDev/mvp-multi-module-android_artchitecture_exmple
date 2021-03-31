package com.oshan.data.di


import com.apollographql.apollo.ApolloClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.oshan.data.BuildConfig
import com.oshan.data.datasource.GithubApiDataSource
import com.oshan.data.datasource.GithubProfileDataSource
import com.oshan.data.repo.GithubProfileRepository
import com.oshan.domain.DomainLayerContract
import com.oshan.domain.DomainLayerContract.Data.Companion.DATA_REPOSITORY_TAG
import com.oshan.domain.model.UserProfileResponse
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val TIMEOUT = 10L

@Module
object RepositoryModule {

    @Provides
    @Named(DATA_REPOSITORY_TAG)
    fun provideDataRepository(
        @Named(GithubProfileDataSource.GITHUB_PROFILE_DATA_SOURCE_TAG)
        numberFactDs: GithubProfileDataSource
    ): @JvmSuppressWildcards DomainLayerContract.Data.DataRepository<UserProfileResponse> =
        GithubProfileRepository.apply { numberFactDataSource = numberFactDs }

}

@Module
class DataSourceModule {

    @Provides
    @Named(GithubProfileDataSource.GITHUB_PROFILE_DATA_SOURCE_TAG)
    fun provideGithubProfileDataSource(ds: GithubApiDataSource): GithubProfileDataSource = ds

    @Provides
    fun provideApolloClient(): ApolloClient = ApolloClient.builder()
            .serverUrl(GithubProfileDataSource.BASE_URL)
            .okHttpClient(getHttpClient())
            .build()
}

/**
 * Interceptor to add auth token to requests
 */
class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_ACCESS_KEY}")

        return chain.proceed(requestBuilder.build())
    }
}

fun getHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(AuthInterceptor())
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

}