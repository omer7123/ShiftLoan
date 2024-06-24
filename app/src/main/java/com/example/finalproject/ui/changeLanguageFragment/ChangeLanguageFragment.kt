package com.example.finalproject.ui.changeLanguageFragment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentChangeLanguageBinding
import com.example.finalproject.presentation.changeLanguageFragment.ChangeLanguageScreenState
import com.example.finalproject.presentation.changeLanguageFragment.ChangeLanguageViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import java.util.Locale
import javax.inject.Inject


class ChangeLanguageFragment : Fragment() {
    private var _binding: FragmentChangeLanguageBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: ChangeLanguageViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ChangeLanguageViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().settingsComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeLanguageBinding.inflate(layoutInflater)
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    private fun render(state: ChangeLanguageScreenState) {
        when (state) {
            is ChangeLanguageScreenState.Content -> renderContent(state.locate)
            is ChangeLanguageScreenState.Error -> requireContext().showToast(state.msg)
            ChangeLanguageScreenState.Initial -> {}
        }
    }

    private fun renderContent(locate: String) {
        if (locate.isEmpty()) binding.russian.isChecked = true
        when (locate) {
            "ru" -> binding.russian.isChecked = true
            "en" -> binding.english.isChecked = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLocale()
        binding.nextBtn.setOnClickListener {
            val currentLocale = resources.configuration.locales[0]
            when (binding.radioGroup.checkedRadioButtonId) {
                R.id.russian -> {
                    if (currentLocale.language != "ru")
                        setLocale("ru")
                }

                R.id.english -> {
                    if (currentLocale.language != "en")
                        setLocale("en")
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        val context = requireContext().createConfigurationContext(config)
        resources.updateConfiguration(config, context.resources.displayMetrics)
        viewModel.saveLocale(locale.language)

        requireActivity().recreate()
    }
}