# ContactVerticalAlphabeticIndexerScroller
![jet](https://github.com/user-attachments/assets/0bf1aae6-c3a7-496a-bbd4-5e75a5670239)

This is an Android Jetpack Compose implementation of a contact list with a vertical alphabetic indexer and scrolling functionality.
![gif](https://github.com/user-attachments/assets/9583fc2b-b7a5-4647-b33a-9b21e4606aae)

## Features

- Displays a list of contacts with their names
- Organizes the contacts by their first letter (A-Z, or "#" for non-alphabetic names)
- Provides a vertical alphabetic indexer on the right side of the screen
- Allows the user to scroll through the contact list and jump to specific letters using the indexer
- Calls an `onCheckChanged` callback whenever a contact is checked/unchecked

## Usage

To use this component, you can call the `AlphabeticContactListWithVerticalIndexer` composable function and pass in the necessary parameters:

```kotlin
@Composable
fun AlphabeticContactListWithVerticalIndexer(
    modifier: Modifier = Modifier,
    contacts: List<Contact>,
    onCheckChanged: (Contact) -> Unit
)
```

- `modifier`: The modifier to be applied to the entire component.
- `contacts`: The list of `Contact` objects to be displayed.
- `onCheckChanged`: A callback function that is called whenever a contact is checked or unchecked.

The `Contact` data class should have at least the following properties:

```kotlin
data class Contact(
    val name: String,
    val isChecked: Boolean
)
```

## Implementation Details

The `AlphabeticContactListWithVerticalIndexer` composable function is responsible for:

1. Organizing the contacts by their first letter (A-Z, or "#" for non-alphabetic names).
2. Creating a lazy column to display the contacts.
3. Rendering a vertical alphabetic indexer on the right side of the screen.
4. Scrolling the contact list to the appropriate position when an item in the indexer is clicked or dragged.

The `VerticalAlphabetIndexer` composable function is responsible for:

1. Rendering the vertical alphabetic indexer.
2. Handling click and drag events on the indexer.
3. Notifying the parent composable about the current letter being interacted with.

The implementation uses various Jetpack Compose features, such as `LazyColumn`, `remember`, `rememberCoroutineScope`, `rememberLazyListState`, and `pointerInput`.

## Example

Here's an example of how you can use the `AlphabeticContactListWithVerticalIndexer` composable:

```kotlin
@Composable
fun ContactsScreen(
    contacts: List<Contact>,
    onContactCheckedChanged: (Contact) -> Unit
) {
    AlphabeticContactListWithVerticalIndexer(
        contacts = contacts,
        onCheckChanged = onContactCheckedChanged
    )
}
```

In this example, the `ContactsScreen` composable function receives a list of `Contact` objects and a callback function to handle changes in the checked state of the contacts.
