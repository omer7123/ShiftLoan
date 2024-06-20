package com.example.finalproject.ui.loansFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentLoansBinding
import com.example.finalproject.presentation.loansFragment.LoansScreenState
import com.example.finalproject.presentation.loansFragment.LoansViewModel
import com.example.finalproject.presentation.multiViewModelFactory.MultiViewModelFactory
import com.example.finalproject.ui.homeFragment.LoanAdapter
import com.example.finalproject.ui.loanDetailFragment.LoanDetailFragment
import com.example.finalproject.util.getAppComponent
import com.example.finalproject.util.showToast
import javax.inject.Inject


class LoansFragment : Fragment() {
    private var _binding: FragmentLoansBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter = LoanAdapter(this::clickListener)

    private fun clickListener(id: Int) {
        val bundle = Bundle()
        bundle.putInt(LoanDetailFragment.ID_KEY, id)
        findNavController().navigate(R.id.action_loansFragment_to_loanDetailFragment, bundle)
    }

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: LoansViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoansViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().loanComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoansBinding.inflate(layoutInflater, container, false)
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniView()
        initListener()
        viewModel.getLoans()
    }

    private fun initListener() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun iniView() {
        binding.loanRv.adapter = adapter
    }

    private fun render(state: LoansScreenState) {
        when (state) {
            is LoansScreenState.Content -> renderContent(state)
            is LoansScreenState.Error -> renderError(state)
            LoansScreenState.Initial -> {}
            LoansScreenState.Loading -> renderLoading()
        }
    }

    private fun renderLoading() {
        showSkeleton(true)
    }

    private fun showSkeleton(show: Boolean) {
        val inflater = LayoutInflater.from(requireContext())

        if (show) {
            binding.skeletonLayout.removeAllViews()
            val skeletonRows = getSkeletonRowCount()
            Log.e("Fsfd", skeletonRows.toString())
            for (i in 0 until skeletonRows) {
                val rowLayout =
                    inflater.inflate(R.layout.item_skeleton_loan, binding.skeletonLayout, false)
                binding.skeletonLayout.addView(rowLayout)
            }
            binding.shimmerSkeleton.visibility = View.VISIBLE
            binding.skeletonLayout.visibility = View.VISIBLE
            binding.shimmerSkeleton.startShimmer()
        } else {
            binding.shimmerSkeleton.stopShimmer()
            binding.shimmerSkeleton.visibility = View.GONE
            binding.skeletonLayout.visibility = View.GONE
            binding.shimmerSkeleton.visibility = View.GONE
        }
    }

    private fun renderError(state: LoansScreenState.Error) {
        showSkeleton(false)
        requireContext().showToast(state.msg)
    }

    private fun renderContent(state: LoansScreenState.Content) {
        adapter.submitList(state.list)
        showSkeleton(false)
    }

    private fun getSkeletonRowCount(): Int {
        val pxHeight = getDeviceHeight(requireContext())
        val skeletonRowHeight =
            requireContext().resources.getDimension(R.dimen.row_layout_height).toInt()
        return kotlin.math.ceil(pxHeight / skeletonRowHeight.toDouble()).toInt()
    }

    private fun getDeviceHeight(context: Context): Int {
        val metrics = context.resources.displayMetrics
        return metrics.heightPixels
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}