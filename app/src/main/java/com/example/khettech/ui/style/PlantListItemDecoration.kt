package com.example.khettech.ui.style

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PlantListItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        // Add top margin for the first item to avoid space at the top of RecyclerView
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}
