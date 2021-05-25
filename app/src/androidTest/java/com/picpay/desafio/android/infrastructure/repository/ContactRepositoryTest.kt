package com.picpay.desafio.android.infrastructure.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.framework.room.ContactDatabase
import com.picpay.desafio.android.getLiveDataValue
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ContactRepositoryTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ContactRepository
    private lateinit var db: ContactDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java)
            .allowMainThreadQueries().build()
        repository = ContactRepository(db)
    }

    @Test
    fun should_get_users_from_api_and_return_success(){
        runBlocking {
            val users = repository.getUsers()
            assertTrue(users.code() == 200)
        }
    }

    @Test
    fun should_save_and_return_saved_contacts() = runBlocking{
        val user = User("","", 1,"")
        repository.upsert(listOf(user))

        val usersSaved = repository.getSavedContacts().getLiveDataValue().find {user ->
            user.username == ""
            user.id == 1
            user.name == ""
            user.img == ""
        }

        assertEquals(user, usersSaved)
    }
}