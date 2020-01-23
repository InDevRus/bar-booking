package org.indevrus.barbooking.utilities;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface BookingConstants {
    int TABLES_COUNT = 10;

    Collection<Integer> ALL_TABLES = IntStream
            .rangeClosed(1, TABLES_COUNT)
            .boxed()
            .collect(Collectors.toList());
}
