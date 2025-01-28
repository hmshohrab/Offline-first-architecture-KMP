package com.puremindit.ofa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.crossfade
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Comment
import compose.icons.fontawesomeicons.solid.StreetView
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(onBackClick: () -> Unit) {
    val viewModel = koinViewModel<PostDetailViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues).padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Post Cover Image
            val sizeResolver = rememberConstraintsSizeResolver()
            val painter = rememberAsyncImagePainter(
                model = uiState.selectedPost?.coverURL,
                imageLoader = ImageLoader.Builder(LocalPlatformContext.current)
                    .crossfade(true)
                    .build()

            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.then(sizeResolver),
            )
            /*            AsyncImage(
                            model = "https://example.com/image.jpg",
                            contentDescription = null,
                        )
                        AsyncImage(
                            model = post.coverURL,
                            contentDescription = "Post Cover Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .padding(16.dp),
                            imageLoader =  ImageLoader.Builder(context)
                                .bitmapPoolPercentage(0.5)
                                .crossfade(true)
                                .build()
                        )*/

            Spacer(modifier = Modifier.height(8.dp))

            // Post Caption
            uiState.selectedPost?.caption?.let { caption ->
                Text(
                    text = caption,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Post Metadata
            uiState.selectedPost?.let { PostMetadataSection(post = it) }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.Gray.copy(alpha = 0.3f)
            )

            // Sponsored Tag
            if (uiState.selectedPost?.isSponsored == true) {
                Text(
                    text = "Sponsored",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(Color.Yellow.copy(alpha = 0.3f))
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun PostMetadataSection(post: Post) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Favorite, contentDescription = "Likes", tint = Color.Red)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${post.likesCount} Likes")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                FontAwesomeIcons.Regular.Comment, contentDescription = "Comments", tint = Color.Blue,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${post.commentsCount} Comments")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Share, contentDescription = "Shares", tint = Color.Green,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${post.sharesCount} Shares")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                FontAwesomeIcons.Solid.StreetView, contentDescription = "Views", tint = Color.Gray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${post.viewsCount} Views")
        }

        Spacer(modifier = Modifier.height(8.dp))

        post.locationName?.let { location ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.Magenta,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = location)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (post.isFavoritedByCurrentUser) Icons.Default.Star else Icons.Default.Star,
                contentDescription = "Favorite Status",
                tint = if (post.isFavoritedByCurrentUser) Color.Yellow else Color.Gray,
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (post.isFavoritedByCurrentUser) "Favorited" else "Not Favorited")
        }
    }
}
