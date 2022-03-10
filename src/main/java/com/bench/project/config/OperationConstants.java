package com.bench.project.config;

import java.util.Arrays;
import java.util.List;

public class OperationConstants {
    public static final String COUNT_WORDS = "count-words";
    public static final String COUNT_KEYWORDS = "count-keywords";

    // to dynamically create Queue and Binding beans
    // need to add queue name to this list
    public static final List<String> list = Arrays.asList(COUNT_WORDS, COUNT_KEYWORDS);
}
