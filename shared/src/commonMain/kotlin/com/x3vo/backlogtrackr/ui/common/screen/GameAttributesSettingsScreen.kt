package com.x3vo.backlogtrackr.ui.common.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import backlogtrackr.shared.generated.resources.Res
import backlogtrackr.shared.generated.resources.add_24px
import com.x3vo.backlogtrackr.domain.model.GameAttribute
import com.x3vo.backlogtrackr.ui.common.components.GameAttributeItem
import com.x3vo.backlogtrackr.utils.AppDimensions
import org.jetbrains.compose.resources.painterResource


@Composable
fun GameAttributesSettingsScreen(
    screenTitle: String = "",
    handleBackNavigation: () -> Unit = {},
    userInput: String = "",
    handleUserInputChange: (String) -> Unit = {},
    userInputHint: String = "",
    attributesList: List<GameAttribute> = emptyList(),
    addAttributeHandler: (String) -> Unit = {},
    deleteAttributeHandler: (String) -> Unit = {},
    resetAttributesHandler: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if(source == NestedScrollSource.UserInput) {
                    focusManager.clearFocus()
                }
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if(listState.isScrollInProgress) {
            focusManager.clearFocus()
        }
    }

    ScreenWithScaffold(
        title = screenTitle,
        canNavBack = true,
        onNavigateBack = handleBackNavigation,
        navButtonEnabled = true,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimensions.paddingSmall),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = userInput,
                    onValueChange = { handleUserInputChange(it) },
                    label = { Text(userInputHint) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = {
                        addAttributeHandler(userInput)
                        handleUserInputChange("")
                        focusManager.clearFocus()
                    })
                )
                FilledIconButton(onClick = {
                    addAttributeHandler(userInput)
                    handleUserInputChange("")
                    focusManager.clearFocus()
                }) {
                    Icon(painter = painterResource(Res.drawable.add_24px), contentDescription = "Add")
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
                    .nestedScroll(nestedScrollConnection),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(attributesList) { attribute ->
                    GameAttributeItem(
                        attributeText = attribute.attributeName,
                        onDelete = { deleteAttributeHandler(attribute.attributeName) }
                    )
                    HorizontalDivider()
                }

                item {
                    Button(
                        onClick = { resetAttributesHandler() },
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    ) {
                        Text(text = "Reset to Default Platforms")
                    }
                }
            }

        }
    }


}