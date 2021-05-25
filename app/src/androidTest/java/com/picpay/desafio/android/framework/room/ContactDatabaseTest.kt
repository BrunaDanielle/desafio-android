package com.picpay.desafio.android.framework.room

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.runner.AndroidJUnit4
import com.picpay.desafio.android.domain.models.User
import com.picpay.desafio.android.getLiveDataValue
import com.picpay.desafio.android.infrastructure.interfaces.db.ContactDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ContactDatabaseTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: ContactDatabase
    private lateinit var dao: ContactDao


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java).build()
        dao = db.getContactDao()
    }

    @Test
    fun should_write_and_read_contacts() = runBlocking{
        val user = User("","", 1,"")
        dao.upsert(listOf(user))

        val usersSaved = dao.getAllContacts().getLiveDataValue().find {user ->
            user.username == ""
            user.id == 1
            user.name == ""
            user.img == ""
        }

        assertEquals(user, usersSaved)
    }

    @After
    fun closeDB(){
        db.close()
    }
}