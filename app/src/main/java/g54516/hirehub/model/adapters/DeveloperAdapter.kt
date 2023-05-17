package g54516.hirehub.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.R
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.databinding.DeveloperCardBinding
import g54516.hirehub.model.viewholders.DeveloperViewHolder

class DeveloperAdapter() :
    RecyclerView.Adapter<DeveloperViewHolder>() {

    var developers = listOf<DeveloperDto>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: DeveloperCardBinding =
            DataBindingUtil.inflate(inflater, R.layout.developer_card, parent, false)
        return DeveloperViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return developers.size
    }

    override fun onBindViewHolder(holder: DeveloperViewHolder, position: Int) {
        holder.bind(developers[position])
    }
}