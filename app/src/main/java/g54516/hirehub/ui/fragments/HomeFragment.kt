package g54516.hirehub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.R
import g54516.hirehub.database.HireHubDB
import g54516.hirehub.databinding.FragmentHomeBinding
import g54516.hirehub.model.factories.HomeViewModelFactory
import g54516.hirehub.model.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application

        val database = HireHubDB.getInstance(application).userDao

        val viewModelFactory = HomeViewModelFactory(database, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.username.text = user.firstName + " " + user.lastName
            }
        }

        return binding.root
    }

}