package com.example.nurtdinov.data

import com.example.nurtdinov.data.database.LocalDataSource
import com.example.nurtdinov.data.network.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) {

    val remote = remoteDataSource
    val local = localDataSource
}