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

package org.vividus.ui.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.vividus.ui.action.IExpectedConditions;
import org.vividus.ui.action.IExpectedSearchContextCondition;

@SuppressWarnings("unchecked")
class DropDownStateTests
{
    static Stream<Arguments> dataForGetExpectedConditionTest()
    {
        return Stream.of(
            Arguments.of(DropDownState.ENABLED, "element to be clickable: .*"),
            Arguments.of(DropDownState.DISABLED, "condition to not be valid: .*"),
            Arguments.of(DropDownState.SELECTED, "element (.*) to be selected"),
            Arguments.of(DropDownState.NOT_SELECTED, "element (.*) to not be selected"),
            Arguments.of(DropDownState.NOT_VISIBLE, "invisibility of .*"),
            Arguments.of(DropDownState.VISIBLE, "visibility of .*"),
            Arguments.of(DropDownState.MULTI_SELECT, "An element (.*) is multiple select"),
            Arguments.of(DropDownState.SINGLE_SELECT, "An element (.*) is single select")
        );
    }

    static Stream<Arguments> dataForGetExpectedConditionWithSearchCriteriaWithVariousSelectStateTest()
    {
        return Stream.of(
            Arguments.of(true, DropDownState.MULTI_SELECT),
            Arguments.of(false, DropDownState.SINGLE_SELECT)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForGetExpectedConditionTest")
    void testGetExpectedCondition(DropDownState state, String expectedConditionPattern)
    {
        WebElement webElement = mock(WebElement.class);
        ExpectedCondition<?> expectedCondition = state.getExpectedCondition(webElement);
        assertTrue(expectedCondition.toString().matches(expectedConditionPattern));
    }

    @Test
    void testGetExpectedConditionWithSearchCriteriaStateEnabled()
    {
        By searchCriteria = mock(By.class);
        IExpectedConditions<By> expectedConditions = mock(IExpectedConditions.class);
        IExpectedSearchContextCondition<WebElement> expectedSearchCondition =
                mock(IExpectedSearchContextCondition.class);
        when(expectedConditions.elementToBeClickable(searchCriteria)).thenReturn(expectedSearchCondition);
        IExpectedSearchContextCondition<?> actualIExpectedSearchCondition =
                DropDownState.ENABLED.getExpectedCondition(expectedConditions, searchCriteria);
        assertEquals(expectedSearchCondition, actualIExpectedSearchCondition);
    }

    @Test
    void testGetExpectedConditionWithSearchCriteriaStateDisabled()
    {
        By searchCriteria = mock(By.class);
        IExpectedConditions<By> expectedConditions = mock(IExpectedConditions.class);
        IExpectedSearchContextCondition<Boolean> expectedSearchCondition = mock(IExpectedSearchContextCondition.class);
        IExpectedSearchContextCondition<WebElement> expectedSearchConditionWebElement =
                mock(IExpectedSearchContextCondition.class);
        when(expectedConditions.elementToBeClickable(searchCriteria)).thenReturn(expectedSearchConditionWebElement);
        when(expectedConditions.not(expectedSearchConditionWebElement)).thenReturn(expectedSearchCondition);
        IExpectedSearchContextCondition<?> actualExpectedSearchCondition =
                DropDownState.DISABLED.getExpectedCondition(expectedConditions, searchCriteria);
        assertEquals(expectedSearchCondition, actualExpectedSearchCondition);
    }

    @Test
    void testGetExpectedConditionWithSearchCriteriaStateSelected()
    {
        boolean selected = true;
        By searchCriteria = mock(By.class);
        IExpectedConditions<By> expectedConditions = mock(IExpectedConditions.class);
        IExpectedSearchContextCondition<Boolean> expectedSearchCondition = mock(IExpectedSearchContextCondition.class);
        when(expectedConditions.elementSelectionStateToBe(searchCriteria, selected))
                .thenReturn(expectedSearchCondition);
        IExpectedSearchContextCondition<?> actualExpectedSearchCondition =
                DropDownState.SELECTED.getExpectedCondition(expectedConditions, searchCriteria);
        assertEquals(expectedSearchCondition, actualExpectedSearchCondition);
    }

    @Test
    void testGetExpectedConditionWithSearchCriteriaStateNotSelected()
    {
        boolean selected = false;
        By searchCriteria = mock(By.class);
        IExpectedConditions<By> expectedConditions = mock(IExpectedConditions.class);
        IExpectedSearchContextCondition<Boolean> expectedSearchCondition = mock(IExpectedSearchContextCondition.class);
        when(expectedConditions.elementSelectionStateToBe(searchCriteria, selected))
                .thenReturn(expectedSearchCondition);
        IExpectedSearchContextCondition<?> actualExpectedSearchCondition =
                DropDownState.NOT_SELECTED.getExpectedCondition(expectedConditions, searchCriteria);
        assertEquals(expectedSearchCondition, actualExpectedSearchCondition);
    }

    @Test
    void testGetExpectedConditionWithSearchCriteriaStateVisible()
    {
        By searchCriteria = mock(By.class);
        IExpectedConditions<By> expectedConditions = mock(IExpectedConditions.class);
        IExpectedSearchContextCondition<WebElement> expectedSearchCondition =
                mock(IExpectedSearchContextCondition.class);
        when(expectedConditions.visibilityOfElement(searchCriteria)).thenReturn(expectedSearchCondition);
        IExpectedSearchContextCondition<?> actualExpectedSearchCondition =
                DropDownState.VISIBLE.getExpectedCondition(expectedConditions, searchCriteria);
        assertEquals(expectedSearchCondition, actualExpectedSearchCondition);
    }

    @Test
    void testGetExpectedConditionWithSearchCriteriaStateNotVisible()
    {
        By searchCriteria = mock(By.class);
        IExpectedConditions<By> expectedConditions = mock(IExpectedConditions.class);
        IExpectedSearchContextCondition<Boolean> expectedSearchCondition = mock(IExpectedSearchContextCondition.class);
        when(expectedConditions.invisibilityOfElement(searchCriteria)).thenReturn(expectedSearchCondition);
        IExpectedSearchContextCondition<?> actualExpectedSearchCondition =
                DropDownState.NOT_VISIBLE.getExpectedCondition(expectedConditions, searchCriteria);
        assertEquals(expectedSearchCondition, actualExpectedSearchCondition);
    }

    @ParameterizedTest
    @MethodSource("dataForGetExpectedConditionWithSearchCriteriaWithVariousSelectStateTest")
    void testGetExpectedConditionWithSearchCriteriaWithVariousSelectState(boolean multiSelect, DropDownState
            dropDownState)
    {
        By searchCriteria = mock(By.class);
        IExpectedConditions<By> expectedConditions = mock(IExpectedConditions.class);
        IExpectedSearchContextCondition<Boolean> expectedSearchCondition = mock(IExpectedSearchContextCondition.class);
        when(expectedConditions.isMultiSelectDropDown(searchCriteria, multiSelect)).thenReturn(expectedSearchCondition);
        IExpectedSearchContextCondition<?> actualExpectedSearchContextCondition = dropDownState
                .getExpectedCondition(expectedConditions, searchCriteria);
        assertEquals(expectedSearchCondition, actualExpectedSearchContextCondition);
    }
}
