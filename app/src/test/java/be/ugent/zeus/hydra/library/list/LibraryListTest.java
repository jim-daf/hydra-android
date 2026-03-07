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

package be.ugent.zeus.hydra.library.list;

import java.util.ArrayList;
import java.util.List;

import be.ugent.zeus.hydra.library.Library;
import org.junit.Test;

import static be.ugent.zeus.hydra.testing.Assert.assertRecordParcelable;

/**
 * @author Niko Strijbol
 */
public class LibraryListTest {

    @Test
    public void parcelable() {
        Library library = new Library("dept", "email@example.com", new ArrayList<>(List.of("Addr")), "Name", "Name NL", "Name EN", "CODE", new ArrayList<>(List.of("0472")), false, null, null, "51.0", "3.7", new ArrayList<>(), null, "campus", "faculty", "https://link", false);
        assertRecordParcelable(new LibraryList("Name", 1, new ArrayList<>(List.of(library))));
    }
}
