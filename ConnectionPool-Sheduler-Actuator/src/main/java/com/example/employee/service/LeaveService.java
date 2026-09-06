package com.example.employee.service;

import com.example.employee.entity.Leave;
import com.example.employee.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public Leave applyLeave(Leave leave) {
        leave.setStatus("PENDING");
        return leaveRepository.save(leave);
    }

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public List<Leave> getLeavesByEmployee(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public Leave updateStatus(Long id, String status) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(status);
        return leaveRepository.save(leave);
    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
}
