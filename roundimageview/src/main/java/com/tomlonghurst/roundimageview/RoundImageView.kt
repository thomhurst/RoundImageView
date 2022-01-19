package com.tomlonghurst.roundimageview

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.tomlonghurst.roundimageview.databinding.RivLayoutBinding
import com.tomlonghurst.roundimageview.extensions.onGlobalLayout
import com.tomlonghurst.roundimageview.extensions.remove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * A view to show Round Images that works with vectors.
 */
class RoundImageView : FrameLayout {

    private lateinit var viewBinding: RivLayoutBinding
    private lateinit var view: View
    @Suppress("MemberVisibilityCanBePrivate")
    var image: ImageView? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        view = LayoutInflater.from(context).inflate(R.layout.riv_layout, this)
        viewBinding = RivLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        setAttributes(attrs, defStyleAttr)

        viewBinding.pictureCardCircleOutline.apply {
            onGlobalLayout {
                radius = min(height, width).div(2).toFloat()
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

        viewBinding.pictureCardCircleOutline.apply {
            onGlobalLayout {
                if (value <= 0f) {
                    viewBinding.pictureCardCircleOutline.setCardBackgroundColor(Color.TRANSPARENT)
                } else {
                    viewBinding.pictureCardCircleOutline.setCardBackgroundColor(borderColor)
                }
            }
        }
    }

    private fun setInnerCardSize(borderWidth: Float) {
        viewBinding.pictureCardCircle.apply {
            onGlobalLayout {
                val newHeight = view.height.minus(borderWidth.times(2).roundToInt())
                val newWidth = view.width.minus(borderWidth.times(2).roundToInt())
                layoutParams.height = newHeight
                layoutParams.width = newWidth

                radius = min(newHeight, newWidth).div(2).toFloat()
                requestLayout()
            }
        }
    }

    private var borderColor: Int = Color.WHITE
        set(value) {
            field = value

            viewBinding.pictureCardCircleOutline.setCardBackgroundColor(value)
        }

    private var cardBackgroundColor: Int = Color.WHITE
        set(value) {
            field = value

            viewBinding.pictureCardCircle.setCardBackgroundColor(value)
        }

    private var placeholderColor: Int = Int.MIN_VALUE

    private var placeholderDrawable: Int = -1

    override fun onFinishInflate() {
        super.onFinishInflate()

        getImage()

        val image = image ?: throw IllegalStateException("No ImageView has been added to the RoundImageView")

        GlobalScope.launch(Dispatchers.Main) {
            image.apply {
                remove()
                viewBinding.innerCircleLayout.addView(image)


                if (placeholderDrawable != -1) {
                    val drawable = ContextCompat.getDrawable(context, placeholderDrawable)?.mutate()
                    if (placeholderColor != Int.MIN_VALUE) {
                        drawable?.setColorFilter(placeholderColor, PorterDuff.Mode.SRC_IN)
                    }
                    image.setImageDrawable(drawable)
                }
            }
        }
    }

    private fun setAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView, defStyleAttr, 0)

        borderWidth = a.getDimension(R.styleable.RoundImageView_riv_border_width, 0f)
        borderColor = a.getColor(R.styleable.RoundImageView_riv_border_color, Color.WHITE)
        cardBackgroundColor = a.getColor(R.styleable.RoundImageView_riv_circle_background_color, Color.WHITE)
        placeholderColor = a.getColor(R.styleable.RoundImageView_riv_circle_placeholder_color, Int.MIN_VALUE)
        placeholderDrawable = a.getResourceId(R.styleable.RoundImageView_riv_circle_placeholder_drawable, -1)

        a.recycle()
    }
}