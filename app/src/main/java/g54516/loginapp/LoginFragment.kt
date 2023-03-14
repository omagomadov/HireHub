package g54516.loginapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import g54516.loginapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login,
                container, false)
        binding.loginButton.setOnClickListener { view : View ->
            verifyEmail(view)
        }
        return binding.root
    }

    private fun verifyEmail(view : View) {
        //todo
    }

}