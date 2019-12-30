package com.example.androidtd


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.androidtd.network.Api
import kotlinx.android.synthetic.main.header_fragment.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class HeaderFragment : Fragment(){

    private val coroutineScope = MainScope()

//    private var headerText : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.header_fragment,container,false)
    }



    override fun onResume() {
        super.onResume()
        //Glide.with(this).load("https://goo.gl/gEgYUd").apply(RequestOptions.bitmapTransform( CircleCrop())).into(header_avatar)
        var avatarUrl: String=""
        coroutineScope.launch {
            var tem=Api.INSTANCE.userService.getInfo()
            header_text.text = (tem.body()?.firstName ?: "") +" "+ tem.body()?.lastName
            avatarUrl= tem.body()?.avatar ?:""

            if(avatarUrl.equals(""))
                context?.let { Glide.with(it).load("https://goo.gl/gEgYUd").apply(RequestOptions.bitmapTransform( CircleCrop())).into(header_avatar) }
            else
                context?.let { Glide.with(it).load(avatarUrl).apply(RequestOptions.bitmapTransform( CircleCrop())).into(header_avatar) }

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}