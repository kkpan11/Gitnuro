package app.ui

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import app.extensions.simpleName
import app.ui.components.SideMenuPanel
import app.ui.components.SideMenuSubentry
import app.ui.context_menu.tagContextMenuItems
import app.viewmodels.TagsViewModel
import org.eclipse.jgit.lib.Ref

@Composable
fun Tags(
    tagsViewModel: TagsViewModel,
    onTagClicked: (Ref) -> Unit,
) {
    val tagsState = tagsViewModel.tags.collectAsState()
    val tags = tagsState.value

    SideMenuPanel(
        title = "Tags",
        items = tags,
        icon = painterResource("tag.svg"),
        itemContent = { tag ->
            TagRow(
                tag = tag,
                onTagClicked = { onTagClicked(tag) },
                onCheckoutTag = { tagsViewModel.checkoutRef(tag) },
                onDeleteTag = { tagsViewModel.deleteTag(tag) }
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TagRow(
    tag: Ref,
    onTagClicked: () -> Unit,
    onCheckoutTag: () -> Unit,
    onDeleteTag: () -> Unit,
) {
    ContextMenuArea(
        items = {
            tagContextMenuItems(
                onCheckoutTag = onCheckoutTag,
                onDeleteTag = onDeleteTag,
            )
        }
    ) {
        SideMenuSubentry(
            text = tag.simpleName,
            iconResourcePath = "tag.svg",
            onClick = onTagClicked,
        )
    }
}