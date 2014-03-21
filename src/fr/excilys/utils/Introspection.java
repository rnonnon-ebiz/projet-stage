package fr.excilys.utils;

public class Introspection {

    public static void closeSafe(Object o) {
	try {
	    o.getClass().getMethod("close").invoke(o);
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
