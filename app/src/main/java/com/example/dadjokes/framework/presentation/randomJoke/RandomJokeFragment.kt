package com.example.dadjokes.framework.presentation.randomJoke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dadjokes.R
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.databinding.FragmentRandomJokeBinding
import com.example.dadjokes.framework.presentation.BaseFragment
import com.example.dadjokes.framework.presentation.state.JokeStateEvent
import com.example.dadjokes.util.sendJokeBySMS
import com.example.dadjokes.util.shareJokeOnSocialMedia
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class RandomJokeFragment : BaseFragment() {

    private var _binding : FragmentRandomJokeBinding ?= null
    private val binding get() = _binding
    private val TAG = "RandomJokeFragment"
    private var currentJoke : Joke ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomJokeBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.setupChannel()
        requestRandomJoke()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        likeImgClickListener()
        shareImgClickListener()
        smsImgClickListener()
        getNewJokeBtnClickListener()
    }


    private fun shareImgClickListener()
    {
        binding?.shareImg?.setOnClickListener {
            currentJoke?.joke?.let { joke->
                context?.shareJokeOnSocialMedia(joke = joke)
            }
        }
    }


    private fun smsImgClickListener()
    {
        binding?.smsImg?.setOnClickListener {
            currentJoke?.joke?.let { joke->
                context?.sendJokeBySMS(joke = joke)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.action_randomJokeFragment_to_favouriteJokesFragment)
        return true
    }

    private fun setJokeToUI()
    {
        binding?.jokeTv?.text = currentJoke?.joke ?: ""
        if (currentJoke?.isFavourite == true)
            binding?.likeImg?.setBackgroundResource(R.drawable.ic_red_heart)
        else
            binding?.likeImg?.setBackgroundResource(R.drawable.ic_white_heart)
    }


    override fun onResume() {
        super.onResume()
        setJokeToUI()
    }

    private fun getNewJokeBtnClickListener()
    {
        binding?.getNewJokeBtn?.setOnClickListener {
            requestRandomJoke()
        }
    }

    private fun likeImgClickListener()
    {
        binding?.likeImg?.setOnClickListener {
            currentJoke?.let { joke->
                if (currentJoke?.isFavourite == true)
                {
                    //remove from favourites
                    viewModel.setStateEvent(JokeStateEvent.DeleteJokeFromFavouritesEvent(joke))
                }
                else
                {
                    // add to favourites
                    viewModel.setStateEvent(JokeStateEvent.AddToFavouriteEvent(joke = joke))
                }
            }
        }
    }




    private fun requestRandomJoke()
    {
        viewModel.setStateEvent(JokeStateEvent.GetRandomJokeEvent())
    }

    private fun subscribeObservers()
    {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState?.jokeModel?.let { jokeModel ->
                Log.d(TAG, "random joke ==> ${jokeModel.joke}")

                if (jokeModel.isDeleteFromFavouriteList == true)
                {
                    if (jokeModel.joke.id == currentJoke?.id)
                    {
                        currentJoke = jokeModel.joke
                        setJokeToUI()
                    }
                }
                else
                {
                    currentJoke = jokeModel.joke
                    setJokeToUI()
                }
            }
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
            stateMessage?.let { message ->
                val errorMsg = message.response.message.toString()
                Log.e(TAG, "message_joke_api => $errorMsg")
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                viewModel.clearErrorMessage()
            }
        })

        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "should_show_progressbar => $it")
            if (it) binding?.progressBar?.visibility = View.VISIBLE
            else binding?.progressBar?.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}