/* NewPlayer
 *
 * @author Christian Schabesberger
 *
 * Copyright (C) NewPipe e.V. 2024 <code(at)newpipe-ev.de>
 *
 * NewPlayer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPlayer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPlayer.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.newpipe.newplayer.ui.videoplayer.controller

import androidx.annotation.OptIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import net.newpipe.newplayer.R
import net.newpipe.newplayer.uiModel.NewPlayerUIState
import net.newpipe.newplayer.uiModel.InternalNewPlayerViewModel
import net.newpipe.newplayer.uiModel.NewPlayerViewModelDummy
import net.newpipe.newplayer.ui.theme.VideoPlayerTheme

@OptIn(UnstableApi::class)
@Composable

/** @hide */
internal fun CenterUI(
    modifier: Modifier = Modifier,
    viewModel: InternalNewPlayerViewModel,
    uiState: NewPlayerUIState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
    ) {

        Box(modifier = Modifier.size(80.dp)) {
            androidx.compose.animation.AnimatedVisibility(
                uiState.currentPlaylistItemIndex != 0,
                enter = fadeIn(animationSpec = tween(400)),
                exit = fadeOut(animationSpec = tween(400))

            ) {
                CenterControlButton(
                    buttonModifier = Modifier.fillMaxSize(),
                    iconModifier = Modifier.size(40.dp),
                    icon = Icons.Filled.SkipPrevious,
                    contentDescription = stringResource(R.string.widget_description_previous_stream),
                    onClick = viewModel::prevStream
                )
            }
        }

        CenterControlButton(
            buttonModifier = Modifier.size(80.dp),
            iconModifier = Modifier.size(60.dp),
            icon = if (uiState.playing) Icons.Filled.Pause else Icons.Filled.PlayArrow,
            contentDescription = stringResource(
                if (uiState.playing) R.string.widget_description_pause
                else R.string.widget_description_play
            ),
            onClick = if (uiState.playing) viewModel::pause else viewModel::play
        )
        Box(modifier = Modifier.size(80.dp)) {
            androidx.compose.animation.AnimatedVisibility(
                uiState.currentPlaylistItemIndex < uiState.playList.size - 1,
                enter = fadeIn(animationSpec = tween(400)),
                exit = fadeOut(animationSpec = tween(400))
            ) {
                CenterControlButton(
                    buttonModifier = Modifier.fillMaxSize(),
                    iconModifier = Modifier.size(40.dp),
                    icon = Icons.Filled.SkipNext,
                    contentDescription = stringResource(R.string.widget_description_next_stream),
                    onClick = viewModel::nextStream
                )
            }
        }
    }
}


@Composable
private fun CenterControlButton(
    buttonModifier: Modifier,
    iconModifier: Modifier,
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        modifier = buttonModifier
    ) {
        Icon(
            imageVector = icon, modifier = iconModifier, contentDescription = contentDescription
        )
    }
}

///////////////////////////////////////////////////////////////////
// Preview
///////////////////////////////////////////////////////////////////

@OptIn(UnstableApi::class)
@Preview(device = "spec:width=1080px,height=600px,dpi=440,orientation=landscape")
@Composable
private fun VideoPlayerControllerUICenterUIPreview() {
    VideoPlayerTheme {
        Surface(color = Color.Black) {
            CenterUI(
                viewModel = NewPlayerViewModelDummy(),
                uiState = NewPlayerUIState.DUMMY.copy(
                    isLoading = false,
                    playing = true,
                    currentPlaylistItemIndex = 1
                )
            )
        }
    }
}
