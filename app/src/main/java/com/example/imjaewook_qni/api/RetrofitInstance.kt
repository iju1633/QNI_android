package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.ui.repository.MainRepository
import com.example.imjaewook_qni.ui.repository.MainRepositoryImpl
import com.example.imjaewook_qni.ui.repository.UserRepository
import com.example.imjaewook_qni.ui.repository.UserRepositoryImpl
import com.example.imjaewook_qni.util.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit) : UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiHelper(userApiHelper: UserApiHelperImpl): UserApiHelper =
        userApiHelper

    @Provides
    @Singleton
    fun provideUserRepository(userRepository : UserRepositoryImpl): UserRepository =
        userRepository


    @Provides
    @Singleton
    fun provideMainApiService(retrofit: Retrofit): MainApiService =
        retrofit.create(MainApiService::class.java)

    @Provides
    @Singleton
    fun provideMainApiHelper(mainApiHelper: MainApiHelperImpl): MainApiHelper =
        mainApiHelper

    @Provides
    @Singleton
    fun provideMainRepository(mainRepository: MainRepositoryImpl): MainRepository =
        mainRepository
}