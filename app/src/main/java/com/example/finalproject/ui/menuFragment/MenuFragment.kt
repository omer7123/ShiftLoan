package com.example.finalproject.ui.menuFragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.AlertDialogBinding
import com.example.finalproject.databinding.FragmentMenuBinding
import com.example.finalproject.presentation.menuFragment.LogOutStatusState
import com.example.finalproject.presentation.menuFragment.MenuViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import javax.inject.Inject


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: MenuViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MenuViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().authenticationComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater)

        viewModel.logOutStatus.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    private fun render(state: LogOutStatusState) {
        when (state) {
            is LogOutStatusState.Error -> requireContext().showToast(state.msg)
            LogOutStatusState.Success -> findNavController().navigate(R.id.action_menuFragment_to_homeAuthenticationFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initListener() {

        binding.questionIv.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_boardFragment)
        }
        binding.myLoansLayout.content.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_loansFragment)
        }
        binding.offersLayout.content.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_specialOfferFragment)
        }
        binding.bankBranchesLayout.content.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_addressOfBankFragment)
        }
        binding.helpLayout.content.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_helpFragment)
        }
        binding.logOutLayout.content.setOnClickListener {
            showAlertDialog()
        }
        binding.languageLayout.content.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_changeLanguageFragment)
        }
    }

    private fun showAlertDialog() {
        val dialogBinding = AlertDialogBinding.inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        dialogBinding.buttonExit.setOnClickListener {
            viewModel.logOut()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun initView() {
        binding.myLoansLayout.title.text = getString(R.string.my_loans)
        binding.offersLayout.title.text = getString(R.string.offers_for_you)
        binding.bankBranchesLayout.title.text = getString(R.string.bank_branches)
        binding.helpLayout.title.text = getString(R.string.help)
        binding.languageLayout.title.text = getString(R.string.language)
        binding.logOutLayout.title.text = getString(R.string.go_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}