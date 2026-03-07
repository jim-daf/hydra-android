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

package be.ugent.zeus.hydra.testing;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;
import java.util.HashSet;

import be.ugent.zeus.hydra.common.MockParcel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hamcrest.*;
import org.junit.ComparisonFailure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Custom assert rules for unit tests.
 *
 * @author Niko Strijbol
 */
public class Assert {

    private static <T> Parcelable.Creator<T> getCreator(T input) {
        try {
            Object creator = FieldUtils.readDeclaredStaticField(input.getClass(), "CREATOR");
            //noinspection unchecked
            return (Parcelable.Creator<T>) creator;
        } catch (IllegalAccessException e) {
            throw new AssertionError("Class does not have a CREATOR field.", e);
        } catch (ClassCastException e) {
            throw new AssertionError("Class does not have a correct CREATOR field, or it is used wrong.", e);
        }
    }

    /**
     * Assert that a class implements {@link Parcelable} correctly.
     * <p>
     * This assertion relies on a proper implementation of the equals method.
     * The result of converting an object to a parcel and back should be the same.
     * <p>
     * The parcelable implementation is assumed to not have any special content descriptions, i.e.
     * {@link Parcelable#describeContents()} always returns {@code 0}.
     *
     * @param instance An instance of the object to check.
     * @param <T>      The type of the object to test.
     */
    public static <T extends Parcelable> void assertRecordParcelable(T instance) {
        // This assert only works with records...
        assertTrue(instance.getClass().isRecord());
        assertEquals(0, instance.describeContents());
        Parcel parcel = MockParcel.writeToParcelable(instance);
        Parcelable.Creator<T> creator = getCreator(instance);
        T other = creator.createFromParcel(parcel);
        assertEquals(instance, other);
        T[] array = creator.newArray(10);
        assertEquals(10, array.length);
    }

    public static <T> void assertThat(T expected, Matcher<? extends T> matcher) {
        if (!matcher.matches(expected)) {
            Description actual = new StringDescription();
            matcher.describeMismatch(expected, actual);
            Description expectedDescription = new StringDescription();
            matcher.describeTo(expectedDescription);
            throw new ComparisonFailure("", expectedDescription.toString(), actual.toString());
        }
    }

    /**
     * Assert that two collections contain the same elements. This does not account for ordering in the collections.
     *
     * @param expected The expected collection.
     * @param actual   The actual collection.
     */
    public static <T> void assertCollectionEquals(Iterable<T> expected, Iterable<T> actual) {
        Collection<Object> actualSet = new HashSet<>();
        Collection<Object> notEqual = new HashSet<>();
        CollectionUtils.addAll(actualSet, actual.iterator());
        CollectionUtils.addAll(notEqual, expected.iterator());
        assertEquals(notEqual, actualSet);
    }
}
