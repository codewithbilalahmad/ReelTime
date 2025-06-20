package com.muhammad.reeltime.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muhammad.reeltime.R
import com.muhammad.reeltime.ui.theme.Poppins
import com.muhammad.reeltime.ui.components.ReelTimeActionButton
import com.muhammad.reeltime.ui.components.ReelTimePasswordTextField
import com.muhammad.reeltime.ui.components.ReelTimeTextField
import com.muhammad.reeltime.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) {event ->
        when(event){
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, event.text.asString(context), Toast.LENGTH_SHORT).show()
            }
            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(context, R.string.youre_logged_in, Toast.LENGTH_SHORT).show()
                onLoginSuccess()
            }
        }
    }
    LoginScreenContent(state = state, onAction = {action ->
        when(action){
            is LoginAction.OnSignUpClick -> onSignUpClick()
            else -> Unit
        }
        viewModel.onAction(action)
    })
}

@Composable
private fun LoginScreenContent(state: LoginState, onAction: (LoginAction) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp).padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_back),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.reeltime_welcome_text),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(48.dp))
            ReelTimeTextField(
                state = state.email,
                startIcon = ImageVector.vectorResource(R.drawable.email),
                endIcon = null,
                keyboardType = KeyboardType.Email,
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                modifier = Modifier.fillMaxWidth(), type = ContentType.EmailAddress
            )
            Spacer(Modifier.height(16.dp))
            ReelTimePasswordTextField(
                state = state.password, type = ContentType.Password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(LoginAction.OnTogglePasswordVisibility)
                },
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(32.dp))
            ReelTimeActionButton(
                text = stringResource(R.string.login),
                isLoading = state.isLoggingIn,
                enabled = state.canLogin && !state.isLoggingIn
            ) {
                onAction(LoginAction.OnLoginClick)
            }
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    append(stringResource(R.string.dont_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(R.string.sign_up)
                    )
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold, fontFamily = Poppins
                        )
                    ) {
                        append(stringResource(R.string.sign_up))
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f).padding(vertical = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                ClickableText(text = annotatedString, onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onAction(LoginAction.OnSignUpClick)
                    }
                })
            }
        }
}