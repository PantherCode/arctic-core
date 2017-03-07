package org.panthercode.arctic.core;

import org.panthercode.arctic.core.helper.identity.Identity;
import org.panthercode.arctic.core.helper.version.Version;
import org.panthercode.arctic.core.runtime.ApplicationSystem;
import org.panthercode.arctic.core.runtime.kernel.Kernel;
import org.panthercode.arctic.core.runtime.kernel.Service;
import org.panthercode.arctic.core.runtime.plugins.Plugin;
import org.quartz.*;

/**
 * Created by architect on 11.12.16.
 */
public class Playground {

    public static class MyPlugin implements Plugin{

        @Override
        public boolean canActivate() {
            return false;
        }

        @Override
        public boolean isActivated() {
            return false;
        }

        @Override
        public void activate() throws RuntimeException {

        }

        @Override
        public void deactivate() throws RuntimeException {

        }

        @Override
        public <T> T process(Class<T> returnType, Object... args) throws RuntimeException {
            return null;
        }

        @Override
        public <T> T getBehindObject(Class<T> clazz) {
            return null;
        }

        @Override
        public Identity identity() {
            return null;
        }

        @Override
        public Version version() {
            return null;
        }

        @SuppressWarnings("unchecked")
        public <T> T get(){
            Plugin plugin = new MyPlugin();

            return (T) plugin.getClass().cast(plugin);
        }
    }

    public static class OtherPlugin implements Plugin{

        @Override
        public boolean canActivate() {
            return false;
        }

        @Override
        public boolean isActivated() {
            return false;
        }

        @Override
        public void activate() throws RuntimeException {

        }

        @Override
        public void deactivate() throws RuntimeException {

        }

        @Override
        public <T> T process(Class<T> returnType, Object... args) throws RuntimeException {
            return null;
        }

        @Override
        public <T> T getBehindObject(Class<T> clazz) {
            return null;
        }

        @Override
        public Identity identity() {
            return null;
        }

        @Override
        public Version version() {
            return null;
        }

        @SuppressWarnings("unchecked")
        public <T> T get(){
            Plugin plugin = new MyPlugin();

            return (T) plugin.getClass().cast(plugin);
        }
    }

    public static void main(String args[]) throws SchedulerException {
        Plugin plugin = new MyPlugin().get();
        //OtherPlugin otherPlugin = new MyPlugin().get();
    }
}
