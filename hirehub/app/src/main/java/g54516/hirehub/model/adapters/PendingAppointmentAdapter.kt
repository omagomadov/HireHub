package g54516.hirehub.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.model.viewholders.PendingAppointmentViewHolder
import g54516.hirehub.ui.fragments.PendingFragment

class PendingAppointmentAdapter(val fragment: PendingFragment) :
    RecyclerView.Adapter<PendingAppointmentViewHolder>() {

    var appointments = listOf<AppointmentDto>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingAppointmentViewHolder {
        return PendingAppointmentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PendingAppointmentViewHolder, position: Int) {
        holder.bind(appointments[position], fragment)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

}