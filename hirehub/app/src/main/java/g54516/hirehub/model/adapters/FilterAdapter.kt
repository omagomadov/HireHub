package g54516.hirehub.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.model.FilterType
import g54516.hirehub.model.viewholders.FilterTypeViewHolder

class FilterAdapter(private val clickListener: FilterListener) :
    RecyclerView.Adapter<FilterTypeViewHolder>() {

    class FilterListener(val clickListener: (type: FilterType) -> Unit) {
        fun onClick(type: FilterType) = clickListener(type)
    }

    private var selectedItem = 0

    var types = listOf<FilterType>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterTypeViewHolder {
        return FilterTypeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FilterTypeViewHolder, position: Int) {
        val isSelected = selectedItem == position

        holder.card.setOnClickListener {
            val previousSelected = selectedItem
            selectedItem = holder.bindingAdapterPosition
            notifyItemChanged(previousSelected)
            notifyItemChanged(holder.bindingAdapterPosition)
            notifyDataSetChanged()
            clickListener.onClick(types[position])
        }

        holder.bind(types[position], clickListener, isSelected)
    }

    override fun getItemCount(): Int {
        return types.size
    }


}