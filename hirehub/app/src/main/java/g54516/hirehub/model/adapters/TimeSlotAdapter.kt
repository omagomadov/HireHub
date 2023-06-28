package g54516.hirehub.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.model.viewholders.TimeSlotViewHolder

class TimeSlotAdapter(private val clickListener: TimeSlotListener) :
    RecyclerView.Adapter<TimeSlotViewHolder>() {

    class TimeSlotListener(val clickListener: (hours: Int) -> Unit) {
        fun onClick(hours: Int) = clickListener(hours)
    }

    private var selectedItem = 0
    var hours = listOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        return TimeSlotViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val isSelected = selectedItem == position

        holder.card.setOnClickListener {
            val previousSelected = selectedItem
            selectedItem = holder.bindingAdapterPosition
            notifyItemChanged(previousSelected)
            notifyItemChanged(holder.bindingAdapterPosition)
            clickListener.onClick(hours[position])
        }

        holder.bind(hours[position], clickListener, isSelected)
    }

}