package com.till.paymentapplicationpoc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.till.paymentapplicationpoc.databinding.FragmentPurchaseBinding
import com.till.paymentapplicationpoc.ui.state.PurchaseState
import com.till.paymentapplicationpoc.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PurchaseFragment : Fragment() {

    private var _binding: FragmentPurchaseBinding? = null
    private lateinit var paymentEditText: EditText
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        val binding = _binding!!
        paymentEditText = binding.paymentAmount
        binding.paymentButton.setOnClickListener {
            paymentEditText.text.toString().toFloatOrNull()?.let { payment ->
                mainActivityViewModel.purchase(paymentEditText.text.toString().trim())
            }
        }
        initPurchaseStateListener()
        return binding.root
    }

    private fun initPurchaseStateListener() {
        lifecycleScope.launchWhenStarted {
            mainActivityViewModel.purchaseState.collectLatest { purchaseState ->
                when(purchaseState) {
                    is PurchaseState.Error -> {
                        makeToast(purchaseState.message)
                    }
                    is PurchaseState.Success -> {
                        makeToast(purchaseState.message)
                        paymentEditText.text.clear()
                    }
                    is PurchaseState.Empty -> { }
                }
            }
        }
    }

    private fun makeToast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}