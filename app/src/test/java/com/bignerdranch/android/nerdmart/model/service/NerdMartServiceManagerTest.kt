package com.bignerdranch.android.nerdmart.model.service

import com.bignerdranch.android.nerdmart.BuildConfig
import org.junit.Before

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(23))
open class NerdMartServiceManagerTest {

    @Before
    fun setUp() {
    }
}