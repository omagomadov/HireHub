package g54516.hirehub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import g54516.hirehub.R
import g54516.hirehub.database.service.AuthService
import g54516.hirehub.databinding.FragmentHomeBinding
import g54516.hirehub.model.Utils


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_home, container, false)

        // Define an click listener on logout menu
        Utils.getNavigation().menu.findItem(R.id.logout).setOnMenuItemClickListener {
            // sign out the user
            AuthService.signOut()
            // go to login fragment
            findNavController().navigate(R.id.loginFragment)
            true
        }

        return binding.root
    }

}