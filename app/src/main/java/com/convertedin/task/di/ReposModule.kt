package com.convertedin.task.di

import com.convertedin.task.data.repository.*
import com.convertedin.task.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReposModule {

    @Binds
    @ViewModelScoped
    abstract fun provideHomeRepo(repoImpl: HomeRepoImpl): HomeRepo

    @Binds
    @ViewModelScoped
    abstract fun provideUserRepo(repoImpl: UserRepoImpl): UserRepo

}