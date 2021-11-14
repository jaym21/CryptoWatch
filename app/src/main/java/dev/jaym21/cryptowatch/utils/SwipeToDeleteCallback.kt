package dev.jaym21.cryptowatch.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.cryptowatch.R

abstract class SwipeToDeleteCallback(context: Context): ItemTouchHelper.Callback() {

    private val background = ColorDrawable()
    private val backgroundColor = ContextCompat.getColor(context, R.color.red)
    private val clearPaint = Paint()

    init {
        //to clear the background color in case of cancellation
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7F
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        val isCancelled = dX == 0f && !isCurrentlyActive

        //if it is cancelled
        if (isCancelled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(),
                itemView.right.toFloat(), itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        background.color = backgroundColor
        background.bounds = Rect(
            (itemView.right + dX).toInt(), itemView.top,
            itemView.right, itemView.bottom)
        background.draw(c)
    }

    private fun clearCanvas(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        canvas.drawRect(left, top, right, bottom, clearPaint)
    }
}