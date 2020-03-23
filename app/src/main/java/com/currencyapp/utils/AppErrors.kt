package com.currencyapp.utils

import java.lang.Exception

//TODO maybe remove?
sealed class AppErrors: Exception()

class InternetConnectionError: AppErrors()

class ServerError: AppErrors()

class NoOfflineDataError: AppErrors()