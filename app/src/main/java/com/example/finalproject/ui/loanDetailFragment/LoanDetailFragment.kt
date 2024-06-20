package com.example.finalproject.ui.loanDetailFragment

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentLoanDetailBinding
import com.example.finalproject.presentation.loanDetailFragment.LoadDetailScreenState
import com.example.finalproject.presentation.loanDetailFragment.LoanDetailViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject


class LoanDetailFragment : Fragment() {
    private var _binding: FragmentLoanDetailBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: LoanDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoanDetailViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().loanComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoanDetailBinding.inflate(layoutInflater)
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }
    private fun initView() {
        val id = arguments!!.getInt(ID_KEY)
        binding.toolbar.title = getString(R.string.number_of_loan, id.toString())
        viewModel.getLoanById(id)
    }

    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun render(state: LoadDetailScreenState) {
        when (state) {
            is LoadDetailScreenState.Content -> renderContent(state)
            is LoadDetailScreenState.Error -> renderError(state.msg)
            LoadDetailScreenState.Initial -> {}
            LoadDetailScreenState.Loading -> renderLoading()
        }
    }

    private fun renderLoading() {
        startShimmer()
        binding.statusContainer.visibility = View.INVISIBLE
        binding.dataOfLoanContainer.visibility = View.INVISIBLE
        binding.dataAboutPersonContainer.visibility = View.INVISIBLE

        binding.dataAboutPersonShimmer.visibility = View.VISIBLE
        binding.dataOfLoanShimmer.visibility = View.VISIBLE
        binding.statusShimmer.visibility = View.VISIBLE

        binding.dataAboutPersonShimmer.startShimmer()
        binding.dataOfLoanShimmer.startShimmer()
        binding.statusShimmer.startShimmer()
    }

    private fun renderError(msg: String) {
        stopShimmer()
        requireContext().showToast(msg)
    }

    private fun renderContent(state: LoadDetailScreenState.Content) {
        stopShimmer()

        binding.nameTv.text = state.loan.firstName
        binding.lastnameTv.text = state.loan.lastName
        binding.phoneTv.text = formatPhoneNumber(state.loan.phoneNumber)

        binding.loanNumberTv.text = getString(R.string.number_of_loan, state.loan.id)
        binding.issueDateTv.text = formatIsoDateString(state.loan.date)
        binding.periodTv.text = state.loan.period.toString()
        binding.percentTv.text = getString(R.string.percent_data, state.loan.percent.toString())
        binding.loanSumTv.text =
            getString(R.string.max_value_predel, state.loan.amount.toInt().toString())

        renderStatus(state.loan.state)
    }

    private fun startShimmer() {
        binding.statusContainer.visibility = View.INVISIBLE
        binding.dataOfLoanContainer.visibility = View.INVISIBLE
        binding.dataAboutPersonContainer.visibility = View.INVISIBLE

        binding.dataAboutPersonShimmer.visibility = View.VISIBLE
        binding.dataOfLoanShimmer.visibility = View.VISIBLE
        binding.statusShimmer.visibility = View.VISIBLE

        binding.dataAboutPersonShimmer.startShimmer()
        binding.dataOfLoanShimmer.startShimmer()
        binding.statusShimmer.startShimmer()
    }

    private fun stopShimmer() {
        binding.statusContainer.visibility = View.VISIBLE
        binding.dataOfLoanContainer.visibility = View.VISIBLE
        binding.dataAboutPersonContainer.visibility = View.VISIBLE

        binding.dataAboutPersonShimmer.visibility = View.GONE
        binding.dataOfLoanShimmer.visibility = View.GONE
        binding.statusShimmer.visibility = View.GONE

        binding.dataAboutPersonShimmer.stopShimmer()
        binding.dataOfLoanShimmer.stopShimmer()
        binding.statusShimmer.stopShimmer()
    }

    private fun formatPhoneNumber(phoneNumber: String?): String {
        return try {
            PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().country)
        } catch (e: Exception) {
            ""
        }
    }

    private fun formatIsoDateString(isoDateString: String): String {
        val isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val date = OffsetDateTime.parse(isoDateString, isoFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return date.format(outputFormatter)
    }

    private fun renderStatus(status: String) {
        when (status) {
            APPROVED -> {
                binding.statusTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(), R.color.indicator_positive
                    )
                )
                binding.statusTv.text = getString(R.string.approved)
            }

            REGISTERED -> {
                binding.statusTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.indicator_attention
                    )
                )
                binding.statusTv.text = getString(R.string.under_consideration)
            }

            REJECTED -> {
                binding.statusTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.indicator_error
                    )
                )
                binding.statusTv.text = getString(R.string.rejected)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ID_KEY = "id_loan"
        const val APPROVED = "APPROVED"
        const val REGISTERED = "REGISTERED"
        const val REJECTED = "REJECTED"
    }
}