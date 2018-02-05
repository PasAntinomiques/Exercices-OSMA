import java.util.*;
import general.Cities;

public class Main {
	public static void main(String[] args) {
		Cities cities = new Cities();
		taboulet t = new taboulet();
		int[] res = t.tabou(cities);
		System.out.println(t.distance(res));
	}
}
	