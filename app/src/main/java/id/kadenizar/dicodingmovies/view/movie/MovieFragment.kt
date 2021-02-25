package id.kadenizar.dicodingmovies.view.movie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.kadenizar.dicodingmovies.R
import id.kadenizar.dicodingmovies.core.data.Resource
import id.kadenizar.dicodingmovies.core.domain.model.Show
import id.kadenizar.dicodingmovies.core.ui.ShowsAdapter
import id.kadenizar.dicodingmovies.core.utils.Const
import id.kadenizar.dicodingmovies.databinding.FragmentMovieBinding
import id.kadenizar.dicodingmovies.services.IView
import id.kadenizar.dicodingmovies.services.ViewNetworkState
import id.kadenizar.dicodingmovies.services.base.BaseFragment
import id.kadenizar.dicodingmovies.support.showSnackBar
import id.kadenizar.dicodingmovies.support.showToast
import id.kadenizar.dicodingmovies.view.movie.adapter.BannerAdapter
import id.kadenizar.dicodingmovies.view.movie.details.DetailActivity
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.scope.viewModel
import org.koin.core.qualifier.named

class MovieFragment : BaseFragment(),ViewNetworkState,IView {

    //Scope and Koin DI for ViewModel
    private val scopeId = "MovieScope"
    private val moduleMovie = getKoin().getOrCreateScope(scopeId, named(Const.VIEW_MODEL))
    private val viewModel: MovieViewModel by moduleMovie.viewModel(this)

    //Binding
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    //Adapter
    private lateinit var movieAdapter: ShowsAdapter
    private lateinit var bannerAdapter: BannerAdapter

    private lateinit var bottomNavigationView: BottomNavigationView
    private var currentPage = 1
    private var maxPage = 6
    private var lastBottomLocation = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Trigger to load data
        viewModel.setPage(currentPage)

        initView()
        viewModelObserver() //Load movie list
    }

    private fun viewModelObserver() {
        viewModel.getMovies().observe(viewLifecycleOwner, { movieList ->
            when (movieList) {
                is Resource.Loading -> {
                    startShimmer()
                }

                is Resource.Success -> {
                    val data = movieList.data as ArrayList<Show>
                    if (!data.isNullOrEmpty()) {
                        movieAdapter.setList(data)
                        bannerAdapter.setBanner(data)
                        stopShimmer()
                    } else {
                        activity?.showToast(getString(R.string.no_movie_found))
                        snackBarRetry(movieList.message)
                    }
                    //Prevent re-shimmer
                    viewModel.setAlreadyShimmer()
                }

                is Resource.Error -> {
                    //Show snackbar for retry load data
                    snackBarRetry(movieList.message)
                }
            }
        })
    }

    private fun snackBarRetry(message: String?) {
        showSnackBar(requireContext(), bottomNavigationView, message) {
            viewModel.refresh()
        }
    }

    private fun startShimmer() {
        if (lastBottomLocation == 0) {
            with(binding) {
                shimmerLayoutMovie.startShimmer()
                shimmerLayoutMovie.visibility = View.VISIBLE
            }
        }
    }

    private fun stopShimmer() {
        with(binding) {
            shimmerLayoutMovie.stopShimmer()
            shimmerLayoutMovie.visibility = View.GONE
            tvMoviePopularTitle.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        moduleMovie.close()
    }

    override fun initView() {
        bottomNavigationView = requireActivity().findViewById(R.id.nav_view)

        //Prevent re-shimmer
        if (viewModel.getIsAlreadyShimmer())
            stopShimmer()
        else
            startShimmer()

        bannerAdapter = BannerAdapter(requireContext())
        movieAdapter = ShowsAdapter()

        //OnClick go to DetailActivity
        movieAdapter.onItemClick = { selectedShow ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_SHOW_ID, selectedShow.id)
            intent.putExtra(DetailActivity.EXTRA_SHOW_TYPE, selectedShow.showType)
            startActivity(intent)
        }

        //Change grid layout spanCount when Landscape/Portrait
        val phoneOrientation = requireActivity().resources.configuration.orientation
        val spanCount = if (phoneOrientation == Configuration.ORIENTATION_PORTRAIT) 3 else 7

        with(binding) {
            rvMovie.layoutManager = GridLayoutManager(context, spanCount)
            rvMovie.hasFixedSize()
            rvMovie.adapter = movieAdapter
            rvMovie.isNestedScrollingEnabled = false

            vpMovieBanner.adapter = bannerAdapter
            dotsIndicatorMovie.setViewPager2(vpMovieBanner)

            //Trigger "Load More" when at bottom and prevent double load request
            nestedScrollMovie.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                    val height = (v?.getChildAt(0)?.measuredHeight ?: 0) - (v?.measuredHeight ?: 0)
                    if (scrollY == height && scrollY > lastBottomLocation) {
                        if (currentPage < maxPage) {
                            viewModel.setPage(++currentPage)
                            lastBottomLocation = scrollY
                            activity?.showToast(getString(R.string.load_more))
                        } else {
                            activity?.showToast(getString(R.string.max_page))
                        }
                    }
                })
        }
    }
}