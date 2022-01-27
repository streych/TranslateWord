package com.example.translateword.dagger

import com.example.translateword.DataModel
import com.example.translateword.DataSource
import com.example.translateword.Repository
import com.example.translateword.RepositoryImplementation
import com.example.translateword.mvpmainfrag.DataSourceLocal
import com.example.translateword.mvpmainfrag.RetrofitImplementation
import com.example.translateword.mvpmainfrag.RoomDataBaseImplementation
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    // можно видеть в любой части модуля
    internal fun provideRepositoryRemote(
        @Named(NAME_REMOTE) dataSourceRemote:
        DataSource<List<DataModel>>
    ): Repository<List<DataModel>> = RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(
        @Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>
    ): Repository<List<DataModel>> = RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> = RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> = RoomDataBaseImplementation()


}