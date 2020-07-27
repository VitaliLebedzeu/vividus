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

package org.vividus.ui.web.action.search;

import java.util.function.Function;

import javax.inject.Named;

import org.openqa.selenium.By;
import org.vividus.ui.action.search.AbstractActionAttributeType;
import org.vividus.ui.action.search.IActionAttributeKey;
import org.vividus.ui.action.search.IElementAction;
import org.vividus.ui.action.search.SearchParameters;
import org.vividus.ui.web.util.LocatorUtil;

@Named
public class ImageSrcPartActionAttributeType extends AbstractActionAttributeType
{
    @Override
    public String getAttributeName()
    {
        return "Image source part";
    }

    @Override
    public Class<? extends IElementAction> getActionClass()
    {
        return ImageWithSourcePartFilter.class;
    }

    @Override
    public Function<SearchParameters, By> getSearchLocatorBuilder()
    {
        return parameters -> LocatorUtil.getXPathLocator(".//img[contains(@src,'%s')]", parameters.getValue());
    }

    @Override
    public IActionAttributeKey getAttributeKey()
    {
        return WebActionAttributeType.IMAGE_SRC_PART;
    }

    @Override
    public IActionAttributeKey getCompetingTypeAttributeKey()
    {
        return WebActionAttributeType.IMAGE_SRC;
    }
}