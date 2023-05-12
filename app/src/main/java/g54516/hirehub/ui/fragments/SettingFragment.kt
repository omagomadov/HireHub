package g54516.hirehub.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import g54516.hirehub.R
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.databinding.FragmentSettingBinding
import g54516.hirehub.ui.activities.LoginActivity

class SettingFragment : Fragment() {

    lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_setting, container, false)

        binding.logoutButton.setOnClickListener {
            AuthService.signOut()
            Toast.makeText(context, R.string.logout_message, Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        binding.aboutButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_aboutFragment)
        }

        return binding.root
    }

}