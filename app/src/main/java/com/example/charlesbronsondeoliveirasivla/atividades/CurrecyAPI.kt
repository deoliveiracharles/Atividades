package com.example.charlesbronsondeoliveirasivla.atividades

import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.ArrayList


class CurrecyAPI() : AsyncTask<String, Void, ArrayList<CurrencyDTO>>(), Parcelable {

    var url : String = "https://api.exchangeratesapi.io/latest"
    var latest : MediaType? = MediaType.parse("application/json; charset=utf-8")

    var client : OkHttpClient = OkHttpClient()
    private lateinit var callback : CurrencyCallback
    private var get : Boolean = false

    constructor(parcel: Parcel) : this() {
        url = parcel.readString()
        get = parcel.readByte() != 0.toByte()
    }

    constructor(callback: CurrencyCallback, get : Boolean) : this (){
        this.callback = callback
        this.get = get
    }

    override fun doInBackground(vararg params: String?): ArrayList<CurrencyDTO>? {

        var currencies : List<CurrencyDTO> = ArrayList<CurrencyDTO>()

        var request : Request = Request.Builder().url(url).build()

        try {
            var response : Response = client.newCall(request).execute()
            var json : String = response.body()!!.string()
            var gson : Gson = Gson()

            currencies = gson.fromJson(json, currencies.javaClass )

            Log.w("currency", currencies[0].toString())

            return ArrayList()

        }catch (e: IOException){
            return null
        }

    }

    override fun onPostExecute(result: ArrayList<CurrencyDTO>?) {
        super.onPostExecute(result)
        this.callback.response (result, get)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeByte(if (get) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrecyAPI> {
        override fun createFromParcel(parcel: Parcel): CurrecyAPI {
            return CurrecyAPI(parcel)
        }

        override fun newArray(size: Int): Array<CurrecyAPI?> {
            return arrayOfNulls(size)
        }
    }

}