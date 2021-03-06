/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.security.messaging.util.matcher;

import java.util.List;

import org.springframework.messaging.Message;

/**
 * {@link MessageMatcher} that will return true if all of the passed in
 * {@link MessageMatcher} instances match.
 *
 * @since 4.0
 */
public final class AndMessageMatcher<T> extends AbstractMessageMatcherComposite<T> {
	/**
	 * Creates a new instance
	 *
	 * @param messageMatchers the {@link MessageMatcher} instances to try
	 */
	public AndMessageMatcher(List<MessageMatcher<T>> messageMatchers) {
		super(messageMatchers);
	}

	/**
	 * Creates a new instance
	 *
	 * @param messageMatchers the {@link MessageMatcher} instances to try
	 */
	@SafeVarargs
	public AndMessageMatcher(MessageMatcher<T>... messageMatchers) {
		super(messageMatchers);

	}

	public boolean matches(Message<? extends T> message) {
		for (MessageMatcher<T> matcher : getMessageMatchers()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Trying to match using " + matcher);
			}
			if (!matcher.matches(message)) {
				LOGGER.debug("Did not match");
				return false;
			}
		}
		LOGGER.debug("All messageMatchers returned true");
		return true;
	}
}