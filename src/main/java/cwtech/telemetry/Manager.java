package cwtech.telemetry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Optional;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Manager {
    private LinkedList<ObjectImpl> m_telemetryObjects = new LinkedList<>();

    public Manager() {
    }

    public void register(Object object) {
        m_telemetryObjects.push(new ObjectImpl(object));
    }

    public void run() {
        for (var object : m_telemetryObjects) {
            object.observe();
            object.update();
        }
    }

    public void inital() {
        for (var object : m_telemetryObjects) {
            object.inital();
        }
    }

    private static boolean isNumber(Class<?> clazz) {
        return clazz.equals(Double.TYPE) || clazz.equals(Number.class) || clazz.equals(Integer.TYPE)
                || clazz.equals(Float.TYPE);
    }

    private static boolean isBoolean(Class<?> clazz) {
        return clazz.equals(Boolean.TYPE);
    }

    private static boolean isString(Class<?> clazz) {
        return clazz.equals(String.class);
    }

    interface TelemetryObject {
        String key();

        void update();

        void observe();

        void inital();
    }

    class ObjectImpl implements TelemetryObject {
        final LinkedList<TelemetryObject> m_objects = new LinkedList<>();
        final Optional<TelemetryObject> m_parent;
        final Optional<Child> m_childAnnotation;
        final Optional<Telemetry> m_telemetryAnnotation;

        final Optional<Field> m_field;
        final Optional<Object> m_fieldObject;
        final Optional<Object> m_object;
        final Class<?> m_class;

        public ObjectImpl(Object obj) {
            m_parent = Optional.empty();
            m_telemetryAnnotation = Optional.of(obj.getClass().getAnnotation(Telemetry.class));
            m_childAnnotation = Optional.empty();
            m_object = Optional.of(obj);
            m_field = Optional.empty();
            m_fieldObject = Optional.empty();
            m_class = obj.getClass();
            register();
        }

        public ObjectImpl(Object obj, Field field, TelemetryObject tObj) {
            m_parent = Optional.of(tObj);
            m_telemetryAnnotation = Optional.empty();
            m_childAnnotation = Optional.of(field.getAnnotation(Child.class));
            m_object = Optional.empty();
            m_field = Optional.of(field);
            m_fieldObject = Optional.of(obj);
            m_class = field.getType();
            register();
        }

        private Object getObject() {
            if (m_object.isPresent()) {
                return m_object.get();
            } else {
                try {
                    m_field.get().setAccessible(true);
                    return m_field.get().get(m_fieldObject.get());
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
            return null;
        }

        private void register() {
            for (Method method : m_class.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Observable.class) || method.isAnnotationPresent(Updateable.class)) {
                    m_objects.push(new MethodImpl(getObject(), method, Optional.of(this)));
                }
            }

            for (Field field : m_class.getDeclaredFields()) {

                if (field.isAnnotationPresent(Observable.class) || field.isAnnotationPresent(Updateable.class)) {
                    m_objects.push(new FieldImpl(getObject(), field, Optional.of(this)));
                } else if (field.isAnnotationPresent(Child.class)) {
                    m_objects.push(new ObjectImpl(getObject(), field, this));
                }
            }
        }

        @Override
        public String key() {
            String key;
            if (m_telemetryAnnotation.isPresent()) {
                key = m_telemetryAnnotation.get().key();
            } else if (m_childAnnotation.isPresent()) {
                key = m_childAnnotation.get().key();
            } else {
                key = "";
            }
            if (key.isEmpty()) {
                key = m_class.getSimpleName();
            }
            return (m_parent.isPresent() ? m_parent.get().key() + "/" : "") + key;
        }

        @Override
        public void observe() {
            for (var object : m_objects) {
                object.observe();
            }
        }

        @Override
        public void update() {
            for (var object : m_objects) {
                object.update();
            }
        }

        @Override
        public void inital() {
            for (var object : m_objects) {
                object.inital();
            }
        }
    }

    class FieldImpl implements TelemetryObject {

        final Class<?> m_class;
        final Object m_object;
        final Field m_field;
        final Optional<Observable> m_observableAnnotation;
        final Optional<Updateable> m_updateableAnnotation;
        final Optional<TelemetryObject> m_parent;
        final NetworkTableEntry m_ntEntry;

        public FieldImpl(Object obj, Field field, Optional<TelemetryObject> parent) {
            m_object = obj;
            m_field = field;
            m_class = obj.getClass();
            m_parent = parent;

            m_field.setAccessible(true);

            if (field.isAnnotationPresent(Observable.class)) {
                m_observableAnnotation = Optional.of(field.getAnnotation(Observable.class));
                m_updateableAnnotation = Optional.empty();
            } else if (field.isAnnotationPresent(Updateable.class)) {
                m_updateableAnnotation = Optional.of(field.getAnnotation(Updateable.class));
                m_observableAnnotation = Optional.empty();
            } else {
                m_updateableAnnotation = Optional.empty();
                m_observableAnnotation = Optional.empty();
            }

            m_ntEntry = NetworkTableInstance.getDefault().getEntry(key());

        }

        @Override
        public String key() {
            String key;
            if (m_observableAnnotation.isPresent()) {
                key = m_observableAnnotation.get().key();
            } else if (m_updateableAnnotation.isPresent()) {
                key = m_updateableAnnotation.get().key();
            } else {
                key = "";
            }
            if (key.isEmpty()) {
                key = m_field.getName();
            }
            return (m_parent.isPresent() ? m_parent.get().key() + "/" : "") + key;
        }

        void uncheckedObserve() {
            try {
                if (isNumber(m_field.getType())) {
                    m_ntEntry.setNumber((Number) m_field.get(m_object));
                } else if (isBoolean(m_field.getType())) {
                    m_ntEntry.setBoolean((Boolean) m_field.get(m_object));
                } else if (isString(m_field.getType())) {
                    m_ntEntry.setString((String) m_field.get(m_object));
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }

        @Override
        public void observe() {
            if (m_observableAnnotation.isPresent()) {
                uncheckedObserve();
            }
        }

        @Override
        public void update() {
            if (m_updateableAnnotation.isPresent()) {
                try {
                    if (isNumber(m_field.getType())) {
                        m_field.set(m_object, m_ntEntry.getNumber(m_updateableAnnotation.get().defaultNumber()));
                    } else if (isBoolean(m_field.getType())) {
                        m_field.set(m_object, m_ntEntry.getBoolean(m_updateableAnnotation.get().defaultBoolean()));
                    } else if (isString(m_field.getType())) {
                        m_field.set(m_object, m_ntEntry.getString(m_updateableAnnotation.get().defaultString()));
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        @Override
        public void inital() {
            uncheckedObserve();
        }

    }

    class MethodImpl implements TelemetryObject {

        final Class<?> m_class;
        final Object m_object;
        final Method m_method;
        final Optional<Observable> m_observableAnnotation;
        final Optional<Updateable> m_updateableAnnotation;
        final Optional<TelemetryObject> m_parent;
        final NetworkTableEntry m_ntEntry;

        public MethodImpl(Object obj, Method method, Optional<TelemetryObject> parent) {
            m_object = obj;
            m_method = method;
            m_class = obj.getClass();
            m_parent = parent;

            m_method.setAccessible(true);

            if (method.isAnnotationPresent(Observable.class)) {
                m_observableAnnotation = Optional.of(method.getAnnotation(Observable.class));
                m_updateableAnnotation = Optional.empty();
            } else if (method.isAnnotationPresent(Updateable.class)) {
                m_updateableAnnotation = Optional.of(method.getAnnotation(Updateable.class));
                m_observableAnnotation = Optional.empty();
            } else {
                m_updateableAnnotation = Optional.empty();
                m_observableAnnotation = Optional.empty();
            }
            m_ntEntry = NetworkTableInstance.getDefault().getEntry(key());
        }

        @Override
        public String key() {
            String key;
            if (m_observableAnnotation.isPresent()) {
                key = m_observableAnnotation.get().key();
            } else if (m_updateableAnnotation.isPresent()) {
                key = m_updateableAnnotation.get().key();
            } else {
                key = "";
            }
            if (key.isEmpty()) {
                key = m_method.getName();
            }
            return (m_parent.isPresent() ? m_parent.get().key() + "/" : "") + key;
        }

        @Override
        public void observe() {
            if (m_observableAnnotation.isPresent()) {
                try {
                    if (isNumber(m_method.getReturnType())) {
                        m_ntEntry.setNumber((Number) m_method.invoke(m_object));
                    } else if (isBoolean(m_method.getReturnType())) {
                        m_ntEntry.setBoolean((Boolean) m_method.invoke(m_object));
                    } else if (isString(m_method.getReturnType())) {
                        m_ntEntry.setString((String) m_method.invoke(m_object));
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        @Override
        public void update() {
            if (m_updateableAnnotation.isPresent()) {
                try {
                    if (isNumber(m_method.getParameterTypes()[0])) {
                        m_method.invoke(m_object, m_ntEntry.getNumber(m_updateableAnnotation.get().defaultNumber()));
                    } else if (isBoolean(m_method.getParameterTypes()[0])) {
                        m_method.invoke(m_object, m_ntEntry.getBoolean(m_updateableAnnotation.get().defaultBoolean()));
                    } else if (isString(m_method.getParameterTypes()[0])) {
                        m_method.invoke(m_object, m_ntEntry.getString(m_updateableAnnotation.get().defaultString()));
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        @Override
        public void inital() {
            if (m_updateableAnnotation.isPresent()) {
                if (isNumber(m_method.getParameterTypes()[0])) {
                    m_ntEntry.setNumber(m_updateableAnnotation.get().defaultNumber());
                } else if (isBoolean(m_method.getParameterTypes()[0])) {
                    m_ntEntry.setBoolean(m_updateableAnnotation.get().defaultBoolean());
                } else if (isString(m_method.getParameterTypes()[0])) {
                    m_ntEntry.setString(m_updateableAnnotation.get().defaultString());
                }
            }
        }

    }

}
