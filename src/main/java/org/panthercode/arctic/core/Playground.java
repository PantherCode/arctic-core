package org.panthercode.arctic.core;

import org.panthercode.arctic.core.json.JsonUtils;
import org.panthercode.arctic.core.processing.modules.RootModule;
import org.panthercode.arctic.core.reflect.ReflectionUtils;
import org.panthercode.arctic.core.settings.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by architect on 11.12.16.
 */
public class Playground {

    @RootModule
    public static class MyModule {
    }


    public static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(MyModule.class);

        List<Class<?>> result = ReflectionUtils.filterClassListByAnnotation(classes, RootModule.class);

        if (result.size() > 0) {
            System.out.println(result.get(0).getName());
        }

        Context context = new Context();
        context.put("question", "Most valueable employee in office.");
        context.put("answer", "Hahahahha. Nobody.");

        System.out.println(JsonUtils.toJson(context));
    }


    /*
    public static void main(String[] args) throws IOException, InterruptedException {

        DirectoryWatcher watcher = DirectoryWatcher.create();

        watcher.registerTree(Directory.open(Paths.get("/home/architect/Dokumente")), directoryWatcherEvent -> System.out.println(directoryWatcherEvent.kind().name() + ": " + directoryWatcherEvent.source()));

        watcher.start();

        Thread.sleep(10000);

        watcher.registerTree(Directory.open(Paths.get("/home/architect/Bilder")), directoryWatcherEvent -> System.out.println(directoryWatcherEvent.kind().name() + ": " + directoryWatcherEvent.source()));
    }
    */

    /*
    public static class Person {
        private int age = 0;

        public Person() {
        }

        @CommandLineParameter(name = "age", hasValue = true, defaultValue = "0", type = Integer.class)
        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "" + age;
        }
    }

    public static void main(String[] args) throws ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args = new String[]{"--age", "50"};

        CommandLineBinder binder = CommandLineBinder.create().bind(Person.class).parse(args);

        Person person = binder.from(Person.class, new Person());

        System.out.println(person);
    }*/
}
