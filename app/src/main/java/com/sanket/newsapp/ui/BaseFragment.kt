package com.sanket.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sanket.newsapp.ConnectionLiveData

abstract class BaseFragment : Fragment() {

    protected val connectionLiveData by lazy { ConnectionLiveData(requireContext()) }

}