package g54516.loginapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import g54516.loginapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate<FragmentLoginBinding>(
                inflater, R.layout.fragment_login,
                container, false
            )
        binding.loginButton.setOnClickListener { view: View ->
            verifyEmail(view)
        }
        return binding.root
    }

    private fun verifyEmail(view: View) {
        // Initialize a toast
        toast =
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.loginEmail.text).matches()) {
                Toast.makeText(activity, "Valid email !", Toast.LENGTH_SHORT)
            } else {
                Toast.makeText(activity, "Invalid email !", Toast.LENGTH_SHORT)
            }
        // Display the toast
        toast.show()
        // Hide 'virtual keyboard' after finished
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}