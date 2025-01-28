package com.puremindit.ofa.di

import com.puremindit.ofa.AppHttpClient
import com.puremindit.ofa.PostDetailViewModel
import com.puremindit.ofa.PostOperations
import com.puremindit.ofa.PostRepository
import com.puremindit.ofa.PostStore
import com.puremindit.ofa.PostStoreFactory
import com.puremindit.ofa.RealPostOperations
import com.puremindit.ofa.RealPostRepository
import com.puremindit.ofa.data.db.AppDatabase
import com.puremindit.ofa.data.db.Factory
import com.puremindit.ofa.data.db.getRoomDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.mobilenativefoundation.store.core5.ExperimentalStoreApi

@OptIn(ExperimentalStoreApi::class)
val appModule = module {
    single { AppHttpClient.create() }
    single<AppDatabase> { getRoomDatabase(Factory().getDatabaseBuilder()) }
    single { get<AppDatabase>().postDao() }
    single { get<AppDatabase>().postFailedDao() }
    single<PostOperations> { RealPostOperations(get()) }
    single { PostStoreFactory(get(),get(),get()) }
    single<PostStore> { get<PostStoreFactory>().create() }
    single<PostRepository> { RealPostRepository(get()) }
    viewModel { PostDetailViewModel(get()) }
}