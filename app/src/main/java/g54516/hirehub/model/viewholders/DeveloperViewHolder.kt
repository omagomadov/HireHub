package g54516.hirehub.model.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.databinding.DeveloperCardBinding

class DeveloperViewHolder(private val binding: DeveloperCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(developer: DeveloperDto) {

        val instance = FirebaseStorage.getInstance().reference
        val ref = instance.child(developer.avatar)

        ref.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri.toString()).into(binding.avatarDeveloper)
        }

        binding.developer = developer
        binding.hasPendingBindings()
    }

}