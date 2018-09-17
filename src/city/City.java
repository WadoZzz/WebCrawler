package city;

/**
 * City work only for OLX query LIST - search all over Ukraine
 */
public enum City {

	LIST("list"), KIEV("kiev"), KHMELNITSKIY("khmelnitskiy"), LVOV("lvov");

	private final String name;

	City(String s) {
		name = s;
	}

	public String toString() {
		return this.name;
	}
}