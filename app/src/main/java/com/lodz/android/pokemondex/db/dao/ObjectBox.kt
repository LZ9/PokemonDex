package com.lodz.android.pokemondex.db.dao

import android.content.Context
import com.lodz.android.pokemondex.db.table.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import io.objectbox.android.BuildConfig

/**
 * @author zhouL
 * @date 2022/3/10
 */
object ObjectBox {

    private lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
        if (BuildConfig.DEBUG) {
            AndroidObjectBrowser(boxStore).start(context.applicationContext)
        }
    }

    fun <T> boxFor(cls: Class<T>): Box<T> = boxStore.boxFor(cls)
}