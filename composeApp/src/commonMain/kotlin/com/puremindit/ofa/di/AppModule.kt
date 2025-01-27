package com.puremindit.ofa.di

import com.puremindit.ofa.PostRepository
import com.puremindit.ofa.PostStoreFactory
import com.puremindit.ofa.RealPostOperations
import com.puremindit.ofa.RealPostRepository
import org.koin.dsl.module
import org.mobilenativefoundation.store.core5.ExperimentalStoreApi

@OptIn(ExperimentalStoreApi::class)
val appModule = module {
     single { RealPostOperations(get()) }
    single { PostStoreFactory(get()) }
    single { get<PostStoreFactory>().create() }
    single { RealPostRepository(get()) }
}