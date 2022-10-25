package com.lodz.android.pokemondex.ui.pokedex

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lodz.android.corekt.anko.getAssetsFileContent
import com.lodz.android.pandora.mvvm.vm.BaseRefreshViewModel
import com.lodz.android.pandora.utils.coroutines.CoroutinesWrapper
import com.lodz.android.pandora.utils.jackson.parseJsonObject
import com.lodz.android.pokemondex.bean.base.BaseListBean
import com.lodz.android.pokemondex.bean.poke.pkm.PkmGenBean
import com.lodz.android.pokemondex.bean.poke.pkm.PkmInfoBean
import com.lodz.android.pokemondex.config.Constant

/**
 * @author zhouL
 * @date 2022/3/11
 */
class PokeDexViewModel : BaseRefreshViewModel() {

    var mDataList = MutableLiveData<ArrayList<PkmGenBean>>()

    fun requestDataList(context: Context) {

        CoroutinesWrapper.create(this)
            .request { getDataMap(context.getAssetsFileContent(Constant.POKEMON_INFO_FILE_NAME).parseJsonObject()) }
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

    private fun getDataMap(bean: BaseListBean<PkmInfoBean>): ArrayList<PkmGenBean> {
        val gen1Bean = createPkmGenBean(Constant.POKE_GENERATION_1)
        val gen2Bean = createPkmGenBean(Constant.POKE_GENERATION_2)
        val gen3Bean = createPkmGenBean(Constant.POKE_GENERATION_3)
        val gen4Bean = createPkmGenBean(Constant.POKE_GENERATION_4)
        val gen5Bean = createPkmGenBean(Constant.POKE_GENERATION_5)
        val gen6Bean = createPkmGenBean(Constant.POKE_GENERATION_6)
        val gen7Bean = createPkmGenBean(Constant.POKE_GENERATION_7)
        val gen8Bean = createPkmGenBean(Constant.POKE_GENERATION_8)

        for (item in bean.records) {
            if (item.generation == Constant.POKE_GENERATION_1) {
                gen1Bean.pkmList.add(item)
                continue
            }
            if (item.generation == Constant.POKE_GENERATION_2) {
                gen2Bean.pkmList.add(item)
                continue
            }
            if (item.generation == Constant.POKE_GENERATION_3) {
                gen3Bean.pkmList.add(item)
                continue
            }
            if (item.generation == Constant.POKE_GENERATION_4) {
                gen4Bean.pkmList.add(item)
                continue
            }
            if (item.generation == Constant.POKE_GENERATION_5) {
                gen5Bean.pkmList.add(item)
                continue
            }
            if (item.generation == Constant.POKE_GENERATION_6) {
                gen6Bean.pkmList.add(item)
                continue
            }
            if (item.generation == Constant.POKE_GENERATION_7) {
                gen7Bean.pkmList.add(item)
                continue
            }
            if (item.generation == Constant.POKE_GENERATION_8) {
                gen8Bean.pkmList.add(item)
                continue
            }
        }
        val list = ArrayList<PkmGenBean>()
        list.add(gen1Bean)
        list.add(gen2Bean)
        list.add(gen3Bean)
        list.add(gen4Bean)
        list.add(gen5Bean)
        list.add(gen6Bean)
        list.add(gen7Bean)
        list.add(gen8Bean)
        return list
    }

    private fun createPkmGenBean(gen: Int): PkmGenBean {
        val bean = PkmGenBean()
        bean.generation = gen
        bean.isExpand = gen == 1
        return bean
    }
}