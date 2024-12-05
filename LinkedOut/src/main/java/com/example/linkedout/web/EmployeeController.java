package com.example.linkedout.web;

import com.example.linkedout.models.dtos.EmployeeDTO;
import com.example.linkedout.services.CompanyService;
import com.example.linkedout.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    public EmployeeController(EmployeeService employeeService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @GetMapping("/all")
    public String showAllEmployees(Model model) {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();

        model.addAttribute("employees", employeeDTOList);

        return "employee-all";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("addEmployeeModel") EmployeeDTO employeeDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addEmployeeModel", employeeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addEmployeeModel", bindingResult);

            return "redirect:/employees/add";
        }

        employeeService.addEmployee(employeeDTO);

        redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");

        return "redirect:/employees/add";
    }

    @GetMapping("/add")
    public String showAddEmployeePage(Model model) {

        if (!model.containsAttribute("addEmployeeModel"))
            model.addAttribute("addEmployeeModel", new EmployeeDTO());

        model.addAttribute("companies", companyService.getAllCompanies());

        return "employee-add";
    }

    @GetMapping("/{id}")
    public String showEmployeeDetails(@PathVariable String id, Model model) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);

        model.addAttribute("employeeDetails", employeeDTO);

        return "employee-details";
    }

    @PostMapping("/{id}")
    public String deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);

        return "redirect:/employees/all";
    }


}
