package com.example.khettech.ui.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.khettech.R

class ImageSliderAdapter(private val context: Context) : PagerAdapter() {

    private val images = intArrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
        R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8,R.drawable.image9 )

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.slider_item, container, false)

        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(images[position])

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
