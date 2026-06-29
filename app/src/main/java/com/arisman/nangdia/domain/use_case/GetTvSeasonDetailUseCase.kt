package com.arisman.nangdia.domain.use_case

import com.arisman.nangdia.domain.model.MediaSeason
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTvSeasonDetailUseCase @Inject constructor(
    private val repository: MdbListRepository
) {
    operator fun invoke(
        tvId: Int,
        seasonNumber: Int
    ): Flow<Resource<MediaSeason>> {
        return repository.getTvSeasonDetail(tvId, seasonNumber)
    }
}
