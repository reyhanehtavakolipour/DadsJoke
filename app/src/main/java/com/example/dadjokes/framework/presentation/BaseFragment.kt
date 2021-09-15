package com.example.dadjokes.framework.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dadjokes.framework.presentation.viewModel.JokeViewModel

abstract class BaseFragment: Fragment() {

    val viewModel : JokeViewModel by activityViewModels()

}