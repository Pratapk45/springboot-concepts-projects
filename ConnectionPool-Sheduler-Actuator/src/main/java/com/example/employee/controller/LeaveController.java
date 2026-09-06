package com.example.employee.controller;

import com.example.employee.entity.Leave;
import com.example.employee.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public Leave applyLeave(@RequestBody Leave leave) {
        return leaveService.applyLeave(leave);
    }

    @GetMapping
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Leave> getLeavesByEmployee(@PathVariable Long employeeId) {
        return leaveService.getLeavesByEmployee(employeeId);
    }

    @PutMapping("/{id}/status")
    public Leave updateStatus(@PathVariable Long id, @RequestParam String status) {
        return leaveService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return "Leave deleted successfully";
    }
}
