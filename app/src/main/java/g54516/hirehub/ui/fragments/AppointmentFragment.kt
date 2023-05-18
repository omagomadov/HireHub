package g54516.hirehub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import g54516.hirehub.R
import g54516.hirehub.databinding.FragmentAppointmentBinding

class AppointmentFragment : Fragment() {

    lateinit var binding: FragmentAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_appointment, container, false)

        var argument = arguments?.let {
            DeveloperFragmentArgs.fromBundle(it).developerEmail
        }

        binding.backButton.setOnClickListener {
            val action = AppointmentFragmentDirections
                .actionAppointmentFragmentToDeveloperFragment().setDeveloperEmail(argument ?: "")
            findNavController().navigate(action)
        }

        return binding.root
    }

}