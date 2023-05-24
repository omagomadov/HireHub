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
import g54516.hirehub.databinding.UserAppointmentCardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserAppointmentViewHolder private constructor(private val binding: UserAppointmentCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): UserAppointmentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: UserAppointmentCardBinding =
                DataBindingUtil
                    .inflate(inflater, R.layout.user_appointment_card, parent, false)
            return UserAppointmentViewHolder(binding)
        }
    }

    fun bind(appointment: AppointmentDto) {

        val instance = FirebaseStorage.getInstance().reference
        val ref = instance.child(appointment.developer_avatar)
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH)
            .format(Date(appointment.date))

        ref.downloadUrl.addOnSuccessListener { uri ->
            Glide
                .with(binding.root.context)
                .load(uri.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.avatarDeveloper)
        }

        binding.appointment = appointment
        binding.date = date
        binding.hasPendingBindings()
    }

}