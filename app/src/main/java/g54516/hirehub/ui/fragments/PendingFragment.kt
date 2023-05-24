package g54516.hirehub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import g54516.hirehub.R
import g54516.hirehub.databinding.FragmentPendingBinding

class PendingFragment : Fragment() {

    lateinit var binding: FragmentPendingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_pending, container, false)
        return binding.root
    }
}