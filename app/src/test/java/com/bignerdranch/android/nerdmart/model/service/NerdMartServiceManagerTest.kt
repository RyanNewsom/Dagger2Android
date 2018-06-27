package com.bignerdranch.android.nerdmart.model.service

import com.bignerdranch.android.nerdmart.BuildConfig
import com.bignerdranch.android.nerdmart.TestNerdMartApplication
import com.bignerdranch.android.nerdmart.inject.TestInjector
import com.bignerdranch.android.nerdmart.model.DataStore
import com.bignerdranch.android.nerdmartservice.model.NerdMartDataSourceInterface
import com.bignerdranch.android.nerdmartservice.service.payload.Cart
import com.bignerdranch.android.nerdmartservice.service.payload.Product
import com.bignerdranch.android.nerdmartservice.service.payload.User
import io.reactivex.observers.TestObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(23))
open class NerdMartServiceManagerTest {
    @Inject
    lateinit var mDataStore: DataStore
    @Inject
    lateinit var mNerdMartServiceManager: NerdMartServiceManager
    @Inject
    lateinit var mNerdMartDataSourceInterface: NerdMartDataSourceInterface

    @Before
    fun setUp() {
        val application = RuntimeEnvironment.application as TestNerdMartApplication
        TestInjector.obtain(application)
                .inject(this)
    }

    @Test
    fun testDependencyInjectionWorked() {
        assertThat(mNerdMartServiceManager).isNotNull()
        assertThat(mDataStore).isNotNull()
        assertThat(mNerdMartDataSourceInterface).isNotNull()
    }

    @Test
    fun testAuthenticateMethodReturnsFalseWithInvalidCredentials() {
        val observer = TestObserver<Boolean>()
        mNerdMartServiceManager.authenticate("johnnydoe", "WRONGPASSWORD")
                .subscribe(observer)
        observer.awaitTerminalEvent()
        assertThat(observer.values()[0]).isEqualTo(false)
        assertThat(mDataStore.getCachedUser()).isEqualTo(User.NO_USER)
    }

    @Test
    fun testAuthenticateMethodReturnsTrueWithValidCredentials() {
        val observer = TestObserver<Boolean>()
        mNerdMartServiceManager.authenticate("johnnydoe", "pizza")
                .subscribe(observer)
        observer.awaitTerminalEvent()
        assertThat(observer.values()[0]).isEqualTo(true)
        assertThat(mDataStore.getCachedUser()).isEqualTo(mNerdMartDataSourceInterface.user)
    }

    @Test
    fun testGetProductsReturnsExpectedProductListings() {
        mDataStore.mCachedUser = mNerdMartDataSourceInterface.user

        val observer = TestObserver<List<Product>>()
        mNerdMartServiceManager.getProducts().toList().subscribe(observer)
        observer.awaitTerminalEvent()
        assertThat(observer.values()
                .get(0)).containsAll(mNerdMartDataSourceInterface.products)
    }

    @Test
    fun testGetCartReturnsCartAndCachesCartInDataStore() {
        mDataStore.mCachedUser = mNerdMartDataSourceInterface.user

        val observer = TestObserver<Cart>()
        mNerdMartServiceManager.getCart().subscribe(observer)
        observer.awaitTerminalEvent()
        val actual = observer.values().get(0)
        assertThat(actual).isNotEqualTo(null)
        assertThat(mDataStore.getCachedCart()).isEqualTo(actual)
        assertThat(mDataStore.getCachedCart()!!.getProducts()).hasSize(0)
    }

    @Test
    fun testPostProductToCartAddsProductsToUserCart() {
        mDataStore.mCachedUser = mNerdMartDataSourceInterface.user

        val products = ArrayList<Product>()
        val postProductsObserver = TestObserver<Boolean>()
        products.addAll(mNerdMartDataSourceInterface.products)
        mNerdMartServiceManager.postProductToCart(products.get(0))
                .subscribe(postProductsObserver)
        postProductsObserver.awaitTerminalEvent()
        assertThat(postProductsObserver.values().get(0)).isEqualTo(true)
        val cartObserver = TestObserver<Cart>()
        mNerdMartServiceManager.getCart().subscribe(cartObserver)
        cartObserver.awaitTerminalEvent()
        val cart = cartObserver.values().get(0) as Cart
        assertThat(cart.getProducts()).hasSize(1)
    }
}