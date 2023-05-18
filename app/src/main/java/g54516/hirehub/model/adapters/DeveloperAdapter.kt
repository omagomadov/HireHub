package g54516.hirehub.model.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.model.viewholders.DeveloperViewHolder

class DeveloperAdapter() :
    RecyclerView.Adapter<DeveloperViewHolder>() {

    var developers = listOf<DeveloperDto>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperViewHolder {
        return DeveloperViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return developers.size
    }

    override fun onBindViewHolder(holder: DeveloperViewHolder, position: Int) {
        holder.bind(developers[position])
    }
}