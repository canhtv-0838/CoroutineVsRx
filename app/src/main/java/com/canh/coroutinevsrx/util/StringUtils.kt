package com.canh.coroutinevsrx.util

object StringUtils {
    fun getSmallImage(image_path: String): String {
        val builder = StringBuilder()
        builder.append(Constants.BASE_IMAGE_PATH)
            .append(Constants.IMAGE_SIZE_W200)
            .append(image_path)
        return builder.toString()
    }
}