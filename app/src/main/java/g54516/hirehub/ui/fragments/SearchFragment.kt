package g54516.hirehub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.R
import g54516.hirehub.database.repository.DeveloperRepository
import g54516.hirehub.databinding.FragmentSearchBinding
import g54516.hirehub.model.adapters.DeveloperAdapter
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

        val adapter = DeveloperAdapter(DeveloperAdapter.DeveloperListener { _ ->
            //todo
        })

        binding.searchCards.adapter = adapter
        binding.bestRatingCards.adapter = adapter

        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]

        viewModel.developers.observe(viewLifecycleOwner) {
            it?.let {
                adapter.developers = it
            }
        }

        viewModel.developersOrdered.observe(viewLifecycleOwner) {
            it?.let {
                adapter.developers = it
            }
        }

        return binding.root
    }

}