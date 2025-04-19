package com.myolwinoo.smartproperty.di

import com.myolwinoo.smartproperty.features.appointments.AppointmentsViewModel
import com.myolwinoo.smartproperty.features.explore.ExploreViewModel
import com.myolwinoo.smartproperty.features.login.LoginViewModel
import com.myolwinoo.smartproperty.features.profile.ProfileViewModel
import com.myolwinoo.smartproperty.features.propertydetail.PropertyDetailViewModel
import com.myolwinoo.smartproperty.features.propertydetail.appointmentform.AppointmentFormViewModel
import com.myolwinoo.smartproperty.features.register.RegisterViewModel
import com.myolwinoo.smartproperty.features.search.SearchViewModel
import com.myolwinoo.smartproperty.features.wishlists.WishlistsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ExploreViewModel)
    viewModelOf(::AppointmentsViewModel)
    viewModelOf(::WishlistsViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::PropertyDetailViewModel)
    viewModelOf(::AppointmentFormViewModel)
}