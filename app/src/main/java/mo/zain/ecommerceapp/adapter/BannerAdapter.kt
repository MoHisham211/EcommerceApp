package mo.zain.ecommerceapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.Runnable
import mo.zain.ecommerceapp.R
import mo.zain.ecommerceapp.model.Banner


//: RecyclerView.Adapter<BannerAdapter.ViewHolder>()
class BannerAdapter(val sliderItems: MutableList<Banner>,val viewPager2: ViewPager2) : RecyclerView.Adapter<BannerAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var imageView:RoundedImageView ?=null
        init {
            imageView=itemView.findViewById(R.id.imageSlide)
        }
        fun setImage(banner: Banner) {
            Glide.with(itemView.context)
                .load(banner.image)
                .into(imageView!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setImage(sliderItems.get(position))
        if (position==sliderItems.size-2){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size

    }


    private val runnable:Runnable= Runnable() {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

}