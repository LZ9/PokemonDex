package com.lodz.android.pokemondex.ui.poketype

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lodz.android.pandora.mvvm.base.activity.BaseVmActivity
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.pandora.utils.viewmodel.bindViewModel
import com.lodz.android.pandora.widget.rv.decoration.GridItemDecoration
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.bean.poke.type.PkmTypeInfoBean
import com.lodz.android.pokemondex.databinding.ActivityPokeTypeBinding

/**
 * 宝可梦属性页面
 * @author zhouL
 * @date 2022/3/16
 */
class PokeTypeActivity : BaseVmActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PokeTypeActivity::class.java)
            context.startActivity(intent)
        }
    }


    private val mViewModel by bindViewModel { PokeTypeViewModel() }

    override fun getViewModel(): PokeTypeViewModel = mViewModel

    private val mBinding: ActivityPokeTypeBinding by bindingLayout(ActivityPokeTypeBinding::inflate)

    override fun getViewBindingLayout(): View = mBinding.root

    /** 属性标签适配器 */
    private lateinit var mTagAdapter: PokeTypeTagAdapter

    /** 属性列表适配器 */
    private lateinit var mTableAdapter: PokeTypeTableAdapter

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(R.string.main_types)
        initTagRecyclerView()
        initTableRecyclerView()
    }

    private fun initTagRecyclerView() {
        val layoutManager = GridLayoutManager(getContext(), 4)
        layoutManager.orientation = RecyclerView.VERTICAL
        mTagAdapter = PokeTypeTagAdapter(getContext())
        mBinding.tagRv.layoutManager = layoutManager
        mBinding.tagRv.setHasFixedSize(true)
        mBinding.tagRv.addItemDecoration(GridItemDecoration.create(getContext()).setDividerSpace(5).setDividerInt(Color.WHITE))
        mBinding.tagRv.adapter = mTagAdapter
    }

    private fun initTableRecyclerView() {
        val layoutManager = GridLayoutManager(getContext(), 19)
        layoutManager.orientation = RecyclerView.VERTICAL
        mTableAdapter = PokeTypeTableAdapter(getContext())
        mBinding.tableRv.layoutManager = layoutManager
        mBinding.tableRv.setHasFixedSize(true)
        mBinding.tableRv.adapter = mTableAdapter
    }

    override fun onClickBackBtn() {
        super.onClickBackBtn()
        finish()
    }

    override fun onClickReload() {
        super.onClickReload()
        showStatusLoading()
        mViewModel.requestData(getContext())
    }

    override fun setListeners() {
        super.setListeners()
        mTagAdapter.setOnItemClickListener { viewHolder, item, position ->
            showPopup(viewHolder.itemView, item)
        }
    }

    override fun setViewModelObserves() {
        super.setViewModelObserves()
        mViewModel.mTypeList.observe(getLifecycleOwner()){
            mTagAdapter.setData(it)
        }

        mViewModel.mTableList.observe(getLifecycleOwner()){
            mTableAdapter.setData(it)
        }
    }

    private fun showPopup(view: View, bean: PkmTypeInfoBean) {
        val popupWindow = PokeTypePopupWindow(getContext(), bean)
        popupWindow.create()
        popupWindow.getPopup().showAsDropDown(view, -50, 20)
    }

    override fun initData() {
        super.initData()
        showStatusLoading()
        mViewModel.requestData(getContext())
    }

}