package id.kadenizar.dicodingmovies.view.movie.search.movieseries

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import id.kadenizar.dicodingmovies.R
import id.kadenizar.dicodingmovies.core.data.Resource
import id.kadenizar.dicodingmovies.core.domain.model.Show
import id.kadenizar.dicodingmovies.core.ui.ShowsAdapter
import id.kadenizar.dicodingmovies.databinding.FragmentSearchSeriesBinding
import id.kadenizar.dicodingmovies.services.IView
import id.kadenizar.dicodingmovies.services.ViewNetworkState
import id.kadenizar.dicodingmovies.services.base.BaseFragment
import id.kadenizar.dicodingmovies.view.movie.search.SearchViewModel
import id.kadenizar.dicodingmovies.view.movie.details.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.getViewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SearchSeriesFragment : BaseFragment(),ViewNetworkState,IView {

    //Shared ViewModel
    //Get the same viewModel instance of SearchFragment as the host
    private val viewModel: SearchViewModel by lazy { requireParentFragment().getViewModel() }

    //Binding
    private var _binding: FragmentSearchSeriesBinding? = null
    private val binding get() = _binding!!

    //Adapter
    private lateinit var seriesAdapter: ShowsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         initView()
        viewModelObserver() //Load search result of series list
    }
    override fun initView() {
        seriesAdapter = ShowsAdapter()

        //OnClick go to DetailActivity
        seriesAdapter.onItemClick = { selectedItem ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_SHOW_ID, selectedItem.id)
            intent.putExtra(DetailActivity.EXTRA_SHOW_TYPE, selectedItem.showType)
            startActivity(intent)
        }

        with(binding) {
            rvSearchSeries.layoutManager = GridLayoutManager(requireContext(), 3)
            rvSearchSeries.adapter = seriesAdapter
        }
    }

    private fun viewModelObserver() {
        viewModel.getSeries().observe(viewLifecycleOwner, { seriesList ->
            when (seriesList) {
                is Resource.Loading -> {
                    setViewVisibility(loading = true, ivInfo = false, tvInfo = false)
                }

                is Resource.Success -> {
                    val data = seriesList.data
                    seriesAdapter.setList(data as ArrayList<Show>)
                    if (data.isNullOrEmpty()) {
                        setViewVisibility(loading = false, ivInfo = true, tvInfo = true)
                        setInfoImageAndMessage(
                            R.drawable.nodata,
                            getString(R.string.no_series_found)
                        )
                    } else {
                        setViewVisibility(loading = false, ivInfo = false, tvInfo = false)
                    }
                }

                is Resource.Error -> {
                    setViewVisibility(loading = false, ivInfo = true, tvInfo = true)
                    setInfoImageAndMessage(
                        R.drawable.notfound,
                        seriesList.message
                    )
                }
            }
        })
    }

    private fun setViewVisibility(loading: Boolean, ivInfo: Boolean, tvInfo: Boolean) {
        with(binding) {
            pbSearchSeries.visibility = if (loading) View.VISIBLE else View.GONE
            ivSearchSeriesInfo.visibility = if (ivInfo) View.VISIBLE else View.INVISIBLE
            tvSearchSeriesInfo.visibility = if (tvInfo) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun setInfoImageAndMessage(
        image: Int,
        message: String? = getString(R.string.unknown_error)
    ) {
        val targetWidth = 1361
        val targetHeight = 938
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.backdrop_placeholder)
            .error(R.drawable.image_placeholder)
            .resize(targetWidth, targetHeight)
            .into(binding.ivSearchSeriesInfo)
        binding.tvSearchSeriesInfo.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _binding = null
    }

}