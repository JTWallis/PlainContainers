package com.hybris.plaincontainers.views.fragments

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import com.hybris.plaincontainers.R
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

abstract class MetadataContainerFragment: MetadataBaseFragment() {

    private lateinit var tvColor: TextView
    private lateinit var ivColorIcon: ImageView
    private lateinit var viewColorPick: View

    protected abstract fun getInitColor(): Int

    override fun initViews(view: View) {
        tvColor = view.findViewById(R.id.tvEditColor)
        ivColorIcon = view.findViewById(R.id.ivEditColor)
        viewColorPick = view.findViewById(R.id.viewEditColor)
        val frameColorPick = view.findViewById<View>(R.id.frameEditColor)

        tvColor.visibility = View.VISIBLE
        ivColorIcon.visibility = View.VISIBLE
        frameColorPick.visibility = View.VISIBLE

        super.initViews(view)
    }

    override fun initViewFillData() {
        super.initViewFillData()
        setColor(getInitColor())
    }

    override fun initListeners() {
        super.initListeners()

        ivColorIcon.setOnClickListener { onColorClicked() }
        viewColorPick.setOnClickListener { onColorClicked() }
    }

    private fun onColorClicked() {
        val builder = ColorPickerDialog.Builder(context)
            .setTitle(requireContext().getString(R.string.metadata_colorpicker_title))
            .setPositiveButton(
                requireContext().getString(R.string.metadata_btn_apply),
                object: ColorEnvelopeListener {
                    override fun onColorSelected(envelope: ColorEnvelope, fromUser: Boolean) {
                        setColor(envelope.color)
                    }
            })
            .setNegativeButton(
                requireContext().getString(R.string.metadata_popup_delete_cancel),
                object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, i: Int) {
                        dialog.dismiss()
                    }
            })
            .attachAlphaSlideBar(false)

        val color =
            if(getInitColor() != 0) getInitColor()
            else requireContext().getColor(R.color.defaultContainer)
        builder.colorPickerView.setInitialColor(color)

        builder.show()
    }

    private fun setColor(@ColorInt color: Int) {
        viewColorPick.setBackgroundColor(color)
    }

    protected fun getColor(): Int {
        val background = viewColorPick.background
        if(background is ColorDrawable) {
            return background.color
        }

        return 0
    }
}