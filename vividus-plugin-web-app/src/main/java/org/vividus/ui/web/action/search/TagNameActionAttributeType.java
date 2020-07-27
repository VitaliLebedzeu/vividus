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
import org.openqa.selenium.support.How;
import org.vividus.ui.action.search.AbstractActionAttributeType;
import org.vividus.ui.action.search.IActionAttributeKey;
import org.vividus.ui.action.search.SearchParameters;

@Named
public class TagNameActionAttributeType extends AbstractActionAttributeType
{
    @Override
    public String getAttributeName()
    {
        return "Tag name";
    }

    @Override
    public Function<SearchParameters, By> getSearchLocatorBuilder()
    {
        return parameters -> How.TAG_NAME.buildBy(parameters.getValue());
    }

    @Override
    public IActionAttributeKey getAttributeKey()
    {
        return WebActionAttributeType.TAG_NAME;
    }
}