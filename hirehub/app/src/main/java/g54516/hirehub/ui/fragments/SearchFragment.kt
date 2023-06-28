package g54516.hirehub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import g54516.hirehub.R
import g54516.hirehub.database.repository.DeveloperRepository
import g54516.hirehub.databinding.FragmentSearchBinding
import g54516.hirehub.model.adapters.DeveloperAdapter
import g54516.hirehub.model.adapters.FilterAdapter
import g54516.hirehub.model.factories.SearchViewModelFactory
import g54516.hirehub.model.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_search, container, false)

        val application = requireNotNull(this.activity).application

        val database = DeveloperRepository()

        val viewModelFactory = SearchViewModelFactory(database, application)

        val developers = DeveloperAdapter(DeveloperAdapter.DeveloperListener { developer ->
            val action = SearchFragmentDirections
                .actionSearchFragmentToDeveloperFragment(developer)
            findNavController().navigate(action)
        })

        val filters = FilterAdapter(FilterAdapter.FilterListener {
            viewModel.update(it)
        })

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchView.clearFocus()
                developers.filter.filter(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0.isNullOrEmpty()) {
                    developers.filter.filter(p0)
                }
                return true
            }

        })

        binding.searchCards.adapter = developers
        binding.bestRatingCards.adapter = developers
        binding.filterCards.adapter = filters

        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]

        viewModel.developers.observe(viewLifecycleOwner) {
            it?.let {
                developers.developers = it
            }
        }

        viewModel.developersOrdered.observe(viewLifecycleOwner) {
            it?.let {
                developers.developers = it
            }
        }

        viewModel.filters.observe(viewLifecycleOwner) {
            it?.let {
                filters.types = it
            }
        }

        return binding.root
    }

}