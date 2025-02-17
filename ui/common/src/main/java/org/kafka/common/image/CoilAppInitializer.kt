package org.kafka.common.image

import android.app.Application
import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import org.kafka.base.AppInitializer
import org.kafka.base.CoroutineDispatchers
import java.io.File
import javax.inject.Inject

class CoilAppInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatchers: CoroutineDispatchers,
    private val okHttpClient: OkHttpClient,
) : AppInitializer {
    override fun init(application: Application) {
        Coil.setImageLoader {
            ImageLoader.Builder(application)
                .okHttpClient(okHttpClient)
                .dispatcher(dispatchers.io)
                .fetcherDispatcher(dispatchers.io)
                .diskCache(
                    DiskCache.Builder().directory(File(context.cacheDir, "images_cache")).build()
                )
                .build()
        }
    }
}
