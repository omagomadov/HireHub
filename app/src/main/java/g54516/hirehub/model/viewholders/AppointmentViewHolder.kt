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
import g54516.hirehub.database.dto.AppointmentDto
import g54516.hirehub.databinding.AppointmentCardBinding
import g54516.hirehub.model.adapters.AppointmentAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppointmentViewHolder private constructor(private val binding: AppointmentCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): AppointmentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: AppointmentCardBinding =
                DataBindingUtil
                    .inflate(inflater, R.layout.appointment_card, parent, false)
            return AppointmentViewHolder(binding)
        }
    }

    fun bind(appointment: AppointmentDto, clickListener: AppointmentAdapter.AppointmentListener) {

        val instance = FirebaseStorage.getInstance().reference
        val ref = instance.child(appointment.developer_avatar)
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
            .format(Date(appointment.date))

        ref.downloadUrl.addOnSuccessListener { uri ->
            Glide
                .with(binding.root.context)
                .load(uri.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.avatarDeveloper)
        }

        binding.clickListener = clickListener
        binding.appointment = appointment
        binding.date = date
        binding.hasPendingBindings()
    }

}