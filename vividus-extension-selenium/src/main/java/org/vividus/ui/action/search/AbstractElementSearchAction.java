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

package org.vividus.ui.action.search;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vividus.ui.action.IExpectedConditions;
import org.vividus.ui.action.IWaitActions;

public abstract class AbstractElementSearchAction
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractElementSearchAction.class);

    @Inject private IWaitActions waitActions;
    @Inject private IExpectedConditions<By> expectedConditions;
    private Duration waitForElementTimeout;
    private boolean retrySearchIfStale;

    public List<WebElement> findElements(SearchContext searchContext, By locator, SearchParameters parameters)
    {
        if (searchContext != null)
        {
            return findElements(searchContext, locator, parameters, false);
        }
        return List.of();
    }

    private List<WebElement> findElements(SearchContext searchContext, By locator, SearchParameters parameters,
            boolean retry)
    {
        List<WebElement> elements = parameters.isWaitForElement()
                ? waitForElement(searchContext, locator)
                : searchContext.findElements(locator);
        boolean elementsFound = null != elements;
        LOGGER.info("Total number of elements found {} is equal to {}", locator, elementsFound ? elements.size() : 0);
        if (elementsFound)
        {
            Visibility visibility = parameters.getVisibility();
            try
            {
                return Visibility.ALL == visibility
                        ? elements
                        : filterElementsByVisibility(elements, visibility == Visibility.VISIBLE, retry);
            }
            catch (StaleElementReferenceException e)
            {
                return findElements(searchContext, locator, parameters, true);
            }
        }
        return List.of();
    }

    private List<WebElement> filterElementsByVisibility(List<WebElement> elements, boolean visible,
            boolean retry)
    {
        return elements.stream().filter(element -> {
            try
            {
                return visible == isElementVisible(element, false);
            }
            catch (StaleElementReferenceException e)
            {
                if (retrySearchIfStale && !retry)
                {
                    throw e;
                }
                LOGGER.warn(e.getMessage(), e);
                return false;
            }
        }).collect(Collectors.toList());
    }

    private boolean isElementVisible(WebElement element, boolean scrolled)
    {
        if (!element.isDisplayed())
        {
            if (!scrolled)
            {
                scrollIntoView(element);
                return isElementVisible(element, true);
            }
            return false;
        }
        return true;
    }

    protected void scrollIntoView(WebElement element)
    {
        // do nothing
    }

    private List<WebElement> waitForElement(SearchContext searchContext, By locator)
    {
        return waitActions.wait(searchContext, waitForElementTimeout,
                expectedConditions.presenceOfAllElementsLocatedBy(locator), false).getData();
    }

    public void setWaitForElementTimeout(Duration waitForElementTimeout)
    {
        this.waitForElementTimeout = waitForElementTimeout;
    }

    public void setRetrySearchIfStale(boolean retrySearchIfStale)
    {
        this.retrySearchIfStale = retrySearchIfStale;
    }
}
