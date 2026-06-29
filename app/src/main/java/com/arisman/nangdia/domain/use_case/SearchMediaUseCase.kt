package com.arisman.nangdia.domain.use_case

import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMediaUseCase @Inject constructor(
    private val repository: MdbListRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<MediaSearchResult>>> {
        return repository.searchMedia(query)
    }
}

