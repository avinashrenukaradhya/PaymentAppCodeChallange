package com.till.paymentapplicationpoc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.till.paymentapplicationpoc.R
import com.till.paymentapplicationpoc.data.Purchase
import com.till.paymentapplicationpoc.data.Transaction
import com.till.paymentapplicationpoc.databinding.FragmentTransactionsBinding
import com.till.paymentapplicationpoc.ui.adapter.TransactionAdapterFactory
import com.till.paymentapplicationpoc.ui.adapter.TransactionsAdapter
import com.till.paymentapplicationpoc.ui.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsFragment : Fragment(), TransactionsAdapter.TransactionClickListener {

    private var _binding: FragmentTransactionsBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionsAdapter
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    @Inject
    lateinit var transactionAdapterFactory: TransactionAdapterFactory

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        recyclerView = root.findViewById(R.id.transaction_list)
        adapter = setUpAdapter(mainActivityViewModel.getTransactions(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return root
    }

    private fun setUpAdapter(dataSet: List<Transaction>,
                             onClickListener: TransactionsAdapter.TransactionClickListener): TransactionsAdapter {
        return transactionAdapterFactory.create(dataSet, onClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTransactionClicked(transaction: Transaction) {
        if (transaction is Purchase) {
            val direction =
                TransactionsFragmentDirections.actionToTransactionView(
                    transaction.id
                )
            binding.root.findNavController().navigate(direction)
        }
    }
}