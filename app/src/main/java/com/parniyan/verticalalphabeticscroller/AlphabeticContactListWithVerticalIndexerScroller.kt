package com.parniyan.verticalalphabeticscroller


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.parniyan.verticalalphabeticscroller.model.Contact
import kotlinx.coroutines.launch

/**
 ** Created by Parniyan on 7/23/24.
 **
 */
@Composable
fun AlphabeticContactListWithVerticalIndexer(
    modifier: Modifier = Modifier,
    contacts: List<Contact>,
    onClick: (Contact) -> Unit
) {
    val aToZ = ('A'..'Z').map { it.toString() }.toList()
    val firstLetters = mutableMapOf<String, List<Contact>>()

    // Initialize the firstLetters map with all the possible letters (A-Z and "#")
    (aToZ + "#").forEach { letter ->
        firstLetters[letter] = emptyList()
    }

    // Populate the contact lists for each letter
    contacts.forEach { contact ->
        val firstLetter = if (aToZ.contains(contact.name.uppercase().firstOrNull().toString())) {
            contact.name.uppercase().first().toString()
        } else {
            "#"
        }
        val currentList = firstLetters[firstLetter] ?: emptyList()
        firstLetters[firstLetter] = currentList + contact
    }

    var indexedList: List<Pair<Int, Contact>> by remember { mutableStateOf(emptyList()) }
    indexedList = contacts.withIndex().map { (index, contact) ->
        index to contact
    }

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = scrollState,
            flingBehavior = ScrollableDefaults.flingBehavior()
        ) {
            firstLetters.forEach { (letter, names) ->
                if (names.isNotEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(vertical = 8.dp),
                                text = letter,
                                color = Color.Black
                            )
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .alpha(0.5f)
                                    .background(color = Color.Gray)
                            )
                        }
                    }
                    items(names) { item: Contact ->
                        ContactItem(
                            item = item,
                            isLastItem = names.last() == item,
                            onCheckChanged = { onClick(item) }
                        )
                    }
                }
            }
        }

        VerticalAlphabetIndexer(
            modifier = Modifier
                .align(Alignment.Top)
                .padding(start = 8.dp),
            items = firstLetters.keys.toList(),
            onItemDragged = { letter ->
                firstLetters.forEach { (initialLetter, contactList) ->
                    if (letter == initialLetter) {
                        if (contactList.isNotEmpty() && indexedList.isNotEmpty()) {
                            indexedList.firstOrNull { it.second.name == contactList.first().name }
                                ?.let { indexedContact ->
                                    val index = indexedContact.first
                                    scope.launch {
                                        scrollState.scrollToItem(index)
                                    }
                                }
                        }
                    }
                }
            },
            onItemClicked = { letter ->
                firstLetters.forEach { (initialLetter, contactList) ->
                    if (letter == initialLetter) {
                        if (contactList.isNotEmpty() && indexedList.isNotEmpty()) {
                            indexedList.firstOrNull { it.second.name == contactList.first().name }
                                ?.let { indexedContact ->
                                    val index = indexedContact.first
                                    scope.launch {
                                        scrollState.scrollToItem(index)
                                    }
                                }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun VerticalAlphabetIndexer(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemClicked: (String) -> Unit,
    onItemDragged: (String) -> Unit
) {
    var currentLetter by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 8.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val letterIndex = (offset.y / this.size.height * items.size).toInt()
                        currentLetter = items.getOrNull(letterIndex)
                        currentLetter?.let { onItemDragged(it) }
                    },
                    onDragEnd = {
                        currentLetter = null
                    },
                    onDrag = { change, _ ->
                        val letterIndex =
                            (change.position.y / this.size.height * items.size).toInt()
                        val newLetter = items.getOrNull(letterIndex)
                        if (newLetter != currentLetter) {
                            currentLetter = newLetter
                            currentLetter?.let { onItemDragged(it) }
                        }
                    }
                )
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.forEach { letter ->
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = { onItemClicked(letter) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(vertical = 2.dp),
                text = letter,
                color = if (letter == currentLetter) Color.Gray else Color.Blue
            )
        }
    }
}