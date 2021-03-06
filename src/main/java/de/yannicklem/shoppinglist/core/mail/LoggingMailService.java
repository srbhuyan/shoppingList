package de.yannicklem.shoppinglist.core.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Conditional;

import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;


/**
 * {@link MailService} Implementation which only logs the mail.
 *
 * @author  David Schilling - davejs92@gmail.com
 */
@Service
@ConditionalOnProperty(name = "shopping-list.mail.host", matchIfMissing = true)
@Conditional(value = LoggingMailCondition.class)
public class LoggingMailService implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void sendMail(String mailTo, String message) {

        LOGGER.info("Sending Mail to {}", mailTo);
        LOGGER.info("Content: {}", message);
    }
}
