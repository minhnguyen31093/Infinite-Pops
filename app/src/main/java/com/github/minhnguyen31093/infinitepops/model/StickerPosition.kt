package com.github.minhnguyen31093.infinitepops.model

data class StickerPosition(
        var stickerPackId: String?,
        var stickerPackName: String?,
        var stickerId: String?,
        var giphyId: String?,
        var topLeft: Position?,
        var bottomRight: Position?,
        var rotationDegrees: Double,
        var zOrder: Int,
        var stickerUrl: String?
)
