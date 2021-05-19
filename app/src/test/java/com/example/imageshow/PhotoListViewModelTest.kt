package com.example.imageshow

import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.imageshow.data.PhotoPagingSourceInterface
import com.example.imageshow.data.PhotoRepositoryInterface
import com.example.imageshow.model.Photo
import com.example.imageshow.model.Urls
import com.example.imageshow.model.User
import com.example.imageshow.ui.PhotoListViewModel
import com.example.imageshow.ui.adapter.PhotosAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


import org.junit.Assert.*

/**
 * Created by Ryu on 18,五月,2021
 */

@ExperimentalCoroutinesApi
class PhotoListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
    }

    /**
     * test for PhotoPagingSource & PhotoRepository
     */
    @Test
    fun getPhotoPagingDataTest(): Unit = runBlockingTest(testDispatcher) {

        val differ = AsyncPagingDataDiffer(
            diffCallback = PhotosAdapter.diffCallback,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = testDispatcher,
            workerDispatcher = testDispatcher,
        )
        val photo = Photo("a", "b", Urls("c", "d", "e", "f", "g"), User("h", "i"))
        val photosMock = arrayListOf<Photo>(photo, photo, photo, photo)
        val repositoryMock = PhotoRepositoryMock(photosMock)
        val mockViewModel = PhotoListViewModel(repositoryMock)

        val job = launch {
            mockViewModel.getPhotoList(null).collectLatest {
                differ.submitData(it)
            }
        }

        assertEquals(photosMock.size, differ.snapshot().size)
        assertEquals(photosMock[1].description, differ.snapshot()[1]?.description)
        job.cancel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getPhotoList() {
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

class PhotoPagingSourceFake(private val mock: List<Photo>) :
    PhotoPagingSourceInterface {

    override fun getPagingSourceData(): PagingSource<Int, Photo> {
        return object : PagingSource<Int, Photo>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
                return LoadResult.Page(
                    data = mock,
                    prevKey = null,
                    nextKey = null
                )
            }

            // ignored in test
            override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
                return null
            }
        }
    }


}

class PhotoRepositoryMock(private val mock: List<Photo>) : PhotoRepositoryInterface {

    private val source = PhotoPagingSourceFake(mock)

    override fun getListPhotoStream(terms: String?): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = Config.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { source.getPagingSourceData() }
        ).flow
    }

}



