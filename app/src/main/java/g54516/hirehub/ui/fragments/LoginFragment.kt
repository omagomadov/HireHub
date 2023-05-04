package g54516.hirehub.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import g54516.hirehub.R
import g54516.hirehub.database.HireHubDB
import g54516.hirehub.databinding.FragmentLoginBinding
import g54516.hirehub.model.factories.LoginViewModelFactory
import g54516.hirehub.model.viewmodel.LoginViewModel
import java.util.Date

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var toast: Toast

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_login, container, false)

        // Set on click listener on login button
        binding.loginButton.setOnClickListener {
            signIn()
        }

        binding.passwordForgot.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.loginQuestionLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Get the application context.
        val application = requireNotNull(this.activity).application

        // Get instance of UserDAO.
        val database = HireHubDB.getInstance(application).userDao
        // Get instance of LoginViewModel.
        val viewModelFactory = LoginViewModelFactory(database, application)

        // Initialize the LoginViewModel.
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        val loginView = binding.loginEmail

        binding.lifecycleOwner = this

        viewModel.emails.observe(viewLifecycleOwner, Observer { list ->
            var adapter = ArrayAdapter(
                application,
                android.R.layout.simple_dropdown_item_1line, list
            )
            loginView.setAdapter(adapter)
            adapter.notifyDataSetChanged()
        })

        viewModel.notification.observe(viewLifecycleOwner, Observer { message ->
            if (viewModel.displayToast.value == true) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                // Hide 'virtual keyboard' after finished
                val imm =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                viewModel.setDisplayToast(false)
            }
        })

        return binding.root
    }

    private fun signIn() {
        viewModel.signIn(
            binding.loginEmail.text.toString(),
            binding.loginPassword.text.toString(), Date()
        )
    }

}