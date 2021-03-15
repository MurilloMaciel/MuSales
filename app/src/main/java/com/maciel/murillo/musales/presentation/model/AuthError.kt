package com.maciel.murillo.musales.presentation.model

import com.maciel.murillo.musales.R

enum class AuthError(val resource: Int) {

    VALID_EMAIL(R.string.auth_error_valid_email),
    STRONGEST_PASSWORD(R.string.auth_error_strongest_password),
    ACCOUNT_ALREADY_EXISTS(R.string.auth_error_account_already_exists),
    WRONG_USER_INFO(R.string.auth_error_info_doesnt_exists),
    USER_NOT_EXISTS(R.string.auth_error_user_not_exists),
    GENERIC(R.string.auth_error_generic),
    EMPTY_EMAIL(R.string.auth_error_email_empty),
    EMPTY_PASSWORD(R.string.auth_error_password_empty),
    EMPTY_NAME(R.string.auth_error_name_empty),
    EMAIL_INVALID(R.string.auth_error_invalid_email),
}
