package com.lodz.android.pokemondex.bean.base

/**
 * 基础列表实体
 * @author zhouL
 * @date 2022/3/22
 */
class BaseListBean<T> {
    var records: ArrayList<T> = arrayListOf()
}