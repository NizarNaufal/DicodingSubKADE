package id.kadenizar.dicodingmovies.favorite.favorite

import androidx.lifecycle.*
import id.kadenizar.dicodingmovies.core.data.Resource
import id.kadenizar.dicodingmovies.core.domain.model.Show
import id.kadenizar.dicodingmovies.core.domain.usecase.ShowUseCase

class FavoriteViewModel(private val showUseCase: ShowUseCase) : ViewModel() {
    private val refreshTrigger = MutableLiveData(Unit)

    private var movieList = refreshTrigger.switchMap {
        showUseCase.getFavoriteMovieList().asLiveData()
    }

    private var seriesList = refreshTrigger.switchMap {
        showUseCase.getFavoriteSeriesList().asLiveData()
    }

    fun getFavoriteMovies(): LiveData<Resource<List<Show>>> = movieList

    fun getFavoriteSeries(): LiveData<Resource<List<Show>>> = seriesList

    fun refresh() {
        refreshTrigger.value = Unit
    }
}