package mo.zain.ecommerceapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mo.zain.ecommerceapp.R
import mo.zain.ecommerceapp.adapter.ProductAdapter
import mo.zain.ecommerceapp.viewModel.UserViewModel


@AndroidEntryPoint
class CategoryDetailsFragment : Fragment() {

    private val viewModel:UserViewModel by viewModels()
    private var productAdapter:ProductAdapter ?=null
    lateinit var productRv:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_category_details, container, false)

        productRv=view.findViewById(R.id.productRv)
        productRv=view.findViewById(R.id.productRv)
        productRv.setHasFixedSize(true)
        productRv.layoutManager= GridLayoutManager(requireContext(),2)

        val idd: Int? =arguments?.getInt("id")
        lifecycleScope.launch {
            viewModel.catDetails(idd!!)
            viewModel.categDetails.observe(requireActivity(),{data->
                productAdapter= ProductAdapter(data)
                productRv.adapter=productAdapter
                productAdapter?.notifyDataSetChanged()
                //Toast.makeText(requireContext(), ""+data, Toast.LENGTH_SHORT).show()
            })
        }

        return view
    }

}