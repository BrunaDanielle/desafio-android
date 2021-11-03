package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.ContactsModule.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContactsApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ContactsApplication)
            modules(presentationModule)
        }
    }
}