package com.athaydes.desktopapp.testPlugin;

import com.athaydes.desktopapp.api.DesktopApp;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import javax.swing.*;
import java.awt.*;

/**
 * Simple extension to core app for tests.
 */
@Component(name = "test-plugin", immediate = true, architecture = true)
@Instantiate
public class TestPlugin {

    @Requires
    private DesktopApp<Container> desktopApp;

    @Validate
    public void start() {
        desktopApp.addAppRunningListener(mainWindow -> {
            mainWindow.add(build());
            mainWindow.revalidate();
        });
    }

    public Box build() {
        Box box = Box.createVerticalBox();
        box.add(new Label("Hello from the Test Plugin!"));
        return box;
    }

}
