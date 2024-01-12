package com.example.controller

open class CustomException(message: String): RuntimeException(message)

class RackPlanNotFoundException(message: String): CustomException(message)
