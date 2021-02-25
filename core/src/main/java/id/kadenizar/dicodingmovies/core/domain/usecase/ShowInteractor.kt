package id.kadenizar.dicodingmovies.core.domain.usecase

import id.kadenizar.dicodingmovies.core.data.Resource
import id.kadenizar.dicodingmovies.core.domain.model.Show
import id.kadenizar.dicodingmovies.core.domain.repository.IShowRepository
import kotlinx.coroutines.flow.Flow

class ShowInteractor(private val showRepository: IShowRepository) : ShowUseCase {
    override fun getMovieList(page: Int): Flow<Resource<List<Show>>> {
        return showRepository.getMovieList(page)
    }

    override fun getSeriesList(page: Int): Flow<Resource<List<Show>>> {
        return showRepository.getSeriesList(page)
    }

    override fun getMovieDetail(movieId: String): Flow<Resource<Show>> {
        return showRepository.getMovieDetail(movieId)
    }

    override fun getSeriesDetail(seriesId: String): Flow<Resource<Show>> {
        return showRepository.getSeriesDetail(seriesId)
    }

    override fun getFavoriteMovieList(): Flow<Resource<List<Show>>> {
        return showRepository.getFavoriteMovieList()
    }

    override fun getFavoriteSeriesList(): Flow<Resource<List<Show>>> {
        return showRepository.getFavoriteSeriesList()
    }

    override fun getSimilarMovieList(movieId: String): Flow<Resource<List<Show>>> {
        return showRepository.getSimilarMovieList(movieId)
    }

    override fun getSimilarSeriesList(seriesId: String): Flow<Resource<List<Show>>> {
        return showRepository.getSimilarSeriesList(seriesId)
    }

    override fun searchMovie(keyword: String): Flow<Resource<List<Show>>> {
        return showRepository.searchMovie(keyword)
    }

    override fun searchSeries(keyword: String): Flow<Resource<List<Show>>> {
        return showRepository.searchSeries(keyword)
    }

    override suspend fun setFavorite(show: Show) {
        showRepository.setFavorite(show)
    }
}