package com.arisman.nangdia.domain.use_case

import com.arisman.nangdia.domain.model.MediaDetail
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaDetailUseCase @Inject constructor(
    private val repository: MdbListRepository
) {
    operator fun invoke(
        id: String,
        mediaType: String,
        provider: String
    ): Flow<Resource<MediaDetail>> {
        return repository.getMediaDetail(id, mediaType, provider)
    }
}

