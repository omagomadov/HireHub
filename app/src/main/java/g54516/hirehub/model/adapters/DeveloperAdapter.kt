package g54516.hirehub.model.adapters

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.model.viewholders.DeveloperViewHolder

class DeveloperAdapter(private val clickListener: DeveloperListener) :
    RecyclerView.Adapter<DeveloperViewHolder>(), Filterable {

    class DeveloperListener(val clickListener: (developer: DeveloperDto) -> Unit) {
        fun onClick(developer: DeveloperDto) = clickListener(developer)
    }

    var developers = listOf<DeveloperDto>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var filteredDevelopers = mutableListOf<DeveloperDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperViewHolder {
        return DeveloperViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        if (filteredDevelopers.size > 0) {
            return filteredDevelopers.size
        }
        return developers.size
    }

    override fun onBindViewHolder(holder: DeveloperViewHolder, position: Int) {
        if (filteredDevelopers.size > 0) {
            holder.bind(filteredDevelopers[position], clickListener)
        } else {
            holder.bind(developers[position], clickListener)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterText = p0?.toString() ?: ""
                filteredDevelopers = if (filterText.isNotEmpty()) {
                    developers.filter {
                        it.firstName.contains(filterText)
                    } as MutableList<DeveloperDto>
                } else {
                    developers as MutableList<DeveloperDto>
                }
                return FilterResults().apply { values = filteredDevelopers }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredDevelopers = if (p1?.values == null)
                    ArrayList()
                else
                    p1.values as ArrayList<DeveloperDto>
                notifyDataSetChanged()
            }

        }
    }
}