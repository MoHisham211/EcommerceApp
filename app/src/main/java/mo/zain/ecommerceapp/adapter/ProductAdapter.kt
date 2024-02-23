package mo.zain.ecommerceapp.adapter

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mo.zain.ecommerceapp.R
import mo.zain.ecommerceapp.model.home.Product

class ProductAdapter(val productList:List<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tv_product_price:TextView
        lateinit var tv_product_old_price:TextView
        lateinit var iv_product:ImageView
        lateinit var tv_product_name:TextView
        lateinit var tv_product_discount:TextView
        init {
            tv_product_price=itemView.findViewById(R.id.tv_product_price)
            tv_product_old_price=itemView.findViewById(R.id.tv_product_old_price)
            iv_product=itemView.findViewById(R.id.iv_product)
            tv_product_name=itemView.findViewById(R.id.tv_product_name)
            tv_product_discount=itemView.findViewById(R.id.tv_product_discount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct:Product=productList.get(position)

        holder.tv_product_name.text=currentProduct.name
        Glide.with(holder.itemView.context)
            .load(currentProduct.image)
            .into(holder.iv_product)
        holder.tv_product_price.text=currentProduct.price.toString()

        if (currentProduct.discount>0){
            holder.tv_product_discount.text=currentProduct.discount.toString() +"%"
            holder.tv_product_old_price.text=currentProduct.old_price.toString()
            holder.tv_product_old_price.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
        }else
        {
            holder.tv_product_discount.visibility=View.GONE
            holder.tv_product_old_price.visibility=View.GONE
        }
        holder.itemView.setOnClickListener {
            val bundle:Bundle= Bundle()
            bundle.putSerializable("CurrentProduct",currentProduct)
            holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,bundle)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }
}