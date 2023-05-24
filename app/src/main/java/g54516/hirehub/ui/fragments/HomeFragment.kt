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
import g54516.hirehub.database.repository.AppointmentRepository
import g54516.hirehub.databinding.FragmentHomeBinding
import g54516.hirehub.model.adapters.UserAppointmentAdapter
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

        // Retrieve 'isDeveloper' bundle from the MainActivity
        // MainActivity know if it is a user or developer
        // He received the information from LoginActivity
        val bundle = requireActivity().intent.extras?.getBoolean("isDeveloper")

        val application = requireNotNull(this.activity).application

        val room = HireHubDB.getInstance(application).userDao

        val database = AppointmentRepository()

        val viewModelFactory = HomeViewModelFactory(database, room, bundle, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        val incomeAdapter = UserAppointmentAdapter()

        val passedAdapter = UserAppointmentAdapter()

        binding.recycleIncomeMeeting.adapter = incomeAdapter
        binding.recyclePassedMeeting.adapter = passedAdapter

        viewModel.incomeAppointments.observe(viewLifecycleOwner) {
            it?.let {
                incomeAdapter.appointments = it
            }
        }

        viewModel.passedAppointments.observe(viewLifecycleOwner) {
            it?.let {
                passedAdapter.appointments = it
            }
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.username.text = user.firstName + " " + user.lastName
            }
        }

        return binding.root
    }

}