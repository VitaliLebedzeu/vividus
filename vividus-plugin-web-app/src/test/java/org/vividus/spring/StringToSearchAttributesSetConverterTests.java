/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.vividus.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.vividus.ui.action.search.SearchAttributes;
import org.vividus.ui.web.action.search.WebActionAttributeType;

class StringToSearchAttributesSetConverterTests
{
    private final StringToSearchAttributesSetConverter converter = new StringToSearchAttributesSetConverter();

    @Test
    void testConvert()
    {
        Set<SearchAttributes> expectedSet = new HashSet<>(Arrays.asList(new SearchAttributes(
                WebActionAttributeType.ID, "id"), new SearchAttributes(WebActionAttributeType.CSS_SELECTOR, "#id")));
        assertEquals(expectedSet, converter.convert("By.id(id), By.cssSelector(#id)"));
    }
}
