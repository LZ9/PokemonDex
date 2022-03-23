package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.lodz.android.corekt.anko.getAssetsFileContent
import com.lodz.android.corekt.anko.toArrayList
import com.lodz.android.pandora.mvvm.vm.BaseRefreshViewModel
import com.lodz.android.pandora.utils.coroutines.CoroutinesWrapper
import com.lodz.android.pokemondex.bean.base.BaseListBean
import com.lodz.android.pokemondex.bean.poke.pkm.PokemonInfoBean
import com.lodz.android.pokemondex.config.Constant
import java.util.ArrayList

/**
 * @author zhouL
 * @date 2022/3/11
 */
class PokeDexViewModel : BaseRefreshViewModel() {

    var mDataList = MutableLiveData<ArrayList<PokemonInfoBean>>()


    fun requestDataList(context: Context) {

        CoroutinesWrapper.create(this)
            .request {
                JSON.parseObject(context.getAssetsFileContent(Constant.POKEMON_INFO_FILE_NAME), object: TypeReference<BaseListBean<PokemonInfoBean>>(){})
            }
            .action {
                onSuccess {
                    setSwipeRefreshFinish()
                    mDataList.value = it.records.toArrayList()
                }
            }
    }
}