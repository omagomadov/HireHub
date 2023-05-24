package g54516.hirehub.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.model.viewholders.DeveloperAppointmentViewHolder

class DeveloperAppointmentAdapter : RecyclerView.Adapter<DeveloperAppointmentViewHolder>() {

    var appointments = listOf<AppointmentDto>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeveloperAppointmentViewHolder {
        return DeveloperAppointmentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DeveloperAppointmentViewHolder, position: Int) {
        holder.bind(appointments[position])
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

}