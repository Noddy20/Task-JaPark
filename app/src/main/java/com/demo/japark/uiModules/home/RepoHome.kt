package com.demo.japark.uiModules.home

import android.content.Context
import androidx.lifecycle.liveData
import com.demo.japark.R
import com.demo.japark.data.room.CitiesDatabase
import com.demo.japark.data.room.FoodsDatabase
import com.demo.japark.models.BaseResponse
import com.demo.japark.models.data.ModelJapanCity
import com.demo.japark.models.data.ModelJapanFood
import com.demo.japark.models.data.ModelMainResponse
import com.demo.japark.network.RetrofitApiClient
import com.demo.japark.uiModules.base.BaseRepo
import com.demo.japark.utils.InternetUtil
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RepoHome @Inject constructor(mAppContext: Context,
                                   private val mInternetUtil: InternetUtil,
                                   private val mRetrofitClient: RetrofitApiClient,
                                   private val mCitiesDb: CitiesDatabase,
                                   private val mFoodsDb: FoodsDatabase): BaseRepo(mAppContext) {

    fun getMainResponse(corContext: CoroutineContext) = liveData(corContext) {
        fun errorResponse() = BaseResponse.error<ModelMainResponse?>(getString(
            if (mInternetUtil.isNetConnected) R.string.err_msg_general else R.string.msg_you_are_offline))

        //Report loading status to observer
        emit(BaseResponse.loading(getString(R.string.msg_loading_data)))

        //Add persisted data source
        emitSource(getPersistedData(corContext))

        kotlin.runCatching {
            mRetrofitClient.getMainDataAsync().await().let { response->
                Timber.d("getMainResponse -> $response")

                if (response == null){
                    //This will emit either the persisted data or the error
                    //Because for example if there is no internet than this emit might cancel the above emitSource for persisted data [line 33]
                    emitSource(getPersistedData(corContext, errorResponse()))

                } else if(response.cities.isNullOrEmpty() && response.foods.isNullOrEmpty()) {
                    emit(BaseResponse.noData<ModelMainResponse?>(getString(R.string.err_msg_no_data)))
                    //Clear persisted data because response was success but empty from server
                    clearPersistedDb()

                } else {
                    emit(BaseResponse.success(response, getString(R.string.msg_success)))
                    //Persist the response
                    insertCities(response.cities?: emptyList())
                    insertFoods(response.foods?: emptyList())

                }
            }
        }.onFailure {
            Timber.e("getMainResponse Exc -> $it")
            //This will emit either the persisted data or the error
            //Because for example if there is no internet than this emit might cancel the above emitSource for persisted data [line 33]
            emitSource(getPersistedData(corContext, errorResponse()))
        }
    }

    private fun getPersistedData(corContext: CoroutineContext,
                                 errorResponse: BaseResponse<ModelMainResponse?>? = null) = liveData(corContext) {
        val cities = mCitiesDb.citiesDao().loadAllCities()
        val foods = mFoodsDb.foodsDao().loadAllFoods()

        Timber.d("Persisted_Data Cities -> $cities \n Foods -> $foods")

        //emit data only if there is some data in cities or foods
        //else emit the error response if it's not null
        if (!cities.isNullOrEmpty() || !foods.isNullOrEmpty())
            emit(BaseResponse.success(ModelMainResponse(cities, foods), getString(R.string.msg_success)))
        else if (errorResponse != null) emit(errorResponse)
    }

    private suspend fun insertCities(list: List<ModelJapanCity>){
        mCitiesDb.citiesDao().insertCities(list)
    }

    private suspend fun insertFoods(list: List<ModelJapanFood>){
        mFoodsDb.foodsDao().insertFoods(list)
    }


    suspend fun clearPersistedDb(){
        mCitiesDb.citiesDao().nukeTable()
        mFoodsDb.foodsDao().nukeTable()
    }

}