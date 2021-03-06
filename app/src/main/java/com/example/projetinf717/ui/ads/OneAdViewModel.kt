package com.example.projetinf717.ui.ads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetinf717.data.httpServices.Ads
import com.example.projetinf717.data.httpServices.VolleyCallbackAds
import com.example.projetinf717.data.httpServices.VolleyCallbackJsonObject
import org.json.JSONArray
import org.json.JSONObject

class OneAdViewModel : ViewModel() {
    private val mAction: MutableLiveData<OneAdAction> = MutableLiveData<OneAdAction>()

    var ad = JSONObject()

    fun getAction(): LiveData<OneAdAction> {
        return mAction
    }

    private val ads = Ads()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    fun displayHome(id: Int) {
        val cb: VolleyCallbackAds = object : VolleyCallbackAds {
            override fun onSuccessObject(result: JSONObject) {
                if (result != null) {
                    ad = result
                }
                showDataLoaded()
            }

            override fun onSuccessArray(result: JSONArray) {
                // Not used
            }

            override fun onError() {
                showNetworkError()
            }
        }
        ads.getHouse(cb, id)
    }

    private fun showDataLoaded() {
        mAction.value = OneAdAction(OneAdAction.HOME_LOADED)
    }

    private fun showNetworkError() {
        mAction.value = OneAdAction(OneAdAction.NETWORK_ERROR)
    }

    private fun onDeleteComplete() {
        mAction.value = OneAdAction(OneAdAction.DELETE_SUCCESS)
    }

    private fun onDeleteError() {
        mAction.value = OneAdAction(OneAdAction.DELETE_ERROR)

    }

    fun deleteHousing(id: Int) {
        val cb: VolleyCallbackAds = object : VolleyCallbackAds {
            override fun onSuccessObject(result: JSONObject) {
                onDeleteComplete()
            }

            override fun onSuccessArray(result: JSONArray) {
                // Not used
            }

            override fun onError() {
                onDeleteError()
            }
        }
        ads.deleteHousing(id, cb)
    }
}

class OneAdAction(val value: Int) {
    companion object {
        const val HOME_LOADED = 0
        const val NETWORK_ERROR = 1
        const val DELETE_SUCCESS = 2
        const val DELETE_ERROR = 3
    }
}