package com.simulator.test;

import com.simulator.annotation.Controller;
import com.simulator.annotation.mapping.PathVariable;
import com.simulator.annotation.mapping.RequestMapping;
import com.simulator.model.Model;

@Controller
public class SimpleController {

	@RequestMapping(value = "/")
	public String getAllRecords(Model model) {
		model.addParam("allEmp", DataProvider.getData());
		return "main";
	}

	@RequestMapping(value = "/empl/{id}")
	public String getAllRecords(@PathVariable(value = "id") Integer id, Model model) {
		model.addParam("empl", DataProvider.getEmpl(id));
		return "/viewEmpl";
	}
}
