package mo.zain.ecommerceapp.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import mo.zain.ecommerceapp.R
import mo.zain.ecommerceapp.adapter.BannerAdapter
import mo.zain.ecommerceapp.adapter.CategoryAdapter
import mo.zain.ecommerceapp.adapter.ProductAdapter
import mo.zain.ecommerceapp.viewModel.UserViewModel


@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var viewPager2: ViewPager2
    lateinit var adsImage:ImageView
    private val viewModel:UserViewModel by viewModels()
    private var mySharedPreferences: SharedPreferences? =null
    private var bannerAdapter:BannerAdapter ? =null
    private var sliderHandeler:Handler = Handler()
    lateinit var recyclerView: RecyclerView
    private var categoryAdapter:CategoryAdapter ?=null
    lateinit var productRv:RecyclerView
    private var productAdapter:ProductAdapter?=null
    lateinit var searchBtn:TextView
    lateinit var searchTxt:EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)

        viewPager2=view.findViewById(R.id.imageSlider)
        viewPager2.clipToPadding=false
        viewPager2.clipChildren=false
        viewPager2.offscreenPageLimit=3
        viewPager2.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER
        var compositePageTransformer=CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer { page, position ->
            val r=1-Math.abs(position)
            page.scaleY=0.85f+r*0.15f
        })
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandeler.removeCallbacks(sliderRunnable)
                sliderHandeler.postDelayed(sliderRunnable,3000)
            }
        })

        //adsImage=view.findViewById(R.id.adsImage)

        recyclerView=view.findViewById(R.id.rv)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


        productRv=view.findViewById(R.id.productRv)
        productRv.setHasFixedSize(true)
        productRv.layoutManager=GridLayoutManager(requireContext(),2)

        searchTxt=view.findViewById(R.id.search)

        searchTxt.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lifecycleScope.launch {
                    viewModel.searchRepo(p0!!.toString())
                    if (viewModel.search().value?.status==true){
                        productAdapter= ProductAdapter(viewModel.search().value?.data!!.data)
                        productRv.adapter=productAdapter
                        productAdapter?.notifyDataSetChanged()
                    }
                }

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lifecycleScope.launch {
                    viewModel.searchRepo(p0!!.toString())
                    if (viewModel.search().value?.status==true){
                        productAdapter= ProductAdapter(viewModel.search().value?.data!!.data)
                        productRv.adapter=productAdapter
                        productAdapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                lifecycleScope.launch {
                    viewModel.searchRepo(p0!!.toString())
                    if (viewModel.search().value?.status==true){
                        productAdapter= ProductAdapter(viewModel.search().value?.data!!.data)
                        productRv.adapter=productAdapter
                        productAdapter?.notifyDataSetChanged()
                    }
                }
            }

        })


        mySharedPreferences = requireActivity().getSharedPreferences("Token", AppCompatActivity.MODE_PRIVATE)
        val myToken =mySharedPreferences!!.getString("Token","null")
        myToken?.let { getHome(it) }


        return view
    }
    private var sliderRunnable:Runnable= Runnable {
        viewPager2.setCurrentItem(viewPager2.currentItem+1)
    }

    private fun getHome(token:String) {
        viewModel.getHome(token)
        viewModel.responseItem.observe(requireActivity(),{data->
            bannerAdapter= BannerAdapter(data.banners,viewPager2)
            viewPager2.adapter=bannerAdapter
            bannerAdapter?.notifyDataSetChanged()

            productAdapter= ProductAdapter(data.products)
            productRv.adapter=productAdapter
            productAdapter?.notifyDataSetChanged()


            //
//            Glide.with(requireContext())
//                .load(data.ad)
//                .into(adsImage)

//            Toast.makeText(requireContext(), ""+data.ad, Toast.LENGTH_SHORT).show()
        })

        viewModel.responseCategory.observe(requireActivity(),{data->
            categoryAdapter= CategoryAdapter(data.data)
            recyclerView.adapter=categoryAdapter
            categoryAdapter!!.notifyDataSetChanged()
            //Toast.makeText(requireContext(), ""+data.data[0].image, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onPause() {
        super.onPause()
        sliderHandeler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandeler.postDelayed(sliderRunnable,3000)
    }

    /**
         lifecycleScope.launch {
        viewModel.searchRepo("ايفون")
            if (viewModel.search().value?.status==true){
            productAdapter= ProductAdapter(viewModel.search().value?.data!!.data)
            productRv.adapter=productAdapter
            productAdapter?.notifyDataSetChanged()
            }
        }
     */

}