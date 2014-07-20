package com.athaydes.desktopapp.api;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Basic representation of a desktop app.
 */
public interface DesktopApp<Container> {

    /**
     * @param name of container
     * @return container identified by the given name if it exists.
     */
    public Optional<Container> getContainer(String name);

    /**
     * Adds the given listener to be notified when the application starts running.
     * <p>
     * If the application has been started, the listener is notified immediately.
     *
     * @param listener
     * @return id of the listener, which can be used to remove it later.
     */
    public String addAppRunningListener(Consumer<Container> listener);

    /**
     * Removes the listener with the given ID.
     *
     * @param id of the listener
     * @return true if the listener was removed, false otherwise.
     * If a listener has already been notified, it will be removed automatically
     * so trying to remove it after it has been notified is unnecessary and this
     * method will return false in that case.
     */
    public boolean removeAppRunningListener(String id);

}
