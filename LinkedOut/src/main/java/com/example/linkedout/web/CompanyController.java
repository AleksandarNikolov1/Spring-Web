package com.example.linkedout.web;

import com.example.linkedout.models.dtos.CompanyDTO;
import com.example.linkedout.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    public String showAllCompanies(Model model) {
        List<CompanyDTO> companyDTOList = companyService.getAllCompanies();

        model.addAttribute("companies", companyDTOList);

        return "company-all";
    }

    @PostMapping("/add")
    public String addCompany(@Valid @ModelAttribute("addCompanyModel") CompanyDTO companyDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCompanyModel", companyDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCompanyModel", bindingResult);

            return "redirect:/companies/add";
        }

        companyService.addCompany(companyDTO);

        redirectAttributes.addFlashAttribute("successMessage", "Company added successfully!");

        return "redirect:/companies/add";
    }

    @GetMapping("/add")
    public String showAddCompanyPage(Model model) {
        if (!model.containsAttribute("addCompanyModel")) {
            model.addAttribute("addCompanyModel", new CompanyDTO());
        }

        return "company-add";
    }

    @GetMapping("/details/{id}")
    public String showCompanyDetails(@PathVariable String id, Model model) {
        CompanyDTO companyDTO = companyService.getCompanyById(id);

        model.addAttribute("companyDetails", companyDTO);

        return "company-details";
    }

    @PostMapping("/details/{id}")
    public String deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);

        return "redirect:/companies/all";
    }
}
