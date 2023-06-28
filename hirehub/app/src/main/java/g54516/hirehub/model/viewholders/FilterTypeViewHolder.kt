package g54516.hirehub.model.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.R
import g54516.hirehub.databinding.FilterCardBinding
import g54516.hirehub.model.FilterType
import g54516.hirehub.model.adapters.FilterAdapter

class FilterTypeViewHolder private constructor(
    private val binding: FilterCardBinding,
    val card: View
) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): FilterTypeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: FilterCardBinding = DataBindingUtil
                .inflate(inflater, R.layout.filter_card, parent, false)
            return FilterTypeViewHolder(binding, binding.filterCard)
        }
    }

    fun bind(filter: FilterType, clickListener: FilterAdapter.FilterListener, isSelected: Boolean) {
        if (isSelected) {
            binding.filterCard.setBackgroundResource(R.drawable.time_slot_rounded_selected)
        } else {
            binding.filterCard.setBackgroundResource(R.drawable.time_slot_rounded)
        }
        binding.filterType = filter.type
        binding.clickListener = clickListener
        binding.hasPendingBindings()
    }
}