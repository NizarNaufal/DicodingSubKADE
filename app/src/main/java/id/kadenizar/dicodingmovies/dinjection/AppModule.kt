package id.kadenizar.dicodingmovies.dinjection

import id.kadenizar.dicodingmovies.core.domain.usecase.ShowInteractor
import id.kadenizar.dicodingmovies.core.domain.usecase.ShowUseCase
import id.kadenizar.dicodingmovies.core.utils.Const
import id.kadenizar.dicodingmovies.view.movie.MovieViewModel
import id.kadenizar.dicodingmovies.view.movie.details.DetailViewModel
import id.kadenizar.dicodingmovies.view.movie.series.SeriesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory<ShowUseCase> { ShowInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    scope(named(Const.VIEW_MODEL)) {
        viewModel { MovieViewModel(get()) }
        viewModel { SeriesViewModel(get()) }
        viewModel { DetailViewModel(get()) }
    }

}