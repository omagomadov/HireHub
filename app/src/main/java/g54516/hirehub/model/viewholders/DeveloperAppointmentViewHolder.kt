package g54516.hirehub.model.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.R
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.databinding.DeveloperAppointmentCardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DeveloperAppointmentViewHolder private constructor
    (private val binding: DeveloperAppointmentCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): DeveloperAppointmentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: DeveloperAppointmentCardBinding =
                DataBindingUtil
                    .inflate(inflater, R.layout.developer_appointment_card, parent, false)
            return DeveloperAppointmentViewHolder(binding)
        }
    }

    fun bind(appointment: AppointmentDto) {
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH)
            .format(Date(appointment.date))

        binding.appointment = appointment
        binding.date = date
        binding.hasPendingBindings()
    }

}