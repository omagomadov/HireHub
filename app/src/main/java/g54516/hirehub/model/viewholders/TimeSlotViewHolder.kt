package g54516.hirehub.model.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.R
import g54516.hirehub.databinding.TimeSlotCardBinding
import g54516.hirehub.model.adapters.TimeSlotAdapter

class TimeSlotViewHolder private constructor(
    private val binding: TimeSlotCardBinding,
    val card: View
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): TimeSlotViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: TimeSlotCardBinding = DataBindingUtil
                .inflate(inflater, R.layout.time_slot_card, parent, false)
            return TimeSlotViewHolder(binding, binding.timeSlotCard)
        }
    }

    fun bind(hours: Int, clickListener: TimeSlotAdapter.TimeSlotListener, isSelected: Boolean) {
        if (isSelected) {
            binding.timeSlotCard.setBackgroundResource(R.drawable.time_slot_rounded_selected)
        } else {
            binding.timeSlotCard.setBackgroundResource(R.drawable.time_slot_rounded)
        }
        binding.hours = hours
        binding.clickListener = clickListener
        binding.hasPendingBindings()
    }
}