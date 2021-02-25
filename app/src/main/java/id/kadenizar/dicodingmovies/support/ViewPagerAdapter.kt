package id.kadenizar.dicodingmovies.support

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.kadenizar.dicodingmovies.view.movie.search.movieseries.SearchMovieFragment
import id.kadenizar.dicodingmovies.view.movie.search.movieseries.SearchSeriesFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 2

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = SearchMovieFragment()
            1 -> fragment = SearchSeriesFragment()
        }

        return fragment as Fragment
    }
}