package com.kafka.data.feature.item

import com.kafka.data.entities.ItemDetail
import com.kafka.data.entities.isAudio
import com.kafka.data.entities.isText
import com.kafka.data.model.item.File
import java.net.URL
import javax.inject.Inject

class FileMapper @Inject constructor() {
    fun map(file: File, item: ItemDetail, prefix: String, localUri: String?) = file.run {
        val extension = name.split(".").last()
        com.kafka.data.entities.File(
            fileId = fileId,
            itemId = item.itemId,
            itemTitle = item.title,
            size = size?.toLongOrNull(),
            name = name,
            title = title ?: name.removeSuffix(".$extension"),
            extension = extension,
            creator = (creator?.joinToString(", ") ?: artist) ?: item.creator,
            time = length,
            format = format.orEmpty(),
            playbackUrl = if (extension.isAudio()) URL("$prefix/$name").toString() else null,
            readerUrl = if (extension.isText()) URL("$prefix/$name").toString() else null,
            downloadUrl = URL("$prefix/$name").toString(),
            coverImage = item.coverImage,
            localUri = localUri,
        )
    }
}
