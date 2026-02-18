Notes App (Android – Kotlin, MVVM, Room, Hilt)

Overview

Notes App is a simple Android application that allows users to create, update, search, and delete notes.
The app is built using modern Android development practices including MVVM architecture, Kotlin Coroutines, Flow, Room Database, Navigation Component, and Hilt Dependency Injection.

Features

Add new notes

Edit existing notes

Delete notes with swipe action

Real-time search functionality

Automatic UI refresh using Kotlin Flow

MVVM clean architecture implementation

Dark & Light theme support


Tech Stack

Language: Kotlin

Architecture: MVVM

UI: XML + ViewBinding

Database: Room

Dependency Injection: Hilt

Async Programming: Kotlin Coroutines + Flow

Navigation: Navigation Component


Architecture

The app follows MVVM (Model-View-ViewModel) architecture:

View (Fragments) – UI rendering and user interaction

ViewModel – Business logic and state handling using StateFlow

Repository – Data abstraction layer

Room Database – Local data storage


Screens

Home Screen – Displays list of notes and search bar.

Add/Edit Screen – Create or update notes


