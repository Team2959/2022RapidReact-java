package cwtech.telemetry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class TelemetryManager {

    /**
     * Information of the Entry annotation that the TelemetryManager needs to track
     */
    public class EntryImpl {
        /**
         * Will post variable value to Dashboard if false and read value from Dashboard
         * if true
         */
        public final boolean writable;
        /** NetworkTableEntry for Dashboard */
        public final NetworkTableEntry entry;
        /** Annotation data */
        public final Entry annotation;

        public EntryImpl(String key, boolean writeable, Entry annotation) {
            this.annotation = annotation;
            this.writable = writeable;
            this.entry = NetworkTableInstance.getDefault().getEntry(key);
        }
    }

    /**
     * Information of the Observer annotation that the TelemetryManager needs to track
     */
    public class ObserverImpl {
        /** NetworkTableEntry for Dashboard */
        final NetworkTableEntry entry;
        /** Annotation data */
        final Observer annotation;

        public ObserverImpl(String key, Observer annotation) {
            this.annotation = annotation;
            this.entry = NetworkTableInstance.getDefault().getEntry(key);
        }
    }

    /** Information of Telemetry annotation that the TelemetryManager needs to track */
    public class TelemetryImpl {
        /** If toplevel {@link Telemetry} object */
        public final boolean top;
        /** Fields to Entry map for updating */
        HashMap<Field, EntryImpl> entries = new HashMap<>();
        HashMap<Field, TelemetryImpl> teles = new HashMap<>();
        HashMap<Method, ObserverImpl> entryFuncs = new HashMap<>();

        public TelemetryImpl(boolean top) {
            this.top = top;
        }
    }

    /** Top Level Telemetry objects */
    private HashMap<Object, TelemetryImpl> m_objects = new HashMap<>();

    public TelemetryManager() {
    }

    public void inital() throws IllegalAccessException, InvocationTargetException {
        for (var obj : m_objects.keySet()) {
            var tele = m_objects.get(obj);
            putTelemetry(obj, tele);
        }
    }

    private void put(NetworkTableEntry entry, Field field, Object obj) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType().equals(Boolean.TYPE)) {
            entry.setBoolean((boolean) field.get(obj));
        } else if (field.getType().equals(Double.TYPE)) {
            entry.setDouble((double) field.get(obj));
        } else if (field.getType().equals(String.class)) {
            entry.setString((String) field.get(obj));
        } else {
            throw new RuntimeException(field.getType().getSimpleName() + " wasn't a valid type");
        }
    }

    private void updateFunc(Object obj, Method method, NetworkTableEntry entry)
            throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        if (method.getReturnType().equals(Boolean.TYPE)) {
            entry.setBoolean((boolean) method.invoke(method.getClass().cast(obj)));
        } else if (method.getReturnType().equals(Double.TYPE)) {
            entry.setDouble((double) method.invoke(method.getDeclaringClass().cast(obj)));
        } else if (method.getReturnType().equals(String.class)) {
            entry.setString((String) method.invoke(method.getClass().cast(obj)));
        } else {
            throw new RuntimeException("Wasn't a valid type");
        }
    }

    private void putTelemetry(Object obj, TelemetryImpl telemetry)
            throws IllegalAccessException, InvocationTargetException {
        for (Field field : telemetry.entries.keySet()) {
            var entryImpl = telemetry.entries.get(field);
            put(entryImpl.entry, field, obj);
        }
        for (Field field : telemetry.teles.keySet()) {
            var teleImpl = telemetry.teles.get(field);
            putTelemetry(field.get(obj), teleImpl);
        }
        for (Method method : telemetry.entryFuncs.keySet()) {
            var efImpl = telemetry.entryFuncs.get(method);
            updateFunc(obj, method, efImpl.entry);
        }
    }

    private void get(NetworkTableEntry entry, Field field, Object obj, EntryImpl impl) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType().equals(Boolean.TYPE)) {
            field.setBoolean(obj, entry.getBoolean(impl.annotation.defaultBoolean()));
        } else if (field.getType().equals(Double.TYPE)) {
            field.setDouble(obj, entry.getDouble(impl.annotation.defaultDouble()));
        } else if (field.getType().equals(String.class)) {
            field.set(obj, entry.getString(impl.annotation.defaultString()));
        } else {
            throw new RuntimeException("Invalid type for get");
        }
    }

    private void updateEntry(Object obj, Field field, EntryImpl entry) throws IllegalAccessException {
        if (entry.annotation.writeable()) {
            get(entry.entry, field, obj, entry);
        } else {
            put(entry.entry, field, obj);
        }
    }

    private void updateTelemetry(Object obj, TelemetryImpl telemetry)
            throws IllegalAccessException, InvocationTargetException {
        for (Field obj2 : telemetry.teles.keySet()) {
            updateTelemetry(obj2.get(obj), telemetry.teles.get(obj2));
        }
        for (Method method : telemetry.entryFuncs.keySet()) {
            updateFunc(obj, method, telemetry.entryFuncs.get(method).entry);
        }
        for (Field field : telemetry.entries.keySet()) {
            updateEntry(obj, field, telemetry.entries.get(field));
        }
    }

    public void update() {
        for (var obj : m_objects.keySet()) {
            var tele = m_objects.get(obj);
            try {
                updateTelemetry(obj, tele);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public void register(Object obj) {
        try {
            m_objects.put(obj, registerTelemetry(obj, "", null));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private TelemetryImpl registerTelemetry(Object obj, String name, String entryName) throws IllegalAccessException {
        if (Objects.isNull(obj)) {
            throw new RuntimeException("The object is null");
        }
        TelemetryImpl impl = new TelemetryImpl(name.isEmpty());
        if (!name.isEmpty()) {
            name += "/";
        }
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Telemetry.class)) {
            throw new RuntimeException("The object doesn't implement Telemetry");
        }
        Telemetry annotation = (Telemetry) clazz.getAnnotation(Telemetry.class);
        if (entryName != null) {
            name += entryName;
        } else if (!annotation.key().isEmpty()) {
            name += annotation.key();
        } else {
            name += obj.getClass().getSimpleName();
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Entry.class)) {
                if (field.getType().isAnnotationPresent(Telemetry.class)) {
                    Entry entryAnnotation = (Entry) field.getAnnotation(Entry.class);
                    field.setAccessible(true);
                    impl.teles.put(field, registerTelemetry(field.get(obj), name, entryAnnotation.key()));
                } else {
                    impl.entries.put(field, registerImpl(obj, field, name));
                }
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Observer.class)) {
                impl.entryFuncs.put(method, registerEntryFuncImpl(obj, method, name));
            }
        }
        return impl;
    }

    private EntryImpl registerImpl(Object obj, Field field, String name) throws IllegalAccessException {
        if (!name.isEmpty()) {
            name += "/";
        }
        if (field.isAnnotationPresent(Entry.class)) {
            Annotation annotation = field.getAnnotation(Entry.class);
            Entry entry = (Entry) annotation;
            name += entry.key();
            return new EntryImpl(name, entry.writeable(), entry);
        } else {
            throw new RuntimeException("Object doesn't implement Entry");
        }
    }

    private ObserverImpl registerEntryFuncImpl(Object obj, Method method, String name) throws IllegalAccessException {
        if (!name.isEmpty()) {
            name += "/";
        }
        if (method.isAnnotationPresent(Observer.class)) {
            Annotation annotation = method.getAnnotation(Observer.class);
            Observer entryFunc = (Observer) annotation;
            name += entryFunc.key();
            return new ObserverImpl(name, entryFunc);
        } else {
            throw new RuntimeException("Object doesn't implement EntryFunc");
        }
    }
}
