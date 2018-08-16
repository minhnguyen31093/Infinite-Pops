package com.github.minhnguyen31093.infinitepops.model

data class RejamOf(
        var id: String?,
        var date: Long,
        var postingUserId: String?,
        var text: String?,
        var imageSrc: String?,
        var stickerPositions: List<StickerPosition>?,
        var containsPhoto: Boolean,
        var videoSrc: List<String>?,
        var thumbnailSrc: String?,
        var videoDuration: Int,
        var subItems: List<String>?,
        var likeCount: Int,
        var selfLiked: Boolean,
        var commentCount: Int,
        var viewCount: Int,
        var frontEndId: String?,
        var rejamCount: Int,
        var selfRejammed: Boolean,
        var videoViewQuartiles: String?,
        var rejamOf: String?,
        var richContent: String?
)
