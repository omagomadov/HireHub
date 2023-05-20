package g54516.hirehub.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.model.viewholders.AppointmentViewHolder

class AppointmentAdapter(private val clickListener: AppointmentListener) :
    RecyclerView.Adapter<AppointmentViewHolder>() {

    class AppointmentListener(val clickListener: (appointment: AppointmentDto) -> Unit) {
        fun onClick(appointment: AppointmentDto) = clickListener(appointment)
    }

    var appointments = listOf<AppointmentDto>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        return AppointmentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(appointments[position], clickListener)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

}