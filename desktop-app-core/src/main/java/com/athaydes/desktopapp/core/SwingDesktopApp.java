package com.athaydes.desktopapp.core;

import com.athaydes.desktopapp.api.DesktopApp;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Basic implementation of a Swing desktop application.
 */
@org.apache.felix.ipojo.annotations.Component(name = "swing-desktop-app", immediate = true, architecture = true)
@Provides
@Instantiate
class SwingDesktopApp implements DesktopApp<Container> {

    // only visible in the Swing Thread
    private JFrame frame;
    private final Map<String, Consumer<Container>> listeners = new ConcurrentHashMap<>(5);

    @Validate
    public void start() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("SwingDesktopApp");
            frame.setSize(new Dimension(400, 300));
            frame.setVisible(true);
            listeners.values().forEach(listener -> listener.accept(frame));
            listeners.clear();
        });
    }

    @Override
    public Optional<Container> getContainer(String name) {
        //TODO
        return Optional.empty();
    }

    @Override
    public String addAppRunningListener(Consumer<Container> listener) {
        final String id = generateId();
        SwingUtilities.invokeLater(() -> {
            if (frame == null) {
                listeners.put(id, listener);
            } else {
                listener.accept(frame);
            }
        });
        return id;
    }

    private String generateId() {
        String id;
        do {
            id = UUID.randomUUID().toString();
            System.out.println("Created ID " + id);
        } while (listeners.containsKey(id));
        return id;
    }

    @Override
    public boolean removeAppRunningListener(String id) {
        return listeners.remove(id) != null;
    }
}
