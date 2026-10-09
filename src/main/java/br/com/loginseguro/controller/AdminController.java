package br.com.loginseguro.controller;

import br.com.loginseguro.dto.UserAdminRequest;
import br.com.loginseguro.model.Role;
import br.com.loginseguro.model.User;
import br.com.loginseguro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminController {
    private final UserService service;
    public AdminController(UserService service){this.service=service;}
    @GetMapping public String list(Model m){m.addAttribute("users",service.findAll()); return "themes/default/admin/users";}
    @GetMapping("/new") public String create(Model m){m.addAttribute("form",new UserAdminRequest()); m.addAttribute("roles",Role.values()); return "themes/default/admin/form";}
    @PostMapping public String create(@Valid UserAdminRequest form, BindingResult result, @RequestParam String password, Model m){
        if(result.hasErrors()){m.addAttribute("roles",Role.values()); return "themes/default/admin/form";}
        try{service.createByAdmin(form,password); return "redirect:/admin/users?success";}catch(IllegalArgumentException e){m.addAttribute("error",e.getMessage());m.addAttribute("roles",Role.values());return "themes/default/admin/form";}
    }
    @GetMapping("/{id}/edit") public String edit(@PathVariable String id, Model m){
        User u=service.findById(id); UserAdminRequest f=new UserAdminRequest(); f.setUsername(u.getUsername()); f.setEmail(u.getEmail()); f.setFullName(u.getFullName()); f.setRoles(u.getRoles()); f.setEnabled(u.isEnabled());
        m.addAttribute("form",f);m.addAttribute("roles",Role.values());m.addAttribute("id",id);return "themes/default/admin/form";
    }
    @PostMapping("/{id}") public String update(@PathVariable String id,@Valid UserAdminRequest form,BindingResult result,Model m){
        if(result.hasErrors()){m.addAttribute("roles",Role.values());m.addAttribute("id",id);return "themes/default/admin/form";}
        try{service.update(id,form);return "redirect:/admin/users?updated";}catch(IllegalArgumentException e){m.addAttribute("error",e.getMessage());m.addAttribute("roles",Role.values());m.addAttribute("id",id);return "themes/default/admin/form";}
    }
    @PostMapping("/{id}/delete") public String delete(@PathVariable String id){service.delete(id);return "redirect:/admin/users?deleted";}
}
