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

package be.ugent.zeus.hydra.association;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import be.ugent.zeus.hydra.common.utils.DateUtils;
import org.junit.Test;

import static be.ugent.zeus.hydra.testing.Assert.assertRecordParcelable;
import static org.junit.Assert.*;

public class EventTest {

    private static Event baseEvent() {
        return new Event(1L, "Title", OffsetDateTime.now(), OffsetDateTime.now().plusHours(2), "Location", "Address", "Description", "https://example.com", "assoc", false);
    }

    @Test
    public void parcelable() {
        assertRecordParcelable(baseEvent());
    }

    @Test
    public void shouldReturnNull_whenEndIsNull() {
        Event event = baseEvent().withEnd(null);
        assertNull(event.localEnd());
    }

    @Test
    public void shouldReturnLocal_whenEndIsNotNull() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        Event event = baseEvent().withEnd(offsetDateTime);
        LocalDateTime localDateTime = event.localEnd();
        assertNotNull(localDateTime);
        assertEquals(DateUtils.toLocalDateTime(offsetDateTime), localDateTime);
    }

    @Test
    public void shouldReturnLocal_whenStart() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        Event event = baseEvent().withStart(offsetDateTime);
        LocalDateTime localDateTime = event.localStart();
        assertEquals(DateUtils.toLocalDateTime(offsetDateTime), localDateTime);
    }

    @Test
    public void shouldHaveUniqueIdentifier_whenDataIsUnique() {
        long resulting = LongStream.range(0, 10).mapToObj(i -> new Event(i, "Title" + i, OffsetDateTime.now().plusHours(i), OffsetDateTime.now().plusHours(i + 1), "Location", "Address", "Desc", "https://example.com", "assoc", false)).map(Event::identifier).distinct().count();
        assertEquals(10, resulting);
    }

    @Test
    public void shouldBeSortedOnStartDate() {
        List<Event> events = LongStream.range(0, 10).mapToObj(i -> new Event(i, "Title" + i, OffsetDateTime.now().plusHours(i), OffsetDateTime.now().plusHours(i + 1), "Location", "Address", "Desc", "https://example.com", "assoc", false)).collect(Collectors.toList());
        List<Event> expected = new ArrayList<>(events);
        expected.sort(Comparator.comparing(Event::start));
        List<Event> actual = new ArrayList<>(events);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldNotHaveLocation_whenThereIsNoLocation() {
        Event event = baseEvent().withLocation(null);
        assertFalse(event.hasLocation());
    }

    @Test
    public void shouldNotHaveLocation_whenThereIsEmptyLocation() {
        Event event = baseEvent().withLocation("");
        assertFalse(event.hasLocation());
    }

    @Test
    public void shouldHaveLocation_whenThereIsLocation() {
        Event event = baseEvent();
        assertTrue(event.hasLocation());
    }

    @Test
    public void shouldHavePreciesLocation_whenThereAreCoordinates() {
        Event event = baseEvent();
        assertTrue(event.hasPreciseLocation());
    }

    @Test
    public void shouldNotHaveUrl_whenThereIsNoUrl() {
        Event event = baseEvent().withUrl(null);
        assertFalse(event.hasUrl());
    }

    @Test
    public void shouldNotHaveUrl_whenThereIsEmptyUrl() {
        Event event = baseEvent().withUrl("");
        assertFalse(event.hasUrl());
    }

    @Test
    public void shouldHaveUrl_whenThereIsUrl() {
        Event event = baseEvent();
        assertTrue(event.hasUrl());
    }
}
