package com.lodz.android.pokemondex

import android.content.Context
import android.widget.LinearLayout
import com.lodz.android.imageloaderkt.ImageloaderManager
import com.lodz.android.pandora.base.application.BaseApplication

/**
 * @author zhouL
 * @date 2022/3/10
 */
class App :BaseApplication(){

    override fun onStartCreate() {
        initImageLoader(applicationContext)
        configTitleBarLayout()
        configLoadingLayout()
    }

    /** 配置标题栏 */
    private fun configTitleBarLayout() {
//        getBaseLayoutConfig().getTitleBarLayoutConfig().isNeedBackBtn = true
//        getBaseLayoutConfig().getTitleBarLayoutConfig().backBtnResId = R.drawable.pandora_ic_launcher
        getBaseLayoutConfig().getTitleBarLayoutConfig().backgroundColor = R.color.red
//        getBaseLayoutConfig().getTitleBarLayoutConfig().backBtnText = "返回"
//        getBaseLayoutConfig().getTitleBarLayoutConfig().backBtnTextColor = R.color.color_d9d9d9
//        getBaseLayoutConfig().getTitleBarLayoutConfig().backBtnTextSize = 14
        getBaseLayoutConfig().getTitleBarLayoutConfig().titleTextColor = R.color.white
//        getBaseLayoutConfig().getTitleBarLayoutConfig().titleTextSize = 18
//        getBaseLayoutConfig().getTitleBarLayoutConfig().isShowDivideLine = true
//        getBaseLayoutConfig().getTitleBarLayoutConfig().divideLineHeightDp = 10
//        getBaseLayoutConfig().getTitleBarLayoutConfig().divideLineColor = R.color.color_2f6dc9
//        getBaseLayoutConfig().getTitleBarLayoutConfig().isNeedElevation = false
//        getBaseLayoutConfig().getTitleBarLayoutConfig().elevationVale = 23f
    }

    private fun configLoadingLayout() {
//        getBaseLayoutConfig().getLoadingLayoutConfig().orientation = LinearLayout.VERTICAL
//        getBaseLayoutConfig().getLoadingLayoutConfig().isNeedTips = true
//        getBaseLayoutConfig().getLoadingLayoutConfig().tips = "正在获取数据"
//        getBaseLayoutConfig().getLoadingLayoutConfig().textColor = R.color.white
//        getBaseLayoutConfig().getLoadingLayoutConfig().textSize = 23
//        getBaseLayoutConfig().getLoadingLayoutConfig().backgroundColor = R.color.color_ff4081
//        getBaseLayoutConfig().getLoadingLayoutConfig().isIndeterminate = true
//        getBaseLayoutConfig().getLoadingLayoutConfig().indeterminateDrawable = android.R.drawable.progress_indeterminate_horizontal
//        getBaseLayoutConfig().getLoadingLayoutConfig().useSysDefDrawable = false
//        getBaseLayoutConfig().getLoadingLayoutConfig().pbWidthPx = dp2px(70)
//        getBaseLayoutConfig().getLoadingLayoutConfig().pbHeightPx = dp2px(70)
    }

    /** 初始化图片加载库 */
    private fun initImageLoader(context: Context) {
        ImageloaderManager.get().newBuilder()
            .setPlaceholderResId(R.mipmap.ic_launcher)//设置默认占位符
            .setErrorResId(R.mipmap.ic_launcher)// 设置加载失败图
            .setDirectoryFile(context.cacheDir)// 设置缓存路径
            .setDirectoryName("image_cache")// 缓存文件夹名称
            .build()
    }

    override fun onExit() {


    }
}