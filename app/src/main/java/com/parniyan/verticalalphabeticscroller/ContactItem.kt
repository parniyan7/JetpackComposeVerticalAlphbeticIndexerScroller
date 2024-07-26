package com.parniyan.verticalalphabeticscroller


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.parniyan.verticalalphabeticscroller.model.Contact

/**
 ** Created by Parniyan on 7/21/24.
 **
 */


@Composable
internal fun ContactItem(
    modifier: Modifier = Modifier,
    item: Contact,
    onCheckChanged: () -> Unit,
    isLastItem: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onCheckChanged
                )
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text = item.name,
                maxLines = 1,
                style = TextStyle.Default,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        }
        if (!isLastItem) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .alpha(0.5f)
                    .background(color = Color.Gray)
            )
        }
    }
}