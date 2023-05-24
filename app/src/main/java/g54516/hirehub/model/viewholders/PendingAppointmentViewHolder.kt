package g54516.hirehub.model.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.R
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.databinding.PendingCardBinding
import g54516.hirehub.ui.fragments.PendingFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PendingAppointmentViewHolder private constructor
    (private val binding: PendingCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): PendingAppointmentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: PendingCardBinding =
                DataBindingUtil
                    .inflate(inflater, R.layout.pending_card, parent, false)
            return PendingAppointmentViewHolder(binding)
        }
    }

    fun bind(appointment: AppointmentDto, fragment: PendingFragment) {
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH)
            .format(Date(appointment.date))

        binding.acceptAppointment.setOnClickListener {
            fragment.approveAppointment(appointment)
        }

        binding.refuseAppointment.setOnClickListener {
            fragment.disapprouveAppointment(appointment)
        }

        binding.appointment = appointment
        binding.date = date
        binding.hasPendingBindings()
    }

}