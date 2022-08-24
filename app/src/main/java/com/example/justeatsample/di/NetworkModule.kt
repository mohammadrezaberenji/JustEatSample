package com.example.justeatsample.di

import com.example.justeatsample.BuildConfig
import com.example.justeatsample.data.db.Dao
import com.example.justeatsample.data.source.remote.webService.WebService
import com.example.justeatsample.data.source.repository.RepositoryImp
import com.example.justeatsample.data.source.repository.Repository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().serializeNulls().create()
    }

    @Singleton
    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }


    @Singleton
    @Provides
    fun getOkHttpClient(httpInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .followSslRedirects(false)
            .addInterceptor(httpInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit.Builder): WebService {
        return retrofit.build().create(WebService::class.java)

    }

    @Singleton
    @Provides
    fun provideRepositoryImp(dao: Dao, webService: WebService) =
        RepositoryImp(webService = webService, dao = dao) as Repository

//    @Module
//    @InstallIn(ViewModelComponent::class)
//    abstract class RepositoryModule {
//
//        @Binds
//        abstract fun bindRepository(repository: Repository): RepositoryImp
//    }

}