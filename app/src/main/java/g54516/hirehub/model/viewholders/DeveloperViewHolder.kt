package g54516.hirehub.model.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import g54516.hirehub.R
import g54516.hirehub.database.dto.DeveloperDto
import g54516.hirehub.databinding.DeveloperCardBinding
import g54516.hirehub.model.adapters.DeveloperAdapter

class DeveloperViewHolder private constructor(private val binding: DeveloperCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): DeveloperViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: DeveloperCardBinding =
                DataBindingUtil
                    .inflate(inflater, R.layout.developer_card, parent, false)
            return DeveloperViewHolder(binding)
        }
    }

    fun bind(developer: DeveloperDto, clickListener: DeveloperAdapter.DeveloperListener) {

        val instance = FirebaseStorage.getInstance().reference
        val ref = instance.child(developer.avatar)

        ref.downloadUrl.addOnSuccessListener { uri ->
            Glide
                .with(binding.root.context)
                .load(uri.toString())
                .load(uri.toString())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.avatarDeveloper)
        }

        binding.developer = developer
        binding.clickListener = clickListener
        binding.hasPendingBindings()
    }

}