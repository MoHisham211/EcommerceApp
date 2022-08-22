package mo.zain.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        holder.tv_product_price.text=currentProduct.price.toString()
        holder.tv_product_old_price.text=currentProduct.old_price.toString()
        holder.tv_product_discount.text=currentProduct.discount.toString()

        Glide.with(holder.itemView.context)
            .load(currentProduct.image)
            .into(holder.iv_product)
    }

    override fun getItemCount(): Int {
        return 6
    }
}