package com.example.dadjokes.framework.presentation.favouriteJokes

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.example.dadjokes.R
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.databinding.FragmentFavouriteJokesBinding
import com.example.dadjokes.framework.presentation.BaseFragment
import com.example.dadjokes.framework.presentation.favouriteJokes.adapter.FavouriteJokesAdapter
import com.example.dadjokes.framework.presentation.state.JokeStateEvent
import com.example.dadjokes.util.customizeEmptyContentView
import com.example.dadjokes.util.displayInfoDialog
import com.example.dadjokes.util.sendJokeBySMS
import com.example.dadjokes.util.shareJokeOnSocialMedia
import kotlinx.android.synthetic.main.no_content_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class FavouriteJokesFragment : BaseFragment(), FavouriteJokesAdapter.FavouriteJokesCallback {

    private var _binding : FragmentFavouriteJokesBinding ?= null
    private val binding get() = _binding
    private val TAG = "FavouriteJokesFragment"
    private val favouriteJokesAdapter = FavouriteJokesAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View? {
       _binding = FragmentFavouriteJokesBinding.inflate(inflater, container, false)
       return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeEmptyContentView(no_content_layout , R.drawable.ic_alert)
        initFavouriteJokesRv()
        getAllFavouriteJokes()
        searchFavouriteListener()
        subscribeObservers()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.favourites_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        context?.displayInfoDialog(getString(R.string.info_share))
        return true
    }

    private fun searchFavouriteListener()
    {
        binding?.searchSv?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) viewModel.setStateEvent(
                    JokeStateEvent.SearchJokesEvent(
                        newText
                    )
                )
                else getAllFavouriteJokes()
                return false
            }
        })
    }


    private fun initFavouriteJokesRv()
    {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true;
        linearLayoutManager.stackFromEnd = true;
        binding?.favouriteRv?.layoutManager = linearLayoutManager
        binding?.favouriteRv?.adapter = favouriteJokesAdapter
    }

    private fun subscribeObservers()
    {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.favouriteJokes?.let { list ->
                    favouriteJokesAdapter.addItems(list)
                    updateUi()
            }

            viewState.jokeModel?.let {
                favouriteJokesAdapter.deleteJoke(it.joke)
                updateUi()
            }
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, Observer { stateMessage ->
            stateMessage?.let { message ->
                Log.d(TAG, "message_jokes_error => ${message.response.message.toString()}")
                viewModel.clearErrorMessage()
            }
        })


        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "should_show_progressbar => $it")
            if (it) binding?.progressBar?.visibility = View.VISIBLE
            else binding?.progressBar?.visibility = View.GONE
        })
    }


    private fun updateUi()
    {
        if (favouriteJokesAdapter.itemCount == 0)
            no_content_layout.visibility = View.VISIBLE
        else
            no_content_layout.visibility = View.GONE
    }

    private fun getAllFavouriteJokes()
    {
        viewModel.setStateEvent(JokeStateEvent.GetAllFavouriteJokesEvent())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        favouriteJokesAdapter.saveState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        favouriteJokesAdapter.restoreState(savedInstanceState)
    }

    override fun deleteJokeListener(joke: Joke) {
        MaterialDialog(requireContext())
            .show {
                title(text = getString(R.string.sure))
                    .message(text = getString(R.string.question_delete_favourite_joke))
                positiveButton(R.string.yes_dialog){
                    viewModel.setStateEvent(JokeStateEvent.DeleteJokeFromFavouritesEvent(joke = joke, isDeleteFromFavouriteList = true))
                }
                negativeButton(R.string.no_dialog){
                    dismiss()
                }

            }
    }

    override fun jokeLongPressClickListener(joke: Joke) {
        val listItems = arrayListOf<String>()
        listItems.add(getString(R.string.share_social_media))
        listItems.add(getString(R.string.share_sms))
        context?.let {mContext->
            MaterialDialog(mContext).show {
                title(text = getString(R.string.share))
                listItemsSingleChoice(items = listItems) { dialog, index, text ->
                    if (index == 0)
                        mContext.shareJokeOnSocialMedia(joke.joke)
                    else if (index == 1)
                        mContext.sendJokeBySMS(joke.joke)
                }
            }
        }
    }

}