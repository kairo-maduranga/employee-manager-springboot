package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 1. Serama employees la pennana eka (Read) saha form eka pennana eka
    @GetMapping("/employees")
    public String viewHomePage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        model.addAttribute("employee", new Employee());
        return "employees";
    }

    // 2. Aluth employee kenek save karana eka (Create)
    @PostMapping("/employees")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listEmployees", employeeService.getAllEmployees());
            return "employees";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    // 3. Update karanna form eka pennana eka
    @GetMapping("/employees/{id}/edit")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    // 4. Update karapu data save karana eka (Update)
    @PostMapping("/employees/{id}/update")
    public String updateEmployee(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            employee.setId(id);
            return "edit-employee";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    // 5. Employee kenek delete karana eka (Delete)
    @PostMapping("/employees/{id}/delete")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }
}