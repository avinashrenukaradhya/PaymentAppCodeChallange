package com.till.paymentapplicationpoc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.till.paymentapplicationpoc.R
import com.till.paymentapplicationpoc.data.repository.TransactionService
import com.till.paymentapplicationpoc.databinding.FragmentTransactionBinding
import com.till.paymentapplicationpoc.ui.state.RefundState
import com.till.paymentapplicationpoc.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private var transactionService = TransactionService.instance
    private var _binding: FragmentTransactionBinding? = null
    private lateinit var refundEditText: EditText

    private val args: TransactionFragmentArgs by navArgs()

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val binding = _binding!!
        val root: View = binding.root

        with(transactionService.getTransaction(args.txId)!!) {
            root.findViewById<TextView>(R.id.amount).text = amount.toString()
            root.findViewById<TextView>(R.id.tx_id).text = id.toString()
        }

        refundEditText = root.findViewById(R.id.refund_amount)
        root.findViewById<Button>(R.id.refund_button).setOnClickListener {
            mainActivityViewModel.refund(refundEditText.text.toString(), args.txId)
        }
        initRefundStateListener()
        return root
    }

    private fun initRefundStateListener() {
        lifecycleScope.launchWhenStarted {
            mainActivityViewModel.refundState.collectLatest { refundState ->
                when(refundState) {
                    is RefundState.Error -> {
                        makeToast(refundState.message)
                    }
                    is RefundState.Success -> {
                        makeToast(refundState.message)
                        refundEditText.text.clear()
                    }
                    is RefundState.Empty -> { }
                }
            }
        }
    }

    private fun makeToast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}