package com.example.imageshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionManager
import com.example.imageshow.ui.adapter.PhotosAdapter
import com.example.imageshow.data.PhotoRepository
import com.example.imageshow.api.PhotoService
import com.example.imageshow.databinding.ActivityMainBinding
import com.example.imageshow.model.Photo
import com.example.imageshow.ui.PhotoDetailActivity
import com.example.imageshow.ui.PhotoListViewModel
import com.example.imageshow.ui.PhotoListViewModelFactory
import com.example.imageshow.ui.adapter.OnItemClickListener
import com.example.imageshow.ui.adapter.PhotosLoadStateAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ryu on 13,五月,2021
 */
class MainActivity() : AppCompatActivity(), CoroutineScope {
    private val TAG = "MainActivity"
    private var job: Job = Job()
    private lateinit var binding: ActivityMainBinding

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var viewModel: PhotoListViewModel

    private val photoService = PhotoService.getInstance()

    /// show by girdView as a listView
    private val listLayoutManager = GridLayoutManager(this, 1)

    private val adapter = PhotosAdapter(object : OnItemClickListener {
        override fun onItemClick(item: Photo?, view: AppCompatImageView) {
            val intent = Intent(applicationContext, PhotoDetailActivity::class.java).apply {
                putExtra("smallUrl", item?.urls?.small)
                putExtra("fullUrl", item?.urls?.full)
            }

            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@MainActivity,
                Pair(view, Config.PHOTO_DETAIL_TRANSITION_NAME)
            )
            ActivityCompat.startActivity(this@MainActivity, intent, activityOptions.toBundle())
        }


    },listLayoutManager)

    /// show by gridView
    private val gridLayoutManager = GridLayoutManager(this, 3)

    /// show by girdView as a listView
//    private val listLayoutManager = GridLayoutManager(this, 1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, PhotoListViewModelFactory(PhotoRepository(photoService))).get(
                PhotoListViewModel::class.java
            )
        initAdapter()
        initSearch()
        // for switch between list and grid
        initSwitchLayout()
        initList()
    }

    private fun initList() {
        job.cancel()
        job = lifecycleScope.launch {
            viewModel.getPhotoList(null).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun searchList(terms: String) {
        job.cancel()
        job = lifecycleScope.launch {
            viewModel.getPhotoList(terms).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {

        binding.photoRecycleview.layoutManager = listLayoutManager
        binding.photoRecycleview.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PhotosLoadStateAdapter { adapter.retry() },
            footer = PhotosLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            showEmptyList(isListEmpty)

            binding.photoRecycleview.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    private fun updatePhotoListFromInput() {
        binding.searchPhoto.text.trim().let {
            if (it.isNotEmpty()) {
                searchList(it.toString())
            }
        }
    }

    private fun initSwitchLayout() {

        binding.btnToList.setOnClickListener {
            listLayoutManager.spanCount = 1
            TransitionManager.beginDelayedTransition(binding.photoRecycleview)
            adapter.notifyItemRangeChanged(0,adapter.itemCount - 1)
        }

        binding.btnToGrid.setOnClickListener {
            listLayoutManager.spanCount = 3
            TransitionManager.beginDelayedTransition(binding.photoRecycleview)
            adapter.notifyItemRangeChanged(0,adapter.itemCount - 1)
        }
    }

    private fun initSearch() {

        binding.searchPhoto.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updatePhotoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchPhoto.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updatePhotoListFromInput()
                true
            } else {
                false
            }
        }

        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.photoRecycleview.scrollToPosition(0) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.photoRecycleview.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.photoRecycleview.visibility = View.VISIBLE
        }
    }


}