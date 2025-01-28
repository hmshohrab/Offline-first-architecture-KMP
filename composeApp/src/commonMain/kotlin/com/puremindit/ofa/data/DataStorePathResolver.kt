package com.puremindit.ofa.data

import okio.Path

fun interface DataStorePathResolver {
    fun resolve(filename: String): Path
}
