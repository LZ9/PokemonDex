package com.lodz.android.pokemondex.ui.type

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.lodz.android.corekt.anko.getAssetsFileContent
import com.lodz.android.corekt.security.AES
import com.lodz.android.pandora.base.activity.BaseActivity
import com.lodz.android.pandora.utils.coroutines.CoroutinesWrapper
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.pandora.widget.rv.decoration.GridItemDecoration
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.bean.base.BaseListBean
import com.lodz.android.pokemondex.bean.poke.type.TypeInfoBean
import com.lodz.android.pokemondex.config.Constant
import com.lodz.android.pokemondex.databinding.ActivityPokeTypeBinding

/**
 * 宝可梦属性页面
 * @author zhouL
 * @date 2022/3/16
 */
class PokeTypeActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PokeTypeActivity::class.java)
            context.startActivity(intent)
        }
    }

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
        val layoutManager = GridLayoutManager(getContext(), 18)
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

    override fun setListeners() {
        super.setListeners()
        mTagAdapter.setOnItemClickListener { viewHolder, item, position ->
            showPopup(viewHolder.itemView, item)
        }
    }

    private fun showPopup(view: View, bean: TypeInfoBean) {
        val popupWindow = PokeTypePopupWindow(getContext(), bean)
        popupWindow.create()
        popupWindow.getPopup().showAsDropDown(view, -50, 20)
    }

    override fun initData() {
        super.initData()

        CoroutinesWrapper.create(getContext())
            .request {
                JSON.parseObject(AES.decrypt(getAssetsFileContent(Constant.TYPE_INFO_FILE_NAME), AES.KEY), object:TypeReference<BaseListBean<TypeInfoBean>>(){})
            }
            .action {
                onSuccess {
                    mTagAdapter.setData(it.records.toMutableList())
                    mTableAdapter.setData(it.records.toMutableList())
                    showStatusCompleted()
                }
            }

    }

}