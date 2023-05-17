package g54516.hirehub.model.viewholders

import androidx.recyclerview.widget.RecyclerView
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.databinding.DeveloperCardBinding

class DeveloperViewHolder(private val binding: DeveloperCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(developer: DeveloperDto) {
        binding.developer = developer
        binding.hasPendingBindings()
    }

}