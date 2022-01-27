package com.example.translateword.dagger

import com.example.translateword.DataModel
import com.example.translateword.Repository
import com.example.translateword.mvpmainfrag.DataSourceLocal
import com.example.translateword.mvpmainfrag.DataSourceRemote
import com.example.translateword.mvpmainfrag.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
