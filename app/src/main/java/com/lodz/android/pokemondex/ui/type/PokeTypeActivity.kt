package com.lodz.android.pokemondex.ui.type

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.lodz.android.corekt.anko.getAssetsFileContent
import com.lodz.android.corekt.security.AES
import com.lodz.android.pandora.base.activity.BaseActivity
import com.lodz.android.pandora.utils.coroutines.CoroutinesWrapper
import com.lodz.android.pandora.utils.viewbinding.bindingLayout
import com.lodz.android.pokemondex.R
import com.lodz.android.pokemondex.bean.poke.TypeInfoListBean
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

    /** 适配器 */
    private lateinit var mAdapter: PokeTypeAdapter

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(R.string.main_types)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(getContext(), 18)
        layoutManager.orientation = RecyclerView.VERTICAL
        mAdapter = PokeTypeAdapter(getContext())
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.adapter = mAdapter
    }

    override fun onClickBackBtn() {
        super.onClickBackBtn()
        finish()
    }

    override fun initData() {
        super.initData()

        CoroutinesWrapper.create(getContext())
            .request {
                JSON.parseObject(AES.decrypt(getAssetsFileContent(Constant.TYPE_INFO_FILE_NAME), AES.KEY), TypeInfoListBean::class.java)
            }
            .action {
                onSuccess {
                    mAdapter.setData(it.records.toMutableList())
                    showStatusCompleted()
                }
            }

    }

}