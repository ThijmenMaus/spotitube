/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.common.exception;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(Class<?> cls) {
        super(String.format("Entity '%s' was not found!", cls.getName()));
    }
}
