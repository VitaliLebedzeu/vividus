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

import javax.inject.Named;

import org.vividus.ui.action.search.AbstractActionAttributeType;
import org.vividus.ui.action.search.IActionAttributeKey;
import org.vividus.ui.action.search.IElementAction;

@Named
public class TextPartFilterActionAttributeType extends AbstractActionAttributeType
{
    @Override
    public Class<? extends IElementAction> getActionClass()
    {
        return TextPartFilter.class;
    }

    @Override
    public String getAttributeName()
    {
        return "Text part";
    }

    @Override
    public IActionAttributeKey getAttributeKey()
    {
        return WebActionAttributeType.TEXT_PART;
    }

    @Override
    public IActionAttributeKey getCompetingTypeAttributeKey()
    {
        // + CASE_SENSITIVE_TEXT
        return WebActionAttributeType.CASE_INSENSITIVE_TEXT;
    }
}