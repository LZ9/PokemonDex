package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.lodz.android.corekt.anko.getAssetsFileContent
import com.lodz.android.pandora.mvvm.vm.BaseRefreshViewModel
import com.lodz.android.pandora.utils.coroutines.CoroutinesWrapper
import com.lodz.android.pokemondex.bean.base.BaseListBean
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.config.Constant
import kotlin.collections.ArrayList

/**
 * @author zhouL
 * @date 2022/3/11
 */
class PokeDexViewModel : BaseRefreshViewModel() {

    var mDataList = MutableLiveData<HashMap<Int, ArrayList<PkmInfoBean>>>()


    fun requestDataList(context: Context) {

        CoroutinesWrapper.create(this)
            .request {
                getDataMap(JSON.parseObject(
                    context.getAssetsFileContent(Constant.POKEMON_INFO_FILE_NAME),
                    object: TypeReference<BaseListBean<PkmInfoBean>>(){}
                ))
            }
            .action {
                onSuccess {
                    setSwipeRefreshFinish()
                    mDataList.value = it
                    showStatusCompleted()
                }
                onError { e, isNetwork ->
                    showStatusError(e)
                    toastShort(e.toString())
                }
            }
    }

    private fun getDataMap(bean: BaseListBean<PkmInfoBean>):HashMap<Int, ArrayList<PkmInfoBean>> {
        val map = HashMap<Int, ArrayList<PkmInfoBean>>()
        map[Constant.POKE_GENERATION_1] = ArrayList()
        map[Constant.POKE_GENERATION_2] = ArrayList()
        map[Constant.POKE_GENERATION_3] = ArrayList()
        map[Constant.POKE_GENERATION_4] = ArrayList()
        map[Constant.POKE_GENERATION_5] = ArrayList()
        map[Constant.POKE_GENERATION_6] = ArrayList()
        map[Constant.POKE_GENERATION_7] = ArrayList()
        map[Constant.POKE_GENERATION_8] = ArrayList()

        for (item in bean.records) {
            if (item.generation == Constant.POKE_GENERATION_1){
                map[Constant.POKE_GENERATION_1]?.add(item)
            }
            if (item.generation == Constant.POKE_GENERATION_2){
                map[Constant.POKE_GENERATION_2]?.add(item)
            }
            if (item.generation == Constant.POKE_GENERATION_3){
                map[Constant.POKE_GENERATION_3]?.add(item)
            }
            if (item.generation == Constant.POKE_GENERATION_4){
                map[Constant.POKE_GENERATION_4]?.add(item)
            }
            if (item.generation == Constant.POKE_GENERATION_5){
                map[Constant.POKE_GENERATION_5]?.add(item)
            }
            if (item.generation == Constant.POKE_GENERATION_6){
                map[Constant.POKE_GENERATION_6]?.add(item)
            }
            if (item.generation == Constant.POKE_GENERATION_7){
                map[Constant.POKE_GENERATION_7]?.add(item)
            }
            if (item.generation == Constant.POKE_GENERATION_8){
                map[Constant.POKE_GENERATION_8]?.add(item)
            }
        }
        return map
    }
}