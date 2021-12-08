package com.example.projetinf717.ui.addads

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projetinf717.Application
import com.example.projetinf717.data.httpServices.Ads
import com.example.projetinf717.data.httpServices.VolleyCallbackJsonObject
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject
import java.util.*

class AddAdsViewModel : ViewModel() {
    private val ads = Ads()
    private val geocoder: Geocoder = Geocoder(Application.appContext, Locale.getDefault())


    private val _text = MutableLiveData<String>().apply {
        value = "This is add ads Fragment"
    }
    val text: LiveData<String> = _text

    private val mAction: MutableLiveData<Action> = MutableLiveData<Action>()

    fun getAction(): LiveData<Action> {
        return mAction
    }


    fun createAd(title: String, address: String,desc : String,estateType: String,
                   estatePrice: String, numberBath: String, numberBed: String,
                   email: String, phone: String, rent: Boolean
    ){
        val latLong: LatLng
        val addressToLatLong: List<Address> =
            geocoder.getFromLocationName(address, 1)
        if(addressToLatLong.isNotEmpty()) {
            latLong = LatLng(addressToLatLong[0].latitude, addressToLatLong[0].longitude)
            val cb: VolleyCallbackJsonObject = object: VolleyCallbackJsonObject {
                override fun onSuccess(result: JSONObject?) {
                    showAdsCreated()
                }
                override fun onError() {
                    showInvalidArguments()
                }
            }
            ads.createAd(title,address,desc,estateType,estatePrice,numberBath,numberBed
                ,email,phone, rent, latLong, cb)
        }else{
            showBadAddress()
        }


    }
    private fun showInvalidArguments() {
        mAction.value = Action(Action.SHOW_INVALID_FORM)
    }
    private fun showAdsCreated() {
        mAction.value = Action(Action.SHOW_AD_CREATED)
    }
    private fun showBadAddress() {
        mAction.value = Action(Action.SHOW_BAD_ADDRESS)
    }
}



class Action(val value: Int) {

    companion object {
        const val SHOW_AD_CREATED = 0
        const val SHOW_INVALID_FORM = 1
        const val SHOW_BAD_ADDRESS = 2
    }
}