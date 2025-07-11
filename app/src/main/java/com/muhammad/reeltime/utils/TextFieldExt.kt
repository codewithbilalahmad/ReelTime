package com.muhammad.reeltime.utils

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow

fun TextFieldState.textAsFlow() = snapshotFlow { text }