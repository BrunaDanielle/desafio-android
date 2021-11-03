package com.picpay.desafio.android.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.ContactsApplication
import com.picpay.desafio.android.domain.models.UserEntity
import com.picpay.desafio.android.framework.room.ContactDatabase
import com.picpay.desafio.android.infrastructure.repository.ContactRepositoryImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class ContactViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private lateinit var contactViewModelSUT: ContactViewModel
    private lateinit var repositoryMock: ContactRepositoryImpl
    private lateinit var appMock: ContactsApplication

    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        repositoryMock = mock(ContactRepositoryImpl::class.java)
        appMock = mock(ContactsApplication::class.java)
        contactViewModelSUT = ContactViewModel(appMock, repositoryMock)

        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun getUsers() {
        runBlockingTest {

            `when`(repositoryMock.getUsers()).thenReturn(
                Response.success(listOf(UserEntity("", "", 1, "")))
            )
            `when`(repositoryMock.getSavedContacts()).thenReturn(null)

            contactViewModelSUT.getUsers()

            verify(repositoryMock, times(1)).getUsers()

        }
    }

    @Test
    fun getSavedUsers(){
        contactViewModelSUT.getSavedContacts()

        verify(repositoryMock, atLeast(1)).getSavedContacts()
    }

    @Test
    fun insertUsers() = runBlockingTest{
        val db = mock(ContactDatabase::class.java)

        val listUsers = UserEntity("", "", 1, "")

//    `when`(db.getContactDao().upsert(listOf())).thenReturn(listOf(1))

        contactViewModelSUT.saveContacts(listOf(listUsers))

        verify(repositoryMock, times(1)).upsert(listOf(listUsers))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }
}