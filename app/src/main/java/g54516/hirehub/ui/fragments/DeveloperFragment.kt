package g54516.hirehub.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import g54516.hirehub.R
import g54516.hirehub.database.repository.DeveloperRepository
import g54516.hirehub.databinding.FragmentDeveloperBinding
import g54516.hirehub.model.factories.DeveloperViewModelFactory
import g54516.hirehub.model.viewmodel.DeveloperViewModel


class DeveloperFragment : Fragment() {

    private lateinit var binding: FragmentDeveloperBinding
    private lateinit var viewModel: DeveloperViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_developer, container, false)

        var argument = arguments?.let {
            DeveloperFragmentArgs.fromBundle(it).developerEmail
        }

        binding.appointementButton.setOnClickListener {
            //todo
        }

        binding.contactButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse(
                "mailto:$argument?subject="
                        + Uri.encode("subject")
                        + "&body=" + Uri.encode("")
            )
            intent.data = data
            startActivity(intent)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        val application = requireNotNull(this.activity).application

        val database = DeveloperRepository()

        val viewModelFactory = DeveloperViewModelFactory(database, argument, application)

        viewModel = ViewModelProvider(this, viewModelFactory)[DeveloperViewModel::class.java]

        viewModel.developer.observe(viewLifecycleOwner) { developer ->
            if (developer != null) {
                binding.developer = developer
                val instance = FirebaseStorage.getInstance().reference
                val ref = instance.child(developer.avatar)

                ref.downloadUrl.addOnSuccessListener { uri ->
                    Glide
                        .with(binding.root.context)
                        .load(uri.toString())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .onlyRetrieveFromCache(true)
                        .apply(RequestOptions.circleCropTransform())
                        .into(binding.developerAvatar)
                }
            }
        }

        return binding.root
    }

}