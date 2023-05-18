package g54516.hirehub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import g54516.hirehub.R
import g54516.hirehub.databinding.FragmentDeveloperBinding


class DeveloperFragment : Fragment() {

    private lateinit var binding: FragmentDeveloperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_developer, container, false)

        return binding.root
    }

}