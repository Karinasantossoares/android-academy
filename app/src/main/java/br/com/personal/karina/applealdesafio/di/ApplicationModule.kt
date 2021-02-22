package br.com.personal.karina.applealdesafio.di

import android.content.Context
import br.com.personal.karina.applealdesafio.persistence.AppPreferences
import br.com.personal.karina.applealdesafio.repository.AuthenticationRepository
import br.com.personal.karina.applealdesafio.repository.ExercisePhotoRepository
import br.com.personal.karina.applealdesafio.repository.ExerciseRepository
import br.com.personal.karina.applealdesafio.repository.TrainingRepository
import br.com.personal.karina.applealdesafio.useCase.AuthenticationUseCase
import br.com.personal.karina.applealdesafio.useCase.ExerciseUseCase
import br.com.personal.karina.applealdesafio.useCase.TrainingUseCase
import br.com.personal.karina.applealdesafio.viewModel.AuthenticationViewModel
import br.com.personal.karina.applealdesafio.viewModel.TrainingViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val modulesTraining = module {
    single { AuthenticationRepository(androidContext(), get())}
    single { ExerciseRepository(androidContext(), get(),get())}
    single { TrainingRepository(androidContext(), get(),get(),get())}
    single { ExercisePhotoRepository( get(),get())}
    single { AuthenticationUseCase(androidContext(), get())}
    single { ExerciseUseCase( androidContext(),get(),get())}
    single { TrainingUseCase(androidContext(), get())}
    single { FirebaseAuth.getInstance() }
    single { FirebaseDatabase.getInstance().reference }
    single { FirebaseStorage.getInstance()}
    single { androidContext().getSharedPreferences(AppPreferences.NAME,Context.MODE_PRIVATE) }
    single { AppPreferences(get()) }
    viewModel { AuthenticationViewModel(androidContext(),get(),get())}
    viewModel { TrainingViewModel(androidContext(),get(),get())}
}
