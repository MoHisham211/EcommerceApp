package mo.zain.ecommerceapp.adapter

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
import mo.zain.ecommerceapp.model.category.DataXX

class CategoryAdapter(val listOfCategory:List<DataXX>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var imageView: ImageView
        lateinit var textView: TextView
        init {
            imageView=itemView.findViewById(R.id.imageCat)
            textView=itemView.findViewById(R.id.catName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData:DataXX=listOfCategory.get(position)

        Glide.with(holder.itemView.context)
            .load(currentData.image)
            .into(holder.imageView)

        holder.textView.text=currentData.name

        holder.itemView.setOnClickListener {
            val bundle:Bundle= Bundle()
            bundle.putInt("id",currentData.id)
            holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_categoryDetailsFragment,bundle)
            //Toast.makeText(holder.itemView.context, ""+currentData.id, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listOfCategory.size
    }


}