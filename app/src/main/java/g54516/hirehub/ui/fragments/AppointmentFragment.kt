package g54516.hirehub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import g54516.hirehub.R
import g54516.hirehub.database.repository.AppointmentRepository
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.databinding.FragmentAppointmentBinding
import g54516.hirehub.model.factories.AppointmentViewModelFactory
import g54516.hirehub.model.viewmodel.AppointmentViewModel
import java.time.LocalDate
import java.util.Date

class AppointmentFragment : Fragment() {

    lateinit var binding: FragmentAppointmentBinding
    lateinit var viewModel: AppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_appointment, container, false)

        var argument = arguments?.let {
            DeveloperFragmentArgs.fromBundle(it).developer
        }

        val application = requireNotNull(this.activity).application

        val database = AppointmentRepository()

        val viewModelFactory = AppointmentViewModelFactory(database, argument, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[AppointmentViewModel::class.java]

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.backButton.setOnClickListener {
            if (argument != null) {
                val action = AppointmentFragmentDirections
                    .actionAppointmentFragmentToDeveloperFragment(argument)
                findNavController().navigate(action)
            }
        }

        binding.appointementButton.setOnClickListener {
            if (argument != null) {
                viewModel.addAppointment(AuthService.getCurrentUser(), argument.email, Date())
            }
        }

        binding.calendarView.setOnDateChangeListener { _, i, i2, i3 ->
            viewModel.updateDate(LocalDate.of(i, i2 + 1, i3))
        }

        return binding.root
    }

}