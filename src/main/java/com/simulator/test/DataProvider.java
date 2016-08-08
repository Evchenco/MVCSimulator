package com.simulator.test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;

import com.simulator.test.bean.Department;
import com.simulator.test.bean.Employee;

public class DataProvider {

	private static String[] fname = { "John", "Leonid", "Vitale", "Alexander", "Nikola", "Mihail" };
	private static String[] lname = { "Petrovski", "Lementov", "Pashkov", "Ionescu", "Volontir",
			"Brinza" };
	private static int i = 0;
	private static HashSet<Employee> employees = new HashSet<Employee>();

	public static Employee getEmpl(Integer id) {
		if (employees.isEmpty())
			initData();

		for (Employee employee : employees) {
			if (employee.getId().equals(id))
				return employee;
		}
		return null;
	}

	public static void initData() {
		if (employees.isEmpty()) {
			Department department = new Department("IT");
			employees.add(getEmpl(department));
			employees.add(getEmpl(department));
			employees.add(getEmpl(department));
			employees.add(getEmpl(department));
			employees.add(getEmpl(department));
			employees.add(getEmpl(department));
			employees.add(getEmpl(department));
			employees.add(getEmpl(department));
			employees.add(getEmpl(department));

			Department department2 = new Department("Back Office");
			employees.add(getEmpl(department2));
			employees.add(getEmpl(department2));
			employees.add(getEmpl(department2));
			employees.add(getEmpl(department2));
			employees.add(getEmpl(department2));

			Department department3 = new Department("Administration");
			employees.add(getEmpl(department3));
			employees.add(getEmpl(department3));
			employees.add(getEmpl(department3));

		}
	}

	public static HashSet<Employee> getData() {
		if (employees.isEmpty())
			initData();
		return new HashSet(employees);
	}

	private static Employee getEmpl(Department department) {
		int idf = new Random().nextInt(fname.length);
		int idl = new Random().nextInt(lname.length);
		return new Employee(i++, fname[idf], lname[idl], getDate(), getDate(), department);
	}

	private static Date getDate() {
		GregorianCalendar gc = new GregorianCalendar();
		int year = randBetween(1950, 1990);
		gc.set(gc.YEAR, year);
		int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
		gc.set(gc.DAY_OF_YEAR, dayOfYear);
		return gc.getTime();
	}

	private static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}
}
