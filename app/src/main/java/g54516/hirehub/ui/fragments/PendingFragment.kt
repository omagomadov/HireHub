package g54516.hirehub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import g54516.hirehub.R
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.database.repository.AppointmentRepository
import g54516.hirehub.databinding.FragmentPendingBinding
import g54516.hirehub.model.adapters.PendingAppointmentAdapter
import g54516.hirehub.model.factories.PendingViewModelFactory
import g54516.hirehub.model.viewmodel.PendingViewModel

class PendingFragment : Fragment() {

    lateinit var binding: FragmentPendingBinding
    private lateinit var viewModel: PendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_pending, container, false)

        val application = requireNotNull(this.activity).application
        val database = AppointmentRepository()
        val viewModelFactory = PendingViewModelFactory(database, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[PendingViewModel::class.java]

        viewModel.appointments.observe(viewLifecycleOwner) {
            it?.let {
                val pending = PendingAppointmentAdapter(this)
                pending.appointments = it
                binding.appointmentCards.adapter = pending
            }
        }

        return binding.root
    }

    fun approveAppointment(appointment: AppointmentDto) {
        viewModel.approveAppointment(appointment)
    }

    fun disapprouveAppointment(appointment: AppointmentDto) {
        viewModel.disapprouveAppointment(appointment)
    }

}