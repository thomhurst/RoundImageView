package com.tomlonghurst.roundimageview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.tomlonghurst.roundimageview.extensions.onGlobalLayout
import com.tomlonghurst.roundimageview.extensions.remove
import kotlinx.android.synthetic.main.riv_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class RoundImageView : FrameLayout {

    var image: ImageView? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        View.inflate(context, R.layout.riv_layout, this)

        getImage()

        getAttributes(attrs, defStyleAttr)

        picture_card_circle_outline.apply {
            onGlobalLayout {
                radius = Math.min(height, width).div(2).toFloat()
            }
        }
    }

    private fun getImage() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is ImageView) {
                image = view
                return
            }
        }
    }

    private var borderWidth: Float = 0f
    set(value) {
        field = value

        setInnerCardSize(value)

        picture_card_circle_outline.apply {
            onGlobalLayout {
                if (value <= 0f) {
                    picture_card_circle_outline.setCardBackgroundColor(Color.TRANSPARENT)
                } else {
                    picture_card_circle_outline.setCardBackgroundColor(borderColor)
                }
            }
        }
    }

    private fun setInnerCardSize(borderWidth: Float) {
        picture_card_circle.apply {
            onGlobalLayout {
                val newHeight = this@RoundImageView.height.minus(borderWidth.times(2).roundToInt())
                val newWidth = this@RoundImageView.width.minus(borderWidth.times(2).roundToInt())
                layoutParams.height = newHeight
                layoutParams.width = newWidth

                radius = Math.min(newHeight, newWidth).div(2).toFloat()
                requestLayout()
            }
        }
    }

    private var borderColor: Int = Color.WHITE
        set(value) {
            field = value

            picture_card_circle_outline.setCardBackgroundColor(value)
        }

    private var cardBackgroundColor: Int = Color.WHITE
        set(value) {
            field = value

            picture_card_circle.setCardBackgroundColor(value)
        }

    override fun onFinishInflate() {
        super.onFinishInflate()

        getImage()

        GlobalScope.launch(Dispatchers.Main) {
            image?.remove()
            inner_circle_layout.addView(image)
            image?.drawable
        }
    }

    private fun getAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let { attributeSet ->
            val a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0)

            borderWidth = a.getDimension(R.styleable.RoundImageView_riv_border_width, 0f)
            borderColor = a.getColor(R.styleable.RoundImageView_riv_border_color, Color.WHITE)
            cardBackgroundColor = a.getColor(R.styleable.RoundImageView_riv_circle_background_color, Color.WHITE)

            a.recycle()
        }
    }
}