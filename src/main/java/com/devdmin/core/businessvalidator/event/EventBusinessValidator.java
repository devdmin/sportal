package com.devdmin.core.businessvalidator.event;

import com.devdmin.core.businessvalidator.BusinessValidator;
import com.devdmin.core.model.Event;
import com.devdmin.core.model.User;
import org.springframework.stereotype.Component;

/**
 * This class provides a implementation of the {@link com.devdmin.core.businessvalidator.ModelBusinessValidator}
 * interface for {@link Event} forms
 *
 * @author Damian Ujma
 */
@Component
public class EventBusinessValidator extends BusinessValidator<Event, User> {
}
