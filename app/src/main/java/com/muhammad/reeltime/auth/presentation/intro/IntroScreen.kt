package com.muhammad.reeltime.auth.presentation.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammad.reeltime.R
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.ui.components.ReelTimeActionButton
import com.muhammad.reeltime.ui.components.ReelTimeOutlinedActionButton

@Composable
fun IntroScreen(onSignUpClick: () -> Unit, onSignInClick: () -> Unit) {
    IntroScreenContent { action ->
        when (action) {
            IntroAction.OnSignInClick -> onSignInClick()
            IntroAction.OnSignUpClick -> onSignUpClick()
        }
    }
}

@Composable
fun IntroScreenContent(onAction: (IntroAction) -> Unit) {
    GradientBackground {
        Column(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.Center
            ) {
                RunwellLogoVertical()
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 48.dp)
            ) {
                Text(
                    text = stringResource(R.string.welcome_to_runwell),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.runwell_description),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(32.dp))
                ReelTimeOutlinedActionButton(
                    text = stringResource(R.string.sign_in),
                    isLoading = false,
                    onClick = {
                        onAction(IntroAction.OnSignInClick)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(32.dp))
                ReelTimeActionButton(
                    isLoading = false,
                    text = stringResource(R.string.sign_up),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onAction(IntroAction.OnSignUpClick)
                }
            }
        }
    }
}

@Composable
fun RunwellLogoVertical(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}