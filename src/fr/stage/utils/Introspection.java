package fr.stage.utils;

public class Introspection {

    public static void closeSafe(Object o) {
	try {
	    o.getClass().getMethod("close").invoke(o);
	}
	catch (Exception e) {
	}
    }
}
