package id.kadenizar.dicodingmovies.favorite.di

import id.kadenizar.dicodingmovies.core.utils.Const
import id.kadenizar.dicodingmovies.favorite.favorite.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val favoriteModule = module {
    scope(named(Const.VIEW_MODEL)) {
        viewModel { FavoriteViewModel(get()) }
    }
}
