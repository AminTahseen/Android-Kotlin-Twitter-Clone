package com.example.threadssocialmediaapp.di

import android.app.Application
import androidx.room.Room
import com.example.threadssocialmediaapp.models.local.TwitterCloneDatabase
import com.example.threadssocialmediaapp.models.local.repository.TwitterCloneRepo
import com.example.threadssocialmediaapp.models.local.repositoryImpl.TwitterCloneRepoImpl
import com.example.threadssocialmediaapp.models.remote.apiInterface.ApiInterface
import com.example.threadssocialmediaapp.models.remote.repository.CommentsRepo
import com.example.threadssocialmediaapp.models.remote.repository.PostsRepo
import com.example.threadssocialmediaapp.models.remote.repositoryImpl.CommentsRepoImpl
import com.example.threadssocialmediaapp.models.remote.repositoryImpl.PostsRepoImpl
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): ApiInterface =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://dummyapi.io/data/v1/")
            .client(okHttpClient)
            .build()
            .create(ApiInterface::class.java)


    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()


    @Provides
    @Singleton
    fun provideOkHttp(
        networkInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(networkInterceptor)
        .build()


    @Provides
    @Singleton
    fun provideNetworkInterceptor(): Interceptor {
        val appId = com.example.threadssocialmediaapp.BuildConfig.APP_KEY
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                // Add headers or modify the request if needed
                .addHeader("app-id", appId)
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providePostsRepo(api: ApiInterface): PostsRepo {
        return PostsRepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideCommentsRepo(api: ApiInterface): CommentsRepo {
        return CommentsRepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideTwitterCloneDatabase(app: Application): TwitterCloneDatabase {
        return Room.databaseBuilder(
            app,
            TwitterCloneDatabase::class.java,
            TwitterCloneDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTwitterCloneRepository(db: TwitterCloneDatabase): TwitterCloneRepo {
        return TwitterCloneRepoImpl(db.twitterCloneDao)
    }
}