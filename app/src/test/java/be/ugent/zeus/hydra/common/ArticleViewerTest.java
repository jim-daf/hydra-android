/*
 * Copyright (c) 2021 The Hydra authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package be.ugent.zeus.hydra.common;

import android.net.Uri;

import java.time.OffsetDateTime;

import be.ugent.zeus.hydra.common.ui.customtabs.ActivityHelper;
import be.ugent.zeus.hydra.news.NewsArticle;
import be.ugent.zeus.hydra.schamper.Article;
import be.ugent.zeus.hydra.testing.RobolectricUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Niko Strijbol
 */
@RunWith(RobolectricTestRunner.class)
public class ArticleViewerTest {

    @Test
    public void viewArticleCustomTabsUGent() {
        ActivityHelper helper = mock(ActivityHelper.class);
        NewsArticle newsItem = new NewsArticle("content", "id", "https://example.com", OffsetDateTime.now(), "summary", "Title", OffsetDateTime.now());
        ArticleViewer.viewArticle(RobolectricUtils.getActivityContext(), newsItem, helper);
        verify(helper, times(1)).openCustomTab(any(Uri.class));
    }

    @Test
    public void viewArticleCustomTabsSchamper() {
        ActivityHelper helper = mock(ActivityHelper.class);
        Article newsItem = new Article("Title", "https://example.com", OffsetDateTime.now(), "Author", "Body", "https://example.com/image", "Category", "Intro", "color");
        ArticleViewer.viewArticle(RobolectricUtils.getActivityContext(), newsItem, helper);
        verify(helper, times(1)).openCustomTab(any(Uri.class));
    }
}
