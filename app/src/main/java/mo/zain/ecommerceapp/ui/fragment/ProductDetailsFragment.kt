package mo.zain.ecommerceapp.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mo.zain.ecommerceapp.R
import mo.zain.ecommerceapp.model.home.Product
import mo.zain.ecommerceapp.viewModel.UserViewModel

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    lateinit var roundedImageView:RoundedImageView
    lateinit var product_name:TextView
    lateinit var product_description:TextView
    lateinit var product_price:TextView
    lateinit var iv_productDetails_inFavourite:ImageView
    private var mySharedPreferences: SharedPreferences? =null
    private val viewModel:UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)

        roundedImageView=view.findViewById(R.id.roundedImageView)
        product_name=view.findViewById(R.id.name)
        product_description=view.findViewById(R.id.description)
        product_price=view.findViewById(R.id.tv_productDetails_price)
        iv_productDetails_inFavourite=view.findViewById(R.id.iv_productDetails_inFavourite)

        mySharedPreferences = requireActivity().getSharedPreferences("Token", AppCompatActivity.MODE_PRIVATE)
        val myToken =mySharedPreferences!!.getString("Token","null")

        Toast.makeText(requireContext(), ""+myToken, Toast.LENGTH_SHORT).show()

        val product:Product= arguments?.getSerializable("CurrentProduct") as Product
        Toast.makeText(requireContext(), ""+product, Toast.LENGTH_SHORT).show()
        Glide.with(requireContext())
            .load(product.images.get(0))
            .into(roundedImageView)

        product.images.get(1)

        product_name.setText(product.name)
        product_description.setText(product.description)
        product_price.setText(product.price.toString())

        if (product.in_favorites){
            iv_productDetails_inFavourite.setImageResource(R.drawable.ic_red_heart)
        }else
        {
            iv_productDetails_inFavourite.setImageResource(R.drawable.ic__52344_favorite_icon)
        }

        iv_productDetails_inFavourite.setOnClickListener {
            lifecycleScope.launch {
                viewModel.favoriteRespo(myToken!!,product.id)
                if (viewModel.setFavorite!!.status==true){
                    Toast.makeText(requireContext(), ""+viewModel.favorite().value, Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

}